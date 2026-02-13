package First.fargo_soul.common.entity.livingEntity;

import First.fargo_soul.register.EntityRegister;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.hoglin.HoglinBase;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;

public class Mutant extends Monster implements Enemy {

    public Mutant(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 10000;
    }

    protected static final ImmutableList<? extends SensorType<? extends Sensor<? super Mutant>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES,
            SensorType.NEAREST_PLAYERS
    );

    protected static final ImmutableList<? extends MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
            MemoryModuleType.NEAREST_LIVING_ENTITIES,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER,
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.PATH,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.ATTACK_COOLING_DOWN
    );

    @Override
    protected Brain.@NotNull Provider<Mutant> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected @NotNull Brain<Mutant> makeBrain(@NotNull Dynamic<?> dynamic) {
        Brain<Mutant> brain = this.brainProvider().makeBrain(dynamic);
        brain.addActivity(
                Activity.CORE,
                0,
                ImmutableList.of(
                        new LookAtTargetSink(45, 90),
                        new MoveToTargetSink()
                ));
        brain.addActivity(
                Activity.IDLE,
                10,
                ImmutableList.of(
                        StartAttacking.create(mutant -> mutant.getBrain()
                                .getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)
                                .orElse(NearestVisibleLivingEntities.empty())
                                .findClosest(this::isTargetable)),
                        SetEntityLookTargetSometimes.create(8.0F, UniformInt.of(30, 60)),
                        new RunOne<>(
                                ImmutableList.of(
                                        Pair.of(RandomStroll.stroll(0.4F), 2),
                                        Pair.of(SetWalkTargetFromLookTarget.create(0.4F, 3), 2),
                                        Pair.of(new DoNothing(30, 60), 1)
                                )
                        )
                )
        );
        brain.addActivityAndRemoveMemoryWhenStopped(
                Activity.FIGHT,
                10,
                ImmutableList.of(
                        BehaviorBuilder.create(instance ->
                                instance.group(
                                        instance.registered(MemoryModuleType.WALK_TARGET),
                                        instance.registered(MemoryModuleType.LOOK_TARGET),
                                        instance.present(MemoryModuleType.ATTACK_TARGET),
                                        instance.registered(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)
                                ).apply(instance, (
                                        walkTargetMemory,
                                        positionTrackerMemory,
                                        livingEntityMemory,
                                        nearestVisibleLivingEntitiesMemory) ->
                                        (level, living, time) -> {
                                            LivingEntity target = instance.get(livingEntityMemory);
                                            Optional<NearestVisibleLivingEntities> nearestVisibleLivingEntities = instance.tryGet(nearestVisibleLivingEntitiesMemory);
                                            if (nearestVisibleLivingEntities.isPresent() && nearestVisibleLivingEntities.get().contains(target) && BehaviorUtils.isWithinAttackRange(living, target, 1)) {
                                                walkTargetMemory.erase();
                                            } else {
                                                positionTrackerMemory.set(new EntityTracker(target, true));
                                                walkTargetMemory.set(new WalkTarget(new EntityTracker(target, false), 1, 0));
                                            }
                                            return true;
                                        })
                        ),
                        SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(1.0F),
                        MeleeAttack.create(40),
                        StopAttackingIfTargetInvalid.create()
                ),
                MemoryModuleType.ATTACK_TARGET
        );
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    private Optional<? extends LivingEntity> findNearestValidAttackTarget() {
        return this.getBrain()
                .getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)
                .orElse(NearestVisibleLivingEntities.empty())
                .findClosest(this::isTargetable);
    }

    private boolean isTargetable(LivingEntity living) {
        return living.getType() != this.getType() && Sensor.isEntityAttackable(this, living);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 40.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6F)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0)
                .add(Attributes.ATTACK_DAMAGE, 6.0);
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
        if (entity instanceof LivingEntity target) {
            //this.attackAnimationRemainingTicks = 10;  // 触发攻击动画
            this.level().broadcastEntityEvent(this, (byte) 4);
            this.makeSound(SoundEvents.ZOGLIN_ATTACK);
            return HoglinBase.hurtAndThrowTarget(this, target);
        }
        return false;
    }

    @Override
    protected void blockedByShield(@NotNull LivingEntity target) {
        HoglinBase.throwTarget(this, target);
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        boolean flag = super.hurt(source, amount);
        if (!level().isClientSide() && flag && source.getEntity() instanceof LivingEntity attacker) {
            if (this.canAttack(attacker) && !BehaviorUtils.isOtherTargetMuchFurtherAwayThanCurrentAttackTarget(this, attacker, 4.0)) {
                this.setAttackTarget(attacker);
            }
            return true;
        }
        return false;
    }

    private void setAttackTarget(LivingEntity target) {
        this.brain.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        this.brain.setMemoryWithExpiry(MemoryModuleType.ATTACK_TARGET, target, 200L);  // 记忆200刻
    }

    @SuppressWarnings("unchecked")
    @Override
    public @NotNull Brain<Mutant> getBrain() {
        return (Brain<Mutant>) brain;
    }

    protected void updateActivity() {
        Activity activity = this.brain.getActiveNonCoreActivity().orElse(null);
        this.brain.setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
        Activity activity1 = this.brain.getActiveNonCoreActivity().orElse(null);
        if (activity1 == Activity.FIGHT && activity != Activity.FIGHT) {
            this.playAngrySound();
        }
        this.setAggressive(this.brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET));
    }

    @Override
    protected void customServerAiStep() {
        this.level().getProfiler().push("mutantBrain");
        this.getBrain().tick((ServerLevel) this.level(), this);  // 大脑tick
        this.level().getProfiler().pop();
        this.updateActivity();  // 更新活动
    }

    @Override
    public void aiStep() {
        /*
        if (this.attackAnimationRemainingTicks > 0) {
            this.attackAnimationRemainingTicks--;  // 攻击动画计时
        }
        */
        super.aiStep();
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 4) {
            //this.attackAnimationRemainingTicks = 10;  // 播放攻击动画
            this.makeSound(SoundEvents.ZOGLIN_ATTACK);
        } else {
            super.handleEntityEvent(id);
        }
    }

    public int getAttackAnimationRemainingTicks() {
        return 0;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (!this.level().isClientSide()) {
            return this.brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET) ? SoundEvents.ZOGLIN_ANGRY : SoundEvents.ZOGLIN_AMBIENT;
        } else {
            return null;
        }
    }

    @Override
    protected @NotNull SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return SoundEvents.ZOGLIN_HURT;
    }

    @Override
    protected @NotNull SoundEvent getDeathSound() {
        return SoundEvents.ZOGLIN_DEATH;
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState block) {
        this.playSound(SoundEvents.ZOGLIN_STEP, 0.15F, 1.0F);
    }

    protected void playAngrySound() {
        this.makeSound(SoundEvents.ZOGLIN_ANGRY);
    }

    @Nullable
    @Override
    public LivingEntity getTarget() {
        return this.getTargetFromBrain();
    }

}

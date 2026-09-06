package first.fargo_soul.common.entity;

import first.fargo_soul.common.attachment.SoulTargetCache;
import first.fargo_soul.register.FargoSoulMobEffectRegister;
import first.fargo_soul.register.SummonerAttachmentEntityRegister;
import first.lyra.common.attachment.InvincibleData;
import first.lyra.common.entity.AttachmentEntity;
import first.lyra.common.entity.AttachmentEntityType;
import first.lyra.common.entity.IEntityCollision;
import first.lyra.common.projectile.Projectile;
import first.lyra.utils.ParticleHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SnowBall extends Projectile implements IEntityCollision<SnowBall> {

    public SnowBall() {
        super();
    }

    public SnowBall(DamageSource damageSource, Vec3 startPos, Vec3 direction) {
        super(startPos, direction);
        setDamageSource(damageSource);
        setDrag(0.97f);
        setMaxSpeed(1.6f);
        setMaxLife(60);
    }

    @Override
    public void tick() {
        Level level = owner.level();
        if (!level.isClientSide()) {
            Vec3 pos = getPos();
            ParticleHelper.create(level)
                    .type(ParticleTypes.ITEM_SNOWBALL)
                    .pos(pos)
                    .offset(0.2)
                    .count(2)
                    .speed(0.5)
                    .spread(Math.TAU)
                    .emit();
        }
        super.tick();
    }

    @Override
    public AttachmentEntityType<? extends AttachmentEntity> getType() {
        return SummonerAttachmentEntityRegister.SNOW_BALL.get();
    }

    @Override
    public @NotNull AABB getHitbox() {
        return new AABB(-0.1, -0.1, -0.1, 0.1, 0.1, 0.1);
    }

    @Override
    public boolean isValidCollisionTarget(SnowBall entity, LivingEntity target) {
        return SoulTargetCache.isTarget(owner, target);
    }

    @Override
    public void onCollisionAttack(List<HitContext> hitContexts) {
        DamageSource source = getDamageSource();
        HitContext first = hitContexts.getFirst();
        LivingEntity living = first.entity();
        living.addEffect(new MobEffectInstance(FargoSoulMobEffectRegister.Chill, 70));
        if (source != null) {
            InvincibleData.attack(living)
                    .attacker(getUuid())
                    .damageSource(source)
                    .damageAmount(getDamage())
                    .invincibleTime(5)
                    .apply();
        }
        setCurrentPathNode(getCollisionAfterPathNode(first.hitPoint(), getCurrentPathNode()));
        setRemove();
    }
}
package first.fargo_soul.common.entity;

import first.fargo_soul.common.attachment.SoulTargetCache;
import first.fargo_soul.register.FargoSoulMobEffectRegister;
import first.fargo_soul.register.SummonerAttachmentEntityRegister;
import first.lyra.common.attachment.InvincibleData;
import first.lyra.common.entity.AttachmentEntity;
import first.lyra.common.entity.AttachmentEntityType;
import first.lyra.common.entity.IEntityCollision;
import first.lyra.common.particle.genericParticle.GenericParticleBuilder;
import first.lyra.common.projectile.Projectile;
import first.lyra.utils.ParticleHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Star extends Projectile implements IEntityCollision<Star> {

    private LivingEntity chaseTarget = null;

    public Star() {
        super();
    }

    public Star(DamageSource damageSource, Vec3 startPos, Vec3 direction) {
        super(startPos, direction);
        setDamageSource(damageSource);
        setDrag(0.8f);
        setMaxSpeed(5f);
        setMaxLife(100);
    }

    @Override
    public AttachmentEntityType<? extends AttachmentEntity> getType() {
        return SummonerAttachmentEntityRegister.STAR.get();
    }

    @Override
    public @NotNull AABB getHitbox() {
        return new AABB(-0.1, -0.1, -0.1, 0.1, 0.1, 0.1);
    }

    @Override
    public boolean isValidCollisionTarget(Star entity, LivingEntity target) {
        return target == chaseTarget;
    }

    @Override
    public void onCollisionAttack(List<HitContext> hitContexts) {
        DamageSource source = getDamageSource();
        if (source != null) {
            Vec3 pos = getPos();
            List<LivingEntity> entities = SoulTargetCache.get(owner).getEntitiesInRadius(pos, 3, null);
            for (LivingEntity entity : entities) {
                InvincibleData.attack(entity)
                        .attacker(getUuid())
                        .damageSource(source)
                        .damageAmount(getDamage())
                        .invincibleTime(5)
                        .apply();
            }
            owner.addEffect(new MobEffectInstance(FargoSoulMobEffectRegister.Starlight, 200));
            ParticleHelper.create(owner.level())
                    .generic(GenericParticleBuilder.create()
                                     .centerColor(0xe7db09)
                                     .edgeColor(0xe0681e)
                                     .lifetime(10)
                                     .lifetimeRandom(20)
                                     .spin(0.3f)
                                     .spinRandom(0.05F)
                                     .friction(0.85F)
                                     .scale(0.025f)
                                     .scaleRandom(0.005f)
                    )
                    .pos(pos)
                    .offset(0.15)
                    .velocity(pos.offsetRandom(owner.getRandom(), 5).subtract(pos).normalize())
                    .count(80)
                    .speed(0.35)
                    .spread(Math.TAU)
                    .emit();
        }
        setCurrentPathNode(getCollisionAfterPathNode(hitContexts.getFirst().hitPoint(),getCurrentPathNode()));
        setRemove();
    }

    @Override
    public void tick() {
        if (!owner.level().isClientSide()) {
            if (chaseTarget != null && chaseTarget.isAlive()) {
                Vec3 targetCenter = chaseTarget.getBoundingBox().getCenter();
                applyForce(targetCenter.subtract(getPos()).normalize().scale(0.6));
            } else {
                setRemove();
            }
        }
        super.tick();
    }

    @Override
    public int getTrailDuration() {
        return 12;
    }

    public void setChaseTarget(LivingEntity chaseTarget) {
        this.chaseTarget = chaseTarget;
    }
}
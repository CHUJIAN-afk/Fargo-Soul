package first.fargo_soul.common.entity;

import first.fargo_soul.common.attachment.SoulTargetCache;
import first.fargo_soul.register.FargoSoulMobEffectRegister;
import first.fargo_soul.register.SummonerAttachmentEntityRegister;
import first.lyra.common.attachmentEntity.IEntityCollision;
import first.lyra.common.particle.genericParticle.GenericParticleBuilder;
import first.lyra.utils.ParticleHelper;
import first.lyra.common.projectile.Projectile;
import net.minecraft.world.entity.player.Player;
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
        super(SummonerAttachmentEntityRegister.STAR);
        setGravity(0);
        setDrag(0.8f);
        setMaxTickCount(100);
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
        Vec3 pos = getPos();
        List<LivingEntity> entities = SoulTargetCache.get(owner).getEntitiesInRadius(pos, 3, null);
        for (LivingEntity entity : entities) {
            attack(entity, getDamage(), 5);
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
        setCurrentPathNode(getCurrentPathNode().modifyPos(hitContexts.getFirst().hitPoint()));
        setRemove();
    }

    @Override
    public void tick() {
        super.tick();
        if (chaseTarget != null && chaseTarget.isAlive()) {
            Vec3 targetCenter = chaseTarget.getBoundingBox().getCenter();
            addVelocity(targetCenter.subtract(getPos()).normalize().scale(0.6));
        } else {
            setRemove();
        }
    }

    public int getTrailDuration() {
        return 12;
    }

    public void setChaseTarget(LivingEntity chaseTarget) {
        this.chaseTarget = chaseTarget;
    }

    @Override
    public DamageSource getDamageSource() {
        Player owner = getOwner();
        return owner.damageSources().playerAttack(owner);
    }
}

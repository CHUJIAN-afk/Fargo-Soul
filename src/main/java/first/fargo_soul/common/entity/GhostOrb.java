package first.fargo_soul.common.entity;

import first.fargo_soul.register.SummonerAttachmentEntityRegister;
import first.lyra.common.attachment.InvincibleData;
import first.lyra.common.entity.AttachmentEntity;
import first.lyra.common.entity.AttachmentEntityType;
import first.lyra.common.entity.IEntityCollision;
import first.lyra.common.projectile.Projectile;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GhostOrb extends Projectile implements IEntityCollision<GhostOrb> {

    private LivingEntity chaseTarget = null;

    public GhostOrb() {
        super();
    }

    public GhostOrb(DamageSource damageSource, Vec3 startPos, Vec3 direction) {
        super(startPos, direction);
        setDamageSource(damageSource);
        setDrag(0.8f);
        setMaxSpeed(4f);
        setMaxLife(80);
    }

    @Override
    public AttachmentEntityType<? extends AttachmentEntity> getType() {
        return SummonerAttachmentEntityRegister.GHOST_ORB.get();
    }

    @Override
    public @NotNull AABB getHitbox() {
        return new AABB(-0.05, -0.05, -0.05, 0.05, 0.05, 0.05);
    }

    @Override
    public boolean isValidCollisionTarget(GhostOrb entity, LivingEntity target) {
        return target == chaseTarget;
    }

    @Override
    public void onCollisionAttack(List<HitContext> hitContexts) {
        DamageSource source = getDamageSource();
        if (source != null) {
            for (HitContext context : hitContexts) {
                InvincibleData.attack(context.entity())
                        .attacker(getUuid())
                        .damageSource(source)
                        .damageAmount(getDamage())
                        .invincibleTime(5)
                        .apply();
            }
        }
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
        return 8;
    }

    public void setChaseTarget(LivingEntity chaseTarget) {
        this.chaseTarget = chaseTarget;
    }
}
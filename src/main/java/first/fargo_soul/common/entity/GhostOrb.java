package first.fargo_soul.common.entity;

import first.fargo_soul.common.attachment.SoulTargetCache;
import first.fargo_soul.register.SummonerAttachmentEntityRegister;
import first.lyra.common.attachmentEntity.IEntityCollision;
import first.lyra.common.projectile.Projectile;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GhostOrb extends Projectile implements IEntityCollision<GhostOrb> {

    private LivingEntity chaseTarget = null;

    public GhostOrb() {
        super(SummonerAttachmentEntityRegister.GHOST_ORB);
        setGravity(0);
        setDrag(0.8f);
        setMaxTickCount(80);
    }

    @Override
    public @NotNull AABB getHitbox() {
        return new AABB(-0.05, -0.05, -0.05, 0.05, 0.05, 0.05);
    }

    @Override
    public boolean isValidCollisionTarget(GhostOrb entity, LivingEntity target) {
        return SoulTargetCache.isTarget(owner, target);
    }

    @Override
    public void onCollisionAttack(List<HitContext> hitContexts) {
        attack(hitContexts.getFirst().entity(), getDamage(), 5);
        setRemove();
    }

    @Override
    public void tick() {
        super.tick();
        if (chaseTarget != null && chaseTarget.isAlive()) {
            if (getTickCount() > 5) {
                Vec3 targetCenter = chaseTarget.getBoundingBox().getCenter();
                addVelocity(targetCenter.subtract(getPos()).normalize().scale(0.6));
            }
        } else {
            setRemove();
        }
    }

    public int getTrailDuration() {
        return 8;
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

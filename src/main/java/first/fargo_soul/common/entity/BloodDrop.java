package first.fargo_soul.common.entity;

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

public class BloodDrop extends Projectile implements IEntityCollision<BloodDrop> {

    private LivingEntity chaseTarget = null;

    public BloodDrop() {
        super(SummonerAttachmentEntityRegister.BLOOD_DROP);
        setGravity(0);
        setDrag(0.8f);
        setMaxTickCount(60);
    }

    @Override
    public @NotNull AABB getHitbox() {
        return new AABB(-0.04, -0.04, -0.04, 0.04, 0.04, 0.04);
    }

    @Override
    public boolean isValidCollisionTarget(BloodDrop entity, LivingEntity target) {
        return target == chaseTarget;
    }

    @Override
    public void onCollisionAttack(List<HitContext> hitContexts) {
        HitContext first = hitContexts.getFirst();
        attack(first.entity(), getDamage(), 5);
        setCurrentPathNode(getCurrentPathNode().modifyPos(first.hitPoint()));
        setRemove();
    }

    @Override
    public void tick() {
        super.tick();
        if (chaseTarget != null && chaseTarget.isAlive()) {
            if (getTickCount() > 10) {
                Vec3 targetCenter = chaseTarget.getBoundingBox().getCenter();
                addVelocity(targetCenter.subtract(getPos()).normalize().scale(0.5));
            }
        } else {
            setRemove();
        }
    }

    public int getTrailDuration() {
        return 4;
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

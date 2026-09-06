package first.fargo_soul.common.entity;

import first.fargo_soul.register.SummonerAttachmentEntityRegister;
import first.lyra.common.entity.AttachmentEntity;
import first.lyra.common.entity.AttachmentEntityType;
import first.lyra.common.entity.IEntityCollision;
import first.lyra.common.entity.PathNode;
import first.lyra.common.projectile.Projectile;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TrackingBlood extends Projectile implements IEntityCollision<TrackingBlood> {

    public TrackingBlood() {
        super();
    }

    public TrackingBlood(Vec3 startPos, Vec3 direction) {
        super(startPos, direction);
        setDrag(0.75f);
        setMaxSpeed(3.5f);
        setMaxLife(60);
    }

    @Override
    public AttachmentEntityType<? extends AttachmentEntity> getType() {
        return SummonerAttachmentEntityRegister.TRACKING_BLOOD.get();
    }

    @Override
    public void tick() {
        if (!owner.level().isClientSide()) {
            Vec3 targetCenter = owner.getBoundingBox().getCenter();
            applyForce(targetCenter.subtract(getPos()).normalize().scale(0.25));
            if (targetCenter.distanceTo(getPos()) < owner.getBoundingBox().getSize()) {
                owner.heal(3.0f);
                PathNode pathNode = getCurrentPathNode();
                setCurrentPathNode(new PathNode(targetCenter, pathNode.yaw(), pathNode.pitch(), pathNode.roll()));
                setVelocity(Vec3.ZERO);
                setRemove();
            }
        }
        super.tick();
    }

    @Override
    public @NotNull AABB getHitbox() {
        return new AABB(-0.06, -0.06, -0.06, 0.06, 0.06, 0.06);
    }

    @Override
    public boolean canCollideAttack() {
        return false;
    }

    @Override
    public boolean isValidCollisionTarget(TrackingBlood entity, LivingEntity target) {
        return false;
    }

    @Override
    public void onCollisionAttack(List<HitContext> hitContexts) {
        setCurrentPathNode(getCollisionAfterPathNode(hitContexts.getFirst().hitPoint(), getCurrentPathNode()));
    }

    @Override
    public int getTrailDuration() {
        return 8;
    }
}
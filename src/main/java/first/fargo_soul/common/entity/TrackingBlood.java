package first.fargo_soul.common.entity;

import first.fargo_soul.register.SummonerAttachmentEntityRegister;
import first.lyra.common.entity.PathNode;
import first.lyra.common.projectile.Projectile;
import net.minecraft.world.phys.Vec3;

public class TrackingBlood extends Projectile {

    public TrackingBlood() {
        super(SummonerAttachmentEntityRegister.TRACKING_BLOOD);
        setDrag(0.75f);
        setMaxLife(60);
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
}

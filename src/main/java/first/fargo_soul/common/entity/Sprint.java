package first.fargo_soul.common.entity;

import first.fargo_soul.common.attachment.SoulItemData;
import first.fargo_soul.common.attachment.SoulTargetCache;
import first.fargo_soul.common.item.base.SoulItem;
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

import java.util.*;

public class Sprint extends Projectile implements IEntityCollision<Sprint> {

    private final Map<SoulItem, Set<LivingEntity>> hits = new HashMap<>();

    public Sprint() {
        super();
    }

    public Sprint(Vec3 pos) {
        super(pos, null);
        setMaxLife(10);
    }

    @Override
    public AttachmentEntityType<? extends AttachmentEntity> getType() {
        return SummonerAttachmentEntityRegister.SPRINT.get();
    }

    @Override
    public void tick() {
        if (!owner.level().isClientSide()) {
            setCurrentPathNode(new PathNode(owner.getBoundingBox().getCenter(), 0, 0, 0));
        }
        super.tick();
    }

    @Override
    protected void tickPhysics() {
    }

    @Override
    public @NotNull AABB getHitbox() {
        return new AABB(-0.3, 0, -0.3, 0.3, 1.8, 0.3);
    }

    @Override
    public boolean isValidCollisionTarget(Sprint entity, LivingEntity target) {
        return SoulTargetCache.isTarget(owner, target);
    }

    @Override
    public void onCollisionAttack(List<HitContext> list) {
        SoulItemData.forEach(owner, soulItem -> soulItem.sprintCollision(owner, list, hits.computeIfAbsent(soulItem, key -> new HashSet<>())));
    }
}

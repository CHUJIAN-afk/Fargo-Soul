package first.fargo_soul.common.entity;

import first.fargo_soul.common.attachment.SoulItemData;
import first.fargo_soul.register.FargoSoulItemRegister;
import first.fargo_soul.register.SummonerAttachmentEntityRegister;
import first.lyra.api.LyraHelper;
import first.lyra.common.attachmentEntity.IEntityCollision;
import first.lyra.common.attachmentEntity.PathNode;
import first.lyra.common.minion.Minion;
import first.lyra.common.minion.MinionSlotType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ShadowOrb extends Minion implements IEntityCollision<ShadowOrb> {

    public ShadowOrb() {
        super(SummonerAttachmentEntityRegister.SHADOW_ORB);
        setSlotCost(0);
        setSlotType(MinionSlotType.None);
    }

    @Override
    public void tick() {
        super.tick();
        if (!SoulItemData.isEquipped(owner, FargoSoulItemRegister.AncientShadowSoulItem)) {
            setRemove();
            return;
        }
        setCurrentPathNode(getOrbitNode(1.0f));
    }

    @Override
    public int getOrder() {
        return LyraHelper.get(owner).getEntityData().get(getType()).indexOf(this);
    }

    @Override
    public int getSameSize() {
        return LyraHelper.get(owner).getEntityData().get(getType()).size();
    }

    /**
     * 计算环绕位置：按同组内顺序均分角度，绕玩家腰部高度旋转。
     * 客户端同步使用同一公式插值渲染。
     */
    public PathNode getOrbitNode(float partialTick) {
        Player owner = getOwner();
        int total = Math.max(1, getSameSize());
        int order = getOrder();
        double angle = (owner.tickCount + partialTick) * 0.08f + order * Mth.TWO_PI / total;

        double px = Mth.lerp(partialTick, owner.xo, owner.getX());
        double py = Mth.lerp(partialTick, owner.yo, owner.getY());
        double pz = Mth.lerp(partialTick, owner.zo, owner.getZ());

        Vec3 pos = new Vec3(px, py, pz).add(Math.cos(angle) * 1.8f, owner.getBbHeight() * 0.5, Math.sin(angle) * 1.8f);
        return new PathNode(pos, 0, 0, 0);
    }

    @Override
    public PathNode getRenderNode(float partialTick) {
        return getOrbitNode(partialTick);
    }

    @Override
    public int getSearchDistance() {
        return 0;
    }

    @Override
    public @NotNull AABB getHitbox() {
        return new AABB(-0.2, -0.2, -0.2, 0.2, 0.2, 0.2);
    }

    @Override
    public void onCollisionAttack(List<HitContext> list) {
        for (HitContext hitContext : list) {
            attack(hitContext.entity(), 5, 2);
        }
    }
}

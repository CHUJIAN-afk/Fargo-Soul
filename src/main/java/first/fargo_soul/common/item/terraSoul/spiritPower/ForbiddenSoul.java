package first.fargo_soul.common.item.terraSoul.spiritPower;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.attachment.SoulInfoData;
import first.fargo_soul.common.attachment.SoulItemData;
import first.fargo_soul.common.entity.Vortex;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.soulInfo.SoulInfo;
import first.fargo_soul.common.soulInfo.SoulInfoType;
import first.fargo_soul.register.FargoSoulItemRegister;
import first.fargo_soul.register.FargoSoulKeyRegister;
import first.fargo_soul.register.FargoSoulSoulInfoRegister;
import first.lyra.api.LyraHelper;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.core.HolderLookup;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.nbt.CompoundTag;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.resources.ResourceLocation;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.entity.LivingEntity;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.entity.player.Player;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.level.ClipContext;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.phys.BlockHitResult;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.phys.HitResult;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ForbiddenSoul extends SoulItem {

    public ForbiddenSoul(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable ResourceLocation keyPressed(Player player, int key) {
        if (FargoSoulKeyRegister.ForbiddenKey.getKey().getValue() == key) {
            return FargoSoul.rl("forbidden_storm");
        }
        return super.keyPressed(player, key);
    }

    @Override
    public void keyHandle(Player player, ResourceLocation location) {
        if (location.equals(FargoSoul.rl("forbidden_storm"))) {
            Info info = SoulInfoData.getSoulInfo(player, FargoSoulSoulInfoRegister.FORBIDDEN_SOUL_INFO);
            if (info == null) {
                info = new Info();
                SoulInfoData.putSoulInfo(player, info);
            }
            if (info.cooldown <= 0) {
                info.cooldown = 600;
                Vec3 eye = player.getEyePosition();
                Vec3 end = eye.add(player.getLookAngle().scale(64));
                BlockHitResult hit = player.level().clip(new ClipContext(eye, end, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
                Vec3 pos = hit.getType() == HitResult.Type.MISS ? eye.add(player.getLookAngle().scale(10)) : hit.getLocation().add(hit.getDirection().getStepX(), hit.getDirection().getStepY(), hit.getDirection().getStepZ());
                Vortex vortex = new Vortex();
                vortex.init(new PathNode(pos, 0, 0, 0));
                vortex.setDamage(0);
                vortex.setOwner(player);
                LyraHelper.get(player).add(vortex);
            }
        }
    }

    public static final class Info extends SoulInfo {

        public int cooldown = 0;

        @Override
        public SoulInfoType<? extends SoulInfo> getType() {
            return FargoSoulSoulInfoRegister.FORBIDDEN_SOUL_INFO.get();
        }

        @Override
        public void tick(LivingEntity living) {
            if (SoulItemData.isEquipped(living, FargoSoulItemRegister.ForbiddenSoulItem.get())) {
                if (cooldown > 0) {
                    cooldown--;
                }
            } else {
                setRemove(true);
            }
        }

        @Override
        public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
            CompoundTag tag = super.serializeNBT(provider);
            tag.putInt("cooldown", cooldown);
            return tag;
        }

        @Override
        public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag tag) {
            cooldown = tag.getInt("cooldown");
        }
    }
}

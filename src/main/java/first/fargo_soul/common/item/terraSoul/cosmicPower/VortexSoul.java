package first.fargo_soul.common.item.terraSoul.cosmicPower;

import com.mojang.blaze3d.platform.InputConstants;
import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.attachment.SoulInfoData;
import first.fargo_soul.common.entity.Vortex;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.soulInfo.SoulInfo;
import first.fargo_soul.common.soulInfo.SoulInfoType;
import first.fargo_soul.register.FargoSoulKeyRegister;
import first.fargo_soul.register.FargoSoulSoulInfoRegister;
import first.lyra.api.LyraHelper;
import first.lyra.common.sound.Playable;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.core.BlockPos;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.core.Direction;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.core.HolderLookup;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.nbt.CompoundTag;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.resources.ResourceLocation;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.sounds.SoundEvents;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.entity.LivingEntity;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.entity.player.Player;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.level.ClipContext;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.level.Level;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.level.block.Blocks;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.phys.BlockHitResult;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.phys.HitResult;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class VortexSoul extends SoulItem {

    public VortexSoul(Properties properties) {
        super(properties);
    }

    @Override
    public ResourceLocation keyPressed(Player player, int key) {
        InputConstants.Key keyKey = FargoSoulKeyRegister.VortexSoulKey.getKey();
        if (key == keyKey.getValue()) {
            return FargoSoul.rl("vortex_soul");
        }
        return null;
    }

    @Override
    public void keyHandle(Player player, ResourceLocation key) {
        if (key.equals(FargoSoul.rl("vortex_soul"))) {
            Info info = SoulInfoData.getSoulInfo(player, FargoSoulSoulInfoRegister.VORTEX_SOUL_INFO);
            if (info == null) {
                info = new Info();
                info.cooldown = 20;
                SoulInfoData.putSoulInfo(player, info);
                Level level = player.level();
                ClipContext clipContext = new ClipContext(player.getEyePosition(), player.getEyePosition().add(player.getLookAngle().scale(256)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player);
                HitResult hitResult = player.level().clip(clipContext);
                if (hitResult instanceof BlockHitResult blockHitResult) {
                    BlockPos pos = blockHitResult.getBlockPos();
                    if (!level.getBlockState(pos).is(Blocks.AIR) && pos.getY() > level.getMinBuildHeight()) {
                        Direction hitFace = blockHitResult.getDirection();
                        double adjustX = pos.getX() + hitFace.getStepX();
                        double adjustY = pos.getY() + hitFace.getStepY();
                        double adjustZ = pos.getZ() + hitFace.getStepZ();
                        player.teleportTo(adjustX, adjustY, adjustZ);
                        Playable.play(SoundEvents.ENDERMAN_TELEPORT, level, player.position(), player.getSoundSource());
                        Vortex vortex = new Vortex();
                        vortex.init(new PathNode(new Vec3(adjustX, adjustY, adjustZ), 0, 0, 0));
                        vortex.setDamage(0.5f);
                        vortex.setOwner(player);
                        LyraHelper.get(player).add(vortex);
                    }
                }
            }
        }
    }

    public static final class Info extends SoulInfo {

        public int cooldown = 0;

        @Override
        public SoulInfoType<? extends SoulInfo> getType() {
            return FargoSoulSoulInfoRegister.VORTEX_SOUL_INFO.get();
        }

        @Override
        public void tick(LivingEntity living) {
            if (--cooldown <= 0) {
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

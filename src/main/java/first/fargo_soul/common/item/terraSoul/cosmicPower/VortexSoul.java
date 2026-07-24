package first.fargo_soul.common.item.terraSoul.cosmicPower;

import com.mojang.blaze3d.platform.InputConstants;
import first.fargo_soul.FargoSoul;
import first.fargo_soul.api.SoulInfoHelper;
import first.fargo_soul.common.attachment.soulInfoData.soulInfo.CoolDownSoulInfo;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.register.KeyRegister;
import first.fargo_soul.register.SoulInfoRegister;
import first.fargo_soul.utils.SoulUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class VortexSoul extends SoulItem {

    public VortexSoul(Properties properties) {
        super(properties);
    }

    @Override
    public ResourceLocation keyPressed(Player player, int key) {
        InputConstants.Key keyKey = KeyRegister.VortexSoulKey.getKey();
        if (key == keyKey.getValue()) {
            return FargoSoul.rl("vortex_soul");
        }
        return null;
    }

    @Override
    public void keyHandle(Player player, ResourceLocation key) {
        if (key.equals(FargoSoul.rl("vortex_soul"))) {
            SoulInfoHelper helper = SoulInfoHelper.get(player);
            CoolDownSoulInfo coolDownSoulInfo = helper.getInfo(FargoSoul.rl("stardust_soul"), SoulInfoRegister.COOLDOWN);
            if (coolDownSoulInfo == null) {
                coolDownSoulInfo = new CoolDownSoulInfo();
                coolDownSoulInfo.setCooldown(120 * 20);
                helper.putInfo(FargoSoul.rl("stardust_soul"), coolDownSoulInfo);
                Level level = player.level();
                HitResult hitResult = SoulUtils.getTargetedBlock(player, 512);
                if (hitResult instanceof BlockHitResult blockHitResult){
                    BlockPos pos = blockHitResult.getBlockPos();
                    if (!level.getBlockState(pos).is(Blocks.AIR) && pos.getY() > level.getMinBuildHeight()) {
                        Direction hitFace = blockHitResult.getDirection();
                        double adjustX = pos.getX() + hitFace.getStepX();
                        double adjustY = pos.getY() + hitFace.getStepY();
                        double adjustZ = pos.getZ() + hitFace.getStepZ();
                        player.teleportTo(adjustX, adjustY, adjustZ);
                        SoulUtils.playSound(
                                level,
                                player.position(),
                                SoundEvents.ENDERMAN_TELEPORT,
                                SoundSource.PLAYERS
                        );
                    }
                }
            }
        }
    }
}

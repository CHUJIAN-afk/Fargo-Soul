package first.fargo_soul.common.item.terraSoul.cosmicPower;

import first.fargo_soul.client.gui.SoulGuiRenderManager;
import first.fargo_soul.client.gui.SoulRenderType;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.terraSoul.CosmicPower;
import first.fargo_soul.register.KeyRegister;
import first.fargo_soul.utils.CurioUtils;
import first.fargo_soul.utils.ParticleUtils;
import first.fargo_soul.utils.SoulUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class VortexSoul extends SoulItem {

    public VortexSoul(Properties properties) {
        super();
    }

    @Override
    public void tick(LivingEntity ticker, boolean isClient) {
        if (!ticker.level().isClientSide() && CurioUtils.isEquipped(ticker, VortexSoul.class)) {
            SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(ticker, VortexSoul.class);
            soulInfo.setMaxCooldown(400);
        }
    }

    @Override
    public String keyPressed(Player player, int key) {
        if (key == KeyRegister.VortexSoulKey.getKey().getValue() && CurioUtils.isEquipped(player, VortexSoul.class)) {
            return VortexSoul.class.getSimpleName();
        }
        return null;
    }

    @Override
    public void keyHandle(Player player, String key) {
        if (key.equals(VortexSoul.class.getSimpleName())) {
            Level level = player.level();
            SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(player, VortexSoul.class);
            int maxDistance = CurioUtils.isEquipped(player, CosmicPower.class) ? 1024 : 512;
            HitResult hitResult = SoulUtils.getTargetedBlock(player, maxDistance);
            if (soulInfo.isReady() && hitResult instanceof BlockHitResult blockHitResult) {
                soulInfo.setCooldown(soulInfo.getMaxCooldown());
                BlockPos pos = blockHitResult.getBlockPos();
                if (!level.getBlockState(pos).is(Blocks.AIR) && pos.getY() > level.getMinBuildHeight()) {
                    Direction hitFace = blockHitResult.getDirection();
                    double adjustX = pos.getX() + hitFace.getStepX();
                    double adjustY = pos.getY() + hitFace.getStepY();
                    double adjustZ = pos.getZ() + hitFace.getStepZ();
                    player.teleportTo(adjustX, adjustY, adjustZ);
                    Vec3 blockPos = new Vec3(pos.getX(), pos.getY(), pos.getZ());
                    for (int i = 1; i < 40; i++) {
                        SoulUtils.executorService.schedule(() -> {
                            List<LivingEntity> livingEntityList = level.getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(8), livingEntity -> !livingEntity.equals(player));
                            for (LivingEntity livingEntity : livingEntityList) {
                                Vec3 delta = blockPos.subtract(livingEntity.position()).normalize();
                                livingEntity.addDeltaMovement(delta);
                                livingEntity.hurt(player.damageSources().magic(), 2);
                            }
                            List<ItemEntity> itemEntityList = level.getEntitiesOfClass(ItemEntity.class, new AABB(pos).inflate(8));
                            for (ItemEntity itemEntity : itemEntityList) {
                                Vec3 delta = blockPos.subtract(itemEntity.position()).normalize();
                                itemEntity.addDeltaMovement(delta);
                            }
                            ParticleUtils.spawnParticleSphere((ServerLevel) level, blockPos, ParticleTypes.DUST_PLUME, 7f, 800, 0.2f);
                        }, i * 250, TimeUnit.MILLISECONDS);
                    }
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

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, VortexSoul.class, SoulRenderType.Cooldown);
    }

}

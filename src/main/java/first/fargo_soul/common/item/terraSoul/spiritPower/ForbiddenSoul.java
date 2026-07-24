package first.fargo_soul.common.item.terraSoul.spiritPower;

import first.fargo_soul.client.gui.SoulGuiRenderManager;
import first.fargo_soul.client.gui.SoulRenderType;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.terraSoul.SpiritPower;
import first.fargo_soul.register.KeyRegister;
import first.fargo_soul.utils.CurioUtils;
import first.fargo_soul.utils.ParticleUtils;
import first.fargo_soul.utils.SoulUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ForbiddenSoul extends SoulItem {

    public ForbiddenSoul(Properties properties) {
        super();
    }

    @Override
    public void tick(LivingEntity ticker, boolean isClient) {
        if (!ticker.level().isClientSide()) {
            SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(ticker, ForbiddenSoul.class);
            soulInfo.setMaxCooldown(600);
        }
    }

    @Override
    public String keyPressed(Player player, int key) {
        if (CurioUtils.isEquipped(player, ForbiddenSoul.class) && KeyRegister.ForbiddenKey.getKey().getValue() == key) {
            return ForbiddenSoul.class.getSimpleName();
        }
        return null;
    }

    @Override
    public void keyHandle(Player player, String key) {
        if (key.equals(ForbiddenSoul.class.getSimpleName())) {
            SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(player, ForbiddenSoul.class);
            HitResult hitResult = player.pick(20.0, 0, false);
            if (soulInfo.isReady() && hitResult instanceof BlockHitResult blockHitResult) {
                soulInfo.setCooldown(soulInfo.getMaxCooldown());
                BlockPos pos = blockHitResult.getBlockPos();
                Vec3 blockPos = new Vec3(pos.getX(), pos.getY(), pos.getZ());
                for (int i = 1; i < (CurioUtils.isEquipped(player, SpiritPower.class) ? 8 : 5); i++) {
                    SoulUtils.executorService.schedule(() -> {
                        int value = CurioUtils.isEquipped(player, SpiritPower.class) ? 6 : 4;
                        List<LivingEntity> livingEntityList = player.level().getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(value), livingEntity -> !CurioUtils.isEquipped(livingEntity, ForbiddenSoul.class));
                        for (LivingEntity livingEntity : livingEntityList) {
                            Vec3 delta = blockPos.subtract(livingEntity.position()).normalize();
                            livingEntity.addDeltaMovement(delta);
                        }
                        ParticleUtils.spawnParticleSphere(
                                (ServerLevel) player.level(),
                                blockPos,
                                ParticleTypes.DUST_PLUME,
                                5f,
                                400,
                                0.0f
                        );
                    }, i, TimeUnit.SECONDS);
                }
            }
        }
    }

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, ForbiddenSoul.class, SoulRenderType.Cooldown);
    }

}

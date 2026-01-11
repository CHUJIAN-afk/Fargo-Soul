package First.fargo_soul.item.terraSoul.lifePower;

import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.LifePower;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.ParticleUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.PumpkinBlock;

import java.util.List;

public class PumpkinSoul extends SoulItem {

    public PumpkinSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(LivingEntity ticker) {
        if (!ticker.level().isClientSide()) {
            if (CurioUtils.isEquipped(ticker, PumpkinSoul.class)) {
                Level level = ticker.level();
                BlockPos position = ticker.blockPosition();
                BlockPos below = position.below();
                if (level.getBlockState(below).getBlock() instanceof FarmBlock && level.getBlockState(position).isAir()) {
                    level.setBlockAndUpdate(position, Blocks.PUMPKIN_STEM.defaultBlockState());
                }
                if (level.getBlockState(below).getBlock() instanceof PumpkinBlock) {
                    level.setBlockAndUpdate(position, Blocks.AIR.defaultBlockState());
                    List<LivingEntity> targetList = ticker.level().getEntitiesOfClass(LivingEntity.class, ticker.getBoundingBox().inflate(2));
                    targetList.remove(ticker);
                    for (LivingEntity target : targetList) {
                        SoulUtils.attack(ticker, target, DamageTypes.EXPLOSION, CurioUtils.isEquipped(ticker, LifePower.class) ? 6 : 3);
                        target.spawnAtLocation(Items.PUMPKIN_SEEDS);
                    }
                    ParticleUtils.spawnParticleSphere(
                            (ServerLevel) level,
                            below.getCenter(),
                            ParticleTypes.EXPLOSION,
                            3,
                            10,
                            0.2f
                    );
                    SoulUtils.playSound(
                            level,
                            ticker.position(),
                            SoundEvents.GENERIC_EXPLODE.value(),
                            SoundSource.PLAYERS
                    );
                }
            }
        }
    }

}

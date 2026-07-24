package first.fargo_soul.common.item.terraSoul.lifePower;

import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.terraSoul.LifePower;
import first.fargo_soul.utils.CurioUtils;
import first.fargo_soul.utils.ParticleUtils;
import first.fargo_soul.utils.SoulUtils;
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

public class PumpkinSoul extends SoulItem {

    public PumpkinSoul(Properties properties) {
        super();
    }

    @Override
    public void tick(LivingEntity ticker, boolean isClient) {
        if (!ticker.level().isClientSide()) {
            if (CurioUtils.isEquipped(ticker, PumpkinSoul.class)) {
                Level level = ticker.level();
                BlockPos position = ticker.blockPosition();
                BlockPos below = position.below();
                if (level.getBlockState(below).getBlock() instanceof FarmBlock && level.getBlockState(position).isAir()) {
                    level.setBlockAndUpdate(position, Blocks.PUMPKIN_STEM.defaultBlockState());
                }
                if (level.getBlockState(below).getBlock() instanceof PumpkinBlock) {
                    level.destroyBlock(below, false);
                    for (LivingEntity target : SoulUtils.getTargetList(ticker, 2)) {
                        SoulUtils.attack(this.getClass(), ticker, ticker, target, DamageTypes.EXPLOSION, CurioUtils.isEquipped(ticker, LifePower.class) ? 6 : 3);
                        if (CurioUtils.isEquipped(ticker, LifePower.class)) {
                            SoulUtils.addItemEetity(level, Items.PUMPKIN_SEEDS.getDefaultInstance(), target.getBoundingBox().getCenter());
                        }
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

package First.fargo_soul.Item.Soul.TerraSoul.LifePower.SoulStone;

import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.TerraSoul.LifePower.LifePower;
import First.fargo_soul.Utils.ParticleUtils;
import First.fargo_soul.Utils.SoulUtils;
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
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

public class PumpkinSoul extends SoulItem {

    public PumpkinSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.BLUE));
    }

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Tick(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                if (SoulUtils.isEquipped(attacker, PumpkinSoul.class)) {
                    Level level = attacker.level();
                    BlockPos position = attacker.blockPosition();
                    BlockPos below = position.below();
                    if (level.getBlockState(below).getBlock() instanceof FarmBlock && level.getBlockState(position).isAir()) {
                        level.setBlockAndUpdate(position, Blocks.PUMPKIN_STEM.defaultBlockState());
                    }
                    if (level.getBlockState(below).getBlock() instanceof PumpkinBlock) {
                        level.setBlockAndUpdate(position, Blocks.AIR.defaultBlockState());
                        List<LivingEntity> targetList = attacker.level().getEntitiesOfClass(LivingEntity.class, attacker.getBoundingBox().inflate(2));
                        targetList.remove(attacker);
                        for (LivingEntity target : targetList) {
                            SoulUtils.attack(attacker, target, DamageTypes.EXPLOSION, SoulUtils.isEquipped(attacker, LifePower.class) ? 6 : 3);
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
                                attacker.position(),
                                SoundEvents.GENERIC_EXPLODE.value(),
                                SoundSource.PLAYERS
                        );
                    }
                }
            }
        }
    }

}

package First.fargo_soul.Item.Soul.TerraSoul.LifePower.SoulStone;

import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.TerraSoul.LifePower.LifePower;
import First.fargo_soul.Utils.SoulUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

public class BeeSoul extends SoulItem {

    public BeeSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.ORANGE));
    }

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Tick(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                if (SoulUtils.isEquipped(attacker, BeeSoul.class)) {
                    if (attacker.tickCount % (SoulUtils.isEquipped(attacker, LifePower.class) ? 20 : 40) == 0) {
                        Level level = attacker.level();
                        List<Bee> beeList = level.getEntitiesOfClass(Bee.class, attacker.getBoundingBox().inflate(5));
                        for (Bee bee : beeList) {
                            bee.heal(1);
                            SoulUtils.playSound(
                                    level,
                                    bee.position(),
                                    SoundEvents.BEE_LOOP,
                                    SoundSource.PLAYERS
                            );
                        }
                        BlockState blockState = level.getBlockState(attacker.blockPosition());
                        if (blockState.getBlock() instanceof FlowerBlock) {
                            attacker.heal(1);
                        }
                    }
                }
            }
        }

        @SubscribeEvent
        public static void ChangeTarget(LivingChangeTargetEvent event) {
            if (event.getEntity() instanceof Bee && event.getNewAboutToBeSetTarget() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                if (SoulUtils.isEquipped(attacker, BeeSoul.class)) {
                    event.setCanceled(true);
                }
            }
        }

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
                if (event.getSource().is(DamageTypes.FALL) && SoulUtils.isEquipped(target, BeeSoul.class)) {
                    event.setCanceled(true);
                }
                if (target instanceof Bee && event.getSource().getEntity() instanceof LivingEntity attacker && SoulUtils.isEquipped(attacker, BeeSoul.class)) {
                    event.setCanceled(true);
                }
            }
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                if (SoulUtils.isEquipped(attacker, BeeSoul.class) && !SoulUtils.isEquipped(target, BeeSoul.class)) {
                    Level level = attacker.level();
                    List<Bee> beeList = level.getEntitiesOfClass(Bee.class, attacker.getBoundingBox().inflate(5));
                    for (Bee bee : beeList) {
                        bee.setTarget(target);
                    }
                }
            }
        }

    }

}

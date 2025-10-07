package First.fargo_soul.Item.Soul.TerraSoul.ForestPower.SoulStone;

import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.TerraSoul.ForestPower.ForestPower;
import First.fargo_soul.Utils.ParticleUtils;
import First.fargo_soul.Utils.SoulUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

public class PalmWoodSoul extends SoulItem {

    public PalmWoodSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.GREEN));
    }

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
                if (event.getSource().is(DamageTypes.ON_FIRE)) {
                    List<LivingEntity> livingEntityList = target.level().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(3), livingEntity -> SoulUtils.isEquipped(livingEntity, PalmWoodSoul.class));
                    if (!livingEntityList.isEmpty()) {
                        event.setAmount(event.getAmount() * 1.5f);
                    }
                }
            }
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                if (!attacker.equals(target) && SoulUtils.isEquipped(attacker, PalmWoodSoul.class)) {
                    int remainingFireTicks = target.getRemainingFireTicks() + 40;
                    remainingFireTicks = Math.min(remainingFireTicks, 600);
                    target.setRemainingFireTicks(remainingFireTicks);
                    if (target.getRemainingFireTicks() > 0 && SoulUtils.isEquipped(attacker, ForestPower.class) && attacker.getRandom().nextDouble() < 0.05) {
                        Level level = attacker.level();
                        List<LivingEntity> livingEntityList = level.getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(1));
                        livingEntityList.remove(attacker);
                        for (LivingEntity livingEntity : livingEntityList) {
                            SoulUtils.attack(attacker, livingEntity, DamageTypes.PLAYER_EXPLOSION, 4);
                        }
                        ParticleUtils.spawnParticleSphere(
                                (ServerLevel) level,
                                target.getX(),
                                target.getBoundingBox().getCenter().y(),
                                target.getZ(),
                                ParticleTypes.EXPLOSION,
                                1f,
                                10,
                                0.2f
                        );
                        SoulUtils.playSound(
                                level,
                                target.position(),
                                SoundEvents.GENERIC_EXPLODE.value(),
                                SoundSource.PLAYERS
                        );
                    }
                }
            }
        }

    }

}

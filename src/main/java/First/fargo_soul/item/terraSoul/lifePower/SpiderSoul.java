package First.fargo_soul.item.terraSoul.lifePower;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.LifePower;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class SpiderSoul extends SoulItem {

    public SpiderSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.LIGHT_PURPLE));
    }

    @EventBusSubscriber(modid = FargoSoul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getSource().getEntity() instanceof TamableAnimal animal && !animal.level().isClientSide()) {
                if (animal.getOwner() instanceof LivingEntity attacker && CurioUtils.isEquipped(attacker, SpiderSoul.class)) {
                    double chance = CurioUtils.isEquipped(attacker, LifePower.class) ? 0.4 : 0.2;
                    if (animal.getRandom().nextDouble() < chance) {
                        float damage = event.getAmount();
                        float newDamage = CurioUtils.isEquipped(attacker, LifePower.class) ? damage * 2 : damage + Math.min(damage * 0.5f, 100);
                        event.setAmount(newDamage);
                    }
                }
            }
        }

    }

}

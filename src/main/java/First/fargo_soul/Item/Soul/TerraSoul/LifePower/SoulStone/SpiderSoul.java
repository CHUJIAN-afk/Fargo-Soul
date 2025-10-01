package First.fargo_soul.Item.Soul.TerraSoul.LifePower.SoulStone;

import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.TerraSoul.LifePower.LifePower;
import First.fargo_soul.Utils.SoulUtils;
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

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getSource().getEntity() instanceof TamableAnimal animal && !animal.level().isClientSide()) {
                if (animal.getOwner() instanceof LivingEntity attacker && SoulUtils.isEquipped(attacker, SpiderSoul.class)) {
                    double chance = SoulUtils.isEquipped(attacker, LifePower.class) ? 0.4 : 0.2;
                    if (animal.getRandom().nextDouble() < chance) {
                        float damage = event.getAmount();
                        float newDamage = SoulUtils.isEquipped(attacker, LifePower.class) ? damage * 2 : damage + Math.min(damage * 0.5f, 100);
                        event.setAmount(newDamage);
                    }
                }
            }
        }

    }

}

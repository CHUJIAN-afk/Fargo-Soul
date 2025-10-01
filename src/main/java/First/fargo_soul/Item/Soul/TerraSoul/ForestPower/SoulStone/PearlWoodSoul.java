package First.fargo_soul.Item.Soul.TerraSoul.ForestPower.SoulStone;

import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.TerraSoul.ForestPower.ForestPower;
import First.fargo_soul.Utils.SoulUtils;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class PearlWoodSoul extends SoulItem {

    public PearlWoodSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.ORANGE));
    }

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent(priority = EventPriority.LOW)
        public static void CriticalHit(CriticalHitEvent event) {
            if (event.getEntity() instanceof Player attacker && !attacker.level().isClientSide()) {
                if (SoulUtils.isEquipped(attacker, PearlWoodSoul.class)) {
                    if (event.isCriticalHit()) {
                        event.setDamageMultiplier(event.getDamageMultiplier() * 1.3f);
                    } else if (attacker.getRandom().nextDouble() < 0.25) {
                        event.setCriticalHit(true);
                    } else if (SoulUtils.isEquipped(attacker, ForestPower.class) && attacker.getRandom().nextDouble() < 0.25) {
                        event.setCriticalHit(true);
                    }
                }
            }
        }

    }

}

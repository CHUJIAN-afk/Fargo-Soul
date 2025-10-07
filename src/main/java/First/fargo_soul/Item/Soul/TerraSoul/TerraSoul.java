package First.fargo_soul.Item.Soul.TerraSoul;

import First.fargo_soul.Event.SoulCreativeTabEvent;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

import static First.fargo_soul.Item.Soul.SoulsRegister.*;

public class TerraSoul extends SoulItem {

    public TerraSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.MASTER));
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                CosmicPower.get(),
                DeathPower.get(),
                EarthPower.get(),
                ForestPower.get(),
                LifePower.get(),
                NaturePower.get(),
                SpiritPower.get(),
                TerraPower.get(),
                WillPower.get()
        );
    }

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void CreativeTabEvent(SoulCreativeTabEvent event) {
            event.add(TerraSoul.get());
        }

    }




}

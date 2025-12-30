package First.fargo_soul.item;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.event.modEvent.AddItemTagEvent;
import First.fargo_soul.item.base.SoulItem;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

import static First.fargo_soul.register.ItemRegister.*;

public class TerraSoul extends SoulItem {

    public TerraSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.MASTER));
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                CosmicPowerItem.get(),
                DeathPowerItem.get(),
                EarthPowerItem.get(),
                ForestPowerItem.get(),
                LifePowerItem.get(),
                NaturePowerItem.get(),
                SpiritPowerItem.get(),
                TerraPowerItem.get(),
                WillPowerItem.get()
        );
    }

    @EventBusSubscriber(modid = FargoSoul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void AddItemTagEvent(AddItemTagEvent event) {
            ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath("curios", "soul");
            event.add(resourceLocation, TerraSoulItem.get());
        }

    }

}

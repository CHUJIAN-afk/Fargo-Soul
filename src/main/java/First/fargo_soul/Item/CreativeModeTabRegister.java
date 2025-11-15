package First.fargo_soul.Item;

import First.fargo_soul.Event.SoulCreativeTabEvent;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;


public class CreativeModeTabRegister {

    public static final DeferredRegister<CreativeModeTab> CreativeModeTabRegister;
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> FargoSoulTab;

    static {
        CreativeModeTabRegister = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Fargo_soul.MODID);
        FargoSoulTab = CreativeModeTabRegister.register(Fargo_soul.MODID, () -> {
            SoulItem terraSoul = SoulsRegister.TerraSoul.get();
            CreativeModeTab.Builder builder = CreativeModeTab.builder();
            builder.title(Component.translatable("itemGroup.fargo_soul"));
            builder.withTabsBefore(CreativeModeTabs.COMBAT);
            builder.icon(terraSoul::getDefaultInstance);
            builder.displayItems((parameters, output) -> {
                output.accept(SoulsRegister.SoulCoreItem);
                SoulCreativeTabEvent event = new SoulCreativeTabEvent();
                NeoForge.EVENT_BUS.post(event);
                List<Item> soulItemList = event.getItemList();
                for (Item item : soulItemList) {
                    output.accept(item);
                }
            });
            return builder.build();
        });
    }

    public static void register(IEventBus eventBus) {
        CreativeModeTabRegister.register(eventBus);
    }

}

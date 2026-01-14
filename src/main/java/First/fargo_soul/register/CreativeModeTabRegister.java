package First.fargo_soul.register;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CreativeModeTabRegister {

    public static final DeferredRegister<CreativeModeTab> Register =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FargoSoul.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> FargoSoulTab =
            Register.register(FargoSoul.MODID, () -> {
                CreativeModeTab.Builder builder = CreativeModeTab.builder();
                builder.title(Component.translatable("itemGroup.fargo_soul"));
                builder.icon(() -> ItemRegister.TerraSoulItem.get().getDefaultInstance());
                builder.displayItems((parameters, output) -> {
                    output.accept(ItemRegister.CosmicCrucibleBlockItem);
                    output.accept(ItemRegister.Soul);
                    List<SoulItem> soulItemList = new ArrayList<>(SoulUtils.RegisterSoulList);
                    Collections.reverse(soulItemList);
                    for (SoulItem soulItem : soulItemList) {
                        if (BuiltInRegistries.ITEM.getKey(soulItem).getNamespace().equals(FargoSoul.MODID)) {
                            output.accept(soulItem);
                        }
                    }
                });
                return builder.build();
            });

    public static void register(IEventBus eventBus) {
        Register.register(eventBus);
    }

}

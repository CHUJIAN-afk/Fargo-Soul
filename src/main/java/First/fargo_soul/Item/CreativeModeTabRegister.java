package First.fargo_soul.Item;

import First.fargo_soul.Compact.Create.CreateCompact;
import First.fargo_soul.Compact.Create.CreateSoulsRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static First.fargo_soul.Item.Soul.SoulsRegister.TerraSoul;

public class CreativeModeTabRegister {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB_DEFERRED_REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Fargo_soul.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> FARGO_SOUL_TAB;

    static {
        //物品栏添加
        FARGO_SOUL_TAB = CREATIVE_MODE_TAB_DEFERRED_REGISTER.register(Fargo_soul.MODID, () -> {
                    SoulItem terraSoul = TerraSoul.get();
                    CreativeModeTab.Builder builder = CreativeModeTab.builder();
                    builder.title(Component.translatable("itemGroup.fargo_soul"));
                    builder.withTabsBefore(CreativeModeTabs.COMBAT);
                    builder.icon(terraSoul::getDefaultInstance);
                    builder.displayItems((parameters, output) -> {
                        addCreativeTab(output, terraSoul);
                        //机械动力联动
                        if (CreateCompact.isLoadCreate()) {
                            addCreativeTab(output, CreateSoulsRegister.Create_Power.get());
                        }
                    });
                    return builder.build();
                }
        );
    }

    private static void addCreativeTab(CreativeModeTab.Output output, SoulItem soulItem) {
        output.accept(soulItem);
        CurioUtils.getAllCurioItems(soulItem.getCurioItemList()).stream().distinct().toList().forEach(output::accept);
    }

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB_DEFERRED_REGISTER.register(eventBus);
    }

}

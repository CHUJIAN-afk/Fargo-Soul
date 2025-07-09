package First.fargo_soul.Curios;


import First.fargo_soul.Curios.SoulStone.*;
import First.fargo_soul.Curios.Power.*;
import First.fargo_soul.Fargo_soul;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CuriosRegister {

    public static final DeferredRegister.Items SoulItems = DeferredRegister.createItems(Fargo_soul.MODID);
    public static final DeferredItem<CurioItem> WoodSoul = SoulItems.registerItem("wood_soul", WoodSoul::new);
    public static final DeferredItem<CurioItem> PineWoodSoul = SoulItems.registerItem("pine_wood_soul", PineWoodSoul::new);
    public static final DeferredItem<CurioItem> RosewoodSoul = SoulItems.registerItem("rose_wood_soul", RosewoodSoul::new);
    public static final DeferredItem<CurioItem> EbonyWoodSoul = SoulItems.registerItem("ebony_wood_soul", EbonyWoodSoul::new);
    public static final DeferredItem<CurioItem> ShadowWoodSoul = SoulItems.registerItem("shadow_wood_soul", ShadowWoodSoul::new);
    public static final DeferredItem<CurioItem> PalmWoodSoul = SoulItems.registerItem("palm_wood_soul", PalmWoodSoul::new);
    public static final DeferredItem<CurioItem> PearlWoodSoul = SoulItems.registerItem("pearl_wood_soul", PearlWoodSoul::new);
    public static final DeferredItem<CurioItem> ForestPower = SoulItems.registerItem("forest_power", ForestPower::new);



    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB_DEFERRED_REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Fargo_soul.MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> FARGO_SOUL_TAB = CREATIVE_MODE_TAB_DEFERRED_REGISTER.register(Fargo_soul.MODID, () ->
            CreativeModeTab.builder()
                    .title(Component.literal("无尽 魂石"))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> WoodSoul.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(WoodSoul.get());
                        output.accept(PineWoodSoul.get());
                        output.accept(RosewoodSoul.get());
                        output.accept(EbonyWoodSoul.get());
                        output.accept(ShadowWoodSoul.get());
                        output.accept(PalmWoodSoul.get());
                        output.accept(PearlWoodSoul.get());
                        output.accept(ForestPower.get());
                    })
                    .build()
    );


}

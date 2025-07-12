package First.fargo_soul.Curios;

import First.fargo_soul.Curios.Soul.EarthPower.EarthPower;
import First.fargo_soul.Curios.Soul.EarthPower.SoulStone.*;
import First.fargo_soul.Curios.Soul.EarthPower.SoulStone.CobaltSoulStone.AncientCobaltSoul;
import First.fargo_soul.Curios.Soul.TerraPower.SoulStone.*;
import First.fargo_soul.Curios.Soul.TerraPower.SoulStone.ObsidianSoulStone.AshWoodSoul;
import First.fargo_soul.Curios.Soul.TerraPower.TerraPower;
import First.fargo_soul.Fargo_soul;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import First.fargo_soul.Curios.Soul.ForestPower.*;
import First.fargo_soul.Curios.Soul.ForestPower.SoulStone.*;
public class Souls {

    public static final DeferredRegister.Items SoulItems = DeferredRegister.createItems(Fargo_soul.MODID);
    //森林之力
    public static final DeferredItem<SoulItem> WoodSoul = SoulItems.registerItem("wood_soul", WoodSoul::new);
    public static final DeferredItem<SoulItem> PineWoodSoul = SoulItems.registerItem("pine_wood_soul", PineWoodSoul::new);
    public static final DeferredItem<SoulItem> RosewoodSoul = SoulItems.registerItem("rose_wood_soul", RoseWoodSoul::new);
    public static final DeferredItem<SoulItem> EbonyWoodSoul = SoulItems.registerItem("ebony_wood_soul", EbonyWoodSoul::new);
    public static final DeferredItem<SoulItem> ShadowWoodSoul = SoulItems.registerItem("shadow_wood_soul", ShadowWoodSoul::new);
    public static final DeferredItem<SoulItem> PalmWoodSoul = SoulItems.registerItem("palm_wood_soul", PalmWoodSoul::new);
    public static final DeferredItem<SoulItem> PearlWoodSoul = SoulItems.registerItem("pearl_wood_soul", PearlWoodSoul::new);
    public static final DeferredItem<SoulItem> ForestPower = SoulItems.registerItem("forest_power", ForestPower::new);
    //泰拉之力
    public static final DeferredItem<SoulItem> CopperSoul = SoulItems.registerItem("copper_soul", CopperSoul::new);
    public static final DeferredItem<SoulItem> TinSoul = SoulItems.registerItem("tin_soul", TinSoul::new);
    public static final DeferredItem<SoulItem> IronSoul = SoulItems.registerItem("iron_soul", IronSoul::new);
    public static final DeferredItem<SoulItem> LeadSoul = SoulItems.registerItem("lead_soul", LeadSoul::new);
    public static final DeferredItem<SoulItem> SilverSoul = SoulItems.registerItem("silver_soul", SilverSoul::new);
    public static final DeferredItem<SoulItem> TungstenSoul = SoulItems.registerItem("tungsten_soul", TungstenSoul::new);
    public static final DeferredItem<SoulItem> AshWoodSoul = SoulItems.registerItem("ash_wood_soul", AshWoodSoul::new);
    public static final DeferredItem<SoulItem> ObsidianSoul = SoulItems.registerItem("obsidian_soul", ObsidianSoul::new);
    public static final DeferredItem<SoulItem> TerraPower = SoulItems.registerItem("terra_power", TerraPower::new);
    //大地之力
    public static final DeferredItem<SoulItem> AncientCobaltSoul = SoulItems.registerItem("ancient_cobalt_soul", AncientCobaltSoul::new);
    public static final DeferredItem<SoulItem> CobaltSoul = SoulItems.registerItem("cobalt_soul", CobaltSoul::new);
    public static final DeferredItem<SoulItem> PalladiumSoul = SoulItems.registerItem("palladium_soul", PalladiumSoul::new);
    public static final DeferredItem<SoulItem> MithrilSoul = SoulItems.registerItem("mithril_soul", MithrilSoul::new);
    public static final DeferredItem<SoulItem> OrichalcumSoul = SoulItems.registerItem("orichalcum_soul", OrichalcumSoul::new);
    public static final DeferredItem<SoulItem> AdamantiteSoul = SoulItems.registerItem("adamantite_soul", AdamantiteSoul::new);
    public static final DeferredItem<SoulItem> TitaniumSoul = SoulItems.registerItem("titanium_soul", TitaniumSoul::new);
    public static final DeferredItem<SoulItem> EarthPower = SoulItems.registerItem("earth_power", EarthPower::new);


    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB_DEFERRED_REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Fargo_soul.MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> FARGO_SOUL_TAB = CREATIVE_MODE_TAB_DEFERRED_REGISTER.register(Fargo_soul.MODID, () ->
            CreativeModeTab.builder()
                    .title(Component.literal("Fargo Soul"))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> WoodSoul.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        //森林
                        output.accept(WoodSoul.get());
                        output.accept(PineWoodSoul.get());
                        output.accept(RosewoodSoul.get());
                        output.accept(EbonyWoodSoul.get());
                        output.accept(ShadowWoodSoul.get());
                        output.accept(PalmWoodSoul.get());
                        output.accept(PearlWoodSoul.get());
                        output.accept(ForestPower.get());
                        //泰拉
                        output.accept(AshWoodSoul.get());
                        output.accept(ObsidianSoul.get());
                        output.accept(CopperSoul.get());
                        output.accept(TinSoul.get());
                        output.accept(IronSoul.get());
                        output.accept(LeadSoul.get());
                        output.accept(SilverSoul.get());
                        output.accept(TungstenSoul.get());
                        output.accept(TerraPower.get());
                        //大地
                        output.accept(AncientCobaltSoul.get());
                        output.accept(CobaltSoul.get());
                        output.accept(PalladiumSoul.get());
                        output.accept(MithrilSoul.get());
                        output.accept(OrichalcumSoul.get());
                        output.accept(AdamantiteSoul.get());
                        output.accept(TitaniumSoul.get());
                        output.accept(EarthPower.get());
                    })
                    .build()
    );


}

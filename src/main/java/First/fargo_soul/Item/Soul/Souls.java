package First.fargo_soul.Item.Soul;

import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.CosmicPower.CosmicPower;
import First.fargo_soul.Item.Soul.CosmicPower.SoulStone.*;
import First.fargo_soul.Item.Soul.DeathPower.DeathPower;
import First.fargo_soul.Item.Soul.DeathPower.SoulStone.*;
import First.fargo_soul.Item.Soul.DeathPower.SoulStone.PenetratingNinjaSoulStone.MonkSoul;
import First.fargo_soul.Item.Soul.EarthPower.EarthPower;
import First.fargo_soul.Item.Soul.EarthPower.SoulStone.*;
import First.fargo_soul.Item.Soul.EarthPower.SoulStone.CobaltSoulStone.AncientCobaltSoul;
import First.fargo_soul.Item.Soul.ForestPower.ForestPower;
import First.fargo_soul.Item.Soul.ForestPower.SoulStone.*;
import First.fargo_soul.Item.Soul.LifePower.LifePower;
import First.fargo_soul.Item.Soul.LifePower.SoulStone.*;
import First.fargo_soul.Item.Soul.LifePower.SoulStone.TurtleSoulStone.CactusSoul;
import First.fargo_soul.Item.Soul.NaturePower.NaturePower;
import First.fargo_soul.Item.Soul.NaturePower.SoulStone.*;
import First.fargo_soul.Item.Soul.SpiritPower.SoulStone.*;
import First.fargo_soul.Item.Soul.SpiritPower.SpiritPower;
import First.fargo_soul.Item.Soul.TerraPower.SoulStone.*;
import First.fargo_soul.Item.Soul.TerraPower.SoulStone.ObsidianSoulStone.AshWoodSoul;
import First.fargo_soul.Item.Soul.TerraPower.TerraPower;
import First.fargo_soul.Item.Soul.WillPower.Soulstone.*;
import First.fargo_soul.Item.Soul.WillPower.WillPower;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
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
    //自然之力
    public static final DeferredItem<SoulItem> CrimsonSoul = SoulItems.registerItem("crimson_soul", CrimsonSoul::new);
    public static final DeferredItem<SoulItem> LavaSoul = SoulItems.registerItem("lava_soul", LavaSoul::new);
    public static final DeferredItem<SoulItem> RainCloudSoul = SoulItems.registerItem("rain_cloud_soul", RainCloudSoul::new);
    public static final DeferredItem<SoulItem> FrostSoul = SoulItems.registerItem("frost_soul", FrostSoul::new);
    public static final DeferredItem<SoulItem> GreenSoul = SoulItems.registerItem("green_soul", GreenSoul::new);
    public static final DeferredItem<SoulItem> MushroomSoul = SoulItems.registerItem("mushroom_soul", MushroomSoul::new);
    public static final DeferredItem<SoulItem> NaturePower = SoulItems.registerItem("nature_power", NaturePower::new);
    //生命之力
    public static final DeferredItem<SoulItem> BeeSoul = SoulItems.registerItem("bee_soul", BeeSoul::new);
    public static final DeferredItem<SoulItem> BeetleSoul = SoulItems.registerItem("beetle_soul", BeetleSoul::new);
    public static final DeferredItem<SoulItem> PumpkinSoul = SoulItems.registerItem("pumpkin_soul", PumpkinSoul::new);
    public static final DeferredItem<SoulItem> SpiderSoul = SoulItems.registerItem("spider_soul", SpiderSoul::new);
    public static final DeferredItem<SoulItem> CactusSoul = SoulItems.registerItem("cactus_soul", CactusSoul::new);
    public static final DeferredItem<SoulItem> TurtleSoul = SoulItems.registerItem("turtle_soul", TurtleSoul::new);
    public static final DeferredItem<SoulItem> LifePower = SoulItems.registerItem("life_power", LifePower::new);
    //心灵之力
    public static final DeferredItem<SoulItem> ForbiddenSoul = SoulItems.registerItem("forbidden_soul", ForbiddenSoul::new);
    public static final DeferredItem<SoulItem> HolySoul = SoulItems.registerItem("holy_soul", HolySoul::new);
    public static final DeferredItem<SoulItem> AncientHolySoul = SoulItems.registerItem("ancient_holy_soul", AncientHolySoul::new);
    public static final DeferredItem<SoulItem> TekeSoul = SoulItems.registerItem("teke_soul", TekeSoul::new);
    public static final DeferredItem<SoulItem> GhostSoul = SoulItems.registerItem("ghost_soul", GhostSoul::new);
    public static final DeferredItem<SoulItem> SpiritPower = SoulItems.registerItem("spirit_power", SpiritPower::new);
    //死亡之力
    public static final DeferredItem<SoulItem> AncientShadowSoul = SoulItems.registerItem("ancient_shadow_soul", AncientShadowSoul::new);
    public static final DeferredItem<SoulItem> NinjaSoul = SoulItems.registerItem("ninja_soul", NinjaSoul::new);
    public static final DeferredItem<SoulItem> CrystalAssassinSoul = SoulItems.registerItem("crystal_assassin_soul", CrystalAssassinSoul::new);
    public static final DeferredItem<SoulItem> DarkArtistSoul = SoulItems.registerItem("dark_artist_soul", DarkArtistSoul::new);
    public static final DeferredItem<SoulItem> GloomySoul = SoulItems.registerItem("gloomy_soul", GloomySoul::new);
    public static final DeferredItem<SoulItem> NecromancerSoul = SoulItems.registerItem("necromancer_soul", NecromancerSoul::new);
    public static final DeferredItem<SoulItem> MonkSoul = SoulItems.registerItem("monk_soul", MonkSoul::new);
    public static final DeferredItem<SoulItem> PenetratingNinjaSoul = SoulItems.registerItem("penetrating_ninja_soul", PenetratingNinjaSoul::new);
    public static final DeferredItem<SoulItem> DeathPower = SoulItems.registerItem("death_power", DeathPower::new);
    //意志之力
    public static final DeferredItem<SoulItem> GoldSoul = SoulItems.registerItem("gold_soul", GoldSoul::new);
    public static final DeferredItem<SoulItem> PlatinumSoul = SoulItems.registerItem("platinum_soul", PlatinumSoul::new);
    public static final DeferredItem<SoulItem> GladiatorSoul = SoulItems.registerItem("gladiator_soul", GladiatorSoul::new);
    public static final DeferredItem<SoulItem> RedRidingSoul = SoulItems.registerItem("red_riding_soul", RedRidingSoul::new);
    public static final DeferredItem<SoulItem> ValhallaKnightSoul = SoulItems.registerItem("valhalla_knight_soul", ValhallaKnightSoul::new);
    public static final DeferredItem<SoulItem> WillPower = SoulItems.registerItem("will_power", WillPower::new);
    //宇宙之力
    public static final DeferredItem<SoulItem> MeteorSoul = SoulItems.registerItem("meteor_soul", MeteorSoul::new);
    public static final DeferredItem<SoulItem> WizardSoul = SoulItems.registerItem("wizard_soul", WizardSoul::new);
    public static final DeferredItem<SoulItem> BlazeSoul = SoulItems.registerItem("blaze_soul", BlazeSoul::new);
    public static final DeferredItem<SoulItem> StardustSoul = SoulItems.registerItem("stardust_soul", StardustSoul::new);
    public static final DeferredItem<SoulItem> NebulaSoul = SoulItems.registerItem("nebula_soul", NebulaSoul::new);
    public static final DeferredItem<SoulItem> VortexSoul = SoulItems.registerItem("vortex_soul", VortexSoul::new);
    public static final DeferredItem<SoulItem> CosmicPower = SoulItems.registerItem("cosmic_power", CosmicPower::new);
    //泰拉之魂
    public static final DeferredItem<SoulItem> TerraSoul = SoulItems.registerItem("terra_soul", TerraSoul::new);

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB_DEFERRED_REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Fargo_soul.MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> FARGO_SOUL_TAB = CREATIVE_MODE_TAB_DEFERRED_REGISTER.register(Fargo_soul.MODID, () ->
            CreativeModeTab.builder()
                    .title(Component.literal("Fargo Soul"))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> TerraSoul.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        //泰拉之魂
                        output.accept(TerraSoul.get());
                        //森林之力
                        output.accept(WoodSoul.get());
                        output.accept(PineWoodSoul.get());
                        output.accept(RosewoodSoul.get());
                        output.accept(EbonyWoodSoul.get());
                        output.accept(ShadowWoodSoul.get());
                        output.accept(PalmWoodSoul.get());
                        output.accept(PearlWoodSoul.get());
                        output.accept(ForestPower.get());
                        //泰拉之力
                        output.accept(AshWoodSoul.get());
                        output.accept(ObsidianSoul.get());
                        output.accept(CopperSoul.get());
                        output.accept(TinSoul.get());
                        output.accept(IronSoul.get());
                        output.accept(LeadSoul.get());
                        output.accept(SilverSoul.get());
                        output.accept(TungstenSoul.get());
                        output.accept(TerraPower.get());
                        //大地之力
                        output.accept(AncientCobaltSoul.get());
                        output.accept(CobaltSoul.get());
                        output.accept(PalladiumSoul.get());
                        output.accept(MithrilSoul.get());
                        output.accept(OrichalcumSoul.get());
                        output.accept(AdamantiteSoul.get());
                        output.accept(TitaniumSoul.get());
                        output.accept(EarthPower.get());
                        //自然之力
                        output.accept(CrimsonSoul.get());
                        output.accept(LavaSoul.get());
                        output.accept(RainCloudSoul.get());
                        output.accept(FrostSoul.get());
                        output.accept(GreenSoul.get());
                        output.accept(MushroomSoul.get());
                        output.accept(NaturePower.get());
                        //生命之力
                        output.accept(BeeSoul.get());
                        output.accept(BeetleSoul.get());
                        output.accept(PumpkinSoul.get());
                        output.accept(SpiderSoul.get());
                        output.accept(CactusSoul.get());
                        output.accept(TurtleSoul.get());
                        output.accept(LifePower.get());
                        //心灵之力
                        output.accept(ForbiddenSoul.get());
                        output.accept(HolySoul.get());
                        output.accept(AncientHolySoul.get());
                        output.accept(TekeSoul.get());
                        output.accept(GhostSoul.get());
                        output.accept(SpiritPower.get());
                        //死亡之力
                        output.accept(NinjaSoul.get());
                        output.accept(AncientShadowSoul.get());
                        output.accept(CrystalAssassinSoul.get());
                        output.accept(DarkArtistSoul.get());
                        output.accept(GloomySoul.get());
                        output.accept(NecromancerSoul.get());
                        output.accept(MonkSoul.get());
                        output.accept(PenetratingNinjaSoul.get());
                        output.accept(DeathPower.get());
                        //意志之力
                        output.accept(GoldSoul.get());
                        output.accept(PlatinumSoul.get());
                        output.accept(GladiatorSoul.get());
                        output.accept(RedRidingSoul.get());
                        output.accept(ValhallaKnightSoul.get());
                        output.accept(WillPower.get());
                        //宇宙之力
                        output.accept(MeteorSoul.get());
                        output.accept(WizardSoul.get());
                        output.accept(BlazeSoul.get());
                        output.accept(StardustSoul.get());
                        output.accept(NebulaSoul.get());
                        output.accept(VortexSoul.get());
                        output.accept(CosmicPower.get());

                    })
                    .build()
    );


}

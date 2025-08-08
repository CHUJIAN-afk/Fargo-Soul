package First.fargo_soul.Item.Soul;

import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.TerraSoul.CosmicPower.CosmicPower;
import First.fargo_soul.Item.Soul.TerraSoul.CosmicPower.SoulStone.*;
import First.fargo_soul.Item.Soul.TerraSoul.DeathPower.DeathPower;
import First.fargo_soul.Item.Soul.TerraSoul.DeathPower.SoulStone.*;
import First.fargo_soul.Item.Soul.TerraSoul.DeathPower.SoulStone.PenetratingNinjaSoulStone.MonkSoul;
import First.fargo_soul.Item.Soul.TerraSoul.EarthPower.EarthPower;
import First.fargo_soul.Item.Soul.TerraSoul.EarthPower.SoulStone.*;
import First.fargo_soul.Item.Soul.TerraSoul.EarthPower.SoulStone.CobaltSoulStone.AncientCobaltSoul;
import First.fargo_soul.Item.Soul.TerraSoul.ForestPower.ForestPower;
import First.fargo_soul.Item.Soul.TerraSoul.ForestPower.SoulStone.*;
import First.fargo_soul.Item.Soul.TerraSoul.LifePower.LifePower;
import First.fargo_soul.Item.Soul.TerraSoul.LifePower.SoulStone.*;
import First.fargo_soul.Item.Soul.TerraSoul.LifePower.SoulStone.TurtleSoulStone.CactusSoul;
import First.fargo_soul.Item.Soul.TerraSoul.NaturePower.NaturePower;
import First.fargo_soul.Item.Soul.TerraSoul.NaturePower.SoulStone.*;
import First.fargo_soul.Item.Soul.TerraSoul.SpiritPower.SoulStone.*;
import First.fargo_soul.Item.Soul.TerraSoul.SpiritPower.SpiritPower;
import First.fargo_soul.Item.Soul.TerraSoul.TerraPower.SoulStone.*;
import First.fargo_soul.Item.Soul.TerraSoul.TerraPower.SoulStone.ObsidianSoulStone.AshWoodSoul;
import First.fargo_soul.Item.Soul.TerraSoul.TerraPower.TerraPower;
import First.fargo_soul.Item.Soul.TerraSoul.TerraSoul;
import First.fargo_soul.Item.Soul.TerraSoul.WillPower.Soulstone.*;
import First.fargo_soul.Item.Soul.TerraSoul.WillPower.WillPower;
import First.fargo_soul.Item.Soul.UniverseSoul.BerserkerSoul.BerserkerSoul;
import First.fargo_soul.Item.Soul.UniverseSoul.BerserkerSoul.Soul.*;
import First.fargo_soul.Item.Soul.UniverseSoul.SharpshooterSoul.SharpshooterSoul;
import First.fargo_soul.Item.Soul.UniverseSoul.SharpshooterSoul.Soul.MarksmanEssence;
import First.fargo_soul.Item.Soul.UniverseSoul.SharpshooterSoul.Soul.MeltRocketBag;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;


public class SoulsRegister {

    public static final DeferredRegister.Items SoulItems;

    public static final DeferredItem<SoulItem> WoodSoul;
    public static final DeferredItem<SoulItem> PineWoodSoul;
    public static final DeferredItem<SoulItem> RosewoodSoul;
    public static final DeferredItem<SoulItem> EbonyWoodSoul;
    public static final DeferredItem<SoulItem> ShadowWoodSoul;
    public static final DeferredItem<SoulItem> PalmWoodSoul;
    public static final DeferredItem<SoulItem> PearlWoodSoul;
    public static final DeferredItem<SoulItem> ForestPower;
    public static final DeferredItem<SoulItem> CopperSoul;
    public static final DeferredItem<SoulItem> TinSoul;
    public static final DeferredItem<SoulItem> IronSoul;
    public static final DeferredItem<SoulItem> LeadSoul;
    public static final DeferredItem<SoulItem> SilverSoul;
    public static final DeferredItem<SoulItem> TungstenSoul;
    public static final DeferredItem<SoulItem> AshWoodSoul;
    public static final DeferredItem<SoulItem> ObsidianSoul;
    public static final DeferredItem<SoulItem> TerraPower;
    public static final DeferredItem<SoulItem> AncientCobaltSoul;
    public static final DeferredItem<SoulItem> CobaltSoul;
    public static final DeferredItem<SoulItem> PalladiumSoul;
    public static final DeferredItem<SoulItem> MithrilSoul;
    public static final DeferredItem<SoulItem> OrichalcumSoul;
    public static final DeferredItem<SoulItem> AdamantiteSoul;
    public static final DeferredItem<SoulItem> TitaniumSoul;
    public static final DeferredItem<SoulItem> EarthPower;
    public static final DeferredItem<SoulItem> CrimsonSoul;
    public static final DeferredItem<SoulItem> LavaSoul;
    public static final DeferredItem<SoulItem> RainCloudSoul;
    public static final DeferredItem<SoulItem> FrostSoul;
    public static final DeferredItem<SoulItem> GreenSoul;
    public static final DeferredItem<SoulItem> MushroomSoul;
    public static final DeferredItem<SoulItem> NaturePower;
    public static final DeferredItem<SoulItem> BeeSoul;
    public static final DeferredItem<SoulItem> BeetleSoul;
    public static final DeferredItem<SoulItem> PumpkinSoul;
    public static final DeferredItem<SoulItem> SpiderSoul;
    public static final DeferredItem<SoulItem> CactusSoul;
    public static final DeferredItem<SoulItem> TurtleSoul;
    public static final DeferredItem<SoulItem> LifePower;
    public static final DeferredItem<SoulItem> ForbiddenSoul;
    public static final DeferredItem<SoulItem> HolySoul;
    public static final DeferredItem<SoulItem> AncientHolySoul;
    public static final DeferredItem<SoulItem> TekeSoul;
    public static final DeferredItem<SoulItem> GhostSoul;
    public static final DeferredItem<SoulItem> SpiritPower;
    public static final DeferredItem<SoulItem> AncientShadowSoul;
    public static final DeferredItem<SoulItem> NinjaSoul;
    public static final DeferredItem<SoulItem> CrystalAssassinSoul;
    public static final DeferredItem<SoulItem> DarkArtistSoul;
    public static final DeferredItem<SoulItem> GloomySoul;
    public static final DeferredItem<SoulItem> NecromancerSoul;
    public static final DeferredItem<SoulItem> MonkSoul;
    public static final DeferredItem<SoulItem> PenetratingNinjaSoul;
    public static final DeferredItem<SoulItem> DeathPower;
    public static final DeferredItem<SoulItem> GoldSoul;
    public static final DeferredItem<SoulItem> PlatinumSoul;
    public static final DeferredItem<SoulItem> GladiatorSoul;
    public static final DeferredItem<SoulItem> RedRidingSoul;
    public static final DeferredItem<SoulItem> ValhallaKnightSoul;
    public static final DeferredItem<SoulItem> WillPower;
    public static final DeferredItem<SoulItem> MeteorSoul;
    public static final DeferredItem<SoulItem> WizardSoul;
    public static final DeferredItem<SoulItem> BlazeSoul;
    public static final DeferredItem<SoulItem> StardustSoul;
    public static final DeferredItem<SoulItem> NebulaSoul;
    public static final DeferredItem<SoulItem> VortexSoul;
    public static final DeferredItem<SoulItem> CosmicPower;
    public static final DeferredItem<SoulItem> TerraSoul;
    public static final DeferredItem<SoulItem> BarbarianEssence;
    public static final DeferredItem<SoulItem> BerserkerGloves;
    public static final DeferredItem<SoulItem> CelestialShell;
    public static final DeferredItem<SoulItem> FireGloves;
    public static final DeferredItem<SoulItem> StingerNecklace;
    public static final DeferredItem<SoulItem> BerserkerSoul;
    public static final DeferredItem<SoulItem> MarksmanEssence;
    public static final DeferredItem<SoulItem> SharpshooterSoul;
    public static final DeferredItem<SoulItem> MeltRocketBag;


    static {
        SoulItems = DeferredRegister.createItems(Fargo_soul.MODID);

        MeltRocketBag = SoulItems.registerItem("melt_rocket_bag", MeltRocketBag::new);
        MarksmanEssence = SoulItems.registerItem("marksman_essence", MarksmanEssence::new);
        SharpshooterSoul = SoulItems.registerItem("sharpshooter_soul", SharpshooterSoul::new);

        //狂战士之魂
        BarbarianEssence = SoulItems.registerItem("barbarian_essence", BarbarianEssence::new);
        BerserkerGloves = SoulItems.registerItem("berserker_gloves", BerserkerGloves::new);
        CelestialShell = SoulItems.registerItem("celestial_shell", CelestialShell::new);
        FireGloves = SoulItems.registerItem("fire_gloves", FireGloves::new);
        StingerNecklace = SoulItems.registerItem("stinger_necklace", StingerNecklace::new);
        BerserkerSoul = SoulItems.registerItem("berserker_soul", BerserkerSoul::new);
        //森林之力
        WoodSoul = SoulItems.registerItem("wood_soul", WoodSoul::new);
        PineWoodSoul = SoulItems.registerItem("pine_wood_soul", PineWoodSoul::new);
        RosewoodSoul = SoulItems.registerItem("rose_wood_soul", RoseWoodSoul::new);
        EbonyWoodSoul = SoulItems.registerItem("ebony_wood_soul", EbonyWoodSoul::new);
        ShadowWoodSoul = SoulItems.registerItem("shadow_wood_soul", ShadowWoodSoul::new);
        PalmWoodSoul = SoulItems.registerItem("palm_wood_soul", PalmWoodSoul::new);
        PearlWoodSoul = SoulItems.registerItem("pearl_wood_soul", PearlWoodSoul::new);
        ForestPower = SoulItems.registerItem("forest_power", ForestPower::new);
        // 泰拉之力
        CopperSoul = SoulItems.registerItem("copper_soul", CopperSoul::new);
        TinSoul = SoulItems.registerItem("tin_soul", TinSoul::new);
        IronSoul = SoulItems.registerItem("iron_soul", IronSoul::new);
        LeadSoul = SoulItems.registerItem("lead_soul", LeadSoul::new);
        SilverSoul = SoulItems.registerItem("silver_soul", SilverSoul::new);
        TungstenSoul = SoulItems.registerItem("tungsten_soul", TungstenSoul::new);
        AshWoodSoul = SoulItems.registerItem("ash_wood_soul", AshWoodSoul::new);
        ObsidianSoul = SoulItems.registerItem("obsidian_soul", ObsidianSoul::new);
        TerraPower = SoulItems.registerItem("terra_power", TerraPower::new);
        // 大地之力
        AncientCobaltSoul = SoulItems.registerItem("ancient_cobalt_soul", AncientCobaltSoul::new);
        CobaltSoul = SoulItems.registerItem("cobalt_soul", CobaltSoul::new);
        PalladiumSoul = SoulItems.registerItem("palladium_soul", PalladiumSoul::new);
        MithrilSoul = SoulItems.registerItem("mithril_soul", MithrilSoul::new);
        OrichalcumSoul = SoulItems.registerItem("orichalcum_soul", OrichalcumSoul::new);
        AdamantiteSoul = SoulItems.registerItem("adamantite_soul", AdamantiteSoul::new);
        TitaniumSoul = SoulItems.registerItem("titanium_soul", TitaniumSoul::new);
        EarthPower = SoulItems.registerItem("earth_power", EarthPower::new);
        // 自然之力
        CrimsonSoul = SoulItems.registerItem("crimson_soul", CrimsonSoul::new);
        LavaSoul = SoulItems.registerItem("lava_soul", LavaSoul::new);
        RainCloudSoul = SoulItems.registerItem("rain_cloud_soul", RainCloudSoul::new);
        FrostSoul = SoulItems.registerItem("frost_soul", FrostSoul::new);
        GreenSoul = SoulItems.registerItem("green_soul", GreenSoul::new);
        MushroomSoul = SoulItems.registerItem("mushroom_soul", MushroomSoul::new);
        NaturePower = SoulItems.registerItem("nature_power", NaturePower::new);
        // 生命之力
        BeeSoul = SoulItems.registerItem("bee_soul", BeeSoul::new);
        BeetleSoul = SoulItems.registerItem("beetle_soul", BeetleSoul::new);
        PumpkinSoul = SoulItems.registerItem("pumpkin_soul", PumpkinSoul::new);
        SpiderSoul = SoulItems.registerItem("spider_soul", SpiderSoul::new);
        CactusSoul = SoulItems.registerItem("cactus_soul", CactusSoul::new);
        TurtleSoul = SoulItems.registerItem("turtle_soul", TurtleSoul::new);
        LifePower = SoulItems.registerItem("life_power", LifePower::new);
        // 心灵之力
        ForbiddenSoul = SoulItems.registerItem("forbidden_soul", ForbiddenSoul::new);
        HolySoul = SoulItems.registerItem("holy_soul", HolySoul::new);
        AncientHolySoul = SoulItems.registerItem("ancient_holy_soul", AncientHolySoul::new);
        TekeSoul = SoulItems.registerItem("teke_soul", TekeSoul::new);
        GhostSoul = SoulItems.registerItem("ghost_soul", GhostSoul::new);
        SpiritPower = SoulItems.registerItem("spirit_power", SpiritPower::new);
        // 死亡之力
        AncientShadowSoul = SoulItems.registerItem("ancient_shadow_soul", AncientShadowSoul::new);
        NinjaSoul = SoulItems.registerItem("ninja_soul", NinjaSoul::new);
        CrystalAssassinSoul = SoulItems.registerItem("crystal_assassin_soul", CrystalAssassinSoul::new);
        DarkArtistSoul = SoulItems.registerItem("dark_artist_soul", DarkArtistSoul::new);
        GloomySoul = SoulItems.registerItem("gloomy_soul", GloomySoul::new);
        NecromancerSoul = SoulItems.registerItem("necromancer_soul", NecromancerSoul::new);
        MonkSoul = SoulItems.registerItem("monk_soul", MonkSoul::new);
        PenetratingNinjaSoul = SoulItems.registerItem("penetrating_ninja_soul", PenetratingNinjaSoul::new);
        DeathPower = SoulItems.registerItem("death_power", DeathPower::new);
        // 意志之力
        GoldSoul = SoulItems.registerItem("gold_soul", GoldSoul::new);
        PlatinumSoul = SoulItems.registerItem("platinum_soul", PlatinumSoul::new);
        GladiatorSoul = SoulItems.registerItem("gladiator_soul", GladiatorSoul::new);
        RedRidingSoul = SoulItems.registerItem("red_riding_soul", RedRidingSoul::new);
        ValhallaKnightSoul = SoulItems.registerItem("valhalla_knight_soul", ValhallaKnightSoul::new);
        WillPower = SoulItems.registerItem("will_power", WillPower::new);
        // 宇宙之力
        MeteorSoul = SoulItems.registerItem("meteor_soul", MeteorSoul::new);
        WizardSoul = SoulItems.registerItem("wizard_soul", WizardSoul::new);
        BlazeSoul = SoulItems.registerItem("blaze_soul", BlazeSoul::new);
        StardustSoul = SoulItems.registerItem("stardust_soul", StardustSoul::new);
        NebulaSoul = SoulItems.registerItem("nebula_soul", NebulaSoul::new);
        VortexSoul = SoulItems.registerItem("vortex_soul", VortexSoul::new);
        CosmicPower = SoulItems.registerItem("cosmic_power", CosmicPower::new);
        // 泰拉之魂
        TerraSoul = SoulItems.registerItem("terra_soul", TerraSoul::new);
    }
}

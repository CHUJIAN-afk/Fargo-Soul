package First.fargo_soul.register;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.item.TerraSoul;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.*;
import First.fargo_soul.item.terraSoul.cosmicPower.*;
import First.fargo_soul.item.terraSoul.deathPower.*;
import First.fargo_soul.item.terraSoul.earthPower.*;
import First.fargo_soul.item.terraSoul.forestPower.*;
import First.fargo_soul.item.terraSoul.lifePower.*;
import First.fargo_soul.item.terraSoul.naturePower.*;
import First.fargo_soul.item.terraSoul.spiritPower.*;
import First.fargo_soul.item.terraSoul.terraPower.*;
import First.fargo_soul.item.terraSoul.willPower.*;
import First.fargo_soul.utils.RegisterUtils;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegister {

    private static final DeferredRegister.Items Register = DeferredRegister.createItems(FargoSoul.MODID);

    public static final DeferredItem<Item> GreenCrystal = Register.registerItem("green_crystal", Item::new);
    public static final DeferredItem<Item> Soul = Register.registerItem("soul", properties -> new Item(properties.rarity(Rarity.EPIC).fireResistant()));

    public static final DeferredItem<BlockItem> CosmicCrucibleBlockItem = Register.registerSimpleBlockItem(BlockRegister.CosmicCrucible,new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));

    public static final DeferredItem<SoulItem> WoodSoulItem = register("wood_soul", WoodSoul.class);
    public static final DeferredItem<SoulItem> PineWoodSoulItem = register("pine_wood_soul", PineWoodSoul.class);
    public static final DeferredItem<SoulItem> RoseWoodSoulItem = register("rose_wood_soul", RoseWoodSoul.class);
    public static final DeferredItem<SoulItem> EbonyWoodSoulItem = register("ebony_wood_soul", EbonyWoodSoul.class);
    public static final DeferredItem<SoulItem> ShadowWoodSoulItem = register("shadow_wood_soul", ShadowWoodSoul.class);
    public static final DeferredItem<SoulItem> PalmWoodSoulItem = register("palm_wood_soul", PalmWoodSoul.class);
    public static final DeferredItem<SoulItem> PearlWoodSoulItem = register("pearl_wood_soul", PearlWoodSoul.class);
    public static final DeferredItem<SoulItem> ForestPowerItem = register("forest_power", ForestPower.class);

    public static final DeferredItem<SoulItem> CopperSoulItem = register("copper_soul", CopperSoul.class);
    public static final DeferredItem<SoulItem> TinSoulItem = register("tin_soul", TinSoul.class);
    public static final DeferredItem<SoulItem> IronSoulItem = register("iron_soul", IronSoul.class);
    public static final DeferredItem<SoulItem> LeadSoulItem = register("lead_soul", LeadSoul.class);
    public static final DeferredItem<SoulItem> SilverSoulItem = register("silver_soul", SilverSoul.class);
    public static final DeferredItem<SoulItem> TungstenSoulItem = register("tungsten_soul", TungstenSoul.class);
    public static final DeferredItem<SoulItem> ObsidianSoulItem = register("obsidian_soul", ObsidianSoul.class);
    public static final DeferredItem<SoulItem> TerraPowerItem = register("terra_power", TerraPower.class);

    public static final DeferredItem<SoulItem> CobaltSoulItem = register("cobalt_soul", CobaltSoul.class);
    public static final DeferredItem<SoulItem> PalladiumSoulItem = register("palladium_soul", PalladiumSoul.class);
    public static final DeferredItem<SoulItem> MithrilSoulItem = register("mithril_soul", MithrilSoul.class);
    public static final DeferredItem<SoulItem> OrichalcumSoulItem = register("orichalcum_soul", OrichalcumSoul.class);
    public static final DeferredItem<SoulItem> AdamantiteSoulItem = register("adamantite_soul", AdamantiteSoul.class);
    public static final DeferredItem<SoulItem> TitaniumSoulItem = register("titanium_soul", TitaniumSoul.class);
    public static final DeferredItem<SoulItem> EarthPowerItem = register("earth_power", EarthPower.class);

    public static final DeferredItem<SoulItem> CrimsonSoulItem = register("crimson_soul", CrimsonSoul.class);
    public static final DeferredItem<SoulItem> LavaSoulItem = register("lava_soul", LavaSoul.class);
    public static final DeferredItem<SoulItem> RainCloudSoulItem = register("rain_cloud_soul", RainCloudSoul.class);
    public static final DeferredItem<SoulItem> FrostSoulItem = register("frost_soul", FrostSoul.class);
    public static final DeferredItem<SoulItem> GreenSoulItem = register("green_soul", GreenSoul.class);
    public static final DeferredItem<SoulItem> MushroomSoulItem = register("mushroom_soul", MushroomSoul.class);
    public static final DeferredItem<SoulItem> NaturePowerItem = register("nature_power", NaturePower.class);

    public static final DeferredItem<SoulItem> BeeSoulItem = register("bee_soul", BeeSoul.class);
    public static final DeferredItem<SoulItem> BeetleSoulItem = register("beetle_soul", BeetleSoul.class);
    public static final DeferredItem<SoulItem> PumpkinSoulItem = register("pumpkin_soul", PumpkinSoul.class);
    public static final DeferredItem<SoulItem> SpiderSoulItem = register("spider_soul", SpiderSoul.class);
    public static final DeferredItem<SoulItem> TurtleSoulItem = register("turtle_soul", TurtleSoul.class);
    public static final DeferredItem<SoulItem> LifePowerItem = register("life_power", LifePower.class);

    public static final DeferredItem<SoulItem> ForbiddenSoulItem = register("forbidden_soul", ForbiddenSoul.class);
    public static final DeferredItem<SoulItem> HolySoulItem = register("holy_soul", HolySoul.class);
    public static final DeferredItem<SoulItem> AncientHolySoulItem = register("ancient_holy_soul", AncientHolySoul.class);
    public static final DeferredItem<SoulItem> TekeSoulItem = register("teke_soul", TekeSoul.class);
    public static final DeferredItem<SoulItem> GhostSoulItem = register("ghost_soul", GhostSoul.class);
    public static final DeferredItem<SoulItem> SpiritPowerItem = register("spirit_power", SpiritPower.class);

    public static final DeferredItem<SoulItem> AncientShadowSoulItem = register("ancient_shadow_soul", AncientShadowSoul.class);
    public static final DeferredItem<SoulItem> NinjaSoulItem = register("ninja_soul", NinjaSoul.class);
    public static final DeferredItem<SoulItem> CrystalAssassinSoulItem = register("crystal_assassin_soul", CrystalAssassinSoul.class);
    public static final DeferredItem<SoulItem> DarkArtistSoulItem = register("dark_artist_soul", DarkArtistSoul.class);
    public static final DeferredItem<SoulItem> GloomySoulItem = register("gloomy_soul", GloomySoul.class);
    public static final DeferredItem<SoulItem> NecromancerSoulItem = register("necromancer_soul", NecromancerSoul.class);
    public static final DeferredItem<SoulItem> PenetratingNinjaSoulItem = register("penetrating_ninja_soul", PenetratingNinjaSoul.class);
    public static final DeferredItem<SoulItem> DeathPowerItem = register("death_power", DeathPower.class);

    public static final DeferredItem<SoulItem> GoldSoulItem = register("gold_soul", GoldSoul.class);
    public static final DeferredItem<SoulItem> PlatinumSoulItem = register("platinum_soul", PlatinumSoul.class);
    public static final DeferredItem<SoulItem> GladiatorSoulItem = register("gladiator_soul", GladiatorSoul.class);
    public static final DeferredItem<SoulItem> RedRidingSoulItem = register("red_riding_soul", RedRidingSoul.class);
    public static final DeferredItem<SoulItem> ValhallaKnightSoulItem = register("valhalla_knight_soul", ValhallaKnightSoul.class);
    public static final DeferredItem<SoulItem> WillPowerItem = register("will_power", WillPower.class);

    public static final DeferredItem<SoulItem> MeteorSoulItem = register("meteor_soul", MeteorSoul.class);
    public static final DeferredItem<SoulItem> WizardSoulItem = register("wizard_soul", WizardSoul.class);
    public static final DeferredItem<SoulItem> BlazeSoulItem = register("blaze_soul", BlazeSoul.class);
    public static final DeferredItem<SoulItem> StardustSoulItem = register("stardust_soul", StardustSoul.class);
    public static final DeferredItem<SoulItem> NebulaSoulItem = register("nebula_soul", NebulaSoul.class);
    public static final DeferredItem<SoulItem> VortexSoulItem = register("vortex_soul", VortexSoul.class);
    public static final DeferredItem<SoulItem> CosmicPowerItem = register("cosmic_power", CosmicPower.class);

    public static final DeferredItem<SoulItem> TerraSoulItem = register("terra_soul", TerraSoul.class);

    private static <T extends SoulItem> DeferredItem<SoulItem> register(String name, Class<T> tClass) {
        return RegisterUtils.registerItem(Register, name, tClass);
    }

    public static void register(IEventBus eventBus) {
        Register.register(eventBus);
    }

}

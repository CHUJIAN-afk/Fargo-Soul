package first.fargo_soul.register;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.item.TerraSoul;
import first.fargo_soul.common.item.terraSoul.*;
import first.fargo_soul.common.item.terraSoul.cosmicPower.*;
import first.fargo_soul.common.item.terraSoul.deathPower.*;
import first.fargo_soul.common.item.terraSoul.earthPower.*;
import first.fargo_soul.common.item.terraSoul.forestPower.*;
import first.fargo_soul.common.item.terraSoul.lifePower.*;
import first.fargo_soul.common.item.terraSoul.naturePower.*;
import first.fargo_soul.common.item.terraSoul.spiritPower.*;
import first.fargo_soul.common.item.terraSoul.terraPower.*;
import first.fargo_soul.common.item.terraSoul.willPower.*;
import first.lyra.common.dataComponent.LyraRarity;
import first.lyra.register.LyraDataComponentRegister;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FargoSoulItemRegister {

    public static final Map<ItemLike, List<Component>> JEI_INFO = new LinkedHashMap<>();
    public static final ItemEntry<TerraSoul> TerraSoulItem =
            register("terra_soul", TerraSoul::new, LyraRarity.Rainbow)
                    .recipe((ctx, output) -> shapeless(output, "terra_soul", FargoSoulItemRegister.TerraSoulItem, FargoSoulItemRegister.CosmicPowerItem, FargoSoulItemRegister.DeathPowerItem, FargoSoulItemRegister.TerraPowerItem, FargoSoulItemRegister.ForestPowerItem, FargoSoulItemRegister.EarthPowerItem, FargoSoulItemRegister.NaturePowerItem, FargoSoulItemRegister.LifePowerItem, FargoSoulItemRegister.SpiritPowerItem, FargoSoulItemRegister.WillPowerItem))
                    .tag(FargoSoulItemTagsRegister.SectionSoul)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    // ============================================================
    // 宇宙之力 · 魔石分段
    // ============================================================
    public static final ItemEntry<CosmicPower> CosmicPowerItem =
            register("cosmic_power", CosmicPower::new, LyraRarity.Purple)
                    .recipe((ctx, output) -> shapeless(output, "cosmic_power", FargoSoulItemRegister.CosmicPowerItem, FargoSoulItemRegister.BlazeSoulItem, FargoSoulItemRegister.METEOR_SOUL, FargoSoulItemRegister.NebulaSoulItem, FargoSoulItemRegister.StardustSoulItem, FargoSoulItemRegister.VortexSoulItem, FargoSoulItemRegister.WizardSoulItem))
                    .tag(FargoSoulItemTagsRegister.SectionPower)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<BlazeSoul> BlazeSoulItem =
            register("blaze_soul", BlazeSoul::new, new LyraRarity(0xFF691F))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<MeteorSoul> METEOR_SOUL =
            register("meteor_soul", MeteorSoul::new, new LyraRarity(0x654A52))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<NebulaSoul> NebulaSoulItem =
            register("nebula_soul", NebulaSoul::new, new LyraRarity(0xFF7AE7))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<StardustSoul> StardustSoulItem =
            register("stardust_soul", StardustSoul::new, new LyraRarity(0x00ADF1))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<VortexSoul> VortexSoulItem =
            register("vortex_soul", VortexSoul::new, new LyraRarity(0x00F4A8))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<WizardSoul> WizardSoulItem =
            register("wizard_soul", WizardSoul::new, LyraRarity.Blue)
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    // ============================================================
    // 死亡之力 · 魔石分段
    // ============================================================
    public static final ItemEntry<DeathPower> DeathPowerItem =
            register("death_power", DeathPower::new, LyraRarity.Purple)
                    .recipe((ctx, output) -> shapeless(output, "death_power", FargoSoulItemRegister.DeathPowerItem, FargoSoulItemRegister.AncientShadowSoulItem, FargoSoulItemRegister.NinjaSoulItem, FargoSoulItemRegister.CrystalAssassinSoulItem, FargoSoulItemRegister.DarkArtistSoulItem, FargoSoulItemRegister.GloomySoulItem, FargoSoulItemRegister.NecromancerSoulItem, FargoSoulItemRegister.PenetratingNinjaSoulItem))
                    .tag(FargoSoulItemTagsRegister.SectionPower)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<AncientShadowSoul> AncientShadowSoulItem =
            register("ancient_shadow_soul", AncientShadowSoul::new, new LyraRarity(0x5E55DC))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<NinjaSoul> NinjaSoulItem =
            register("ninja_soul", NinjaSoul::new, new LyraRarity(0x48494D))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<CrystalAssassinSoul> CrystalAssassinSoulItem =
            register("crystal_assassin_soul", CrystalAssassinSoul::new, new LyraRarity(0xCF249D))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<DarkArtistSoul> DarkArtistSoulItem =
            register("dark_artist_soul", DarkArtistSoul::new, new LyraRarity(0x9C59B2))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<GloomySoul> GloomySoulItem =
            register("gloomy_soul", GloomySoul::new, new LyraRarity(0x644D75))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<NecromancerSoul> NecromancerSoulItem =
            register("necromancer_soul", NecromancerSoul::new, new LyraRarity(0x565642))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<PenetratingNinjaSoul> PenetratingNinjaSoulItem =
            register("penetrating_ninja_soul", PenetratingNinjaSoul::new, new LyraRarity(0x945B0A))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    // ============================================================
    // 泰拉之力 · 魔石分段
    // ============================================================
    public static final ItemEntry<TerraPower> TerraPowerItem =
            register("terra_power", TerraPower::new, LyraRarity.Purple)
                    .recipe((ctx, output) -> shapeless(output, "terra_power", FargoSoulItemRegister.TerraPowerItem, FargoSoulItemRegister.CopperSoulItem, FargoSoulItemRegister.TinSoulItem, FargoSoulItemRegister.IronSoulItem, FargoSoulItemRegister.LeadSoulItem, FargoSoulItemRegister.SilverSoulItem, FargoSoulItemRegister.TungstenSoulItem, FargoSoulItemRegister.ObsidianSoulItem))
                    .tag(FargoSoulItemTagsRegister.SectionPower)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<CopperSoul> CopperSoulItem =
            register("copper_soul", CopperSoul::new, new LyraRarity(0xD56617))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<TinSoul> TinSoulItem =
            register("tin_soul", TinSoul::new, new LyraRarity(0xA28B4E))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<IronSoul> IronSoulItem =
            register("iron_soul", IronSoul::new, new LyraRarity(0x988E83))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<LeadSoul> LeadSoulItem =
            register("lead_soul", LeadSoul::new, new LyraRarity(0x46485D))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<SilverSoul> SilverSoulItem =
            register("silver_soul", SilverSoul::new, new LyraRarity(0xB4B4CD))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<TungstenSoul> TungstenSoulItem =
            register("tungsten_soul", TungstenSoul::new, new LyraRarity(0xB0D2B2))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<ObsidianSoul> ObsidianSoulItem =
            register("obsidian_soul", ObsidianSoul::new, new LyraRarity(0x453C75))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    // ============================================================
    // 森林之力 · 魔石分段
    // ============================================================
    public static final ItemEntry<ForestPower> ForestPowerItem =
            register("forest_power", ForestPower::new, LyraRarity.Purple)
                    .recipe((ctx, output) -> shapeless(output, "forest_power", FargoSoulItemRegister.ForestPowerItem, FargoSoulItemRegister.WoodSoulItem, FargoSoulItemRegister.PineWoodSoulItem, FargoSoulItemRegister.RoseWoodSoulItem, FargoSoulItemRegister.EbonyWoodSoulItem, FargoSoulItemRegister.ShadowWoodSoulItem, FargoSoulItemRegister.PalmWoodSoulItem, FargoSoulItemRegister.PearlWoodSoulItem))
                    .tag(FargoSoulItemTagsRegister.SectionPower)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<WoodSoul> WoodSoulItem =
            register("wood_soul", WoodSoul::new, new LyraRarity(0x986B48))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<PineWoodSoul> PineWoodSoulItem =
            register("pine_wood_soul", PineWoodSoul::new, new LyraRarity(0x8B7464))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<RoseWoodSoul> RoseWoodSoulItem =
            register("rose_wood_soul", RoseWoodSoul::new, new LyraRarity(0xB56C64))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<EbonyWoodSoul> EbonyWoodSoulItem =
            register("ebony_wood_soul", EbonyWoodSoul::new, new LyraRarity(0x645A8D))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<ShadowWoodSoul> ShadowWoodSoulItem =
            register("shadow_wood_soul", ShadowWoodSoul::new, new LyraRarity(0x586876))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<PalmWoodSoul> PalmWoodSoulItem =
            register("palm_wood_soul", PalmWoodSoul::new, new LyraRarity(0xB78D56))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<PearlWoodSoul> PearlWoodSoulItem =
            register("pearl_wood_soul", PearlWoodSoul::new, new LyraRarity(0xAD9A5F))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    // ============================================================
    // 大地之力 · 魔石分段
    // ============================================================
    public static final ItemEntry<EarthPower> EarthPowerItem =
            register("earth_power", EarthPower::new, LyraRarity.Purple)
                    .recipe((ctx, output) -> shapeless(output, "earth_power", FargoSoulItemRegister.EarthPowerItem, FargoSoulItemRegister.CobaltSoulItem, FargoSoulItemRegister.PalladiumSoulItem, FargoSoulItemRegister.MithrilSoulItem, FargoSoulItemRegister.OrichalcumSoulItem, FargoSoulItemRegister.AdamantiteSoulItem, FargoSoulItemRegister.TitaniumSoulItem))
                    .tag(FargoSoulItemTagsRegister.SectionPower)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<CobaltSoul> CobaltSoulItem =
            register("cobalt_soul", CobaltSoul::new, new LyraRarity(0x3DA4C4))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<PalladiumSoul> PalladiumSoulItem =
            register("palladium_soul", PalladiumSoul::new, new LyraRarity(0xF5AC28))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<MithrilSoul> MithrilSoulItem =
            register("mithril_soul", MithrilSoul::new, new LyraRarity(0x9DD290))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<OrichalcumSoul> OrichalcumSoulItem =
            register("orichalcum_soul", OrichalcumSoul::new, new LyraRarity(0xEB3291))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<AdamantiteSoul> AdamantiteSoulItem =
            register("adamantite_soul", AdamantiteSoul::new, new LyraRarity(0xDD557D))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<TitaniumSoul> TitaniumSoulItem =
            register("titanium_soul", TitaniumSoul::new, new LyraRarity(0x828C88))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    // ============================================================
    // 自然之力 · 魔石分段
    // ============================================================
    public static final ItemEntry<NaturePower> NaturePowerItem =
            register("nature_power", NaturePower::new, LyraRarity.Purple)
                    .recipe((ctx, output) -> shapeless(output, "nature_power", FargoSoulItemRegister.NaturePowerItem, FargoSoulItemRegister.CrimsonSoulItem, FargoSoulItemRegister.LavaSoulItem, FargoSoulItemRegister.RainCloudSoulItem, FargoSoulItemRegister.FrostSoulItem, FargoSoulItemRegister.GreenSoulItem, FargoSoulItemRegister.MushroomSoulItem))
                    .tag(FargoSoulItemTagsRegister.SectionPower)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<CrimsonSoul> CrimsonSoulItem =
            register("crimson_soul", CrimsonSoul::new, new LyraRarity(0xCA3448))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<LavaSoul> LavaSoulItem =
            register("lava_soul", LavaSoul::new, new LyraRarity(0xC32925))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<RainCloudSoul> RainCloudSoulItem =
            register("rain_cloud_soul", RainCloudSoul::new, new LyraRarity(0xFFFF33))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<FrostSoul> FrostSoulItem =
            register("frost_soul", FrostSoul::new, new LyraRarity(0x78BDB9))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<GreenSoul> GreenSoulItem =
            register("green_soul", GreenSoul::new, new LyraRarity(0x1D8A00))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<MushroomSoul> MushroomSoulItem =
            register("mushroom_soul", MushroomSoul::new, new LyraRarity(0x0089F7))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    // ============================================================
    // 生命之力 · 魔石分段
    // ============================================================
    public static final ItemEntry<LifePower> LifePowerItem =
            register("life_power", LifePower::new, LyraRarity.Purple)
                    .recipe((ctx, output) -> shapeless(output, "life_power", FargoSoulItemRegister.LifePowerItem, FargoSoulItemRegister.BeeSoulItem, FargoSoulItemRegister.BeetleSoulItem, FargoSoulItemRegister.PumpkinSoulItem, FargoSoulItemRegister.SpiderSoulItem, FargoSoulItemRegister.TurtleSoulItem))
                    .tag(FargoSoulItemTagsRegister.SectionPower)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<BeeSoul> BeeSoulItem =
            register("bee_soul", BeeSoul::new, new LyraRarity(0xFFC300))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<BeetleSoul> BeetleSoulItem =
            register("beetle_soul", BeetleSoul::new, new LyraRarity(0x6D5B86))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<PumpkinSoul> PumpkinSoulItem =
            register("pumpkin_soul", PumpkinSoul::new, new LyraRarity(0xE56500))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<SpiderSoul> SpiderSoulItem =
            register("spider_soul", SpiderSoul::new, new LyraRarity(0x65483F))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<TurtleSoul> TurtleSoulItem =
            register("turtle_soul", TurtleSoul::new, new LyraRarity(0xE86024))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    // ============================================================
    // 心灵之力 · 魔石分段
    // ============================================================
    public static final ItemEntry<SpiritPower> SpiritPowerItem =
            register("spirit_power", SpiritPower::new, LyraRarity.Purple)
                    .recipe((ctx, output) -> shapeless(output, "spirit_power", FargoSoulItemRegister.SpiritPowerItem, FargoSoulItemRegister.ForbiddenSoulItem, FargoSoulItemRegister.HolySoulItem, FargoSoulItemRegister.AncientHolySoulItem, FargoSoulItemRegister.TekeSoulItem, FargoSoulItemRegister.GhostSoulItem))
                    .tag(FargoSoulItemTagsRegister.SectionPower)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<ForbiddenSoul> ForbiddenSoulItem =
            register("forbidden_soul", ForbiddenSoul::new, new LyraRarity(0xE8B300))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<HolySoul> HolySoulItem =
            register("holy_soul", HolySoul::new, new LyraRarity(0x968562))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<AncientHolySoul> AncientHolySoulItem =
            register("ancient_holy_soul", AncientHolySoul::new, new LyraRarity(0x968564))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<TekeSoul> TekeSoulItem =
            register("teke_soul", TekeSoul::new, new LyraRarity(0x53A71E))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<GhostSoul> GhostSoulItem =
            register("ghost_soul", GhostSoul::new, new LyraRarity(0xABCCFE))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    // ============================================================
    // 意志之力 · 魔石分段
    // ============================================================
    public static final ItemEntry<WillPower> WillPowerItem =
            register("will_power", WillPower::new, LyraRarity.Purple)
                    .recipe((ctx, output) -> shapeless(output, "will_power", FargoSoulItemRegister.WillPowerItem, FargoSoulItemRegister.GoldSoulItem, FargoSoulItemRegister.PlatinumSoulItem, FargoSoulItemRegister.GladiatorSoulItem, FargoSoulItemRegister.RedRidingSoulItem, FargoSoulItemRegister.ValhallaKnightSoulItem))
                    .tag(FargoSoulItemTagsRegister.SectionPower)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<GoldSoul> GoldSoulItem =
            register("gold_soul", GoldSoul::new, new LyraRarity(0xE7B21C))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<PlatinumSoul> PlatinumSoulItem =
            register("platinum_soul", PlatinumSoul::new, new LyraRarity(0x7F96B9))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<GladiatorSoul> GladiatorSoulItem =
            register("gladiator_soul", GladiatorSoul::new, new LyraRarity(0x9C924E))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<RedRidingSoul> RedRidingSoulItem =
            register("red_riding_soul", RedRidingSoul::new, new LyraRarity(0xC01B3C))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();


    public static final ItemEntry<ValhallaKnightSoul> ValhallaKnightSoulItem =
            register("valhalla_knight_soul", ValhallaKnightSoul::new, new LyraRarity(0x93651E))
                    .tag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .defaultModel()
                    .tag(FargoSoulItemTagsRegister.Curio)
                    .register();

    public static void init() {
        JEI_INFO.clear();
        JEI_INFO.computeIfAbsent(BlazeSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.blaze_souljei.description.1"));
        JEI_INFO.computeIfAbsent(METEOR_SOUL, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.meteor_souljei.description.1"));
        JEI_INFO.computeIfAbsent(METEOR_SOUL, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.meteor_souljei.description.2"));
        JEI_INFO.computeIfAbsent(NebulaSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.nebula_souljei.description.1"));
        JEI_INFO.computeIfAbsent(NebulaSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.nebula_souljei.description.2"));
        JEI_INFO.computeIfAbsent(StardustSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.stardust_souljei.description.1"));
        JEI_INFO.computeIfAbsent(StardustSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.stardust_souljei.description.2"));
        JEI_INFO.computeIfAbsent(VortexSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.vortex_souljei.description.1"));
        JEI_INFO.computeIfAbsent(VortexSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.vortex_souljei.description.2"));
        JEI_INFO.computeIfAbsent(WizardSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.wizard_souljei.description.1"));
        JEI_INFO.computeIfAbsent(AncientShadowSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.ancient_shadow_souljei.description.1"));
        JEI_INFO.computeIfAbsent(AncientShadowSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.ancient_shadow_souljei.description.2"));
        JEI_INFO.computeIfAbsent(NinjaSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.ninja_souljei.description.1"));
        JEI_INFO.computeIfAbsent(NinjaSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.ninja_souljei.description.2"));
        JEI_INFO.computeIfAbsent(CrystalAssassinSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.crystal_assassin_souljei.description.1"));
        JEI_INFO.computeIfAbsent(DarkArtistSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.dark_artist_souljei.description.1"));
        JEI_INFO.computeIfAbsent(DarkArtistSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.dark_artist_souljei.description.2"));
        JEI_INFO.computeIfAbsent(GloomySoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.gloomy_souljei.description.1"));
        JEI_INFO.computeIfAbsent(GloomySoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.gloomy_souljei.description.2"));
        JEI_INFO.computeIfAbsent(NecromancerSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.necromancer_souljei.description.1"));
        JEI_INFO.computeIfAbsent(PenetratingNinjaSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.penetrating_ninja_souljei.description.1"));
        JEI_INFO.computeIfAbsent(CopperSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.copper_souljei.description.1"));
        JEI_INFO.computeIfAbsent(CopperSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.copper_souljei.description.2"));
        JEI_INFO.computeIfAbsent(TinSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.tin_souljei.description.1"));
        JEI_INFO.computeIfAbsent(TinSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.tin_souljei.description.2"));
        JEI_INFO.computeIfAbsent(IronSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.iron_souljei.description.1"));
        JEI_INFO.computeIfAbsent(IronSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.iron_souljei.description.2"));
        JEI_INFO.computeIfAbsent(LeadSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.lead_souljei.description.1"));
        JEI_INFO.computeIfAbsent(SilverSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.silver_souljei.description.1"));
        JEI_INFO.computeIfAbsent(SilverSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.silver_souljei.description.2"));
        JEI_INFO.computeIfAbsent(TungstenSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.tungsten_souljei.description.1"));
        JEI_INFO.computeIfAbsent(TungstenSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.tungsten_souljei.description.2"));
        JEI_INFO.computeIfAbsent(ObsidianSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.obsidian_souljei.description.1"));
        JEI_INFO.computeIfAbsent(ObsidianSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.obsidian_souljei.description.2"));
        JEI_INFO.computeIfAbsent(WoodSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.wood_souljei.description.1"));
        JEI_INFO.computeIfAbsent(PineWoodSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.pine_wood_souljei.description.1"));
        JEI_INFO.computeIfAbsent(RoseWoodSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.rose_wood_souljei.description.1"));
        JEI_INFO.computeIfAbsent(EbonyWoodSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.ebony_wood_souljei.description.1"));
        JEI_INFO.computeIfAbsent(ShadowWoodSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.shadow_wood_souljei.description.1"));
        JEI_INFO.computeIfAbsent(PalmWoodSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.palm_wood_souljei.description.1"));
        JEI_INFO.computeIfAbsent(PearlWoodSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.pearl_wood_souljei.description.1"));
        JEI_INFO.computeIfAbsent(CobaltSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.cobalt_souljei.description.1"));
        JEI_INFO.computeIfAbsent(CobaltSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.cobalt_souljei.description.2"));
        JEI_INFO.computeIfAbsent(PalladiumSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.palladium_souljei.description.1"));
        JEI_INFO.computeIfAbsent(MithrilSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.mithril_souljei.description.1"));
        JEI_INFO.computeIfAbsent(MithrilSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.mithril_souljei.description.2"));
        JEI_INFO.computeIfAbsent(OrichalcumSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.orichalcum_souljei.description.1"));
        JEI_INFO.computeIfAbsent(OrichalcumSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.orichalcum_souljei.description.2"));
        JEI_INFO.computeIfAbsent(AdamantiteSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.adamantite_souljei.description.1"));
        JEI_INFO.computeIfAbsent(TitaniumSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.titanium_souljei.description.1"));
        JEI_INFO.computeIfAbsent(CrimsonSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.crimson_souljei.description.1"));
        JEI_INFO.computeIfAbsent(LavaSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.lava_souljei.description.1"));
        JEI_INFO.computeIfAbsent(LavaSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.lava_souljei.description.2"));
        JEI_INFO.computeIfAbsent(RainCloudSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.rain_cloud_souljei.description.1"));
        JEI_INFO.computeIfAbsent(RainCloudSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.rain_cloud_souljei.description.2"));
        JEI_INFO.computeIfAbsent(FrostSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.frost_souljei.description.1"));
        JEI_INFO.computeIfAbsent(GreenSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.green_souljei.description.1"));
        JEI_INFO.computeIfAbsent(GreenSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.green_souljei.description.2"));
        JEI_INFO.computeIfAbsent(MushroomSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.mushroom_souljei.description.1"));
        JEI_INFO.computeIfAbsent(BeeSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.bee_souljei.description.1"));
        JEI_INFO.computeIfAbsent(BeetleSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.beetle_souljei.description.1"));
        JEI_INFO.computeIfAbsent(BeetleSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.beetle_souljei.description.2"));
        JEI_INFO.computeIfAbsent(PumpkinSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.pumpkin_souljei.description.1"));
        JEI_INFO.computeIfAbsent(SpiderSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.spider_souljei.description.1"));
        JEI_INFO.computeIfAbsent(TurtleSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.turtle_souljei.description.1"));
        JEI_INFO.computeIfAbsent(ForbiddenSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.forbidden_souljei.description.1"));
        JEI_INFO.computeIfAbsent(HolySoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.holy_souljei.description.1"));
        JEI_INFO.computeIfAbsent(AncientHolySoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.ancient_holy_souljei.description.1"));
        JEI_INFO.computeIfAbsent(AncientHolySoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.ancient_holy_souljei.description.2"));
        JEI_INFO.computeIfAbsent(TekeSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.teke_souljei.description.1"));
        JEI_INFO.computeIfAbsent(GhostSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.ghost_souljei.description.1"));
        JEI_INFO.computeIfAbsent(GhostSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.ghost_souljei.description.2"));
        JEI_INFO.computeIfAbsent(GoldSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.gold_souljei.description.1"));
        JEI_INFO.computeIfAbsent(GoldSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.gold_souljei.description.2"));
        JEI_INFO.computeIfAbsent(PlatinumSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.platinum_souljei.description.1"));
        JEI_INFO.computeIfAbsent(PlatinumSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.platinum_souljei.description.2"));
        JEI_INFO.computeIfAbsent(GladiatorSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.gladiator_souljei.description.1"));
        JEI_INFO.computeIfAbsent(GladiatorSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.gladiator_souljei.description.2"));
        JEI_INFO.computeIfAbsent(RedRidingSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.red_riding_souljei.description.1"));
        JEI_INFO.computeIfAbsent(ValhallaKnightSoulItem, item -> new ArrayList<>()).add(Component.translatable("item.fargo_soul.valhalla_knight_souljei.description.1"));
    }

    private static <T extends Item> ItemBuilder<T, Registrate> register(String name, NonNullFunction<Item.Properties, T> factory, LyraRarity rarity) {
        return FargoSoul.REGISTRATE.item(name, factory).properties(properties -> properties.stacksTo(1).fireResistant().component(LyraDataComponentRegister.RARITY, rarity));
    }

    private static void shapeless(RecipeOutput output, String id, ItemLike result, ItemLike... parts) {
        ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result);
        for (ItemLike part : parts) {
            builder.requires(part);
        }
        builder.unlockedBy("has_" + parts[0].asItem(), InventoryChangeTrigger.TriggerInstance.hasItems(parts[0]));
        builder.save(output, FargoSoul.rl(id));
    }
}

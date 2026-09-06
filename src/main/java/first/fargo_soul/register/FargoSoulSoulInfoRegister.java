package first.fargo_soul.register;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.item.terraSoul.CosmicPower;
import first.fargo_soul.common.item.terraSoul.EarthPower;
import first.fargo_soul.common.item.terraSoul.ForestPower;
import first.fargo_soul.common.item.terraSoul.LifePower;
import first.fargo_soul.common.item.terraSoul.WillPower;
import first.fargo_soul.common.item.terraSoul.TerraPower;
import first.fargo_soul.common.item.terraSoul.forestPower.*;
import first.fargo_soul.common.item.terraSoul.earthPower.CobaltSoul;
import first.fargo_soul.common.item.terraSoul.earthPower.MithrilSoul;
import first.fargo_soul.common.item.terraSoul.earthPower.OrichalcumSoul;
import first.fargo_soul.common.item.terraSoul.earthPower.PalladiumSoul;
import first.fargo_soul.common.item.terraSoul.earthPower.TitaniumSoul;
import first.fargo_soul.common.item.terraSoul.willPower.RedRidingSoul;
import first.fargo_soul.common.item.terraSoul.lifePower.BeetleSoul;
import first.fargo_soul.common.item.terraSoul.naturePower.CrimsonSoul;
import first.fargo_soul.common.item.terraSoul.naturePower.FrostSoul;
import first.fargo_soul.common.item.terraSoul.naturePower.RainCloudSoul;
import first.fargo_soul.common.item.terraSoul.spiritPower.HolySoul;
import first.fargo_soul.common.item.terraSoul.spiritPower.ForbiddenSoul;
import first.fargo_soul.common.item.terraSoul.spiritPower.GhostSoul;
import first.fargo_soul.common.item.terraSoul.willPower.PlatinumSoul;
import first.fargo_soul.common.item.terraSoul.willPower.GoldSoul;
import first.fargo_soul.common.item.terraSoul.cosmicPower.BlazeSoul;
import first.fargo_soul.common.item.terraSoul.cosmicPower.NebulaSoul;
import first.fargo_soul.common.item.terraSoul.cosmicPower.StardustSoul;
import first.fargo_soul.common.item.terraSoul.cosmicPower.VortexSoul;
import first.fargo_soul.common.item.terraSoul.deathPower.DarkArtistSoul;
import first.fargo_soul.common.item.terraSoul.deathPower.NecromancerSoul;
import first.fargo_soul.common.item.terraSoul.deathPower.NinjaSoul;
import first.fargo_soul.common.item.terraSoul.terraPower.CopperSoul;
import first.fargo_soul.common.item.terraSoul.terraPower.IronSoul;
import first.fargo_soul.common.item.terraSoul.terraPower.SilverSoul;
import first.fargo_soul.common.item.terraSoul.terraPower.TinSoul;
import first.fargo_soul.common.soulInfo.FlySoulInfo;
import first.fargo_soul.common.soulInfo.PenetratingSoulInfo;
import first.fargo_soul.common.soulInfo.SoulInfo;
import first.fargo_soul.common.soulInfo.SoulInfoType;
import first.fargo_soul.common.soulInfo.SprintSoulInfo;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FargoSoulSoulInfoRegister {

    private static final DeferredRegister<SoulInfoType<?>> REGISTER = DeferredRegister.create(FargoSoulRegisters.SOUL_INFO_TYPE, FargoSoul.MODID);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<SprintSoulInfo>> SPRINT_SOUL_INFO =
            register("sprint_soul_info", SprintSoulInfo::new, true);
    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<FlySoulInfo>> FLY_SOUL_INFO =
            register("fly_soul_info", FlySoulInfo::new, true);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<CosmicPower.Info>> COSMIC_POWER_INFO =
            register("cosmic_power_info", CosmicPower.Info::new);
    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<BlazeSoul.Info>> BLAZE_SOUL_INFO =
            register("blaze_soul_info", BlazeSoul.Info::new);
    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<NebulaSoul.Info>> NEBULA_SOUL_INFO =
            register("nebula_soul_info", NebulaSoul.Info::new);
    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<StardustSoul.Info>> STARDUST_SOUL_INFO =
            register("stardust_soul_info", StardustSoul.Info::new);
    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<VortexSoul.Info>> VORTEX_SOUL_INFO =
            register("vortex_soul_info", VortexSoul.Info::new);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<DarkArtistSoul.Info>> DARK_ARTIST_SOUL_INFO =
            register("dark_artist_soul_info", DarkArtistSoul.Info::new);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<NecromancerSoul.Info>> NECROMANCER_SOUL_INFO =
            register("necromancer_soul_info", NecromancerSoul.Info::new);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<PenetratingSoulInfo>> PENETRATING_SOUL_INFO =
            register("penetrating_soul_info", PenetratingSoulInfo::new);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<NinjaSoul.Info>> NINJA_SOUL_INFO =
            register("ninja_soul_info", NinjaSoul.Info::new);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<CopperSoul.Info>> COPPER_SOUL_INFO =
            register("copper_soul_info", CopperSoul.Info::new);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<IronSoul.Info>> IRON_SOUL_INFO =
            register("iron_soul_info", IronSoul.Info::new);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<SilverSoul.Info>> SILVER_SOUL_INFO =
            register("silver_soul_info", SilverSoul.Info::new);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<TinSoul.Info>> TIN_SOUL_INFO =
            register("tin_soul_info", TinSoul.Info::new);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<TerraPower.Info>> TERRA_POWER_INFO =
            register("terra_power_info", TerraPower.Info::new);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<WoodSoul.Info>> WOOD_SOUL_INFO =
            register("wood_soul_info", WoodSoul.Info::new);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<RoseWoodSoul.Info>> ROSE_WOOD_SOUL_INFO =
            register("rose_wood_soul_info", RoseWoodSoul.Info::new);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<EbonyWoodSoul.Info>> EBONY_WOOD_SOUL_INFO =
            register("ebony_wood_soul_info", EbonyWoodSoul.Info::new);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<ShadowWoodSoul.Info>> SHADOW_WOOD_SOUL_INFO =
            register("shadow_wood_soul_info", ShadowWoodSoul.Info::new);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<PearlWoodSoul.Info>> PEARL_WOOD_SOUL_INFO =
            register("pearl_wood_soul_info", PearlWoodSoul.Info::new);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<ForestPower.Info>> FOREST_POWER_INFO =
            register("forest_power_info", ForestPower.Info::new);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<EarthPower.Info>> EARTH_POWER_INFO =
            register("earth_power_info", EarthPower.Info::new);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<LifePower.Info>> LIFE_POWER_INFO =
            register("life_power_info", LifePower.Info::new);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<WillPower.Info>> WILL_POWER_INFO =
            register("will_power_info", WillPower.Info::new);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<CobaltSoul.Info>> COBALT_SOUL_INFO =
            register("cobalt_soul_info", CobaltSoul.Info::new);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<FrostSoul.Info>> FROST_SOUL_INFO =
            register("frost_soul_info", FrostSoul.Info::new);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<CrimsonSoul.Info>> CRIMSON_SOUL_INFO =
            register("crimson_soul_info", CrimsonSoul.Info::new);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<PlatinumSoul.Info>> PLATINUM_SOUL_INFO =
            register("platinum_soul_info", PlatinumSoul.Info::new);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<GoldSoul.Info>> GOLD_SOUL_INFO =
            register("gold_soul_info", GoldSoul.Info::new);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<RainCloudSoul.Info>> RAIN_CLOUD_SOUL_INFO =
            register("rain_cloud_soul_info", RainCloudSoul.Info::new);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<HolySoul.Info>> HOLY_SOUL_INFO =
            register("holy_soul_info", HolySoul.Info::new);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<GhostSoul.Info>> GHOST_SOUL_INFO =
            register("ghost_soul_info", GhostSoul.Info::new);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<ForbiddenSoul.Info>> FORBIDDEN_SOUL_INFO =
            register("forbidden_soul_info", ForbiddenSoul.Info::new);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<MithrilSoul.Info>> MITHRIL_SOUL_INFO =
            register("mithril_soul_info", MithrilSoul.Info::new);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<OrichalcumSoul.Info>> ORICHALCUM_SOUL_INFO =
            register("orichalcum_soul_info", OrichalcumSoul.Info::new);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<PalladiumSoul.Info>> PALLADIUM_SOUL_INFO =
            register("palladium_soul_info", PalladiumSoul.Info::new);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<TitaniumSoul.Info>> TITANIUM_SOUL_INFO =
            register("titanium_soul_info", TitaniumSoul.Info::new);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<RedRidingSoul.Info>> RED_RIDING_SOUL_INFO =
            register("red_riding_soul_info", RedRidingSoul.Info::new);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<BeetleSoul.Info>> BEETLE_SOUL_INFO =
            register("beetle_soul_info", BeetleSoul.Info::new);

    private static <T extends SoulInfo> DeferredHolder<SoulInfoType<?>, SoulInfoType<T>> register(String name, Supplier<T> factory) {
        return register(name, factory, false);
    }

    private static <T extends SoulInfo> DeferredHolder<SoulInfoType<?>, SoulInfoType<T>> register(String name, Supplier<T> factory, boolean clientSide) {
        return REGISTER.register(name, () -> new SoulInfoType<>(factory, clientSide));
    }

    public static void register(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }
}

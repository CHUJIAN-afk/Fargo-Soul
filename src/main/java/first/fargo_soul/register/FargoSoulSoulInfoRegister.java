package first.fargo_soul.register;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.item.terraSoul.CosmicPower;
import first.fargo_soul.common.item.terraSoul.TerraPower;
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

package first.fargo_soul.register;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.entity.BloodDrop;
import first.fargo_soul.common.entity.ChlorophyteOrb;
import first.fargo_soul.common.entity.GhostOrb;
import first.fargo_soul.common.entity.TerraBlade;
import first.fargo_soul.common.entity.LightningOrb;
import first.fargo_soul.common.entity.Meteor;
import first.fargo_soul.common.entity.Petal;
import first.fargo_soul.common.entity.ShadowOrb;
import first.fargo_soul.common.entity.SnowBall;
import first.fargo_soul.common.entity.Star;
import first.fargo_soul.common.entity.TrackingBlood;
import first.fargo_soul.common.entity.Sprint;
import first.fargo_soul.common.entity.Vortex;
import first.lyra.common.entity.AttachmentEntity;
import first.lyra.common.entity.AttachmentEntityType;
import first.lyra.register.LyraRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class SummonerAttachmentEntityRegister {

    private static final DeferredRegister<AttachmentEntityType<?>> Register = DeferredRegister.create(LyraRegistries.ATTACHMENT_ENTITY_TYPES, FargoSoul.MODID);

    public static final DeferredHolder<AttachmentEntityType<?>, AttachmentEntityType<Meteor>> METEOR =
            register("meteor", Meteor::new);

    public static final DeferredHolder<AttachmentEntityType<?>, AttachmentEntityType<Vortex>> VORTEX =
            register("vortex", Vortex::new);

    public static final DeferredHolder<AttachmentEntityType<?>, AttachmentEntityType<Sprint>> SPRINT =
            register("sprint", Sprint::new);

    public static final DeferredHolder<AttachmentEntityType<?>, AttachmentEntityType<ShadowOrb>> SHADOW_ORB =
            register("shadow_orb", ShadowOrb::new);

    public static final DeferredHolder<AttachmentEntityType<?>, AttachmentEntityType<LightningOrb>> LIGHTNING_ORB =
            register("lightning_orb", LightningOrb::new);

    public static final DeferredHolder<AttachmentEntityType<?>, AttachmentEntityType<SnowBall>> SNOW_BALL =
            register("snow_ball", SnowBall::new);

    public static final DeferredHolder<AttachmentEntityType<?>, AttachmentEntityType<TrackingBlood>> TRACKING_BLOOD =
            register("tracking_blood", TrackingBlood::new);

    public static final DeferredHolder<AttachmentEntityType<?>, AttachmentEntityType<BloodDrop>> BLOOD_DROP =
            register("blood_drop", BloodDrop::new);

    public static final DeferredHolder<AttachmentEntityType<?>, AttachmentEntityType<Star>> STAR =
            register("star", Star::new);

    public static final DeferredHolder<AttachmentEntityType<?>, AttachmentEntityType<Petal>> PETAL =
            register("petal", Petal::new);

    public static final DeferredHolder<AttachmentEntityType<?>, AttachmentEntityType<GhostOrb>> GHOST_ORB =
            register("ghost_orb", GhostOrb::new);

    public static final DeferredHolder<AttachmentEntityType<?>, AttachmentEntityType<ChlorophyteOrb>> CHLOROPHYTE_ORB =
            register("chlorophyte_orb", ChlorophyteOrb::new);

    public static final DeferredHolder<AttachmentEntityType<?>, AttachmentEntityType<TerraBlade>> TERRA_BLADE =
            register("terra_blade", TerraBlade::new);

    private static <T extends AttachmentEntity> DeferredHolder<AttachmentEntityType<?>, AttachmentEntityType<T>> register(String name, Supplier<T> supplier) {
        return Register.register(name, ResourceLocation -> new AttachmentEntityType<>(ResourceLocation, supplier));
    }

    public static void register(IEventBus eventBus) {
        Register.register(eventBus);
    }
}

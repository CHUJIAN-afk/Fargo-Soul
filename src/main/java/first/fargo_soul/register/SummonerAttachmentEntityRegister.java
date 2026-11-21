package first.fargo_soul.register;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.entity.BloodDrop;
import first.fargo_soul.common.entity.ChlorophyteOrb;
import first.fargo_soul.common.entity.GhostOrb;
import first.fargo_soul.common.entity.LightningOrb;
import first.fargo_soul.common.entity.Meteor;
import first.fargo_soul.common.entity.Petal;
import first.fargo_soul.common.entity.ShadowOrb;
import first.fargo_soul.common.entity.SnowBall;
import first.fargo_soul.common.entity.Sprint;
import first.fargo_soul.common.entity.Star;
import first.fargo_soul.common.entity.TerraBlade;
import first.fargo_soul.common.entity.TrackingBlood;
import first.fargo_soul.common.entity.Vortex;
import first.lyra.common.attachmentEntity.AttachmentEntity;
import first.lyra.common.attachmentEntity.AttachmentEntityType;
import first.lyra.register.LyraRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

public class SummonerAttachmentEntityRegister {

    private static final Registrate REGISTER = FargoSoul.REGISTRATE;

    @SuppressWarnings("unchecked")
    private static final ResourceKey<Registry<AttachmentEntityType<?>>> ATTACHMENT_ENTITY_TYPES_KEY =
            (ResourceKey<Registry<AttachmentEntityType<?>>>) (ResourceKey<?>) LyraRegistries.ATTACHMENT_ENTITY_TYPES.key();

    public static final RegistryEntry<AttachmentEntityType<?>, AttachmentEntityType<Meteor>> METEOR =
            register("meteor", Meteor::new);

    public static final RegistryEntry<AttachmentEntityType<?>, AttachmentEntityType<Vortex>> VORTEX =
            register("vortex", Vortex::new);

    public static final RegistryEntry<AttachmentEntityType<?>, AttachmentEntityType<Sprint>> SPRINT =
            register("sprint", Sprint::new);

    public static final RegistryEntry<AttachmentEntityType<?>, AttachmentEntityType<ShadowOrb>> SHADOW_ORB =
            register("shadow_orb", ShadowOrb::new);

    public static final RegistryEntry<AttachmentEntityType<?>, AttachmentEntityType<LightningOrb>> LIGHTNING_ORB =
            register("lightning_orb", LightningOrb::new);

    public static final RegistryEntry<AttachmentEntityType<?>, AttachmentEntityType<SnowBall>> SNOW_BALL =
            register("snow_ball", SnowBall::new);

    public static final RegistryEntry<AttachmentEntityType<?>, AttachmentEntityType<TrackingBlood>> TRACKING_BLOOD =
            register("tracking_blood", TrackingBlood::new);

    public static final RegistryEntry<AttachmentEntityType<?>, AttachmentEntityType<BloodDrop>> BLOOD_DROP =
            register("blood_drop", BloodDrop::new);

    public static final RegistryEntry<AttachmentEntityType<?>, AttachmentEntityType<Star>> STAR =
            register("star", Star::new);

    public static final RegistryEntry<AttachmentEntityType<?>, AttachmentEntityType<Petal>> PETAL =
            register("petal", Petal::new);

    public static final RegistryEntry<AttachmentEntityType<?>, AttachmentEntityType<GhostOrb>> GHOST_ORB =
            register("ghost_orb", GhostOrb::new);

    public static final RegistryEntry<AttachmentEntityType<?>, AttachmentEntityType<ChlorophyteOrb>> CHLOROPHYTE_ORB =
            register("chlorophyte_orb", ChlorophyteOrb::new);

    public static final RegistryEntry<AttachmentEntityType<?>, AttachmentEntityType<TerraBlade>> TERRA_BLADE =
            register("terra_blade", TerraBlade::new);

    private static <T extends AttachmentEntity> RegistryEntry<AttachmentEntityType<?>, AttachmentEntityType<T>> register(String name, Supplier<T> supplier) {
        return REGISTER.simple(name, ATTACHMENT_ENTITY_TYPES_KEY, () -> new AttachmentEntityType<>(FargoSoul.rl(name), supplier));
    }

    public static void init() {
    }
}

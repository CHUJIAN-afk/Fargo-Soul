package first.fargo_soul.register;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.entity.Meteor;
import first.fargo_soul.common.entity.ShadowOrb;
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

    private static <T extends AttachmentEntity> DeferredHolder<AttachmentEntityType<?>, AttachmentEntityType<T>> register(String name, Supplier<T> supplier) {
        return Register.register(name, ResourceLocation -> new AttachmentEntityType<>(ResourceLocation, supplier));
    }

    public static void register(IEventBus eventBus) {
        Register.register(eventBus);
    }
}

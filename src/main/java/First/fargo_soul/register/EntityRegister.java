package First.fargo_soul.register;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.client.renderer.entityRenderer.BoneRenderer;
import First.fargo_soul.client.renderer.entityRenderer.NeedleRenderer;
import First.fargo_soul.entity.projectile.Bone;
import First.fargo_soul.entity.projectile.NeedleProjectile;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = FargoSoul.MODID)
public class EntityRegister {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(Registries.ENTITY_TYPE, FargoSoul.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<NeedleProjectile>> Needle;
    public static final DeferredHolder<EntityType<?>, EntityType<Bone>> Bone;

    static {
        Bone = EntityTypeRegister("bone", Bone::new, 0.75f);
        Needle = EntityTypeRegister("needle", NeedleProjectile::new, 0.75f);
    }

    private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> EntityTypeRegister(String name, EntityType.EntityFactory<T> entityFactory, MobCategory category, float width, float height, int trackingRange, int updateInterval) {
        return ENTITY_TYPE.register(name, () ->
                EntityType.Builder.of(entityFactory, category)
                        .sized(width, height)
                        .clientTrackingRange(trackingRange)
                        .updateInterval(updateInterval)
                        .build(name)
        );
    }

    private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> EntityTypeRegister(String name, EntityType.EntityFactory<T> entityFactory, float big) {
        return EntityTypeRegister(name, entityFactory, MobCategory.MISC, big, big, 4, 20);
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPE.register(eventBus);
    }

    @SubscribeEvent
    public static void RegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(Needle.get(), NeedleRenderer::new);
        event.registerEntityRenderer(Bone.get(), BoneRenderer::new);
    }

}


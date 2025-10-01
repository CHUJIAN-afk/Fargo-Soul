package First.fargo_soul.Entity;

import First.fargo_soul.Entity.Arrow.Bone;
import First.fargo_soul.Entity.Arrow.NeedleProjectile;
import First.fargo_soul.Fargo_soul;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EntityRegister {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(Registries.ENTITY_TYPE, Fargo_soul.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<NeedleProjectile>> Needle;
    public static final DeferredHolder<EntityType<?>, EntityType<Bone>> Bone;

    static {
        Bone = EntityTypeRegister("bone",Bone::new,0.75f);
        Needle = EntityTypeRegister("needle",NeedleProjectile::new,0.75f);
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

}


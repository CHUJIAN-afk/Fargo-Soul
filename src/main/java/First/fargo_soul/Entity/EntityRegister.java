package First.fargo_soul.Entity;

import First.fargo_soul.Entity.Arrow.*;
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
    public static final DeferredHolder<EntityType<?>, EntityType<Ghost>> Ghost;
    public static final DeferredHolder<EntityType<?>, EntityType<Bone>> Bone;
    public static final DeferredHolder<EntityType<?>, EntityType<Spear>> Spear;
    public static final DeferredHolder<EntityType<?>, EntityType<Banner>> Banner;
    public static final DeferredHolder<EntityType<?>, EntityType<NebulaEmpoweredFlame>> NebulaEmpoweredFlame;
    public static final DeferredHolder<EntityType<?>, EntityType<IceSpike>> IceSpike;

    static {
        NebulaEmpoweredFlame = EntityTypeRegister("nebula_empowered_flame", NebulaEmpoweredFlame::new, 0.25f);
        Banner = EntityTypeRegister("banner",Banner::new,0.75f);
        Spear = EntityTypeRegister("spear",Spear::new,0.75f);
        Bone = EntityTypeRegister("bone",Bone::new,0.75f);
        Ghost = EntityTypeRegister("ghost", Ghost::new,0.75f);
        Needle = EntityTypeRegister("needle",NeedleProjectile::new,0.75f);
        IceSpike = EntityTypeRegister("ice_spike",IceSpike::new,0.25f);
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


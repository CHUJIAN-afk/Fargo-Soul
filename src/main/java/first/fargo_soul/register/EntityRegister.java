package first.fargo_soul.register;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.entity.projectile.Bone;
import first.fargo_soul.common.entity.projectile.Needle;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EntityRegister {

    private static final DeferredRegister<EntityType<?>> Register = DeferredRegister.create(Registries.ENTITY_TYPE, FargoSoul.MODID);
/*
    public static final DeferredHolder<EntityType<?>, EntityType<Mutant>> Mutant =
            Register.register("mutant", () -> EntityType.Builder.of(Mutant::new, MobCategory.MONSTER)
                    .sized(1, 2)
                    .clientTrackingRange(1)
                    .updateInterval(1)
                    .build("mutant")
            );
*/
    public static final DeferredHolder<EntityType<?>, EntityType<Needle>> NeedleEntity =
            Register.register("needle", () -> EntityType.Builder.of(Needle::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("needle")
            );

    public static final DeferredHolder<EntityType<?>, EntityType<Bone>> BoneEntity =
            Register.register("bone", () -> EntityType.Builder.of(Bone::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("bone")
            );

    public static void register(IEventBus eventBus) {
        Register.register(eventBus);
    }

}
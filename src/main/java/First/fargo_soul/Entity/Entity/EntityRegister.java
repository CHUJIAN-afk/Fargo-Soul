package First.fargo_soul.Entity.Entity;

import First.fargo_soul.Fargo_soul;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EntityRegister {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE_DEFERRED_REGISTER = DeferredRegister.create(Registries.ENTITY_TYPE, Fargo_soul.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<First.fargo_soul.Entity.Entity.Sword.Sword>> Sword = ENTITY_TYPE_DEFERRED_REGISTER.register("sword", () ->
            EntityType.Builder.<First.fargo_soul.Entity.Entity.Sword.Sword>of(First.fargo_soul.Entity.Entity.Sword.Sword::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(64)
                    .updateInterval(10)
                    .build("sword")
    );

}

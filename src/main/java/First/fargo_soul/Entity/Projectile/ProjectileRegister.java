package First.fargo_soul.Entity.Projectile;

import First.fargo_soul.Entity.Projectile.IceSpike.IceSpike;
import First.fargo_soul.Fargo_soul;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ProjectileRegister {
    public static final DeferredRegister<EntityType<?>> SoulProjectile = DeferredRegister.create(Registries.ENTITY_TYPE, Fargo_soul.MODID);

        public static final DeferredHolder<EntityType<?>, EntityType<IceSpike>> IceSpike = SoulProjectile.register("ice_spike", () ->
            EntityType.Builder.<IceSpike>of(IceSpike::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(64)
                    .updateInterval(10)
                    .build("ice_spike")
    );



}

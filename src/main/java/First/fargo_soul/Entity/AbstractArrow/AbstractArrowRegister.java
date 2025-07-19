package First.fargo_soul.Entity.AbstractArrow;

import First.fargo_soul.Entity.AbstractArrow.NeedleProjectile.NeedleProjectile;
import First.fargo_soul.Fargo_soul;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AbstractArrowRegister {
    public static final DeferredRegister<EntityType<?>> SoulAbstractArrow = DeferredRegister.create(Registries.ENTITY_TYPE, Fargo_soul.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<NeedleProjectile>> Needle = SoulAbstractArrow.register("needle", () ->
            EntityType.Builder.of(NeedleProjectile::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("needle")
    );

}

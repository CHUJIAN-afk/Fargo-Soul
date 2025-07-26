package First.fargo_soul.Entity.AbstractArrow;

import First.fargo_soul.Entity.AbstractArrow.Banner.Banner;
import First.fargo_soul.Entity.AbstractArrow.Bone.Bone;
import First.fargo_soul.Entity.AbstractArrow.NebulaEmpoweredFlame.NebulaEmpoweredFlame;
import First.fargo_soul.Entity.AbstractArrow.NeedleProjectile.NeedleProjectile;
import First.fargo_soul.Entity.AbstractArrow.SoulProjectile.SoulProjectile;
import First.fargo_soul.Entity.AbstractArrow.Spear.Spear;
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

    public static final DeferredHolder<EntityType<?>, EntityType<SoulProjectile>> Soul = SoulAbstractArrow.register("soul", () ->
            EntityType.Builder.of(SoulProjectile::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("soul")
    );

    public static final DeferredHolder<EntityType<?>, EntityType<Bone>> Bone = SoulAbstractArrow.register("bone", () ->
            EntityType.Builder.of(Bone::new, MobCategory.MISC)
                    .sized(0.75f, 0.75f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("bone")
    );

    public static final DeferredHolder<EntityType<?>, EntityType<Spear>> Abstract = SoulAbstractArrow.register("spear", () ->
            EntityType.Builder.of(Spear::new, MobCategory.MISC)
                    .sized(0.75f, 0.75f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("spear")
    );

    public static final DeferredHolder<EntityType<?>, EntityType<Banner>> Banner = SoulAbstractArrow.register("banner", () ->
            EntityType.Builder.of(Banner::new, MobCategory.MISC)
                    .sized(0.75f, 0.75f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("banner")
    );

    public static final DeferredHolder<EntityType<?>, EntityType<NebulaEmpoweredFlame>> NebulaEmpoweredFlame = SoulAbstractArrow.register("nebula_empowered_flame", () ->
            EntityType.Builder.of(NebulaEmpoweredFlame::new, MobCategory.MISC)
                    .sized(0.25f, 0.25f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("nebula_empowered_flame")
    );




}

package First.fargo_soul.mixin.minecraft;


import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;

@Mixin(ProjectileUtil.class)
public class ProjectileUtilMixin {

	@ModifyExpressionValue(
			method = "getEntityHitResult(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;D)Lnet/minecraft/world/phys/EntityHitResult;",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/level/Level;getEntities(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;"
			)
	)
	private static List<Entity> getEntities(List<Entity> original) {
		return new ArrayList<>(original);
	}

}

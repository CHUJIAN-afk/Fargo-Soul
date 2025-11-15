package First.fargo_soul.mixin.minecraft;

import First.fargo_soul.Fargo_soul;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {


	@Inject(
			method = "getRenderer",
			at = @At("RETURN")
	)
	private <T extends Entity> void getRenderer(T entity, CallbackInfoReturnable<EntityRenderer<? super T>> cir) {
		EntityType<?> type = entity.getType();
		if (cir.getReturnValue() == null) {
			Fargo_soul.logger.debug(type.getDescriptionId());
		}
	}

}




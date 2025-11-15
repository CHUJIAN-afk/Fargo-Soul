package First.fargo_soul.mixin.minecraft;

import First.fargo_soul.DataComponent.DataComponents.SoulComponent;
import First.fargo_soul.DataComponent.DataComponentsRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulCoreItem;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

	@Shadow
	public abstract ItemModelShaper getItemModelShaper();

	@Inject(
			method = "getModel",
			at = @At("RETURN"),
			cancellable = true
	)
	private void getModel(ItemStack itemStack, @Nullable Level level, @Nullable LivingEntity entity, int seed, CallbackInfoReturnable<BakedModel> cir) {
		if (itemStack.getItem() instanceof SoulCoreItem && itemStack.get(DataComponentsRegister.SoulData.get()) instanceof SoulComponent soulComponent) {
			BakedModel newModel = this.getItemModelShaper().getItemModel(soulComponent.item());
			if (newModel != null) {
				cir.setReturnValue(newModel);
			}
		}
	}

}

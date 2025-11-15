package First.fargo_soul.mixin.minecraft;

import First.fargo_soul.Event.AddItemTagEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagLoader;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;
import java.util.function.Function;

@Mixin(TagLoader.class)
public class TagLoaderMixin<T> {

	@Shadow
	@Final
	Function<ResourceLocation, Optional<? extends T>> idToValue;

	@Inject(
			method = "build(Ljava/util/Map;)Ljava/util/Map;",
			at = @At("RETURN"),
			cancellable = true
	)
	private void addSimpleTag(Map<ResourceLocation, List<TagLoader.EntryWithSource>> builders, CallbackInfoReturnable<Map<ResourceLocation, Collection<T>>> cir) {
		Map<ResourceLocation, Collection<T>> tags = cir.getReturnValue();
		AddItemTagEvent event = new AddItemTagEvent();
		NeoForge.EVENT_BUS.post(event);
		event.getMap().forEach((tag, list) -> list.forEach(resourceLocation -> {
			if (idToValue.apply(resourceLocation).isPresent()) {
				tags.computeIfAbsent(tag, k -> new ArrayList<>()).add(idToValue.apply(resourceLocation).get());
			}
		}));
		cir.setReturnValue(tags);
	}

}

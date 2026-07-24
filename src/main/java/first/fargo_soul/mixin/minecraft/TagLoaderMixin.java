package first.fargo_soul.mixin.minecraft;

import first.fargo_soul.common.event.modEvent.AddItemTagEvent;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.registries.BuiltInRegistries;
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
			at = @At(
					value = "INVOKE",
					target = "Ljava/util/Map;forEach(Ljava/util/function/BiConsumer;)V",
					shift = At.Shift.AFTER
			)
	)
	private void addSimpleTag(
			Map<ResourceLocation, List<TagLoader.EntryWithSource>> builders,
			CallbackInfoReturnable<Map<ResourceLocation, Collection<T>>> cir,
			@Local(name = "map") Map<ResourceLocation, Collection<T>> map
	) {
		AddItemTagEvent event = new AddItemTagEvent();
		NeoForge.EVENT_BUS.post(event);
		event.getMap().forEach((tag, list) -> list.forEach(item -> {
			ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(item);
			if (idToValue.apply(resourceLocation).isPresent()) {
				map.computeIfAbsent(tag, k -> new ArrayList<>()).add(idToValue.apply(resourceLocation).get());
			}
		}));
	}

}

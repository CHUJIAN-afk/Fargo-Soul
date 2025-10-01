package First.fargo_soul.mixin.fargo;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.common.data.CuriosEntityManager;

import java.util.Map;

@Mixin(CuriosEntityManager.class)
public class CuriosEntityManagerMixin {

	@Inject(
			method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V",
			at = @At(
					value = "HEAD"
			)
	)
	private void apply(
			Map<ResourceLocation, JsonElement> pObject,
			ResourceManager pResourceManager, ProfilerFiller pProfiler,
			CallbackInfo ci,
			@Local(ordinal = 0, argsOnly = true) LocalRef<Map<ResourceLocation, JsonElement>> sorted
	) {
		Map<ResourceLocation, JsonElement> resourceLocationJsonElementMap = sorted.get();
		ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath("soul", "entities");
		JsonElement el = pObject.get(resourceLocation);
		if (el != null && el.isJsonObject()) {
			JsonObject jsonObject = el.getAsJsonObject();
			JsonArray entitiesArray = jsonObject.getAsJsonArray("entities");
			DefaultedRegistry<EntityType<?>> entityTypeDefaultedRegistry = BuiltInRegistries.ENTITY_TYPE;
			for (EntityType<?> entityType : entityTypeDefaultedRegistry) {
				ResourceLocation key = entityTypeDefaultedRegistry.getKey(entityType);
				String string = ResourceLocation.fromNamespaceAndPath(key.getNamespace(), key.getPath()).toString();
				if (!entitiesArray.contains(new JsonPrimitive(string))) {
					entitiesArray.add(string);
				}
			}
			resourceLocationJsonElementMap.put(resourceLocation, jsonObject);
		}
		sorted.set(resourceLocationJsonElementMap);
	}

}

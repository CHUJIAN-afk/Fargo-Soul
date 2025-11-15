package First.fargo_soul.Recipe;

import First.fargo_soul.DataComponent.DataComponents.SoulComponent;
import First.fargo_soul.Fargo_soul;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static First.fargo_soul.Utils.SoulUtils.random;

@EventBusSubscriber(modid = Fargo_soul.MODID)
public class RecipeParser {

	private static SoulComponent parseRecipe(JsonObject json) {
		String level = getStringTarget(json.get("level"));
		String biome = getStringTarget(json.get("biome"));
		String block = getStringTarget(json.get("block"));
		String weather = getStringTarget(json.get("weather"));
		String time = getStringTarget(json.get("time"));
		int minHeight = getIntTarget(json.get("minHeight"));
		int maxHeight = getIntTarget(json.get("maxHeight"));
		int conditions = 35;
		if (!level.equals("any")) conditions--;
		if (!biome.equals("any")) conditions--;
		if (!block.equals("any")) conditions--;
		if (!weather.equals("any")) conditions--;
		if (!time.equals("any")) conditions--;
		if (minHeight < maxHeight) conditions--;
		conditions = (int) (conditions * 0.2);
		Map<Item, Integer> material = new HashMap<>();
		JsonObject materials = json.getAsJsonObject("materials");
		JsonObject primary = materials.getAsJsonObject("primary");
		for (Map.Entry<String, JsonElement> entry : primary.entrySet()) {
			Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(entry.getKey()));
			if (!(item instanceof StandingAndWallBlockItem) && !entry.getKey().contains("music_disc")) {
				int count = getIntTarget(entry.getValue());
				MaterialsPutOrAdd(material, item, count);
			}
		}
		JsonObject secondary = materials.getAsJsonObject("secondary");
		List<String> secondaryItems = new ArrayList<>(secondary.keySet());
		for (int i = 0; i < getIntTarget(materials.get("secondaryCount")) * conditions; i++) {
			String itemId = secondaryItems.get(random.nextInt(secondaryItems.size()));
			Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(itemId));
			if (!(item instanceof StandingAndWallBlockItem) && !itemId.contains("music_disc")) {
				int count = getIntTarget(secondary.get(itemId));
				MaterialsPutOrAdd(material, item, count);
			}
		}
		return new SoulComponent(
				level,
				biome,
				block,
				weather,
				time,
				minHeight,
				maxHeight,
				BuiltInRegistries.ITEM.get(ResourceLocation.parse(GsonHelper.getAsString(json, "item"))),
				material
		);
	}

	private static void MaterialsPutOrAdd(Map<Item, Integer> material, Item item, int count) {
		if (material.containsKey(item)) {
			material.put(item, material.get(item) + count);
		} else {
			material.put(item, count);
		}
	}

	private static int getIntTarget(JsonElement jsonElement) {
		int count;
		if (jsonElement.isJsonArray()) {
			JsonArray range = jsonElement.getAsJsonArray();
			int min = range.get(0).getAsInt();
			if (range.size() > 1) {
				int max = range.get(1).getAsInt();
				if (min < max) {
					count = random.nextInt(min, max);
				} else {
					count = min;
				}
			} else {
				count = min;
			}
		} else {
			count = jsonElement.getAsInt();
		}
		return count;
	}

	private static String getStringTarget(JsonElement jsonElement) {
		if (jsonElement != null) {
			String string;
			if (jsonElement.isJsonArray()) {
				JsonArray jsonArray = jsonElement.getAsJsonArray();
				string = jsonArray.get(random.nextInt(jsonArray.size())).getAsString();
			} else {
				string = jsonElement.getAsString();
			}
			return string;
		}
		return "any";
	}

	private static final Map<Item, JsonObject> map = new HashMap<>();

	public static void updateAllRecipes(ResourceManager resourceManager) throws IOException {
		map.clear();
		Map<ResourceLocation, Resource> recipeResources = resourceManager.listResources("recipes", location -> location.getPath().endsWith(".json"));
		List<Map.Entry<ResourceLocation, Resource>> list = recipeResources.entrySet().stream().toList();
		List<JsonObject> jsonObjectList = new ArrayList<>();
		Map<JsonObject, ResourceLocation> objectResourceLocationMap = new HashMap<>();
		for (Map.Entry<ResourceLocation, Resource> resourceLocationResourceEntry : list) {
			Resource value = resourceLocationResourceEntry.getValue();
			ResourceLocation resourceLocation = resourceLocationResourceEntry.getKey();
			JsonElement element = JsonParser.parseReader(new InputStreamReader(value.open()));
			if (element.isJsonArray()) {
				for (JsonElement jsonElement : element.getAsJsonArray()) {
					JsonObject asJsonObject = jsonElement.getAsJsonObject();
					jsonObjectList.add(asJsonObject);
					objectResourceLocationMap.put(asJsonObject, resourceLocation);
				}
			} else {
				JsonObject asJsonObject = element.getAsJsonObject();
				jsonObjectList.add(asJsonObject);
				objectResourceLocationMap.put(asJsonObject, resourceLocation);
			}
		}
		int size = 0;
		for (JsonObject jsonObject : jsonObjectList) {
			String[] required = {"level", "biome", "block", "weather", "time", "minHeight", "maxHeight", "item", "materials"};
			boolean add = true;
			for (String key : required) {
				if (!jsonObject.has(key)) {
					Fargo_soul.logger.error("解析{}时出现错误，缺少{}资源", objectResourceLocationMap.get(jsonObject).toString(), key);
					add = false;
					break;
				}
			}
			if (add) {
				size++;
				map.put(BuiltInRegistries.ITEM.get(ResourceLocation.parse(GsonHelper.getAsString(jsonObject, "item"))), jsonObject);
			}
		}
		Fargo_soul.logger.info("已加载{}个配方", size);
	}

	@SubscribeEvent
	public static void Roald(ServerStartedEvent event) throws IOException {
		updateAllRecipes(event.getServer().getResourceManager());
	}

	public static SoulComponent getRecipeFromItem(Item item) {
		if (map.containsKey(item)) {
			return parseRecipe(map.get(item));
		}
		return null;
	}

	public static SoulComponent getRandomRecipe(){
		if (!map.isEmpty()) {
			return parseRecipe(map.values().stream().toList().get(random.nextInt(map.size())));
		}
		return null;
	}

}

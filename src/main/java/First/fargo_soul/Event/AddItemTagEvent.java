package First.fargo_soul.Event;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Utils.SoulUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddItemTagEvent extends Event {

	private final Map<ResourceLocation, List<ResourceLocation>> map = new HashMap<>();

	public AddItemTagEvent() {
	}

	public Map<ResourceLocation, List<ResourceLocation>> getMap() {
		return map;
	}

	public void add(ResourceLocation target, List<ResourceLocation> add) {
		if (map.containsKey(target)) {
			map.get(target).addAll(add);
		} else {
			map.put(target, add);
		}
	}

	public void add(ResourceLocation target, SoulItem soulItem) {
		List<SoulItem> soulItemList = SoulUtils.getAllCurioItems(soulItem.getSoulItemList());
		soulItemList.addFirst(soulItem);
		List<ResourceLocation> resourceLocationList = new ArrayList<>();
		soulItemList.forEach((item) -> resourceLocationList.add(BuiltInRegistries.ITEM.getKey(item)));
		add(target, resourceLocationList);
	}

}

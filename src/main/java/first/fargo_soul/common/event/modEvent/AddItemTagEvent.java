package first.fargo_soul.common.event.modEvent;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddItemTagEvent extends Event {

	private final Map<ResourceLocation, List<Item>> map;

	public AddItemTagEvent() {
		this.map = new HashMap<>();
	}

	public Map<ResourceLocation, List<Item>> getMap() {
		return map;
	}

	public void add(ResourceLocation target, Item... add) {
		List<Item> list = new ArrayList<>(List.of(add));
		if (map.containsKey(target)) {
			map.get(target).addAll(list);
		} else {
			map.put(target, list);
		}
	}

}

package First.fargo_soul.Event;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.Event;

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

}

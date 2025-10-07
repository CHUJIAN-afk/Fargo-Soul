package First.fargo_soul.Event;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import net.neoforged.bus.api.Event;

import java.util.HashMap;
import java.util.Map;

public class DataGeneratorModelsEvent extends Event {

	private final Map<String, SoulItem> map = new HashMap<>();

	public void add(String modid, SoulItem soulItem) {
		map.put(modid, soulItem);
	}

	public Map<String, SoulItem> getMap() {
		return map;
	}
}

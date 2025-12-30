package First.fargo_soul.event.modEvent;

import First.fargo_soul.item.base.SoulItem;
import net.neoforged.bus.api.Event;

import java.util.ArrayList;
import java.util.List;

public class DataGeneratorRecipeEvent extends Event {

	private final List<SoulItem> soulItemList = new ArrayList<>();

	public void add(SoulItem soulItem) {
		soulItemList.add(soulItem);
	}

	public List<SoulItem> getSoulItemList() {
		return soulItemList;
	}

}

package First.fargo_soul.Event;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
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

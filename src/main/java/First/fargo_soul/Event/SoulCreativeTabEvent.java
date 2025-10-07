package First.fargo_soul.Event;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Utils.SoulUtils;
import net.neoforged.bus.api.Event;

import java.util.ArrayList;
import java.util.List;

public class SoulCreativeTabEvent extends Event {

	private final List<SoulItem> soulItemList = new ArrayList<>();

	public SoulCreativeTabEvent() {
	}

	public List<SoulItem> getSoulItemList() {
		return soulItemList;
	}

	public void add(SoulItem soulItem) {
		soulItemList.add(soulItem);
		List<SoulItem> soulItems = SoulUtils.getAllCurioItems(soulItem.getSoulItemList());
		if (!soulItems.isEmpty()) {
			soulItemList.addAll(soulItems);
		}
	}

}

package First.fargo_soul.Event;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Utils.SoulUtils;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.Event;

import java.util.ArrayList;
import java.util.List;

public class SoulCreativeTabEvent extends Event {

	private final List<Item> itemList = new ArrayList<>();

	public SoulCreativeTabEvent() {
	}

	public List<Item> getItemList() {
		return itemList;
	}

	public void add(Item item) {
		if (item instanceof SoulItem soulItem) {
			itemList.add(soulItem);
			List<SoulItem> soulItems = SoulUtils.getAllCurioItems(soulItem.getSoulItemList());
			if (!soulItems.isEmpty()) {
				itemList.addAll(soulItems);
			}
		} else {
			itemList.add(item);
		}
	}

}

package First.fargo_soul.Attachment.Attachment;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;

import java.util.ArrayList;
import java.util.List;

public class Data {

	private final List<SoulItem> SoulItemList = new ArrayList<>();

	public List<SoulItem> getSoulItemList() {
		return SoulItemList;
	}

	public void addSoulItem(SoulItem soulItem) {
		if (getSoulItem(soulItem.getClass()) == null) {
			SoulItemList.add(soulItem);
		}
	}

	public <T extends SoulItem> void removeSoulItem(Class<T> type) {
		if (getSoulItem(type) instanceof SoulItem soulItem) {
			SoulItemList.remove(soulItem);
		}
	}

	public <T extends SoulItem> SoulItem getSoulItem(Class<T> type) {
		List<SoulItem> list = SoulItemList.stream().filter(soulItem -> soulItem.getClass().equals(type)).toList();
		return list.isEmpty() ? null : list.getFirst();
	}


}

package First.fargo_soul.Attachment.Attachment;


import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;

import java.util.List;

public class SoulData {

	private List<SoulItem> soulItemList = null;

	public void setSoulItemList(List<SoulItem> soulItemList) {
		this.soulItemList = soulItemList;
	}

	public List<SoulItem> getSoulItemList() {
		return this.soulItemList;
	}

}

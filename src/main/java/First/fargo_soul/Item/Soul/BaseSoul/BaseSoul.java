package First.fargo_soul.Item.Soul.BaseSoul;

import net.minecraft.network.chat.Component;

import java.util.List;

public interface BaseSoul {
	List<SoulItem> getSoulItemList();
	List<Component> getAttributeList();
	List<Component> getTooltipList();
}

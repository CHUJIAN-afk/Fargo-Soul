package First.fargo_soul.Event;

import net.minecraft.world.entity.item.ItemEntity;
import net.neoforged.bus.api.Event;

public class SoulCoreItemEntityTickEvent extends Event {

	private final ItemEntity itemEntity;

	public SoulCoreItemEntityTickEvent(ItemEntity itemEntity) {
		this.itemEntity = itemEntity;
	}

	public ItemEntity getItemEntity() {
		return itemEntity;
	}

}

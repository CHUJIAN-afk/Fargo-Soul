package first.fargo_soul.common.item.base;

import first.fargo_soul.api.item.ISoulItem;
import net.minecraft.world.item.Item;

public abstract class SoulItem extends Item implements ISoulItem {

    public SoulItem(Properties properties) {
        super(properties.stacksTo(1));
    }
}
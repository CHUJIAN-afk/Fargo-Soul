package First.fargo_soul.Item.BaseItem;

import First.fargo_soul.Fargo_soul;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BaseItemsRegister {
    public static final DeferredRegister.Items Items = DeferredRegister.createItems(Fargo_soul.MODID);
    public static final DeferredItem<Item> IceSpike = Items.registerItem("ice_spike", Item::new);
    public static final DeferredItem<Item> GreenCrystal = Items.registerItem("green_crystal", Item::new);
    public static final DeferredItem<Item> TerraPrism = Items.registerItem("terra_prism", Item::new);
    public static final DeferredItem<Item> Spear = Items.registerItem("spear", Item::new);
    public static final DeferredItem<Item> Flame = Items.registerItem("flame", Item::new);
}

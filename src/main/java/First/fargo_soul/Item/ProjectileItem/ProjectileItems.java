package First.fargo_soul.Item.ProjectileItem;

import First.fargo_soul.Fargo_soul;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ProjectileItems {
    public static final DeferredRegister.Items Projectiles = DeferredRegister.createItems(Fargo_soul.MODID);
    public static final DeferredItem<Item> IceSpike = Projectiles.registerItem("ice_spike", Item::new);
    public static final DeferredItem<Item> GreenCrystal = Projectiles.registerItem("green_crystal", Item::new);
    public static final DeferredItem<Item> RedUmbrella = Projectiles.registerItem("red_umbrella", Item::new);
    public static final DeferredItem<Item> TerraPrism = Projectiles.registerItem("terra_prism", Item::new);
    public static final DeferredItem<Item> Spear = Projectiles.registerItem("spear", Item::new);
    public static final DeferredItem<Item> Flame = Projectiles.registerItem("flame", Item::new);




}

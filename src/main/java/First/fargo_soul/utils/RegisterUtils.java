package First.fargo_soul.utils;

import First.fargo_soul.common.item.base.SoulItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public abstract class RegisterUtils {

    public static <T extends SoulItem> DeferredItem<SoulItem> registerItem(DeferredRegister.Items items, String name, Class<T> tClass) {
        return items.registerItem(name, properties -> {
            try {
                return tClass.getConstructor(Item.Properties.class).newInstance(properties);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static <T extends SoulItem> DeferredItem<SoulItem> registerItem(DeferredRegister.Items items, String name, Class<T> tClass, ModRarity modRarity) {
        return items.registerItem(name, properties -> {
            try {
                return tClass.getConstructor(Item.Properties.class).newInstance(properties.component(ConfluenceMagicLib.MOD_RARITY, modRarity));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

}

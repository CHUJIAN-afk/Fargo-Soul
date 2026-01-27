package First.fargo_soul.utils;

import First.fargo_soul.common.dataComponents.SoulRarity;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.register.DataComponentsRegister;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

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

    public static <T extends SoulItem> DeferredItem<SoulItem> registerItem(DeferredRegister.Items items, String name, Class<T> tClass, SoulRarity soulRarity) {
        return items.registerItem(name, properties -> {
            try {
                return tClass.getConstructor(Item.Properties.class).newInstance(properties.component(DataComponentsRegister.SoulRarity, soulRarity));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

}

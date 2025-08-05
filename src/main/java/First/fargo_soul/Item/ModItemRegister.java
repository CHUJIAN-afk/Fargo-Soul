package First.fargo_soul.Item;

import First.fargo_soul.Item.BaseItem.BaseItemsRegister;
import First.fargo_soul.Item.Soul.SoulsRegister;
import net.neoforged.bus.api.IEventBus;

public class ModItemRegister {

    public static void register(IEventBus eventBus) {
        BaseItemsRegister.Items.register(eventBus);
        SoulsRegister.SoulItems.register(eventBus);
    }

}

package First.fargo_soul.Item;

import First.fargo_soul.Compact.Create.CreateCompact;
import First.fargo_soul.Compact.Create.CreateSoulsRegister;
import First.fargo_soul.Item.BaseItem.BaseItemsRegister;
import First.fargo_soul.Item.Soul.SoulsRegister;
import net.neoforged.bus.api.IEventBus;

public class ModItemRegister {

    public static void register(IEventBus eventBus) {
        BaseItemsRegister.Items.register(eventBus);
        SoulsRegister.SoulItems.register(eventBus);
        //机械动力联动
        if (CreateCompact.isLoadCreate()) {
            CreateSoulsRegister.register(eventBus);
        }
    }

}

package First.fargo_soul.Item;

import First.fargo_soul.Compact.Avaritia.AvaritiaSoulsRegister;
import First.create.Create.CreateSoulsRegister;
import First.fargo_soul.Item.BaseItem.BaseItemsRegister;
import First.fargo_soul.Item.Soul.SoulsRegister;
import net.neoforged.bus.api.IEventBus;

public class ModItemRegister {

    public static void register(IEventBus eventBus) {
        //基础物品
        BaseItemsRegister.Items.register(eventBus);
        //主魂石
        SoulsRegister.SoulItems.register(eventBus);
        //机械动力联动
        CreateSoulsRegister.register(eventBus);
        //无尽贪婪联动
        AvaritiaSoulsRegister.register(eventBus);
    }

}

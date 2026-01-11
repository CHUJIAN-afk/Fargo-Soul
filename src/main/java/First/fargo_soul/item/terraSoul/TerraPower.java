package First.fargo_soul.item.terraSoul;

import First.fargo_soul.item.base.SoulItem;

import java.util.List;

import static First.fargo_soul.register.ItemRegister.*;

public class TerraPower extends SoulItem {

    public TerraPower(Properties properties) {
        super(properties);
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                CopperSoulItem.get(),
                IronSoulItem.get(),
                LeadSoulItem.get(),
                ObsidianSoulItem.get(),
                SilverSoulItem.get(),
                TinSoulItem.get(),
                TungstenSoulItem.get()
        );
    }


}
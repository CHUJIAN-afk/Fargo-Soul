package First.fargo_soul.item.terraSoul;

import First.fargo_soul.item.base.SoulItem;

import java.util.List;

import static First.fargo_soul.register.ItemRegister.*;

public class ForestPower extends SoulItem {

    public ForestPower(Properties properties) {
        super(properties);
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                EbonyWoodSoulItem.get(),
                PalmWoodSoulItem.get(),
                PearlWoodSoulItem.get(),
                PineWoodSoulItem.get(),
                RoseWoodSoulItem.get(),
                ShadowWoodSoulItem.get(),
                WoodSoulItem.get()
        );
    }


}

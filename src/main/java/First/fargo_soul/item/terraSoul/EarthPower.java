package First.fargo_soul.item.terraSoul;

import First.fargo_soul.item.base.SoulItem;

import java.util.List;

import static First.fargo_soul.register.ItemRegister.*;

public class EarthPower extends SoulItem {

    public EarthPower(Properties properties) {
        super(properties);
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                AdamantiteSoulItem.get(),
                CobaltSoulItem.get(),
                MithrilSoulItem.get(),
                OrichalcumSoulItem.get(),
                PalladiumSoulItem.get(),
                TitaniumSoulItem.get()
        );
    }


}

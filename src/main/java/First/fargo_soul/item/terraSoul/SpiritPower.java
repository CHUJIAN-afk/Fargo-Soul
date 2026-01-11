package First.fargo_soul.item.terraSoul;

import First.fargo_soul.item.base.SoulItem;

import java.util.List;

import static First.fargo_soul.register.ItemRegister.*;

public class SpiritPower extends SoulItem {

    public SpiritPower(Properties properties) {
        super(properties);
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                AncientHolySoulItem.get(),
                ForbiddenSoulItem.get(),
                GhostSoulItem.get(),
                HolySoulItem.get(),
                TekeSoulItem.get()
        );
    }


}

package First.fargo_soul.item.terraSoul;

import First.fargo_soul.item.base.SoulItem;

import java.util.List;

import static First.fargo_soul.register.ItemRegister.*;

public class CosmicPower extends SoulItem {

    public CosmicPower(Properties properties) {
        super(properties);
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                BlazeSoulItem.get(),
                NebulaSoulItem.get(),
                StardustSoulItem.get(),
                VortexSoulItem.get(),
                MeteorSoulItem.get(),
                WizardSoulItem.get()
        );
    }

}

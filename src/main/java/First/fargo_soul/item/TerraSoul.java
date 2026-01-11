package First.fargo_soul.item;

import First.fargo_soul.item.base.SoulItem;

import java.util.List;

import static First.fargo_soul.register.ItemRegister.*;

public class TerraSoul extends SoulItem {

    public TerraSoul(Properties properties) {
        super(properties);
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                CosmicPowerItem.get(),
                DeathPowerItem.get(),
                EarthPowerItem.get(),
                ForestPowerItem.get(),
                LifePowerItem.get(),
                NaturePowerItem.get(),
                SpiritPowerItem.get(),
                TerraPowerItem.get(),
                WillPowerItem.get()
        );
    }

}

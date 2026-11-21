package first.fargo_soul.common.item;

import first.fargo_soul.common.item.base.SoulItem;

import java.util.Set;

import static first.fargo_soul.register.FargoSoulItemRegister.*;

public class TerraSoul extends SoulItem {

    public TerraSoul(Properties properties) {
        super(properties);
    }

    @Override
    public Set<SoulItem> getSoulItemList() {
        return Set.of(CosmicPowerItem.get(), DeathPowerItem.get(), EarthPowerItem.get(), ForestPowerItem.get(), LifePowerItem.get(), NaturePowerItem.get(), SpiritPowerItem.get(), TerraPowerItem.get(), WillPowerItem.get());
    }
}
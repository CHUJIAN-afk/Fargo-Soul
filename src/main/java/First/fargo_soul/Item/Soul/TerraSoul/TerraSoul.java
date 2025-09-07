package First.fargo_soul.Item.Soul.TerraSoul;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

import static First.fargo_soul.Item.Soul.SoulsRegister.*;

public class TerraSoul extends SoulItem {

    public TerraSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.MASTER));
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                EarthPower.get(),
                ForestPower.get(),
                LifePower.get(),
                NaturePower.get(),
                TerraPower.get(),
                SpiritPower.get(),
                DeathPower.get(),
                WillPower.get(),
                CosmicPower.get()
        );
    }

}

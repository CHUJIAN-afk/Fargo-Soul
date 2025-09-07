package First.fargo_soul.Item.Soul.TerraSoul.SpiritPower.SoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class AncientHolySoul extends SoulItem {

    public AncientHolySoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.LIGHT_PURPLE));
    }


}

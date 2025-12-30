package First.fargo_soul.item.terraSoul.spiritPower;

import First.fargo_soul.item.base.SoulItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class AncientHolySoul extends SoulItem {

    public AncientHolySoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.LIGHT_PURPLE));
    }

}

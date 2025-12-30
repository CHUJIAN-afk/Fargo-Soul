package First.fargo_soul.item.terraSoul.cosmicPower;

import First.fargo_soul.item.base.SoulItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class WizardSoul  extends SoulItem {

    public WizardSoul (Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.BLUE));
    }

}

package First.fargo_soul.Compact.Create.CreatePower.SoulStone;

import First.fargo_soul.Compact.Create.CreatePower.CreateSoulItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class CogWheelSouL extends CreateSoulItem {

    public CogWheelSouL(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.YELLOW));
    }


}

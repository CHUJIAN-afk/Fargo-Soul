package First.fargo_soul.item.terraSoul;

import First.fargo_soul.item.base.SoulItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

import static First.fargo_soul.register.ItemRegister.*;

public class TerraPower extends SoulItem {

    public TerraPower(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PURPLE));
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                CopperSoulItem.get(),
                IronSoulItem.get(),
                LeadSoulItem.get(),
                ObsidianSoulItem.get(),
                SilverSoulItem.get(),
                TinSoulItem.get(),
                TungstenSoulItem.get()
        );
    }


}
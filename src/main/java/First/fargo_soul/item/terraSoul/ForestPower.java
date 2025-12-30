package First.fargo_soul.item.terraSoul;

import First.fargo_soul.item.base.SoulItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

import static First.fargo_soul.register.ItemRegister.*;

public class ForestPower extends SoulItem {

    public ForestPower(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PURPLE));
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                EbonyWoodSoulItem.get(),
                PalmWoodSoulItem.get(),
                PearlWoodSoulItem.get(),
                PineWoodSoulItem.get(),
                RoseWoodSoulItem.get(),
                ShadowWoodSoulItem.get(),
                WoodSoulItem.get()
        );
    }


}

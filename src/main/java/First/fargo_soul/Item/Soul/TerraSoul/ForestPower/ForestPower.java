package First.fargo_soul.Item.Soul.TerraSoul.ForestPower;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

import static First.fargo_soul.Item.Soul.SoulsRegister.*;

public class ForestPower extends SoulItem {

    public ForestPower(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PURPLE));
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                EbonyWoodSoul.get(),
                PalmWoodSoul.get(),
                PearlWoodSoul.get(),
                PineWoodSoul.get(),
                RosewoodSoul.get(),
                ShadowWoodSoul.get(),
                WoodSoul.get()
        );
    }


}

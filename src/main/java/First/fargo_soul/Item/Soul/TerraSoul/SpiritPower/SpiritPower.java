package First.fargo_soul.Item.Soul.TerraSoul.SpiritPower;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

import static First.fargo_soul.Item.Soul.SoulsRegister.*;

public class SpiritPower extends SoulItem {

    public SpiritPower(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PURPLE));
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                AncientHolySoul.get(),
                ForbiddenSoul.get(),
                GhostSoul.get(),
                HolySoul.get(),
                TekeSoul.get()
        );
    }


}

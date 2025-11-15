package First.fargo_soul.Item.Soul.TerraSoul.WillPower;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

import static First.fargo_soul.Item.Soul.SoulsRegister.*;

public class WillPower extends SoulItem {

    public WillPower(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PURPLE));
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                GladiatorSoul.get(),
                GoldSoul.get(),
                PlatinumSoul.get(),
                RedRidingSoul.get(),
                ValhallaKnightSoul.get()
        );
    }

}

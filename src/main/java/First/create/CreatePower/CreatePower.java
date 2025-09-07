package First.create.CreatePower;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

import static First.create.SoulsRegister.*;

public class CreatePower extends SoulItem {

    public CreatePower(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PURPLE));
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                Burner_Soul.get(),
                CardBoard_Soul.get(),
                CogWheel_Soul.get(),
                DeepDiving_Soul.get(),
                Goggles_Soul.get()
        );
    }

}

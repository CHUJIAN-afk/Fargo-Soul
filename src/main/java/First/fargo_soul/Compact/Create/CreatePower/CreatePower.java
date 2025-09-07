package First.fargo_soul.Compact.Create.CreatePower;

import First.fargo_soul.Compact.Create.CreateSoulsRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

public class CreatePower extends CreateSoulItem {

    public CreatePower(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PURPLE));
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                CreateSoulsRegister.Burner_Soul.get(),
                CreateSoulsRegister.CardBoard_Soul.get(),
                CreateSoulsRegister.CogWheel_Soul.get(),
                CreateSoulsRegister.DeepDiving_Soul.get(),
                CreateSoulsRegister.Goggles_Soul.get()
        );
    }

}

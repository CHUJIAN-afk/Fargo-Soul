package First.fargo_soul.Item.Soul.TerraSoul.CosmicPower;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

import static First.fargo_soul.Item.Soul.SoulsRegister.*;

public class CosmicPower extends SoulItem {

    public CosmicPower(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PURPLE));
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                BlazeSoul.get(),
                NebulaSoul.get(),
                StardustSoul.get(),
                VortexSoul.get(),
                MeteorSoul.get(),
                WizardSoul.get()
        );
    }

}

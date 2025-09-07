package First.fargo_soul.Item.Soul.TerraSoul.NaturePower;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

import static First.fargo_soul.Item.Soul.SoulsRegister.*;

public class NaturePower extends SoulItem {

    public NaturePower(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PURPLE));
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                CrimsonSoul.get(),
                FrostSoul.get(),
                GreenSoul.get(),
                LavaSoul.get(),
                MushroomSoul.get(),
                RainCloudSoul.get()
        );
    }


}

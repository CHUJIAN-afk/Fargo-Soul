package First.fargo_soul.Item.Soul.TerraSoul.DeathPower;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

import static First.fargo_soul.Item.Soul.SoulsRegister.*;

public class DeathPower extends SoulItem {

    public DeathPower(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PURPLE));
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                AncientShadowSoul.get(),
                CrystalAssassinSoul.get(),
                DarkArtistSoul.get(),
                GloomySoul.get(),
                NecromancerSoul.get(),
                NinjaSoul.get(),
                PenetratingNinjaSoul.get()
        );
    }

}

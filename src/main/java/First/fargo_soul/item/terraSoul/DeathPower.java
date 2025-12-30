package First.fargo_soul.item.terraSoul;

import First.fargo_soul.item.base.SoulItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

import static First.fargo_soul.register.ItemRegister.*;

public class DeathPower extends SoulItem {

    public DeathPower(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PURPLE));
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                AncientShadowSoulItem.get(),
                CrystalAssassinSoulItem.get(),
                DarkArtistSoulItem.get(),
                GloomySoulItem.get(),
                NecromancerSoulItem.get(),
                NinjaSoulItem.get(),
                PenetratingNinjaSoulItem.get()
        );
    }

}

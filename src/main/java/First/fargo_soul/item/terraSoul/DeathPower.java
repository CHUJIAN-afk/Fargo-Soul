package First.fargo_soul.item.terraSoul;

import First.fargo_soul.item.base.SoulItem;

import java.util.List;

import static First.fargo_soul.register.ItemRegister.*;

public class DeathPower extends SoulItem {

    public DeathPower(Properties properties) {
        super(properties);
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

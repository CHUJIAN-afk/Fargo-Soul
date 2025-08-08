package First.fargo_soul.Item.Soul.TerraSoul.DeathPower;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
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

    public final List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.death_power.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
    );

    @Override
    public List<Component> getTooltipList() {
        return TooltipList;
    }

}

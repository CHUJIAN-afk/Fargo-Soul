package First.fargo_soul.Item.Soul.TerraSoul.SpiritPower;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
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

    @Override
    public List<Component> getTooltipList() {
        return TooltipList;
    }

    public final List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.spirit_power.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
    );


}

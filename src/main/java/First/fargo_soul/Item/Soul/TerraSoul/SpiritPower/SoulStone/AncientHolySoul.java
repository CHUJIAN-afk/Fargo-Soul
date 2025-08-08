package First.fargo_soul.Item.Soul.TerraSoul.SpiritPower.SoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

public class AncientHolySoul extends SoulItem {

    public AncientHolySoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.LIGHT_PURPLE));
    }

    public final List<Component> AttributeList = List.of(
            Component.translatable("item.fargo_soul.ancient_holy_soul.attribute.1").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.ancient_holy_soul.attribute.2").withStyle(ChatFormatting.BLUE)
    );

    public final List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.ancient_holy_soul.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
    );


    @Override
    public List<Component> getAttributeList() {
        return AttributeList;
    }

    @Override
    public List<Component> getTooltipList() {
        return TooltipList;
    }

}

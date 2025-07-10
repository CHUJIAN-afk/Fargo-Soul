package First.fargo_soul.Curios.Soul.ForestPower.SoulStone;

import First.fargo_soul.Curios.SoulItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class PalmWoodSoul extends SoulItem {

    public PalmWoodSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("被你攻击的敌人在10秒内受到的火焰伤害+50%").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“出奇的宁静”").withStyle(ChatFormatting.DARK_GRAY)
    );

    @Override
    public List<Component> getAttributeList() {
        return this.AttributeList;
    }
    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, Item.TooltipContext context, ItemStack stack) {
        tooltips.addAll(AttributeList);
        tooltips.addAll(TooltipList);
        return tooltips;
    }

}

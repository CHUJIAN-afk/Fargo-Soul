package First.fargo_soul.Curios.Soul.ForestPower.SoulStone;

import First.fargo_soul.Curios.SoulItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class EbonyWoodSoul extends SoulItem {

    public EbonyWoodSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("你被一圈腐化光环环绕").withStyle(ChatFormatting.BLUE),
            Component.literal("光环内的敌人越多，腐化值充能越快").withStyle(ChatFormatting.BLUE),
            Component.literal("根据腐化值增加至多5点固定伤害和5%伤害减免").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“潜力未完全开发”").withStyle(ChatFormatting.DARK_GRAY)
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

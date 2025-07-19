package First.fargo_soul.Item.Soul.SpiritPower.SoulStone;

import First.fargo_soul.Item.Soul.SoulItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class AncientHolySoul extends SoulItem {

    public AncientHolySoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("召唤一柄泰拉棱镜，跟随你并攻击敌怪").withStyle(ChatFormatting.BLUE),
            Component.literal("用剑切割敌对射弹会将其反射至敌人身上").withStyle(ChatFormatting.BLUE),
            Component.literal("反射有15秒冷却时间，且只能切割造成伤害低于150点的敌对射弹").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“你有足够的力量驾驭我吗？”").withStyle(ChatFormatting.DARK_GRAY)
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

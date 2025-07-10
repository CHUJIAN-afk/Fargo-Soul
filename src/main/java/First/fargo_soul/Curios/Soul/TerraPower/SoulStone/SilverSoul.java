package First.fargo_soul.Curios.Soul.TerraPower.SoulStone;

import First.fargo_soul.Curios.SoulItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class SilverSoul extends SoulItem {

    public SilverSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("举盾状态会增加10点防御力").withStyle(ChatFormatting.BLUE),
            Component.literal("盾牌格挡如果时机正确，反弹200%当次伤害，并获得“惊人一刻”增益").withStyle(ChatFormatting.BLUE),
            Component.literal("惊人一刻：下次近战攻击伤害提高400%").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“反射”").withStyle(ChatFormatting.DARK_GRAY)
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

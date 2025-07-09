package First.fargo_soul.Curios.SoulStone;

import First.fargo_soul.Curios.CurioItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ShadowWoodSoul extends CurioItem {

    public ShadowWoodSoul(Item.Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("召唤一圈鲜血光环").withStyle(ChatFormatting.BLUE),
            Component.literal("在鲜血光环内的敌人有概率会喷出血液，同时有概率为你恢复生命值").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“出奇的干净”").withStyle(ChatFormatting.DARK_GRAY)
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

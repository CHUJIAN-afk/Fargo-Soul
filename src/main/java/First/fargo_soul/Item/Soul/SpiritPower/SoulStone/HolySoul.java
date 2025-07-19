package First.fargo_soul.Item.Soul.SpiritPower.SoulStone;

import First.fargo_soul.Item.Soul.SoulItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class HolySoul extends SoulItem {

    public HolySoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("治疗效果增加40%，但是会随时间推移逐渐生效").withStyle(ChatFormatting.BLUE),
            Component.literal("治疗时会产生冲击波，可击退附近的敌对射弹").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("《尽管放马过来》").withStyle(ChatFormatting.DARK_GRAY)
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

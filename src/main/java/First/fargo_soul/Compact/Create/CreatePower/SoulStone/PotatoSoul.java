package First.fargo_soul.Compact.Create.CreatePower.SoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class PotatoSoul extends SoulItem {

    public PotatoSoul(Properties properties) {
        super(properties);
    }

    public final List<Component> AttributeList = List.of(
            Component.literal("土豆加农炮的射弹无视重力").withStyle(ChatFormatting.BLUE),
            Component.literal("土豆加农炮50%概率不消耗蔬菜").withStyle(ChatFormatting.BLUE)
    );

    public final List<Component> TooltipList = List.of(
            Component.literal("“蔬菜的愤怒！”").withStyle(ChatFormatting.DARK_GRAY)
    );

    @Override
    public List<Component> getAttributeList() {
        return this.AttributeList;
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
        tooltips.addAll(AttributeList);
        tooltips.addAll(TooltipList);
        return tooltips;
    }

}

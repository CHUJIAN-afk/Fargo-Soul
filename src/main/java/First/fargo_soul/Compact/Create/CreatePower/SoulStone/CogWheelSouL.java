package First.fargo_soul.Compact.Create.CreatePower.SoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class CogWheelSouL extends SoulItem {

    public CogWheelSouL(Properties properties) {
        super(properties);
    }

    public final List<Component> AttributeList = List.of(
            //Component.literal("摇动手摇曲柄的应力量提高至64倍").withStyle(ChatFormatting.BLUE),
            Component.literal("摇动手摇曲柄不消耗饥饿值或饱和度").withStyle(ChatFormatting.BLUE)
    );

    public final List<Component> TooltipList = List.of(
            Component.literal("“高效蓄能”").withStyle(ChatFormatting.DARK_GRAY)
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

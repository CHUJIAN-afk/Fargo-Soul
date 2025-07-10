package First.fargo_soul.Curios.Soul.TerraPower.SoulStone;

import First.fargo_soul.Curios.SoulItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class TinSoul extends SoulItem {

    public TinSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("无法通过跳跃造成暴击").withStyle(ChatFormatting.BLUE),
            Component.literal("将你的暴击率设为5%，暴击伤害设为250%").withStyle(ChatFormatting.BLUE),
            Component.literal("每次暴击时都会增加5%暴击率，暴击率的最大值为100%").withStyle(ChatFormatting.BLUE),
            Component.literal("受伤会使暴击率减半，最低为5%").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“暴击回归”").withStyle(ChatFormatting.DARK_GRAY)
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


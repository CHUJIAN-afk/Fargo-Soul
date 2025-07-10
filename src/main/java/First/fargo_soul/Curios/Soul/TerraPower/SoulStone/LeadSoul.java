package First.fargo_soul.Curios.Soul.TerraPower.SoulStone;

import First.fargo_soul.Curios.SoulItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class LeadSoul extends SoulItem {

    public LeadSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("连续受到同一敌人的伤害时，减少10%该敌人对你造成的伤害").withStyle(ChatFormatting.BLUE),
            Component.literal("攻击有几率造成铅中毒减益").withStyle(ChatFormatting.BLUE),
            Component.literal("铅中毒减益持续造成伤害并会扩散至周围的敌人").withStyle(ChatFormatting.BLUE)
            );

    public List<Component> TooltipList = List.of(
            Component.literal("“不建议食用”").withStyle(ChatFormatting.DARK_GRAY)
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

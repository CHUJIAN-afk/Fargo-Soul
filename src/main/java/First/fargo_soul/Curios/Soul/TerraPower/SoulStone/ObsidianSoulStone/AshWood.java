package First.fargo_soul.Curios.Soul.TerraPower.SoulStone.ObsidianSoulStone;

import First.fargo_soul.Curios.SoulItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class AshWood extends SoulItem {

    public AshWood(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("定期产生火球攻击附近敌人").withStyle(ChatFormatting.BLUE),
            Component.literal("极大降低岩浆的接触伤害").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“你告诉我，这不是木头？”").withStyle(ChatFormatting.DARK_GRAY)
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

package First.fargo_soul.Item.Soul.SpiritPower.SoulStone;

import First.fargo_soul.Item.Soul.SoulItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class GhostSoul extends SoulItem {

    public GhostSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("生命值降低至0时复活，复活血量为最大生命值20%，清除减益效果并产生多个灵魂").withStyle(ChatFormatting.BLUE),
            Component.literal("复活后免疫效果，移动速度更快，可以无限飞行并且无法攻击，持续5秒").withStyle(ChatFormatting.BLUE),
            Component.literal("每次攻击会产生一些灵魂").withStyle(ChatFormatting.BLUE),
            Component.literal("触碰灵魂，每个灵魂可治疗2生命值并将其送往最近的敌人，造成魔法伤害").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“他们的生命力将毁灭他们自己”").withStyle(ChatFormatting.DARK_GRAY)
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

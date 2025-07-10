package First.fargo_soul.Curios.Soul.TerraPower.SoulStone;

import First.fargo_soul.Curios.SoulItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class CopperSoul extends SoulItem {

    public CopperSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("攻击有概率释放闪电攻击敌人，此效果有5秒冷却").withStyle(ChatFormatting.BLUE),
            Component.literal("命中有雨中或水中的敌人时释放闪电的概率增加").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“它的音乐还是电音”").withStyle(ChatFormatting.DARK_GRAY)
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

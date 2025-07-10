package First.fargo_soul.Curios.Soul.TerraPower.SoulStone;

import First.fargo_soul.Curios.SoulItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class TungstenSoul extends SoulItem {

    public TungstenSoul(Item.Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("增加50%实体触及距离").withStyle(ChatFormatting.BLUE),
            Component.literal("攻击命中敌人时会产生一个基础伤害为当次攻击50%的爆炸，此效果有2.5秒冷却时间").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“大就是好”").withStyle(ChatFormatting.DARK_GRAY)
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

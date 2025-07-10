package First.fargo_soul.Curios.Soul.TerraPower.SoulStone;

import First.fargo_soul.Curios.SoulItem;
import First.fargo_soul.Curios.Souls;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ObsidianSoul extends SoulItem {

    public ObsidianSoul(Properties properties) {
        super(properties);
    }

    public final List<SoulItem> soulItemList = List.of(
            Souls.AshWood.get()
    );

    public List<Component> list1 =
            soulItemList.stream()
                    .flatMap(curioItem -> curioItem.getAttributeList().stream())
                    .collect(Collectors.toList());

    public List<Component> list2 = List.of(
            Component.literal("免疫火块与熔岩").withStyle(ChatFormatting.BLUE),
            Component.literal("你可以在熔岩中正常移动和游泳").withStyle(ChatFormatting.BLUE),
            //Component.literal("在熔岩中攻击会产生爆炸").withStyle(ChatFormatting.BLUE),
            Component.literal("火系伤害对你无效").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> AttributeList = Stream.concat(
            list1.stream(),
            list2.stream()
    ).collect(Collectors.toList());

    public List<Component> TooltipList = List.of(
            Component.literal("“大地在呼唤”").withStyle(ChatFormatting.DARK_GRAY)
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
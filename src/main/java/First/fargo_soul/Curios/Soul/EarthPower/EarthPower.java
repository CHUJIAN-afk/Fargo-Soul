package First.fargo_soul.Curios.Soul.EarthPower;

import First.fargo_soul.Curios.SoulItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

import static First.fargo_soul.Curios.Souls.*;

public class EarthPower extends SoulItem {

    public EarthPower(Properties properties) {
        super(properties);
    }

    public final List<SoulItem> soulItemList = List.of(
            AdamantiteSoul.get(),
            CobaltSoul.get(),
            MithrilSoul.get(),
            OrichalcumSoul.get(),
            PalladiumSoul.get(),
            TitaniumSoul.get()
    );

    @Override
    public List<SoulItem> getCurioItemList() {
        return this.soulItemList;
    }

    public List<Component> AttributeList = soulItemList.stream()
            .flatMap(curioItem -> curioItem.getAttributeList().stream())
            .collect(Collectors.toList());

    @Override
    public List<Component> getAttributeList() {
        return this.AttributeList;
    }

    public List<Component> TooltipList = List.of(
            Component.literal("“盖亚的祝福照耀着你”").withStyle(ChatFormatting.DARK_GRAY)
    );

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, Item.TooltipContext context, ItemStack stack) {
        tooltips.addAll(AttributeList);
        tooltips.addAll(TooltipList);
        return tooltips;
    }

}

package First.fargo_soul.Curios.Power;

import First.fargo_soul.Curios.CurioItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

import static First.fargo_soul.Curios.CuriosRegister.*;

public class ForestPower extends CurioItem {

    public ForestPower(Properties properties) {
        super(properties);
    }

    public final List<CurioItem> CurioItemList = List.of(
            EbonyWoodSoul.get(),
            PalmWoodSoul.get(),
            PearlWoodSoul.get(),
            PineWoodSoul.get(),
            RosewoodSoul.get(),
            ShadowWoodSoul.get(),
            WoodSoul.get()
    );

    @Override
    public List<CurioItem> getCurioItemList() {
        return this.CurioItemList;
    }

    public List<Component> AttributeList = CurioItemList.stream()
            .flatMap(curioItem -> curioItem.getAttributeList().stream())
            .collect(Collectors.toList());
    @Override
    public List<Component> getAttributeList() {
        return this.AttributeList;
    }

    public List<Component> TooltipList = List.of(
            Component.literal("“很硬”").withStyle(ChatFormatting.DARK_GRAY)
    );

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, Item.TooltipContext context, ItemStack stack) {
        tooltips.addAll(AttributeList);
        tooltips.addAll(TooltipList);
        return tooltips;
    }

}

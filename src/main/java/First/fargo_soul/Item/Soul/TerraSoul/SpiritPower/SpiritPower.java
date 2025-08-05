package First.fargo_soul.Item.Soul.TerraSoul.SpiritPower;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

import static First.fargo_soul.Item.Soul.SoulsRegister.*;

public class SpiritPower extends SoulItem {

    public SpiritPower(Properties properties) {
        super(properties);
    }

    public final List<SoulItem> soulItemList = List.of(
            AncientHolySoul.get(),
            ForbiddenSoul.get(),
            GhostSoul.get(),
            HolySoul.get(),
            TekeSoul.get()
    );

    @Override
    public List<SoulItem> getCurioItemList() {
        return this.soulItemList;
    }

    public final List<Component> AttributeList = soulItemList.stream()
            .flatMap(curioItem -> curioItem.getAttributeList().stream())
            .collect(Collectors.toList());

    @Override
    public List<Component> getAttributeList() {
        return this.AttributeList;
    }


    public final List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.spirit_power.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
    );

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, Item.TooltipContext context, ItemStack stack) {
        tooltips.addAll(AttributeList);
        tooltips.addAll(TooltipList);
        return tooltips;
    }

}

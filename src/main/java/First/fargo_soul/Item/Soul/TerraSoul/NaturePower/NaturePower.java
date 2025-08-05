package First.fargo_soul.Item.Soul.TerraSoul.NaturePower;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

import static First.fargo_soul.Item.Soul.SoulsRegister.*;

public class NaturePower extends SoulItem {

    public NaturePower(Properties properties) {
        super(properties);
    }

    public final List<SoulItem> soulItemList = List.of(
            CrimsonSoul.get(),
            FrostSoul.get(),
            GreenSoul.get(),
            LavaSoul.get(),
            MushroomSoul.get(),
            RainCloudSoul.get()
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
            Component.translatable("item.fargo_soul.nature_power.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
    );

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, Item.TooltipContext context, ItemStack stack) {
        tooltips.addAll(AttributeList);
        tooltips.addAll(TooltipList);
        return tooltips;
    }

}

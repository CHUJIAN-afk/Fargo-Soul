package First.fargo_soul.Item.Soul.DeathPower;

import First.fargo_soul.Item.Soul.SoulItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

import static First.fargo_soul.Item.Soul.Souls.*;

public class DeathPower extends SoulItem {

    public DeathPower(Properties properties) {
        super(properties);
    }

    public final List<SoulItem> soulItemList = List.of(
            AncientShadowSoul.get(),
            CrystalAssassinSoul.get(),
            DarkArtistSoul.get(),
            GloomySoul.get(),
            NecromancerSoul.get(),
            NinjaSoul.get(),
            PenetratingNinjaSoul.get()
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
            Component.literal("“黑暗，更黑暗，还是更黑暗”").withStyle(ChatFormatting.DARK_GRAY)
    );

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, Item.TooltipContext context, ItemStack stack) {
        tooltips.addAll(AttributeList);
        tooltips.addAll(TooltipList);
        return tooltips;
    }

}

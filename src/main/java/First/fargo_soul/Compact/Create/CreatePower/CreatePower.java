package First.fargo_soul.Compact.Create.CreatePower;

import First.fargo_soul.Compact.Create.CreateSoulsRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

public class CreatePower extends SoulItem {

    public CreatePower(Properties properties) {
        super(properties);
    }

    public final List<SoulItem> soulItemList = List.of(
            CreateSoulsRegister.Burner_Soul.get(),
            CreateSoulsRegister.CardBoard_Soul.get(),
            CreateSoulsRegister.CogWheel_Soul.get(),
            CreateSoulsRegister.DeepDiving_Soul.get(),
            CreateSoulsRegister.Goggles_Soul.get(),
            CreateSoulsRegister.Potato_Soul.get()

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
            Component.literal("“你突然有一种建造工厂的冲动”").withStyle(ChatFormatting.DARK_GRAY)
    );

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, Item.TooltipContext context, ItemStack stack) {
        tooltips.addAll(AttributeList);
        tooltips.addAll(TooltipList);
        return tooltips;
    }

}

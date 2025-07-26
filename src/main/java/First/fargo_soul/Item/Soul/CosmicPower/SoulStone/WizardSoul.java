package First.fargo_soul.Item.Soul.CosmicPower.SoulStone;

import First.fargo_soul.Item.Soul.SoulItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class WizardSoul  extends SoulItem {

    public WizardSoul (Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("让环绕着你的魔石全部发光").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“我们爱施放魔法”").withStyle(ChatFormatting.DARK_GRAY)
    );

    @Override
    public List<Component> getAttributeList() {
        return this.AttributeList;
    }
    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
        tooltips.addAll(AttributeList);
        tooltips.addAll(TooltipList);
        return tooltips;
    }

}

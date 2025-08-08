package First.fargo_soul.Item.Soul.TerraSoul.CosmicPower;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

import static First.fargo_soul.Item.Soul.SoulsRegister.*;

public class CosmicPower extends SoulItem {

    public CosmicPower(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PURPLE));
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                BlazeSoul.get(),
                MeteorSoul.get(),
                NebulaSoul.get(),
                StardustSoul.get(),
                VortexSoul.get(),
                WizardSoul.get()
        );
    }

    public final List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.cosmic_power.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
    );

    @Override
    public List<Component> getTooltipList() {
        return TooltipList;
    }

}

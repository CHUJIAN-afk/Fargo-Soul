package First.fargo_soul.Item.Soul.TerraSoul.WillPower;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

import static First.fargo_soul.Item.Soul.SoulsRegister.*;

public class WillPower extends SoulItem {

    public WillPower(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PURPLE));
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                GladiatorSoul.get(),
                GoldSoul.get(),
                PlatinumSoul.get(),
                RedRidingSoul.get(),
                ValhallaKnightSoul.get()
        );
    }

    @Override
    public List<Component> getTooltipList() {
        return TooltipList;
    }

    public final List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.will_power.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
    );


}

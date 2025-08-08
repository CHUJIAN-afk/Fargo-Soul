package First.fargo_soul.Item.Soul.TerraSoul.EarthPower;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

import static First.fargo_soul.Item.Soul.SoulsRegister.*;

public class EarthPower extends SoulItem {

    public EarthPower(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PURPLE));
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                AdamantiteSoul.get(),
                CobaltSoul.get(),
                MithrilSoul.get(),
                OrichalcumSoul.get(),
                PalladiumSoul.get(),
                TitaniumSoul.get()
        );
    }

    @Override
    public List<Component> getTooltipList() {
        return TooltipList;
    }

    public final List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.earth_power.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
    );


}

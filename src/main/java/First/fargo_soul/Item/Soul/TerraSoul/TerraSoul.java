package First.fargo_soul.Item.Soul.TerraSoul;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

import static First.fargo_soul.Item.Soul.SoulsRegister.*;

public class TerraSoul extends SoulItem {

    public TerraSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.MASTER));
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                EarthPower.get(),
                ForestPower.get(),
                LifePower.get(),
                NaturePower.get(),
                TerraPower.get(),
                SpiritPower.get(),
                DeathPower.get(),
                WillPower.get(),
                CosmicPower.get()
        );
    }

    public final List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.terra_soul.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
    );

    @Override
    public List<Component> getTooltipList() {
        return TooltipList;
    }





/*
    private long lastUpdateTick = 0;
    private List<Component> cachedRandomTips = List.of();
    private int maxLength = 0;
    public List<Component> G(List<Component> tooltips, Item.TooltipContext context, ItemStack stack) {
        if (context.level() instanceof ClientLevel level) {
            long currentTick = level.getGameTime();
            if (currentTick - lastUpdateTick >= 3 || cachedRandomTips.isEmpty()) {
                List<Component> shuffledList = new ArrayList<>(AttributeList);
                Collections.shuffle(shuffledList, random);
                cachedRandomTips = shuffledList.subList(0, Math.min(9, shuffledList.size()));
                lastUpdateTick = currentTick;
            }
            if (maxLength == 0) {
                for (Component component : cachedRandomTips) {
                    maxLength = Math.max(maxLength, component.toString().length());
                }
            }
            tooltips.addAll(cachedRandomTips);
        }
        tooltips.addAll(TooltipList);
        return tooltips;
    }
*/
}

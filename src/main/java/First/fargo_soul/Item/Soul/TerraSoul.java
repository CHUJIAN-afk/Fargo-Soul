package First.fargo_soul.Item.Soul;

import First.fargo_soul.Client.Corlor.MasterColorAnimation;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static First.fargo_soul.Item.Soul.Souls.*;
import static First.fargo_soul.Utils.MathUtils.random;

public class TerraSoul extends SoulItem {

    public TerraSoul(Properties properties) {
        super(properties);
    }

    public final List<SoulItem> soulItemList = List.of(
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
            Component.translatable("item.fargo_soul.terra_soul.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
    );

    private long lastUpdateTick = 0;
    private List<Component> cachedRandomTips = List.of();
    private int maxLength = 0;

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, Item.TooltipContext context, ItemStack stack) {
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


    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        return Component.translatable(getDescriptionId())
                .withStyle(style -> style.withColor(MasterColorAnimation.INSTANCE.getColor()));
    }






}

package First.fargo_soul.client.tooltip;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record CosmicCrucibleItemTooltipComponent(List<ItemStack> list) implements ClientTooltipComponent, TooltipComponent {

    private static final int ItemsPerRow = 9;
    private static final int SlotSize = 16;

    @Override
    public int getHeight() {
        int rows = (int) Math.ceil(list.size() / (double) ItemsPerRow);
        return rows * SlotSize + 4;
    }

    @Override
    public int getWidth(@NotNull Font font) {
        int columns = Math.min(list.size(), ItemsPerRow);
        return columns * SlotSize;
    }

    @Override
    public void renderImage(@NotNull Font font, int x, int y, @NotNull GuiGraphics guiGraphics) {
        for (ItemStack stack : list) {
            int i = list.indexOf(stack);
            int row = i / ItemsPerRow;
            int col = i % ItemsPerRow;
            int drawX = x + col * SlotSize;
            int drawY = y + row * SlotSize;
            guiGraphics.renderItem(stack, drawX, drawY);
            guiGraphics.renderItemDecorations(font, stack, drawX, drawY);
        }
    }

}

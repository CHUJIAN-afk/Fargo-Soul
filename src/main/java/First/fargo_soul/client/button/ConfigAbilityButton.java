package First.fargo_soul.client.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ConfigAbilityButton extends Button {
    private final ItemStack itemStack;
    private final Component tooltip;
    private Boolean isActive;

    public ConfigAbilityButton(int x, int y, int width, int height, ItemStack itemStack, Component message, Component tooltip, Boolean isActive, OnPress onPress) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
        this.itemStack = itemStack;
        this.tooltip = tooltip;
        this.isActive = isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // 原版按钮渲染
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);

        // 在左侧渲染状态指示器
        int indicatorX = this.getX() - 15;
        int indicatorY = this.getY() + (this.height - 8) / 2;
        int indicatorSize = 8;

        if (this.isActive) {
            guiGraphics.fill(indicatorX, indicatorY, indicatorX + indicatorSize, indicatorY + indicatorSize, 0xFF00FF00);
        } else {
            guiGraphics.fill(indicatorX, indicatorY, indicatorX + indicatorSize, indicatorY + indicatorSize, 0xFFFF0000);
        }
        guiGraphics.renderOutline(indicatorX, indicatorY, indicatorSize, indicatorSize, 0xFF000000);

        // 绘制物品贴图
        guiGraphics.renderItem(itemStack, this.getX() + 5, this.getY() + 2);

        // 绘制悬停效果和工具提示
        if (this.isHovered() && tooltip != null) {
            guiGraphics.renderTooltip(Minecraft.getInstance().font, this.tooltip, mouseX, mouseY);
        }
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Component getComponent() {
        return tooltip;
    }

    public boolean isActive() {
        return isActive;
    }

    public OnPress getOnPress() {
        return onPress;
    }

}

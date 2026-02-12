package First.fargo_soul.client.screen;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.client.button.SoulContainerButton;
import First.fargo_soul.client.slot.SoulSlot;
import First.fargo_soul.common.attachment.SoulContainerData;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.common.menu.SoulContainer;
import First.fargo_soul.mixin.minecraft.ScreenAccessor;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.utils.CurioUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static net.minecraft.client.gui.screens.inventory.InventoryScreen.renderEntityInInventoryFollowsMouse;

public class SoulContainerScreen extends AbstractContainerScreen<SoulContainer> {

    private final List<SoulItem> origin;
    private double scrollY;
    private final Map<SoulItem, ButtonInfo> buttonInfoMap = new HashMap<>();
    private final Player player;

    public SoulContainerScreen(SoulContainer menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.player = inventory.player;
        this.origin = CurioUtils.getSoulFromSlots(player);
    }

    @Override
    protected void init() {
        super.init();
        titleLabelX = 97;
        scrollY = -getGuiTop() - 2;
        updateButtonPositions();
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        SoulContainerData data = player.getData(AttachmentRegister.SoulContainerData);
        if (data.isChange()) {
            data.setChange(false);
            updateButtonPositions();
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        this.scrollY -= scrollY * 18;
        updateButtonPositions();
        return true;
    }

    @Override
    protected void renderSlot(@NotNull GuiGraphics guiGraphics, @NotNull Slot slot) {
        if (slot instanceof SoulSlot) {
            PoseStack pose = guiGraphics.pose();
            pose.pushPose();
            int x = slot.x;
            int y = slot.y;
            float scale = 1.5f;
            pose.translate(x + 8, y + 8, 0);
            pose.scale(scale, scale, 1);
            pose.translate(-(x + 8), -(y + 8), 0);
            super.renderSlot(guiGraphics, slot);
            pose.popPose();
        } else {
            super.renderSlot(guiGraphics, slot);
        }
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (!buttonInfoMap.isEmpty()) {
            TooltipRenderUtil.renderTooltipBackground(guiGraphics, getXPos(), getYPos(), getWidth(), getHeight(), 0);
        }
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        drawTreeConnections(guiGraphics);
        for (Slot slot : menu.slots) {
            if (slot.getItem().getItem() instanceof SoulItem soulItem) {
                soulItem.renderSlot(slot, guiGraphics, partialTick);
            }
        }
    }

    @Override
    protected void renderSlotHighlight(@NotNull GuiGraphics guiGraphics, @NotNull Slot slot, int mouseX, int mouseY, float partialTick) {
        if (!(slot instanceof SoulSlot)) {
            super.renderSlotHighlight(guiGraphics, slot, mouseX, mouseY, partialTick);
        }
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        pose.translate(-getGuiLeft(), -getGuiTop(), 0);
        renderTooltip(guiGraphics, mouseX, mouseY);
        pose.popPose();
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(FargoSoul.rl("textures/gui/soul_container.png"), leftPos, topPos, 0, 0, imageWidth, imageHeight);
        renderEntityInInventoryFollowsMouse(guiGraphics, leftPos + 26, topPos + 8, leftPos + 75, topPos + 78, 30, 0.0625F, mouseX, mouseY, player);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(font, title, titleLabelX, titleLabelY, 4210752, false);
    }

    public void updateButtonPositions() {
        origin.clear();
        origin.addAll(CurioUtils.getSoulFromSlots(player));
        clearWidgets();
        buttonInfoMap.clear();
        AtomicInteger[] columnY = new AtomicInteger[20];
        for (int i = 0; i < columnY.length; i++) columnY[i] = new AtomicInteger(0);
        addRecursive(origin, 0, columnY, null);
    }

    @Override
    protected void clearWidgets() {
        if (this instanceof ScreenAccessor accessor) {
            accessor.invokeGetRenderables().removeIf(renderable -> renderable instanceof SoulContainerButton);
            accessor.invokeGetChildren().removeIf(child -> child instanceof SoulContainerButton);
            accessor.invokeGetNarratables().removeIf(narratable -> narratable instanceof SoulContainerButton);
        }
    }

    private void addRecursive(List<SoulItem> items, int depth, AtomicInteger[] columnY, SoulItem parent) {
        for (SoulItem item : items) {
            int currentY = columnY[depth].get();
            SoulContainerButton btn = new SoulContainerButton(item);
            int x = 6 + getGuiLeft() + getXSize() + depth * (btn.getWidth() + 10);
            int y = currentY - (int) scrollY;
            btn.setPosition(x, y);
            this.addRenderableWidget(btn);
            buttonInfoMap.put(item, new ButtonInfo(x, y, btn.getWidth(), btn.getHeight(), parent));
            columnY[depth].addAndGet(btn.getHeight());
            if (!item.getSoulItemList().isEmpty()) {
                columnY[depth + 1].set(Math.max(columnY[depth + 1].get(), currentY));
                addRecursive(item.getSoulItemList(), depth + 1, columnY, item);
                columnY[depth].set(Math.max(columnY[depth].get(), columnY[depth + 1].get()));
            }
        }
    }

    private void drawTreeConnections(GuiGraphics guiGraphics) {
        int connectionColor = 0xFFFFFFFF;
        for (Map.Entry<SoulItem, ButtonInfo> entry : buttonInfoMap.entrySet()) {
            ButtonInfo childInfo = entry.getValue();
            SoulItem parentItem = childInfo.parent();
            if (parentItem != null) {
                ButtonInfo parentInfo = buttonInfoMap.get(parentItem);
                if (parentInfo != null) {
                    int parentRightX = parentInfo.x() + parentInfo.width();
                    int parentCenterY = parentInfo.y() + parentInfo.height() / 2;
                    int childLeftX = childInfo.x() - 1;
                    int childCenterY = childInfo.y() + childInfo.height() / 2;
                    int midX = parentRightX + (childLeftX - parentRightX) / 2;
                    guiGraphics.hLine(parentRightX, midX, parentCenterY, connectionColor);
                    guiGraphics.vLine(midX, Math.min(parentCenterY, childCenterY), Math.max(parentCenterY, childCenterY), connectionColor);
                    guiGraphics.hLine(midX, childLeftX, childCenterY, connectionColor);
                }
            }
        }
    }

    public record ButtonInfo(int x, int y, int width, int height, SoulItem parent) {

    }

    public int getXPos() {
        if (!buttonInfoMap.isEmpty()) {
            return buttonInfoMap.values().stream().mapToInt(SoulContainerScreen.ButtonInfo::x).min().getAsInt();
        }
        return getGuiLeft() + getXSize();
    }

    public int getYPos() {
        if (!buttonInfoMap.isEmpty()) {
            return buttonInfoMap.values().stream().mapToInt(SoulContainerScreen.ButtonInfo::y).min().getAsInt();
        }
        return getGuiTop();
    }

    public int getWidth() {
        if (!buttonInfoMap.isEmpty()) {
            return (buttonInfoMap.values().stream().mapToInt(b -> b.x() + b.width()).max().getAsInt() - getXPos());
        }
        return 0;
    }

    public int getHeight() {
        if (!buttonInfoMap.isEmpty()) {
            return (buttonInfoMap.values().stream().mapToInt(b -> b.y() + b.height()).max().getAsInt() - getYPos());
        }
        return 0;
    }

    public boolean isHovering(Slot slot, double mouseX, double mouseY) {
        if (slot instanceof SoulSlot) {
            int i = this.leftPos;
            int j = this.topPos;
            double centerX = i + slot.x + 8;
            double centerY = j + slot.y + 8;
            double distance = Math.sqrt((mouseX - centerX) * (mouseX - centerX) + (mouseY - centerY) * (mouseY - centerY));
            return distance <= 8;
        }
        return super.isHovering(slot.x, slot.y, 16, 16, mouseX, mouseY);
    }

}

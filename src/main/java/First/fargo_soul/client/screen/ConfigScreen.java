package First.fargo_soul.client.screen;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.attachment.SoulAbilityEnabledData;
import First.fargo_soul.client.button.ConfigAbilityButton;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.register.AttachmentRegister;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ConfigScreen extends Screen {
    private final List<ConfigAbilityButton> buttonList;
    private int scrollOffset;
    private boolean isDraggingScrollbar = false;
    private static final int BUTTON_HEIGHT = 20;
    private static final int BUTTON_MARGIN = 5;
    private static final int SCROLLBAR_WIDTH = 6;
    private static final int SCROLLBAR_MARGIN = 2;

    public ConfigScreen(Component title, List<ConfigAbilityButton> buttonList) {
        super(title);
        this.buttonList = buttonList;
        this.scrollOffset = 0;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void init() {
        super.init();
        this.clearWidgets();

        int visibleHeight = this.height - 50;
        int maxVisibleButtons = visibleHeight / (BUTTON_HEIGHT + BUTTON_MARGIN);
        int maxScroll = Math.max(0, buttonList.size() - maxVisibleButtons);
        this.scrollOffset = Math.min(this.scrollOffset, maxScroll);
        if (Minecraft.getInstance().player instanceof LocalPlayer localPlayer) {
            Map<SoulItem, Boolean> ability = localPlayer.getData(AttachmentRegister.AbilityEnabledData).getAbility();
            for (int i = scrollOffset; i < Math.min(buttonList.size(), scrollOffset + maxVisibleButtons); i++) {
                ConfigAbilityButton originalButton = buttonList.get(i);
                int yPos = 30 + (i - scrollOffset) * (BUTTON_HEIGHT + BUTTON_MARGIN);
                boolean active = originalButton.isActive();
                if (Minecraft.getInstance().player != null) {
                    active = ability.getOrDefault((SoulItem) originalButton.getItemStack().getItem(), true);
                }
                ConfigAbilityButton button = new ConfigAbilityButton(
                        this.width / 2 - 75,
                        yPos,
                        150,
                        20,
                        originalButton.getItemStack(),
                        originalButton.getMessage(),
                        originalButton.getComponent(),
                        active,
                        originalButton.getOnPress()
                );
                this.addRenderableWidget(button);
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && isMouseOverScrollbar(mouseX, mouseY)) {
            this.isDraggingScrollbar = true;
            updateScrollFromMouse(mouseY);
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            this.isDraggingScrollbar = false;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.isDraggingScrollbar) {
            updateScrollFromMouse(mouseY);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    private boolean isMouseOverScrollbar(double mouseX, double mouseY) {
        int totalButtons = buttonList.size();
        if (totalButtons <= getMaxVisibleButtons()) {
            return false;
        }

        int scrollbarX = this.width / 2 + 75 + SCROLLBAR_MARGIN;
        int scrollbarY = 30;
        int scrollbarHeight = this.height - 80;

        return mouseX >= scrollbarX && mouseX <= scrollbarX + SCROLLBAR_WIDTH &&
                mouseY >= scrollbarY && mouseY <= scrollbarY + scrollbarHeight;
    }

    private void updateScrollFromMouse(double mouseY) {
        int totalButtons = buttonList.size();
        int maxScroll = Math.max(0, totalButtons - getMaxVisibleButtons());
        if (maxScroll == 0) return;

        int scrollbarY = 30;
        int scrollbarHeight = this.height - 80;
        int sliderHeight = Math.max(20, (int) (scrollbarHeight * ((float) getMaxVisibleButtons() / totalButtons)) / 3); // 同样改为三分之一

        // 计算鼠标位置对应的滚动比例
        double relativeY = mouseY - scrollbarY - (sliderHeight / 2.0);
        double progress = relativeY / (scrollbarHeight - sliderHeight);
        progress = Math.max(0, Math.min(1, progress));

        this.scrollOffset = (int) (progress * maxScroll);
        this.init(); // 更新显示
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (scrollY != 0) {
            int newScrollOffset = this.scrollOffset - (int)scrollY;
            int maxScroll = Math.max(0, buttonList.size() - getMaxVisibleButtons());
            this.scrollOffset = Math.max(0, Math.min(newScrollOffset, maxScroll));
            this.init(); // 重新初始化以更新按钮位置
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // 渲染所有组件（包括按钮）
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        // 绘制标题
        guiGraphics.drawString(this.font, this.title, this.width / 2 - this.font.width(this.title) / 2, 10, 0xFFFFFF);
        // 渲染滚动条
        renderScrollbar(guiGraphics);
    }

    private void renderScrollbar(GuiGraphics guiGraphics) {
        int totalButtons = buttonList.size();
        if (totalButtons <= getMaxVisibleButtons()) {
            return;
        }

        int scrollbarX = this.width / 2 + 75 + SCROLLBAR_MARGIN; // 按钮右侧
        int scrollbarY = 30;
        int scrollbarHeight = this.height - 80; // 滚动条总高度

        // 绘制滚动条背景
        guiGraphics.fill(scrollbarX, scrollbarY, scrollbarX + SCROLLBAR_WIDTH, scrollbarY + scrollbarHeight, 0xFF666666);

        // 计算滑块位置和大小
        int maxScroll = totalButtons - getMaxVisibleButtons();
        float scrollProgress = (float) scrollOffset / maxScroll;
        int sliderHeight = Math.max(20, (int) (scrollbarHeight * ((float) getMaxVisibleButtons() / totalButtons)) / 3); // 改为原来的三分之一长度
        int sliderY = scrollbarY + (int) (scrollProgress * (scrollbarHeight - sliderHeight));

        // 绘制滑块
        guiGraphics.fill(scrollbarX, sliderY, scrollbarX + SCROLLBAR_WIDTH, sliderY + sliderHeight, 0xFFCCCCCC);

        // 绘制滑块边框
        guiGraphics.renderOutline(scrollbarX, sliderY, SCROLLBAR_WIDTH, sliderHeight, 0xFF000000);
    }

    private int getMaxVisibleButtons() {
        int visibleHeight = this.height - 50;
        return visibleHeight / (BUTTON_HEIGHT + BUTTON_MARGIN);
    }

    public record Packet(ResourceLocation resourceLocation, boolean is) implements CustomPacketPayload {

        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(FargoSoul.MODID, "ability_data_config");
        public static final Type<Packet> TYPE = new Type<>(ID);

        public static final StreamCodec<ByteBuf, Packet> STREAM_CODEC = StreamCodec.composite(
                ResourceLocation.STREAM_CODEC,
                Packet::resourceLocation,
                ByteBufCodecs.BOOL,
                Packet::is,
                Packet::new
        );

        @Override
        public @NotNull Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }

        public void handle(IPayloadContext context) {
            context.enqueueWork(() -> {
                Player player = context.player();
                SoulItem soulItem = (SoulItem) BuiltInRegistries.ITEM.get(resourceLocation);
                SoulAbilityEnabledData enabledData = player.getData(AttachmentRegister.AbilityEnabledData);
                enabledData.setEnabled(soulItem, is);
                player.syncData(AttachmentRegister.AbilityEnabledData);
            });
        }

    }

}
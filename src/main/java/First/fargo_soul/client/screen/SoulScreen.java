package First.fargo_soul.client.screen;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.attachment.SoulAbilityEnabledData;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.utils.CurioUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class SoulScreen extends Screen {

    private final List<SoulItem> origin;
    private double scrollY;
    private final Map<SoulItem, ButtonInfo> buttonInfoMap = new HashMap<>();

    public SoulScreen(List<SoulItem> origin) {
        super( Component.translatable("fargo_soul.screen.soul"));
        this.origin = origin;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    protected void init() {
        super.init();
        this.clearWidgets();
        buttonInfoMap.clear();
        updateButtonPositions();
    }

    private void updateButtonPositions() {
        this.clearWidgets();
        AtomicInteger[] columnY = new AtomicInteger[20];
        for (int i = 0; i < columnY.length; i++) columnY[i] = new AtomicInteger(30);
        addRecursive(origin, 0, columnY, null);
    }

    private void addRecursive(List<SoulItem> items, int depth, AtomicInteger[] columnY, SoulItem parent) {
        for (SoulItem item : items) {
            int currentY = columnY[depth].get();
            SoulButton btn = new SoulButton(item);
            int x = 10 + depth * (btn.getWidth() + 10);
            int y = currentY - (int) scrollY;
            btn.setPosition(x, y);
            this.addRenderableWidget(btn);
            buttonInfoMap.put(item, new ButtonInfo(x, y, btn.getWidth(), btn.getHeight(), parent));
            columnY[depth].addAndGet(btn.getHeight() + 1);
            if (!item.getSoulItemList().isEmpty()) {
                columnY[depth + 1].set(Math.max(columnY[depth + 1].get(), currentY));
                addRecursive(item.getSoulItemList(), depth + 1, columnY, item);
                columnY[depth].set(Math.max(columnY[depth].get(), columnY[depth + 1].get()));
            }
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        this.scrollY -= scrollY * 20;
        updateButtonPositions();
        return true;
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        drawTreeConnections(guiGraphics);
        guiGraphics.renderTooltip(this.font, this.title, this.width / 2 - this.font.width(this.title) / 2, 23);
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

    private record ButtonInfo(int x, int y, int width, int height, SoulItem parent) {}

    private static class SoulButton extends Button {

        private boolean isActive;
        private final SoulItem soulItem;

        protected SoulButton(SoulItem soulItem) {
            super(
                    0,
                    0,
                    120,
                    20,
                    soulItem.getName(soulItem.getDefaultInstance()),
                    button -> {
                        if (button instanceof SoulButton configButton) {
                            configButton.setActive(!configButton.isActive());
                            ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(soulItem);
                            if (Minecraft.getInstance().player instanceof LocalPlayer player) {
                                player.getData(AttachmentRegister.AbilityEnabledData).setEnabled(soulItem, configButton.isActive());
                            }
                            PacketDistributor.sendToServer(new Packet(resourceLocation, configButton.isActive()));
                        }
                    },
                    DEFAULT_NARRATION
            );
            this.soulItem = soulItem;
            this.isActive = Minecraft.getInstance().player instanceof LocalPlayer player && player.getData(AttachmentRegister.AbilityEnabledData).isEnabled(soulItem);
        }

        @Override
        protected void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
            ItemStack defaultInstance = soulItem.getDefaultInstance();
            guiGraphics.renderItem(defaultInstance, this.getX() + 2, this.getY() + 2);
            boolean active = this.isActive();
            String icon = active ? "✔" : "✘";
            int iconColor = active ? 0xFF55FF55 : 0xFFFF5555;
            int iconX = this.getX() + this.width - 15;
            int iconY = this.getY() + (this.height - 8) / 2;
            guiGraphics.drawString(Minecraft.getInstance().font, icon, iconX, iconY, iconColor, true);
            if (this.isHovered()) {
                List<Component> componentList = new ArrayList<>();
                componentList.add(soulItem.getName(defaultInstance));
                List<Component> soulItemAttributesComponent = CurioUtils.getSoulItemAttributesComponent(soulItem);
                if (!soulItemAttributesComponent.isEmpty()) {
                    componentList.add(Component.empty());
                    componentList.addAll(soulItemAttributesComponent);
                }
                guiGraphics.renderTooltip(Minecraft.getInstance().font, componentList, defaultInstance.getTooltipImage(), mouseX, mouseY);
            }
        }

        @Override
        public void playDownSound(@NotNull SoundManager handler) {
            handler.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, this.isActive() ? 0.85F : 1.15F));
        }

        public boolean isActive() {
            return isActive;
        }

        public void setActive(boolean active) {
            isActive = active;
        }

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
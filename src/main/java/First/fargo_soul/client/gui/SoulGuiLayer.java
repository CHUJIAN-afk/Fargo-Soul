package First.fargo_soul.client.gui;

import First.fargo_soul.attachment.SoulAbilityEnabledData;
import First.fargo_soul.config.ClientConfig;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.utils.CurioUtils;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SoulGuiLayer {

    private static final Table<SoulItem, Integer, SoulRenderInfo> SoulTooltipManager = HashBasedTable.create();

    public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        if (!ClientConfig.ShowSoulTooltip.get()) return;
        float partialTick = deltaTracker.getGameTimeDeltaPartialTick(true);
        if (Minecraft.getInstance().player instanceof LocalPlayer player) {
            Font font = Minecraft.getInstance().font;
            List<SoulItem> soulItemList = CurioUtils.getEntitySoulItem(player);
            SoulAbilityEnabledData enabledData = player.getData(AttachmentRegister.AbilityEnabledData);
            soulItemList = soulItemList.stream().filter(enabledData::isEnabled).toList();
            long gameTime = player.level().getGameTime();
            for (SoulItem soulItem : soulItemList) {
                soulItem.renderGui(player, guiGraphics, partialTick, font);
                List<SoulRenderInfo> renderInfoList = new ArrayList<>();
                soulItem.getRenderInfo(renderInfoList);
                List<Component> guiTooltip = soulItem.getGuiTooltip(player);
                for (Component component : guiTooltip) {
                    SoulRenderInfo renderInfo = SoulTooltipManager.get(soulItem, guiTooltip.indexOf(component));
                    if (renderInfo == null && !component.equals(Component.empty())) {
                        renderInfo = new SoulRenderInfo(soulItem, component, gameTime - 40);
                        SoulTooltipManager.put(soulItem, guiTooltip.indexOf(component), renderInfo);
                    }
                    if (renderInfo != null && !renderInfo.getTooltip().toString().equals(component.toString())) {
                        renderInfo.setTooltip(component);
                        renderInfo.setStartTime(gameTime);
                    }
                }
            }
            renderSoulTooltips(enabledData, guiGraphics, font, gameTime);
        }
    }

    private static void renderSoulTooltips(SoulAbilityEnabledData enabledData, GuiGraphics guiGraphics, Font font, long gameTime) {
        if (!SoulTooltipManager.isEmpty()) {
            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();
            float scale = ClientConfig.ShowSoulTooltipScale.get().floatValue();
            poseStack.scale(scale, scale, scale);
            int xOffset = ClientConfig.ShowSoulTooltipXOffset.get();
            int yOffset = ClientConfig.ShowSoulTooltipYOffset.get();
            int scaledHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();
            int totalLines = 0;
            for (SoulRenderInfo renderInfo : SoulTooltipManager.values()) {
                if (gameTime - renderInfo.getStartTime() < 40 && enabledData.isEnabled(renderInfo.soulItem)) {
                    totalLines++;
                }
            }
            int interval = (int) (10 + ((float) (scaledHeight - (totalLines * 16)) / (float) scaledHeight) * 6);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            int currentY = 20 + (int) ((yOffset) * scale);
            int x = 2 + (int) ((xOffset) * scale);
            for (SoulRenderInfo renderInfo : SoulTooltipManager.values()) {
                SoulItem soulItem = renderInfo.getSoulItem();
                long timeDiff = gameTime - renderInfo.getStartTime();
                if (timeDiff < 40 && enabledData.isEnabled(soulItem)) {
                    poseStack.pushPose();
                    poseStack.translate(0, 0, 1000);
                    guiGraphics.renderItem(soulItem.getDefaultInstance(), x, currentY - 16);
                    poseStack.popPose();
                    float alpha = timeDiff < 4 ? 1.3f : timeDiff < 10 ? 1.5f - timeDiff * 0.05f : timeDiff < 35 ? 1.0f : 1.0f - (timeDiff - 35f) / 5.0f;
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha * 0.8F);

                    guiGraphics.renderTooltip(font, renderInfo.getTooltip(), x - 7, currentY);

                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                    currentY += interval;
                }
            }
            poseStack.popPose();
        }
    }

    public static final class SoulRenderInfo {

        private final SoulItem soulItem;
        private Component tooltip;
        private long startTime;
        private int color;
        private float percentage;

        public SoulRenderInfo(SoulItem soulItem, Component tooltip, long startTime) {
            this.soulItem = soulItem;
            this.tooltip = tooltip;
            this.startTime = startTime;
            this.color = 0xFFFFFFFF;
            this.percentage = 0.0F;
        }

        public SoulItem getSoulItem() {
            return soulItem;
        }

        public Component getTooltip() {
            return tooltip;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setTooltip(Component tooltip) {
            this.tooltip = tooltip;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

    }

}
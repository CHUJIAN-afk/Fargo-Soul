package First.fargo_soul.client.gui;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.config.ClientConfig;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.SoulUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SoulGuiLayer {

    public static final Map<Object, SoulRenderInfo> SoulTooltipManager = new LinkedHashMap<>();
    public static final int MaxRenderTime = 40;
    private static long lastUpdateTime = 0;

    public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        if (ClientConfig.ShowSoulTooltip.get() && Minecraft.getInstance().player instanceof LocalPlayer player) {
            List<SoulItem> soulItemList = SoulUtils.RegisterSoulList;
            float partialTick = deltaTracker.getGameTimeDeltaTicks();
            for (SoulItem soulItem : soulItemList) {
                soulItem.renderGui(player, guiGraphics, partialTick, Minecraft.getInstance().font);
            }
            long gameTime = player.level().getGameTime();
            if (lastUpdateTime != gameTime) {
                lastUpdateTime = gameTime;
                SoulGuiLayer.SoulRenderManager renderManager = new SoulGuiLayer.SoulRenderManager(player, SoulTooltipManager);
                for (SoulItem soulItem : soulItemList) {
                    if (CurioUtils.isEquipped(player, soulItem.getClass())) {
                        soulItem.getSoulRenderInfo(renderManager);
                    }
                }
            }
            render(guiGraphics, gameTime);
        }
    }

    private static void render(GuiGraphics guiGraphics, long gameTime) {
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        float scale = ClientConfig.ShowSoulTooltipScale.get().floatValue();
        poseStack.scale(scale, scale, scale);
        int xOffset = ClientConfig.ShowSoulTooltipXOffset.get();
        int yOffset = ClientConfig.ShowSoulTooltipYOffset.get();
        Collection<SoulRenderInfo> soulRenderInfos = SoulTooltipManager.values();
        int interval = ClientConfig.InformationInterval.get();
        int currentY = 2 + (int) ((yOffset) * scale);
        int x = 2 + (int) ((xOffset) * scale);
        for (SoulRenderInfo renderInfo : soulRenderInfos) {
            long timeDiff = gameTime - renderInfo.startTime;
            if (timeDiff < MaxRenderTime) {
                float renderPercentage = (float) timeDiff / MaxRenderTime;
                renderInfo.alpha = renderPercentage < 0.1f ? 1f : renderPercentage < 0.2f ? 1f - (renderPercentage - 0.1f) : renderPercentage < 0.9f ? 0.9f : 0.9f * (1f - (renderPercentage - 0.9f) * 10f);
                renderInfo.x = x;
                renderInfo.y = currentY;
                currentY += interval;
                renderInfo.render(guiGraphics);
            }
        }
        poseStack.popPose();
    }

    public static class SoulRenderManager {

        private final Player player;
        private final long startTime;
        private final Map<Object, SoulRenderInfo> SoulTooltipManager;

        public SoulRenderManager(Player player, Map<Object, SoulRenderInfo> SoulTooltipManager) {
            this.player = player;
            this.startTime = player.level().getGameTime();
            this.SoulTooltipManager = SoulTooltipManager;
        }

        public <T extends SoulItem> void add(SoulItem soulItem, Class<T> tClass, SoulRenderType type) {
            add(soulItem, tClass.getName(), type);
        }

        public void add(SoulItem soulItem, String id, SoulRenderType type) {
            SoulAbilityData.SoulInfo soulInfo = SoulAbilityData.getSoulInfo(player, id);
            Object key = List.of(soulItem, id, type);
            SoulRenderInfo soulRenderInfo = SoulTooltipManager.computeIfAbsent(key, k -> new SoulRenderInfo(soulItem, startTime - 40, 0, soulItem.getModRarity().color(), type));
            soulRenderInfo.setPercentage(switch (type) {
                case Duration -> (float) soulInfo.getDuration() / soulInfo.getMaxDuration();
                case Stack -> (float) soulInfo.getStacks() / soulInfo.getMaxStacks();
                case Cooldown -> 1 - (float) soulInfo.getCooldown() / soulInfo.getMaxCooldown();
            });
            if (soulRenderInfo.isChange || (soulRenderInfo.percentage > 0 && type == SoulRenderType.Stack)) {
                soulRenderInfo.startTime = startTime;
                soulRenderInfo.isChange = false;
            }
        }

    }

    public static class SoulRenderInfo {

        private final SoulItem soulItem;
        private final int color;
        private final SoulRenderType soulRenderType;
        private final ResourceLocation frame;
        private final ResourceLocation core;
        private final int coreWidth;
        private final int height;
        private long startTime;
        private boolean isChange = false;
        private int x, y;
        private float alpha, percentage;

        public SoulRenderInfo(SoulItem soulItem, long startTime, float percentage, int color, SoulRenderType soulRenderType) {
            this.soulItem = soulItem;
            this.startTime = startTime;
            this.percentage = percentage;
            this.color = color;
            this.soulRenderType = soulRenderType;
            switch (soulRenderType) {
                case Duration -> {
                    frame = FargoSoul.rl("textures/basic/duration_bar.png");
                    core = FargoSoul.rl("textures/basic/duration.png");
                    coreWidth = 32;
                    height = 6;
                }
                case Stack -> {
                    frame = FargoSoul.rl("textures/basic/stack_bar.png");
                    core = FargoSoul.rl("textures/basic/stack.png");
                    coreWidth = 31;
                    height = 12;
                }
                case Cooldown -> {
                    frame = FargoSoul.rl("textures/basic/cooldown_bar.png");
                    core = FargoSoul.rl("textures/basic/cooldown.png");
                    coreWidth = 31;
                    height = 6;
                }
                default -> throw new IllegalStateException("Unsupported render TYPE: " + soulRenderType);
            }
        }

        public void render(GuiGraphics guiGraphics) {
            guiGraphics.setColor(1.0F, 1.0F, 1.0F, alpha);
            guiGraphics.renderItem(soulItem.getDefaultInstance(), x, y);
            int barX = x + 13;
            int barY = soulRenderType == SoulRenderType.Stack ? y + 2 : y + 5;
            guiGraphics.blit(frame, barX, barY, 0, 0, 33, height, 33, height);
            guiGraphics.setColor(((color >> 16) & 0xFF) / 255.0F, ((color >> 8) & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, alpha);
            guiGraphics.blit(core, barX, barY, 0, 0, Math.round(percentage * coreWidth), height, coreWidth, height);
            guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        }

        public void setPercentage(float percentage) {
            percentage = Math.round(percentage * 1000) / 1000.0f;
            if (this.percentage != percentage) {
                this.percentage = percentage;
                isChange = true;
            }
        }

    }

}
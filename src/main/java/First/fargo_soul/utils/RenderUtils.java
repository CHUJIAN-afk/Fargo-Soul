package First.fargo_soul.utils;

import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.item.base.SoulItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.confluence.lib.common.component.ModRarity;

public abstract class RenderUtils {

    public static float getAgeInTicks(Entity attacker, float partialTick, float speed) {
        float render = attacker.tickCount * speed;
        return Mth.lerp(partialTick, render - speed, render);
    }

    public static float getAgeInTicks(Level level, float partialTick, float speed) {
        float render = level.getGameTime() * speed;
        return Mth.lerp(partialTick, render - speed, render);
    }

    /**
     * 生成标准格式的持续时间提示文本
     *
     * @param soulItem 物品
     * @param soulInfo 灵魂能力数据
     * @return 持续时间提示组件
     */
    public static Component createDurationTooltip(SoulItem soulItem, String key, SoulAbilityData.SoulInfo soulInfo) {
        return createTooltip(soulItem, key, soulInfo, TooltipType.Duration);
    }

    /**
     * 生成标准格式的层数提示文本
     *
     * @param soulItem  物品
     * @param soulInfo  灵魂能力数据
     * @return 层数提示组件
     */
    public static Component createStackTooltip(SoulItem soulItem, String key, SoulAbilityData.SoulInfo soulInfo) {
        return createTooltip(soulItem, key, soulInfo, TooltipType.Stack);
    }

    /**
     * 生成标准格式的冷却提示文本
     *
     * @param soulItem 物品
     * @param soulInfo 灵魂能力数据
     * @return 冷却提示组件
     */
    public static Component createCooldownTooltip(SoulItem soulItem, String key, SoulAbilityData.SoulInfo soulInfo) {
        return createTooltip(soulItem, key, soulInfo, TooltipType.Cooldown);
    }

    private static Component createTooltip(SoulItem soulItem, String key, SoulAbilityData.SoulInfo soulInfo, TooltipType type) {
        ModRarity modRarity = soulItem.getModRarity();
        MutableComponent tooltip = Component.literal(" ".repeat(6));
        tooltip = tooltip.append(Component.translatable(key).withColor(modRarity.color()));
        tooltip = tooltip.append(Component.literal(" ".repeat(4)));
        switch (type) {
            case Duration:
                float seconds = soulInfo.getDuration() / 20.0f;
                tooltip = tooltip.append(Component.literal(String.format("%.0fs", seconds)).withColor(modRarity.color()));
                break;
            case Stack:
                if (soulInfo.getMaxStacks() > 0) {
                    float percentage = (float) soulInfo.getStacks() / soulInfo.getMaxStacks();
                    tooltip = tooltip.append(createBar(percentage, modRarity));
                    break;
                }
                return Component.empty();
            case Cooldown:
                if (soulInfo.getMaxCooldown() > 0) {
                    float percentage = 1 - (float) soulInfo.getCooldown() / soulInfo.getMaxCooldown();
                    tooltip = tooltip.append(createBar(percentage, modRarity));
                    break;
                }
                return Component.empty();
        }
        return tooltip;
    }

    private enum TooltipType {
        Duration, Stack, Cooldown
    }

    /**
     * 生成标准长度的进度条文本
     * @param percentage 百分比 (0.0-1.0)
     * @return 格式化的进度条
     */
    public static Component createBar(float percentage, ModRarity modRarity) {
        int length = 8;
        int totalSteps = length * 8;
        int currentSteps = (int) (percentage * totalSteps);
        currentSteps = Math.max(0, Math.min(currentSteps, totalSteps));
        int fullBlocks = currentSteps / 8;
        int partialStep = currentSteps % 8;
        MutableComponent bar = Component.literal("");
        int usedLength = 0;
        for (int i = 0; i < fullBlocks; i++) {
            bar.append(Component.literal("█").withColor(modRarity.color()));
            usedLength++;
        }
        if (partialStep > 0 && usedLength < length) {
            String[] partialChars = {"", "▏", "▎", "▍", "▌", "▋", "▊", "▉"};
            bar.append(Component.literal(partialChars[partialStep]).withColor(modRarity.color()));
            usedLength++;
        }
        while (usedLength < length) {
            bar.append(Component.literal("░").withStyle(ChatFormatting.DARK_GRAY));
            usedLength++;
        }
        return bar;
    }

}
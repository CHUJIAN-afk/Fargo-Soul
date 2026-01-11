package First.fargo_soul.item.base;

import First.fargo_soul.client.gui.SoulGuiLayer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.Input;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ISoulItem {

    default void getRenderInfo(List<SoulGuiLayer.SoulRenderInfo> renderInfoList) {

    }

    default void drop(LivingDropsEvent event) {

    }

    default void shieldBlock(LivingShieldBlockEvent event) {

    }

    default void pickup(ItemEntityPickupEvent.Post event) {

    }

    default void itemUseFinish(LivingEntityUseItemEvent.Finish event) {

    }

    default void render(RenderLivingEvent.Post<?, ?> event) {

    }

    default void criticalHit(CriticalHitEvent event) {

    }

    default void heal(LivingHealEvent event) {

    }

    default void targetChange(LivingChangeTargetEvent event) {

    }

    /**
     * 实体死亡时调用，如果事件在之前被取消，不会调用
     */
    default void death(LivingDeathEvent event) {

    }

    default void effectApplicable(MobEffectEvent.Applicable event) {

    }

    /**
     * 子魂石列表
     * @return 子魂石列表
     */
    default List<SoulItem> getSoulItemList() {
        return List.of();
    }

    /**
     * 穿戴时实体的常驻属性修改器
     * @return 常驻属性修改器
     */
    default Map<Holder<Attribute>, AttributeModifier> getAttributeModifiers() {
        return Map.of();
    }

    /**
     * 穿戴时实体触发tick事件时调用
     */
    default void tick(LivingEntity ticker) {

    }

    /**
     * 穿戴时当实体触发伤害事件时调用
     */
    default void hurt(LivingIncomingDamageEvent event) {

    }

    default void renderGui(Player player, GuiGraphics guiGraphics, float partialTick, Font font) {

    }

    default List<Component> getGuiTooltip(Player player) {
        return new ArrayList<>();
    }

    default void keyPressed(Player player, int key) {

    }

    default void movementInput(Player player, Input input) {


    }

}

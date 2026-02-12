package First.fargo_soul.common.item.base;

import First.fargo_soul.client.gui.SoulGuiRenderManager;
import First.fargo_soul.common.attachment.SoulAbilityData;
import First.fargo_soul.common.event.modEvent.PlayerFlyEvent;
import First.fargo_soul.common.event.modEvent.SprintEvent;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.Input;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ISoulItem {

    default void renderSlot(Slot slot, GuiGraphics guiGraphics, float partialTick) {
    }

    default <T extends SoulItem> SoulAbilityData.SoulInfo getInfo(LivingEntity livingEntity, Class<T> tClass) {
        return SoulAbilityData.getSoulInfo(livingEntity, tClass);
    }

    default SoulAbilityData.SoulInfo getInfo(LivingEntity livingEntity, String id) {
        return SoulAbilityData.getSoulInfo(livingEntity, id);
    }

    default void fly(PlayerFlyEvent event) {
    }

    default void sprintServer(SprintEvent.Server event) {
    }

    default void sprintClient(SprintEvent.Client event) {
    }

    default void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {

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

    default void death(LivingDeathEvent event) {
    }

    default void effectApplicable(MobEffectEvent.Applicable event) {
    }

    default List<SoulItem> getSoulItemList() {
        return new ArrayList<>();
    }

    default Map<Holder<Attribute>, AttributeModifier> getAttributeModifiers() {
        return new HashMap<>();
    }

    default void tick(LivingEntity ticker) {
    }

    default void hurt(LivingIncomingDamageEvent event) {
    }

    default void renderGui(Player player, GuiGraphics guiGraphics, float partialTick, Font font) {
    }

    default String keyPressed(Player player, int key) {
        return null;
    }

    default void keyHandle(Player player, String key) {
    }

    default void movementInput(Player player, Input input) {
    }

}

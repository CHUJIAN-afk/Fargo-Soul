package first.fargo_soul.api.item;

import first.fargo_soul.common.event.modEvent.PlayerFlyEvent;
import first.fargo_soul.common.event.modEvent.SprintEvent;
import net.minecraft.client.player.Input;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.HashMap;
import java.util.Map;

public interface ISoulItem extends ICurioItem {

    default void attack(@NotNull LivingEntity target, @NotNull LivingEntity attacker, @NotNull DamageContainer container, boolean isClient) {

    }

    default void target(@NotNull LivingEntity target, @Nullable Entity attacker, @NotNull DamageContainer container, boolean isClient) {

    }

    default void fly(PlayerFlyEvent event) {
    }

    default void sprintServer(SprintEvent.Server event) {
    }

    default void sprintClient(SprintEvent.Client event) {
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

    default Map<Holder<Attribute>, AttributeModifier> getAttributeModifiers() {
        return new HashMap<>();
    }

    default void movementInput(Player player, Input input) {
    }
}

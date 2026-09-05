package first.fargo_soul.common;


import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.attachment.SoulItemData;
import first.fargo_soul.common.attachment.SoulTargetCache;
import first.fargo_soul.common.blcokEntity.CosmicCrucibleBlockEntity;
import first.fargo_soul.common.item.base.ValueModifier;
import first.fargo_soul.register.FargoSoulAttachmentRegister;
import first.fargo_soul.register.FargoSoulAttributeRegister;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = FargoSoul.MODID)
public class Event {

    @SubscribeEvent
    public static void tick(EntityTickEvent.Post event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide()) {
            player.getData(FargoSoulAttachmentRegister.SOUL_ITEM_DATA).tick(player);
            SoulTargetCache.get(player).tick(player);
            SoulItemData.forEach(player, soulItem -> soulItem.tick(player));
        }
    }

    @SubscribeEvent
    public static void tick(CurioChangeEvent event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide()) {
            player.getData(FargoSoulAttachmentRegister.SOUL_ITEM_DATA).update(player);
        }
    }

    @SubscribeEvent
    public static void attack(LivingIncomingDamageEvent event) {
        Entity attacker = event.getSource().getEntity();
        LivingEntity target = event.getEntity();
        DamageContainer container = event.getContainer();
        if (!target.level().isClientSide()) {
            List<ValueModifier> modifiers = new ArrayList<>();
            if (attacker instanceof Player player) {
                SoulItemData.forEach(player, soulItem -> soulItem.attack(player, target, container, modifiers));
            }
            if (target instanceof Player player) {
                SoulItemData.forEach(player, soulItem -> soulItem.hurt(attacker, player, container, modifiers));
            }
            event.setAmount(ValueModifier.getModifierAfter(event.getAmount(), modifiers));
        }
    }

    @SubscribeEvent
    public static void death(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide()) {
            SoulItemData.forEach(player, soulItem -> soulItem.death(player, event.getSource()));
        }
    }

    @SubscribeEvent
    public static void applicable(MobEffectEvent.Applicable event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide()) {
            SoulItemData.forEach(player, soulItem -> {
                if (!soulItem.effectApplicable(player, event.getEffectInstance())) {
                    event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
                }
            });
        }
    }

    @SubscribeEvent
    public static void EntityAttributeModificationEvent(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, FargoSoulAttributeRegister.ArmorPierce);
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        CosmicCrucibleBlockEntity.registerCapabilities(event);
    }
}

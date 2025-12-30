package First.fargo_soul.item.terraSoul.willPower;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.utils.AttributeUtils;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

public class ValhallaKnightSoul extends SoulItem {

    public ValhallaKnightSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.YELLOW));
    }

    @EventBusSubscriber(modid = FargoSoul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Tick(EntityTickEvent.Post event){
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                List<Entity> passengers = attacker.getPassengers();
                LivingEntity passenger = null;
                if (!passengers.isEmpty()) {
                    for (Entity passenger1 : passengers) {
                        if (passenger1 instanceof LivingEntity livingEntity && CurioUtils.isEquipped(livingEntity, ValhallaKnightSoul.class)) {
                            passenger = livingEntity;
                            break;
                        }
                    }
                }
                AttributeUtils.ConditionAttributeModifier(attacker, Attributes.MOVEMENT_SPEED, ItemRegister.ValhallaKnightSoulItem.getId(), 0.25, AttributeModifier.Operation.ADD_MULTIPLIED_BASE, passenger != null);
                AttributeUtils.ConditionAttributeModifier(attacker, Attributes.JUMP_STRENGTH, ItemRegister.ValhallaKnightSoulItem.getId(), 0.25, AttributeModifier.Operation.ADD_MULTIPLIED_BASE, passenger != null);
                AttributeUtils.ConditionAttributeModifier(attacker, Attributes.ARMOR, ItemRegister.ValhallaKnightSoulItem.getId(), 15, AttributeModifier.Operation.ADD_VALUE, passenger != null);
                AttributeUtils.ConditionAttributeModifier(
                        attacker,
                        Attributes.ARMOR,
                        ItemRegister.ValhallaKnightSoulItem.getId(),
                        15,
                        AttributeModifier.Operation.ADD_VALUE,
                        attacker.getVehicle() != null && CurioUtils.isEquipped(attacker, ValhallaKnightSoul.class)
                );
            }
        }

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getSource().is(DamageTypes.FALL)) {
                if (event.getEntity() instanceof LivingEntity livingEntity) {
                    List<Entity> entities = livingEntity.getPassengers();
                    for (Entity entity : entities) {
                        if (entity instanceof LivingEntity attacker && CurioUtils.isEquipped(attacker, ValhallaKnightSoul.class)) {
                            event.setCanceled(true);
                            break;
                        }
                    }
                }
                if (event.getEntity() instanceof LivingEntity attacker && CurioUtils.isEquipped(attacker, ValhallaKnightSoul.class)) {
                    if (attacker.getVehicle() != null) {
                        event.setCanceled(true);
                    }
                }
            }
        }

        @SubscribeEvent
        public static void ValhallaKnightSoulHealHandler(LivingHealEvent event) {
            if (event.getEntity() instanceof LivingEntity attacker && CurioUtils.isEquipped(attacker, ValhallaKnightSoul.class)) {
                event.setAmount(event.getAmount() * 1.15f);
            }
        }

    }

}

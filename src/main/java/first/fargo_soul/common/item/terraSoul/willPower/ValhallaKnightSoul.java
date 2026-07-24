package first.fargo_soul.common.item.terraSoul.willPower;

import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.register.FargoSoulItemRegister;
import first.fargo_soul.utils.AttributeUtils;
import first.fargo_soul.utils.CurioUtils;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

public class ValhallaKnightSoul extends SoulItem {

    public ValhallaKnightSoul(Properties properties) {
        super();
    }

    @Override
    public void tick(LivingEntity ticker, boolean isClient) {
        if (!ticker.level().isClientSide()) {
            List<Entity> passengers = ticker.getPassengers();
            LivingEntity passenger = null;
            if (!passengers.isEmpty()) {
                for (Entity passenger1 : passengers) {
                    if (passenger1 instanceof LivingEntity livingEntity && CurioUtils.isEquipped(livingEntity, ValhallaKnightSoul.class)) {
                        passenger = livingEntity;
                        break;
                    }
                }
            }
            AttributeUtils.condition(ticker, Attributes.MOVEMENT_SPEED, FargoSoulItemRegister.ValhallaKnightSoulItem.getId(), 0.25, AttributeModifier.Operation.ADD_MULTIPLIED_BASE, passenger != null);
            AttributeUtils.condition(ticker, Attributes.JUMP_STRENGTH, FargoSoulItemRegister.ValhallaKnightSoulItem.getId(), 0.25, AttributeModifier.Operation.ADD_MULTIPLIED_BASE, passenger != null);
            AttributeUtils.condition(ticker, Attributes.ARMOR, FargoSoulItemRegister.ValhallaKnightSoulItem.getId(), 15, AttributeModifier.Operation.ADD_VALUE, passenger != null);
            AttributeUtils.condition(
                    ticker,
                    Attributes.ARMOR,
                    FargoSoulItemRegister.ValhallaKnightSoulItem.getId(),
                    15,
                    AttributeModifier.Operation.ADD_VALUE,
                    ticker.getVehicle() != null && CurioUtils.isEquipped(ticker, ValhallaKnightSoul.class)
            );
        }
    }

    @Override
    public void livingIncomingDamageEvent(LivingIncomingDamageEvent event) {
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

    @Override
    public void heal(LivingHealEvent event) {
        if (event.getEntity() instanceof LivingEntity attacker && CurioUtils.isEquipped(attacker, ValhallaKnightSoul.class)) {
            event.setAmount(event.getAmount() * 1.15f);
        }
    }

}

package first.fargo_soul.mixin.minecraft;


import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import first.fargo_soul.register.FargoSoulAttributeRegister;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CombatRules.class)
public class CombatRulesMixin {

    @WrapMethod(method = "getDamageAfterAbsorb")
    private static float modifyArmorValue(LivingEntity entity, float damage, DamageSource damageSource, float armorValue, float armorToughness, Operation<Float> original) {
        if (damageSource.getEntity() instanceof LivingEntity attacker) {
            if (attacker.getAttribute(FargoSoulAttributeRegister.ArmorPierce) instanceof AttributeInstance attributeInstance) {
                armorValue -= (float) attributeInstance.getValue();
            }
        }
        return original.call(entity, damage, damageSource, Math.max(armorValue, 0), armorToughness);
    }
}

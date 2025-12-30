package First.fargo_soul.mixin.minecraft;


import First.fargo_soul.register.AttributeRegister;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(CombatRules.class)
public class CombatRulesMixin {

    @ModifyVariable(
            method = "getDamageAfterAbsorb",
            at = @At("HEAD"),
            argsOnly = true,
            ordinal = 1
    )
    private static float modifyArmorValue(float armorValue, LivingEntity entity, float damage, DamageSource damageSource, float armorToughness) {
        if (damageSource.getEntity() instanceof LivingEntity attacker) {
            if (attacker.getAttribute(AttributeRegister.ArmorPierce) instanceof AttributeInstance attributeInstance) {
                armorValue -= (float) attributeInstance.getValue();
            }
        }
        return Math.max(armorValue, 0);
    }

}

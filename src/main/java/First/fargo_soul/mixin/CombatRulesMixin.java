package First.fargo_soul.mixin;


import First.fargo_soul.Item.Soul.Souls;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
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
        if (damageSource.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.RedRidingSoul.get())) {
            float ArmorPierce = player.getPersistentData().getFloat("RedRidingSoul");
            armorValue -= ArmorPierce * 2;
        }
        return Math.max(armorValue, 0);
    }

}

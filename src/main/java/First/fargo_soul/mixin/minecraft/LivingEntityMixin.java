package First.fargo_soul.mixin.minecraft;

import First.fargo_soul.item.terraSoul.cosmicPower.WizardSoul;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "doPush", at = @At("HEAD"), cancellable = true)
    private void doPush(Entity entity, CallbackInfo ci) {
        if (entity instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
            if (CurioUtils.isEquipped(attacker, WizardSoul.class)) {
                ci.cancel();
            }
        }
    }

}

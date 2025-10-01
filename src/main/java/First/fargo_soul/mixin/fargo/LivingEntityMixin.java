package First.fargo_soul.mixin.fargo;

import First.fargo_soul.Item.Soul.TerraSoul.CosmicPower.SoulStone.WizardSoul;
import First.fargo_soul.Utils.SoulUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(
            method = "doPush",
            at = @At("HEAD"),
            cancellable = true
    )
    private void doPush(Entity entity, CallbackInfo ci) {
        if (entity instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
            if (SoulUtils.isEquipped(attacker, WizardSoul.class)) {
                ci.cancel();
            }
        }
    }

}

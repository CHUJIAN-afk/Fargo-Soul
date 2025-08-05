package First.fargo_soul.mixin.fargo;

import net.minecraft.world.entity.projectile.Arrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Arrow.class)
public class ArrowMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        Arrow arrow = (Arrow) (Object) this;
        if (arrow.getPersistentData().getBoolean("soul") && arrow.tickCount > 160) {
            arrow.discard();
        }
    }

}

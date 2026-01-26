package First.fargo_soul.mixin.minecraft;


import First.fargo_soul.common.entity.projectile.Bone;
import First.fargo_soul.common.entity.projectile.Needle;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public class AbstractArrowMixin {

    @Shadow private int life;

    @Inject(
            method = "tickDespawn",
            at = @At("HEAD"),
            cancellable = true
    )
    private void tickDespawn(CallbackInfo ci) {
        AbstractArrow abstractArrow = (AbstractArrow) (Object) this;
        if (abstractArrow instanceof Needle || abstractArrow instanceof Bone) {
            if (life > 40) {
                abstractArrow.discard();
                ci.cancel();
            }
        }
    }

}

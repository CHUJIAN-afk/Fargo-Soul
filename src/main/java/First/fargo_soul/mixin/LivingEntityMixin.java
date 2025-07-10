package First.fargo_soul.mixin;


import First.fargo_soul.Curios.Souls;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "actuallyHurt", at = @At("HEAD"), cancellable = true)
    protected void actuallyHurt(DamageSource damageSource, float damageAmount, CallbackInfo ci) {
        if (damageSource.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.TinSoul.get())) {
            ci.cancel();
        }
    }

}

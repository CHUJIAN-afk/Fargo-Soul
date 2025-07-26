package First.fargo_soul.mixin;


import First.fargo_soul.Entity.AbstractArrow.Banner.Banner;
import First.fargo_soul.Item.Soul.Souls;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "knockback", at = @At("HEAD"), cancellable = true)
    public void knockback(double strength, double x, double z, CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (livingEntity instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.GladiatorSoul.get())) {
            if (!player.serverLevel().getEntitiesOfClass(Banner.class, player.getBoundingBox().inflate(4)).isEmpty()) {
                ci.cancel();
            }
        }
    }


}

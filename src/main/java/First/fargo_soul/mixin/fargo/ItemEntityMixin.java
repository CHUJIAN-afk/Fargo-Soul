package First.fargo_soul.mixin.fargo;


import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    public void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        ItemEntity itemEntity = (ItemEntity) (Object) this;
        if (itemEntity.getItem().getItem() instanceof SoulItem) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "getDefaultGravity", at = @At("HEAD"), cancellable = true)
    public void getDefaultGravity(CallbackInfoReturnable<Double> cir) {
        ItemEntity itemEntity = (ItemEntity) (Object) this;
        if (itemEntity.getItem().getItem() instanceof SoulItem) {
            cir.setReturnValue(0d);
        }
    }

}

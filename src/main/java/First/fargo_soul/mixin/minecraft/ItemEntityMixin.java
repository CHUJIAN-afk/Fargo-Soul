package First.fargo_soul.mixin.minecraft;


import First.fargo_soul.common.item.base.SoulItem;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {

    @Inject(
            method = "getDefaultGravity",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    public void getDefaultGravity(CallbackInfoReturnable<Double> cir) {
        ItemEntity itemEntity = (ItemEntity) (Object) this;
        if (itemEntity.getItem().getItem() instanceof SoulItem) {
            cir.setReturnValue(0d);
        }
    }



}

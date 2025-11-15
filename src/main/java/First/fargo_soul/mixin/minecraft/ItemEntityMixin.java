package First.fargo_soul.mixin.minecraft;


import First.fargo_soul.Event.SoulCoreItemEntityTickEvent;
import First.fargo_soul.Item.Soul.BaseSoul.SoulCoreItem;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {

    @Inject(
            method = "tick",
            at = @At(
                    value = "HEAD"
            )
    )
    public void tick(CallbackInfo ci) {
        ItemEntity itemEntity = (ItemEntity) (Object) this;
        ItemStack itemStack = itemEntity.getItem();
        if (itemStack.getItem() instanceof SoulCoreItem) {
            SoulCoreItemEntityTickEvent event = new SoulCoreItemEntityTickEvent(itemEntity);
            NeoForge.EVENT_BUS.post(event);
        }
    }

    @Inject(
            method = "hurt",
            at = @At("HEAD"),
            cancellable = true
    )
    public void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        ItemEntity itemEntity = (ItemEntity) (Object) this;
        if (itemEntity.getItem().getItem() instanceof SoulItem) {
            cir.setReturnValue(false);
        }
        Level level = itemEntity.level();
        List<ItemEntity> entities = level.getEntitiesOfClass(ItemEntity.class, new AABB(itemEntity.blockPosition()), entity -> entity.getItem().getItem() instanceof SoulCoreItem);
        if (!entities.isEmpty()) {
            cir.setReturnValue(false);
        }
    }

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

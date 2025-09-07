package First.fargo_soul.mixin.fargo;


import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

    @WrapWithCondition(
            method = "push(Lnet/minecraft/world/entity/Entity;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;push(DDD)V",
                    ordinal = 0
            )
    )
    private boolean push(Entity instance, double x, double y, double z) {
        return false;
    }

    @Inject(
            method = "isColliding",
            at = @At("HEAD"),
            cancellable = true
    )
    private void bypassCollision(BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (((Entity) (Object) this) instanceof Player player && player.noPhysics) {
            cir.setReturnValue(false);
        }
    }

    @Inject(
            method = "getSharedFlag(I)Z",
            at = @At("RETURN"),
            cancellable = true
    )
    public void getDefaultGravity(int flag, CallbackInfoReturnable<Boolean> cir) {
        Entity entity = (Entity) (Object) this;
        if (flag == 6 && entity instanceof ItemEntity itemEntity && itemEntity.getItem().getItem() instanceof SoulItem) {
            cir.setReturnValue(true);
        }
        if (flag == 6 && entity instanceof LivingEntity livingEntity && livingEntity.getPersistentData().getBoolean("ScoutScope")) {
            cir.setReturnValue(true);
        }
    }

}

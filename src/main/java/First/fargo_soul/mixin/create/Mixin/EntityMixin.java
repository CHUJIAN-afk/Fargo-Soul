package First.fargo_soul.mixin.create.Mixin;

import First.fargo_soul.Compact.Create.CreateSoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.simibubi.create.content.equipment.potatoCannon.PotatoProjectileEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

    @ModifyReturnValue(
            method = "fireImmune()Z",
            at = @At("RETURN")
    )
    public boolean fireImmune(boolean original) {
        Entity entity = (Entity) (Object) this;
        return original || (entity instanceof Player player && CurioUtils.isEquipped(player, CreateSoulsRegister.DeepDiving_Soul.get()));
    }


    @Inject(
            method = "getSharedFlag(I)Z",
            at = @At("RETURN"),
            cancellable = true
    )
    public void getDefaultGravity(int flag, CallbackInfoReturnable<Boolean> cir) {
        Entity entity = (Entity) (Object) this;
        if (flag == 6 && entity instanceof PotatoProjectileEntity projectile && projectile.getOwner() instanceof ServerPlayer player && CurioUtils.isEquipped(player, CreateSoulsRegister.Potato_Soul.get())) {
            cir.setReturnValue(true);
        }
    }


}

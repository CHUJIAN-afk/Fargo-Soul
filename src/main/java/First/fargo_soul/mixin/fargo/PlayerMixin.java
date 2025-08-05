package First.fargo_soul.mixin.fargo;


import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Player.class)
public class PlayerMixin {

    @Inject(
            method = "getAttackStrengthScale",
            at = @At("HEAD"),
            cancellable = true)
    public void getAttackStrengthScale(float adjustTicks, CallbackInfoReturnable<Float> cir) {
        Player player = (Player) (Object) this;
        if (CurioUtils.isEquipped(player, SoulsRegister.AncientHolySoul.get()) && player.getMainHandItem().is(ItemTags.SWORDS)) {
            if (player.isShiftKeyDown()) {
                cir.setReturnValue(1.0f);
            }
        }
    }


    @ModifyArgs(
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/neoforged/neoforge/common/CommonHooks;fireCriticalHit(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/entity/Entity;ZF)Lnet/neoforged/neoforge/event/entity/player/CriticalHitEvent;"
            )
    )
    private void modifyCritParams(Args args) {
        Player player = (Player) (Object) this;
        if (CurioUtils.isEquipped(player, SoulsRegister.TinSoul.get())) {
            args.set(3, 1.0F);
        }
    }



}

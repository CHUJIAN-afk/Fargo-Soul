package First.fargo_soul.mixin;


import First.fargo_soul.Curios.Souls;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {

    @Inject(method = "updateSwimming", at = @At("HEAD"), cancellable = true)
    private void forceLavaSwimming(CallbackInfo ci) {
        Player player = (Player)(Object)this;
        if (player.isInLava() && CurioUtils.isEquipped(player, Souls.ObsidianSoul.get())) {
            player.setSwimming(true);
            ci.cancel();
        }
    }
}

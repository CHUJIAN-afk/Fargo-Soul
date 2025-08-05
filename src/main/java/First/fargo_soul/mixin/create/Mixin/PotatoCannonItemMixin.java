package First.fargo_soul.mixin.create.Mixin;

import First.fargo_soul.Compact.Create.CreateSoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.Utils;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.equipment.potatoCannon.PotatoCannonItem;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PotatoCannonItem.class)
public class PotatoCannonItemMixin {

    @ModifyExpressionValue(
            method = "use",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isCreative()Z")
    )
    private boolean onlyFlyIfAllowed(boolean original, @Local(argsOnly = true) Player player) {
        if (CurioUtils.isEquipped(player, CreateSoulsRegister.Potato_Soul.get()) && Utils.random.nextBoolean()) {
            return false;
        } else {
            return original;
        }
    }

}

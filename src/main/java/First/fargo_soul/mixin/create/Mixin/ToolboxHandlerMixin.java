package First.fargo_soul.mixin.create.Mixin;

import First.fargo_soul.Compact.Create.CreateSoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import com.simibubi.create.content.equipment.toolbox.ToolboxHandler;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ToolboxHandler.class)
public class ToolboxHandlerMixin {

    @Inject(method = "getMaxRange", at = @At(value = "RETURN"), cancellable = true)
    private static void getNearest(Player player, CallbackInfoReturnable<Double> cir) {
        if (CurioUtils.isEquipped(player, CreateSoulsRegister.Goggles_Soul.get())) {
            cir.setReturnValue(cir.getReturnValue() * 8);
        }
    }

}

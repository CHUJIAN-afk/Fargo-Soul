package First.fargo_soul.mixin.create.Mixin;


import First.create.Create.CreateSoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import com.simibubi.create.content.equipment.goggles.GogglesItem;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(GogglesItem.class)
public class GogglesItemMixin {

    @Inject(method = "isWearingGoggles", at = @At("RETURN"), cancellable = true)
    private static void isWearingGoggles(Player player, CallbackInfoReturnable<Boolean> cir) {
        if (CurioUtils.isEquipped(player, CreateSoulsRegister.Goggles_Soul.get())) {
            cir.setReturnValue(true);
        }
    }


}

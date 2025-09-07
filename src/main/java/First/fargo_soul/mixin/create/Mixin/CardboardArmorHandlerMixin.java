package First.fargo_soul.mixin.create.Mixin;

import First.create.Create.CreateSoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import com.simibubi.create.content.equipment.armor.CardboardArmorHandler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(CardboardArmorHandler.class)
public class CardboardArmorHandlerMixin {

    @Inject(
            method = "testForStealth",
            at = @At(value = "RETURN"),
            cancellable = true
    )
    private static void forceHasArmor(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof Player player && CurioUtils.isEquipped(player, CreateSoulsRegister.CardBoard_Soul.get())) {
            if (player.getPose().equals(Pose.CROUCHING) && !player.getAbilities().flying) {
                cir.setReturnValue(true);
            }
        }
    }

}

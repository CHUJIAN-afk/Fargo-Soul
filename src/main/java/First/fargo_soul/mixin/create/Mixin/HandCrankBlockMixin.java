package First.fargo_soul.mixin.create.Mixin;

import First.create.Create.CreateSoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.simibubi.create.content.kinetics.crank.HandCrankBlock;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(HandCrankBlock.class)
public class HandCrankBlockMixin {

    @WrapWithCondition(
            method = "useItemOn",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;causeFoodExhaustion(F)V")
    )
    protected boolean useItemOn(Player player, float exhaustion) {
        return !(CurioUtils.isEquipped(player, CreateSoulsRegister.CogWheel_Soul.get()));
    }

}

package First.fargo_soul.mixin.create.Mixin;

import First.create.Create.CreateSoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.simibubi.create.content.equipment.armor.RemainingAirOverlay;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(RemainingAirOverlay.class)
public class RemainingAirOverlayMixin {


    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/player/LocalPlayer;isCreative()Z",
                    shift = At.Shift.AFTER
            )
    )
    private void onlyFlyIfAllowed(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci, @Local LocalPlayer player) {
        if (CurioUtils.isEquipped(player, CreateSoulsRegister.DeepDiving_Soul.get())) {
            player.getPersistentData().putInt("VisualBacktankAir", Integer.MAX_VALUE);
        }
    }

    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/createmod/catnip/gui/element/RenderElement;render(Lnet/minecraft/client/gui/GuiGraphics;)V",
                    shift = At.Shift.AFTER
            )
    )
    protected void useItemOn(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci, @Local LocalRef<Component> text, @Local LocalPlayer player) {
        if (CurioUtils.isEquipped(player, CreateSoulsRegister.DeepDiving_Soul.get())) {
            text.set(Component.literal("∞"));
        }
    }

}

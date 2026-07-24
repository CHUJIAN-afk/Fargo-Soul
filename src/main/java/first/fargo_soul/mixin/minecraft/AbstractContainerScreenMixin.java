package first.fargo_soul.mixin.minecraft;

import first.fargo_soul.client.screen.SoulContainerScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractContainerScreen.class)
public class AbstractContainerScreenMixin {

    @Inject(
            method = "isHovering(Lnet/minecraft/world/inventory/Slot;DD)Z",
            at = @At("RETURN"),
            cancellable = true
    )
    private void isHovering(Slot slot, double mouseX, double mouseY, CallbackInfoReturnable<Boolean> cir) {
        AbstractContainerScreen<?> abstractContainerScreen = (AbstractContainerScreen<?>) (Object) this;
        if (abstractContainerScreen instanceof SoulContainerScreen soulContainerScreen) {
            cir.setReturnValue(soulContainerScreen.isHovering(slot, mouseX, mouseY));
        }
    }

}

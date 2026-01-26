package First.fargo_soul.mixin.minecraft;


import First.fargo_soul.client.button.OpenSoulContainerButton;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeModeInventoryScreen.class)
public class CreativeModeInventoryScreenMixin {

    @Inject(
            method = "containerTick",
            at = @At("HEAD")
    )
    private void containerTick(CallbackInfo ci) {
        CreativeModeInventoryScreen screen = (CreativeModeInventoryScreen) (Object) this;
        for (Renderable renderable : screen.renderables) {
            if (renderable instanceof OpenSoulContainerButton button) {
                button.visible = screen.isInventoryOpen();
            }
        }
    }


}

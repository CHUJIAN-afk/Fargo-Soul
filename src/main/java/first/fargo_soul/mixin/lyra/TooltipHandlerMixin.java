package first.fargo_soul.mixin.lyra;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import first.fargo_soul.common.item.base.SoulItem;
import first.lyra.client.tooltip.TooltipHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(TooltipHandler.class)
public class TooltipHandlerMixin {

    @ModifyExpressionValue(
            method = "handler",
            at = @At(
                    value = "INVOKE",
                    target = "Lfirst/lyra/client/tooltip/TooltipHandler;getCustomTooltip(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/player/Player;)Ljava/util/List;"
            )
    )
    private static List<Component> addAll(List<Component> original, @Local(argsOnly = true) ItemTooltipEvent event, @Local(name = "itemStack") ItemStack itemStack) {
        if (itemStack.getItem() instanceof SoulItem soulItem) {
            return soulItem.getTooltip(itemStack, event.getFlags(), true);
        }
        return original;
    }
}

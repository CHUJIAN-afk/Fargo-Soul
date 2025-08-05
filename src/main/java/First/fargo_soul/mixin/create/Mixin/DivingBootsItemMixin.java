package First.fargo_soul.mixin.create.Mixin;


import First.fargo_soul.Compact.Create.CreateSoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.equipment.armor.DivingBootsItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DivingBootsItem.class)
public class DivingBootsItemMixin {

    @Inject(
            method = "getWornItem",
            at = @At("RETURN"),
            cancellable = true)
    private static void getWornItem(Entity entity, CallbackInfoReturnable<ItemStack> cir) {
        if (entity instanceof Player player && CurioUtils.isEquipped(player, CreateSoulsRegister.DeepDiving_Soul.get())) {
            cir.setReturnValue(AllItems.NETHERITE_DIVING_BOOTS.asItem().getDefaultInstance());
        }
    }

}

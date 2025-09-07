package First.fargo_soul.mixin.create.Mixin;

import First.create.Create.CreateSoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import com.simibubi.create.AllDataComponents;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.equipment.armor.BacktankUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Pseudo
@Mixin(BacktankUtil.class)
public class BacktankUtilMixin {

    @Inject(
            method = "getAllWithAir",
            at = @At("RETURN"),
            cancellable = true
    )
    private static void addBacktankSupplier(LivingEntity entity, CallbackInfoReturnable<List<ItemStack>> cir) {
        if (entity instanceof Player player && CurioUtils.isEquipped(player, CreateSoulsRegister.DeepDiving_Soul.get())) {
            ItemStack itemStack = AllItems.NETHERITE_BACKTANK.asStack();
            itemStack.set(AllDataComponents.BACKTANK_AIR, Integer.MAX_VALUE);
            cir.setReturnValue(List.of(itemStack));
        }

    }
}

package First.fargo_soul.mixin.minecraft;

import First.fargo_soul.common.attachment.SoulAbilityData;
import First.fargo_soul.common.item.terraSoul.WillPower;
import First.fargo_soul.common.item.terraSoul.cosmicPower.WizardSoul;
import First.fargo_soul.common.item.terraSoul.willPower.PlatinumSoul;
import First.fargo_soul.common.item.terraSoul.willPower.RedRidingSoul;
import First.fargo_soul.utils.CurioUtils;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "doPush", at = @At("HEAD"), cancellable = true)
    private void doPush(Entity entity, CallbackInfo ci) {
        if (entity instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
            if (CurioUtils.isEquipped(attacker, WizardSoul.class)) {
                ci.cancel();
            }
        }
    }

    @ModifyArg(
            method = "dropFromLootTable",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/storage/loot/LootTable;getRandomItems(Lnet/minecraft/world/level/storage/loot/LootParams;JLjava/util/function/Consumer;)V"
            ),
            index = 2
    )
    private Consumer<ItemStack> modifyDropConsumer(Consumer<ItemStack> originalConsumer, @Local(argsOnly = true) DamageSource damageSource) {
        return itemStack -> {
            if (damageSource.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide() && CurioUtils.isEquipped(attacker, PlatinumSoul.class) && attacker.getRandom().nextDouble() < 0.2) {
                int scale = CurioUtils.isEquipped(attacker, WillPower.class) ? 8 : 5;
                SoulAbilityData.SoulInfo soulInfo = SoulAbilityData.getSoulInfo(attacker, RedRidingSoul.class);
                if (scale == 8 && soulInfo.getStacks() == soulInfo.getMaxStacks()) {
                    scale = 16;
                }
                int newCount = itemStack.getCount() * scale;
                int maxStackSize = itemStack.getMaxStackSize();
                while (newCount > 0) {
                    ItemStack newStack = itemStack.copy();
                    newStack.setCount(Math.min(newCount, maxStackSize));
                    originalConsumer.accept(newStack);
                    newCount -= maxStackSize;
                }
            } else {
                originalConsumer.accept(itemStack);
            }
        };
    }

}

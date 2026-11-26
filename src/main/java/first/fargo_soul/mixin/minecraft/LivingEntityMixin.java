package first.fargo_soul.mixin.minecraft;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import first.fargo_soul.common.attachment.SoulInfoData;
import first.fargo_soul.common.attachment.SoulItemData;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.register.FargoSoulAttachmentRegister;
import first.fargo_soul.register.FargoSoulSoulInfoRegister;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import java.util.function.Consumer;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @ModifyExpressionValue(
            method = "hurt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/damagesource/DamageSource;is(Lnet/minecraft/tags/TagKey;)Z",
                    ordinal = 4
            )
    )
    private boolean is(boolean original, @Local(argsOnly = true) DamageSource damageSource) {
        if (damageSource.getDirectEntity() instanceof Projectile projectile) {
            if (SoulInfoData.getSoulInfo(projectile, FargoSoulSoulInfoRegister.PROJECTILE_INFO) != null) {
                return true;
            }
        }
        return original;
    }

    @WrapOperation(method = "dropFromLootTable", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/loot/LootTable;getRandomItems(Lnet/minecraft/world/level/storage/loot/LootParams;JLjava/util/function/Consumer;)V"))
    private void dropFromLootTable(LootTable table, LootParams params, long seed, Consumer<ItemStack> output, Operation<Void> original, @Local(argsOnly = true) DamageSource damageSource) {
        if (damageSource.getEntity() instanceof Player attacker && attacker.hasData(FargoSoulAttachmentRegister.SOUL_ITEM_DATA)
                && !attacker.getData(FargoSoulAttachmentRegister.SOUL_ITEM_DATA).getCache().isEmpty()) {
            LivingEntity target = LivingEntity.class.cast(this);
            SoulItemData data = attacker.getData(FargoSoulAttachmentRegister.SOUL_ITEM_DATA);
            float scale = 1;
            for (SoulItem soulItem : data.getCache()) {
                scale = soulItem.dropFromLootTableScale(attacker, target, scale);
            }
            float lootScale = scale;
            Consumer<ItemStack> scaledOutput = scale == 1 ? output : stack -> {
                int remaining = (int) (stack.getCount() * lootScale);
                while (remaining > 0) {
                    int count = Math.min(remaining, stack.getMaxStackSize());
                    output.accept(stack.copyWithCount(count));
                    remaining -= count;
                }
            };
            original.call(table, params, seed, scaledOutput);
            for (SoulItem soulItem : data.getCache()) {
                for (ItemStack stack : soulItem.dropFromLootTableAfter(attacker, target)) {
                    scaledOutput.accept(stack);
                }
            }
        } else {
            original.call(table, params, seed, output);
        }
    }
}

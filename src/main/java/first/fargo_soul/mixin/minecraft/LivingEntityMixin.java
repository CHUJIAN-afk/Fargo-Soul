package first.fargo_soul.mixin.minecraft;

import first.fargo_soul.common.attachment.SoulItemData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "dropFromLootTable", at = @At("TAIL"))
    private void dropFromLootTable(DamageSource damageSource, boolean hitByPlayer, CallbackInfo ci) {
        if (damageSource.getEntity() instanceof Player attacker) {
            LivingEntity target = LivingEntity.class.cast(this);
            List<ItemStack> list = new ArrayList<>();
            SoulItemData.forEach(attacker, soulItem -> list.addAll(soulItem.dropFromLootTableAfter(attacker, target)));
            AtomicReference<Float> scale = new AtomicReference<>(1f);
            SoulItemData.forEach(attacker, soulItem -> scale.set(soulItem.dropFromLootTableScale(attacker, target, scale.get())));
            for (ItemStack itemStack : list) {
                if (!itemStack.isEmpty()) {
                    itemStack.setCount((int) (itemStack.getCount() * scale.get()));
                }
                target.spawnAtLocation(itemStack);
            }
        }
    }
}

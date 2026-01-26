package First.fargo_soul.mixin.minecraft;


import First.fargo_soul.common.item.terraSoul.ForestPower;
import First.fargo_soul.common.item.terraSoul.forestPower.WoodSoul;
import First.fargo_soul.utils.CurioUtils;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Villager.class)
public abstract class VillagerMixin {

    @ModifyExpressionValue(
            method = "updateSpecialPrices",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/npc/Villager;getPlayerReputation(Lnet/minecraft/world/entity/player/Player;)I"
            )
    )
    private int WoodSoul(int original, Player player) {
        if (CurioUtils.isEquipped(player, WoodSoul.class)) {
            int max = Math.max((int) (original * 1.5), 5);
            max *= (CurioUtils.isEquipped(player, ForestPower.class)) ? 2 : 1;
            return max;
        }
        return original;
    }

}
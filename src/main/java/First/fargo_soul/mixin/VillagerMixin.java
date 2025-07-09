package First.fargo_soul.mixin;


import First.fargo_soul.Curios.SoulStone.WoodSoul;
import First.fargo_soul.Utils.CurioUtils;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static First.fargo_soul.Curios.CuriosRegister.*;

@Mixin(Villager.class)
public abstract class VillagerMixin {

    @ModifyExpressionValue(method = "updateSpecialPrices", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/npc/Villager;getPlayerReputation(Lnet/minecraft/world/entity/player/Player;)I"))
    private int WoodSoul(int original, Player player) {
        if (CurioUtils.isEquipped(player, WoodSoul.get())) {
            return (int) (original * 1.5);
        }
        return original;
    }
}
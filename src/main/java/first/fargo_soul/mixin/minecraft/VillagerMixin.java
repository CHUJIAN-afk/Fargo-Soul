package first.fargo_soul.mixin.minecraft;


import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import first.fargo_soul.common.attachment.SoulItemData;
import first.fargo_soul.common.item.base.ValueModifier;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;

@Mixin(Villager.class)
public class VillagerMixin {

    @ModifyExpressionValue(
            method = "updateSpecialPrices",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/npc/Villager;getPlayerReputation(Lnet/minecraft/world/entity/player/Player;)I"
            )
    )
    private int WoodSoul(int original, Player player) {
        Villager villager = Villager.class.cast(this);
        List<ValueModifier> modifiers = new ArrayList<>();
        SoulItemData.forEach(player, soulItem -> soulItem.updateSpecialPrices(player, villager, modifiers));
        return (int) ValueModifier.getModifierAfter(original, modifiers);
    }
}
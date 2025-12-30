package First.fargo_soul.mixin.minecraft;


import First.fargo_soul.item.terraSoul.TerraPower;
import First.fargo_soul.item.terraSoul.terraPower.TungstenSoul;
import First.fargo_soul.item.universeSoul.BerserkerSoul.Soul.BerserkerGloves;
import First.fargo_soul.item.universeSoul.BerserkerSoul.Soul.FireGloves;
import First.fargo_soul.utils.CurioUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {

    @Inject(
            method = "renderItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V"
            )
    )
    private void ItemRender(LivingEntity entity, ItemStack itemStack, ItemDisplayContext displayContext, boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int seed, CallbackInfo ci) {
        if (entity instanceof Player player) {
            float scale = 1.0f;
            if (CurioUtils.isEquipped(player, TungstenSoul.class)) {
                scale += CurioUtils.isEquipped(player, TerraPower.class) ? 1.5f : 1.0f;
            }

            if (CurioUtils.isEquipped(player, BerserkerGloves.class) && itemStack.getItem() instanceof TieredItem) {
                scale += 1;
            }
            if (CurioUtils.isEquipped(player, FireGloves.class) && itemStack.getItem() instanceof TieredItem) {
                scale += 1;
            }

            poseStack.scale(scale, scale, scale);
        }
    }

}


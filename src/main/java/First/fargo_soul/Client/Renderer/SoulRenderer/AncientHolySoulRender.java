package First.fargo_soul.Client.Renderer.SoulRenderer;

import First.fargo_soul.Item.BaseItem.BaseItemsRegister;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;

public class AncientHolySoulRender {
    public static void AncientHolySoulRenderHnadler(Player player, PoseStack poseStack, MultiBufferSource renderTypeBuffer, float ageInTicks) {
        if (player instanceof LocalPlayer && CurioUtils.isEquipped(player, SoulsRegister.AncientHolySoul.get())) {
            poseStack.pushPose();
            poseStack.scale(2.0f, 2.0f, 2.0f);
            float floatingOffset = (float) Math.sin(ageInTicks * 0.1f) * 0.05f;
            poseStack.translate(0, floatingOffset, 0.3);
            Minecraft.getInstance().getItemRenderer().renderStatic(
                    BaseItemsRegister.TerraPrism.get().getDefaultInstance(),
                    ItemDisplayContext.FIXED,
                    LightTexture.FULL_BRIGHT,
                    OverlayTexture.NO_OVERLAY,
                    poseStack,
                    renderTypeBuffer,
                    player.level(),
                    0
            );
            poseStack.popPose();
        }
    }
}

package First.fargo_soul.Client.Renderer.SoulRenderer;

import First.fargo_soul.Item.BaseItem.BaseItemsRegister;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import org.joml.Quaternionf;

public class GreenSoulRender {


    public static void GreenSoulRenderHnadler(Player player, PoseStack poseStack, MultiBufferSource renderTypeBuffer, float ageInTicks) {
        if (player instanceof LocalPlayer && CurioUtils.isEquipped(player, SoulsRegister.GreenSoul.get())) {
            Minecraft minecraft = Minecraft.getInstance();
            float floatingOffset = (float) Math.sin(ageInTicks * 0.1f) * 0.2f + 1.0f;
            poseStack.pushPose();
            Quaternionf playerRotation = new Quaternionf();
            poseStack.last().pose().getNormalizedRotation(playerRotation);
            playerRotation.conjugate();
            poseStack.mulPose(playerRotation);
            poseStack.translate(0, floatingOffset, 0);
            poseStack.mulPose(Axis.YP.rotationDegrees(ageInTicks));
            poseStack.scale(1.5f, 1.5f, 1.5f);
            minecraft.getItemRenderer().renderStatic(
                    BaseItemsRegister.GreenCrystal.get().getDefaultInstance(),
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

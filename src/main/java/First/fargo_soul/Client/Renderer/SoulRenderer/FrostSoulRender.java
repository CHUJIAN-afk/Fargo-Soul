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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import org.joml.Quaternionf;

public class FrostSoulRender {

    public static void FrostSoulRenderHnadler(Player player, PoseStack matrixStack, MultiBufferSource renderTypeBuffer, float ageInTicks) {
        if (player instanceof LocalPlayer && CurioUtils.isEquipped(player, SoulsRegister.FrostSoul.get())) {
            int frostSoulCount = player.getPersistentData().getInt("FrostSoul");
            if (frostSoulCount > 0) {
                Minecraft minecraft = Minecraft.getInstance();
                int displayCount = Math.min(frostSoulCount, 10);
                float angleIncrement = 360.0F / displayCount;
                float radius = 1.2f + (displayCount * 0.05f);
                float floatOffset = Mth.sin(ageInTicks * 0.1f) * 0.1f;
                for (int i = 0; i < displayCount; i++) {
                    matrixStack.pushPose();
                    Quaternionf playerRotation = new Quaternionf();
                    matrixStack.last().pose().getNormalizedRotation(playerRotation);
                    playerRotation.conjugate();
                    matrixStack.mulPose(playerRotation);
                    float angle = (ageInTicks * 1.5f + i * angleIncrement) % 360;
                    float rad = (float) Math.toRadians(angle);
                    float xPos = Mth.cos(rad) * radius;
                    float zPos = Mth.sin(rad) * radius;
                    float yOffset = i % 2 == 0 ? 0.1f : -0.1f;
                    matrixStack.translate(xPos, floatOffset + yOffset, zPos);
                    matrixStack.mulPose(new Quaternionf().rotateY((float) Math.toRadians(180 - angle)));
                    matrixStack.mulPose(new Quaternionf().rotateX(ageInTicks * 0.05f));
                    float scale = 0.6f + Mth.sin(ageInTicks * 0.2f + i) * 0.05f;
                    matrixStack.scale(scale, scale, scale);
                    minecraft.getItemRenderer().renderStatic(
                            BaseItemsRegister.IceSpike.get().getDefaultInstance(),
                            ItemDisplayContext.FIXED,
                            LightTexture.FULL_BRIGHT,
                            OverlayTexture.NO_OVERLAY,
                            matrixStack,
                            renderTypeBuffer,
                            player.level(),
                            0
                    );
                    matrixStack.popPose();
                }
            }
        }
    }


}

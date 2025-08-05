package First.fargo_soul.Client.Renderer.SoulRenderer;

import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class LavaSoulRender {
    public static void LavaSoulRenderHandler(Player player, PoseStack poseStack, MultiBufferSource buffer, float ageInTicks) {
        if (player instanceof LocalPlayer && CurioUtils.isEquipped(player, SoulsRegister.LavaSoul.get())) {
            ItemStack magmaBlock = Items.MAGMA_BLOCK.getDefaultInstance();
            Minecraft minecraft = Minecraft.getInstance();
            ItemRenderer itemRenderer = minecraft.getItemRenderer();
            float radius = 5.0f;
            int blockCount = 100;
            for (int i = 0; i < blockCount; i++) {
                poseStack.pushPose();
                float angle = ageInTicks * 0.05f + (float) (Math.PI * 2 / blockCount * i);
                float x = (float) (Math.cos(angle) * radius);
                float z = (float) (Math.sin(angle) * radius);
                poseStack.translate(x, 0, z);
                poseStack.mulPose(Axis.YP.rotationDegrees(angle * 180 / (float) Math.PI));
                poseStack.scale(0.1f, 0.1f, 0.1f);
                itemRenderer.renderStatic(
                        magmaBlock,
                        ItemDisplayContext.FIXED,
                        LightTexture.FULL_BRIGHT,
                        OverlayTexture.NO_OVERLAY,
                        poseStack,
                        buffer,
                        player.level(),
                        0
                );
                poseStack.popPose();
            }
        }
    }
}

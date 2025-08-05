package First.fargo_soul.Client.Renderer.SoulRenderer;

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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DarkArtistSoulRender {
    public static void DarkArtistSoulRenderHnadler(Player player, PoseStack poseStack, MultiBufferSource buffer, float ageInTicks) {
        if (player instanceof LocalPlayer && CurioUtils.isEquipped(player, SoulsRegister.DarkArtistSoul.get())) {
            ItemStack itemStack = Items.FIRE_CHARGE.getDefaultInstance();
            Minecraft minecraft = Minecraft.getInstance();
            poseStack.pushPose();
            poseStack.scale(0.5f, 0.5f, 0.5f);
            poseStack.translate(-1, -0.5, 0);
            poseStack.mulPose(Axis.ZP.rotation(ageInTicks));
            minecraft.getItemRenderer().renderStatic(
                    itemStack,
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

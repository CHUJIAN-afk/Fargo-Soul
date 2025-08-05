package First.fargo_soul.Client.Renderer.SoulRenderer;

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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class GoldSoulRender {
    public static void GoldSoulRenderHnadler(Player player, PoseStack poseStack, MultiBufferSource buffer) {
        if (player instanceof LocalPlayer && CurioUtils.isEquipped(player, SoulsRegister.GoldSoul.get())) {
            if (player.getPersistentData().getBoolean("GoldSoulDamage")) {
                ItemStack itemStack = Items.GOLD_BLOCK.getDefaultInstance();
                Minecraft minecraft = Minecraft.getInstance();
                poseStack.pushPose();
                poseStack.scale(3, 6, 3f);
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
}

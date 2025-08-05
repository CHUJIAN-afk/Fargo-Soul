package First.fargo_soul.Client.Renderer;

import First.fargo_soul.Client.Renderer.SoulRenderer.*;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.List;
import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class PlayerRenderer implements ICurioRenderer {

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource buffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (slotContext.entity() instanceof LocalPlayer player && stack.getItem() instanceof SoulItem soul) {
            Minecraft minecraft = Minecraft.getInstance();
            ItemRenderer itemRenderer = minecraft.getItemRenderer();
            List<SoulItem> soulItemList = CurioUtils.getAllCurioItems(soul.getCurioItemList());
            soulItemList.add(soul);
            for (SoulItem soulItem : soulItemList) {
                poseStack.pushPose();
                //去除玩家视角影响
                Quaternionf playerRotation = new Quaternionf();
                poseStack.last().pose().getNormalizedRotation(playerRotation);
                playerRotation.conjugate();
                poseStack.mulPose(playerRotation);
                //坐标偏移
                int hashCode = soulItem.hashCode();
                double random = new Random(new Random(hashCode).nextInt()).nextDouble();
                double angle = ageInTicks * (random * 4 + 1);
                double semiMajorAxis = 0.6 + random * 2.4f;
                double semiMinorAxis = 0.4 + random * 1.6f;
                if (new Random(hashCode + 1).nextBoolean()) angle *= -new Random(hashCode + 2).nextFloat();
                float x = (float) (Math.cos(Math.toRadians(angle)) * semiMajorAxis);
                float y = (float) Math.sin(Math.toRadians(angle / 2));
                float z = (float) (Math.sin(Math.toRadians(angle)) * semiMinorAxis);
                //模型坐标
                poseStack.translate(x, y, z);
                //物品旋转角度
                poseStack.mulPose(new Quaternionf().rotateX(x));
                poseStack.mulPose(new Quaternionf().rotateY(y));
                poseStack.mulPose(new Quaternionf().rotateZ(z));
                //物品模型缩放
                poseStack.scale(0.5f, 0.5f, 0.5f);
                itemRenderer.renderStatic(
                        soulItem.getDefaultInstance(),
                        ItemDisplayContext.FIXED,
                        CurioUtils.isEquipped(minecraft.player, SoulsRegister.WizardSoul.get()) ? LightTexture.FULL_BRIGHT : light,
                        OverlayTexture.NO_OVERLAY,
                        poseStack,
                        buffer,
                        player.level(),
                        0
                );
                poseStack.popPose();
            }
            //魂石特效渲染
            SoulRender(player, poseStack, buffer, ageInTicks);
        }
    }

    private static void SoulRender(@NotNull Player player, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, float ageInTicks) {
        FrostSoulRender.FrostSoulRenderHnadler(player, poseStack, buffer, ageInTicks);
        GreenSoulRender.GreenSoulRenderHnadler(player, poseStack, buffer, ageInTicks);
        AncientHolySoulRender.AncientHolySoulRenderHnadler(player, poseStack, buffer, ageInTicks);
        LavaSoulRender.LavaSoulRenderHandler(player, poseStack, buffer, ageInTicks);
        DarkArtistSoulRender.DarkArtistSoulRenderHnadler(player, poseStack, buffer, ageInTicks);
        GoldSoulRender.GoldSoulRenderHnadler(player, poseStack, buffer);
    }


}


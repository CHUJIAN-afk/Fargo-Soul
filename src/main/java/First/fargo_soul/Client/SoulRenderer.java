package First.fargo_soul.Client;

import First.fargo_soul.Curios.SoulItem;
import First.fargo_soul.Utils.CurioUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.List;
import java.util.Random;

public class SoulRenderer implements ICurioRenderer {

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        Minecraft minecraft = Minecraft.getInstance();
        if (stack.getItem() instanceof SoulItem soul) {
            List<SoulItem> soulItemList = CurioUtils.getAllCurioItems(soul.getCurioItemList());
            soulItemList.add(soul);
            for (SoulItem soulItem : soulItemList) {
                ModelManager modelManager = minecraft.getModelManager();
                ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(soulItem);
                BakedModel model = modelManager.getModel(ModelResourceLocation.inventory(resourceLocation));
                matrixStack.pushPose();
                //去除玩家视角影响
                Quaternionf playerRotation = new Quaternionf();
                matrixStack.last().pose().getNormalizedRotation(playerRotation);
                playerRotation.conjugate();
                matrixStack.mulPose(playerRotation);
                //坐标偏移
                int hashCode = soulItem.hashCode();
                double random = new Random(hashCode + soulItemList.indexOf(soulItem)).nextDouble();
                double angle = ageInTicks * (random * 4 + 1);
                double semiMajorAxis = 0.6 + random * 2.4f;
                double semiMinorAxis = 0.4 + random * 1.6f;
                float x = (float) (Math.cos(Math.toRadians(angle)) * semiMajorAxis);
                float y = (float) Math.sin(Math.toRadians(angle)) * 0.5f;
                float z = (float) (Math.sin(Math.toRadians(angle)) * semiMinorAxis);
                if (hashCode % 2 == 0) x *= -1;
                if (hashCode % 3 == 0) y *= -1;
                if (hashCode % 5 == 0) z *= -1;
                //模型坐标
                matrixStack.translate(x, y * 2, z);
                //物品旋转角度
                matrixStack.mulPose(new Quaternionf().rotateX(x));
                matrixStack.mulPose(new Quaternionf().rotateY(y));
                matrixStack.mulPose(new Quaternionf().rotateZ(z));
                //物品模型缩放
                matrixStack.scale(0.5f, 0.5f, 0.5f);
                minecraft.getItemRenderer().render(
                        soulItem.getDefaultInstance(),
                        ItemDisplayContext.HEAD,
                        false,
                        matrixStack,
                        renderTypeBuffer,
                        light,
                        OverlayTexture.NO_OVERLAY,
                        model
                );
                matrixStack.popPose();
            }
        }
    }
}

package First.fargo_soul.Client.Renderer;

import First.fargo_soul.Entity.Arrow.Spear;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.BaseItem.BaseItemsRegister;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class SpearRenderer extends EntityRenderer<Spear> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Fargo_soul.MODID,"spear");
    private final ItemStack itemStack = new ItemStack(BaseItemsRegister.Spear.get());

    public SpearRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(@NotNull Spear spear, float yaw, float partialTick, PoseStack poseStack, @NotNull MultiBufferSource buffer, int light) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, spear.yRotO, spear.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTick, spear.xRotO, spear.getXRot())));
        poseStack.mulPose(Axis.XP.rotationDegrees(45.0F));
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        itemRenderer.renderStatic(
                itemStack,
                ItemDisplayContext.FIXED,
                light,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                spear.level(),
                0
        );
        poseStack.popPose();

    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Spear entity) {
        return TEXTURE;
    }
}

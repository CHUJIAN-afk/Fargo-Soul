package First.fargo_soul.Entity.Entity.Sword;

import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.ProjectileItem.ProjectileItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

public class SwordRenderer extends EntityRenderer<Sword> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Fargo_soul.MODID, "terra_prism");

    private final ItemRenderer itemRenderer;

    public SwordRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(@NotNull Sword sword, float entityYaw, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        super.render(sword, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        poseStack.pushPose();
        poseStack.scale(1.0F, 1.0F, 1.0F);
        Quaternionf cameraOrientation = this.entityRenderDispatcher.cameraOrientation();
        //poseStack.mulPose(cameraOrientation);
        //poseStack.mulPose(Axis.XP.rotationDegrees(0));
        poseStack.mulPose(Axis.YP.rotationDegrees(cameraOrientation.y()));
        poseStack.mulPose(Axis.ZP.rotationDegrees(135));
        this.itemRenderer.renderStatic(
                ProjectileItems.TerraPrism.get().getDefaultInstance(),
                ItemDisplayContext.FIXED,
                LightTexture.FULL_BRIGHT,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                bufferSource,
                sword.level(),
                sword.getId()
        );
        poseStack.popPose();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Sword sword) {
        return TEXTURE;
    }

}

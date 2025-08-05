package First.fargo_soul.Client.Renderer;

import First.fargo_soul.Entity.Arrow.IceSpike;
import First.fargo_soul.Fargo_soul;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class IceSpikeRenderer extends EntityRenderer<IceSpike> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Fargo_soul.MODID, "ice_spike");

    private final ItemRenderer itemRenderer;

    public IceSpikeRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(@NotNull IceSpike iceSpike, float entityYaw, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        super.render(iceSpike, entityYaw, partialTick, poseStack, bufferSource, packedLight);poseStack.pushPose();
        poseStack.scale(1.0F, 1.0F, 1.0F);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        this.itemRenderer.renderStatic(
                iceSpike.getItem(),
                ItemDisplayContext.GROUND,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                bufferSource,
                iceSpike.level(),
                iceSpike.getId()
        );
        poseStack.popPose();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull IceSpike iceSpike) {
        return TEXTURE;
    }

}

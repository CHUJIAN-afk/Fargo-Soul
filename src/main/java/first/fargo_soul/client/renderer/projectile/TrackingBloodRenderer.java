package first.fargo_soul.client.renderer.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import first.fargo_soul.common.entity.TrackingBlood;
import first.lyra.client.render.AbstractAttachmentEntityRenderer;
import first.lyra.client.render.RenderContext;
import first.lyra.client.render.rendererHelper.SphereRendererHelper;
import first.lyra.common.entity.PathNode;
import net.minecraft.client.renderer.MultiBufferSource;

public class TrackingBloodRenderer extends AbstractAttachmentEntityRenderer<TrackingBlood> {

    @Override
    protected RenderContext<TrackingBlood> createContext(TrackingBlood entity, float partialTick) {
        return RenderContext.<TrackingBlood>builder()
                .build();
    }

    @Override
    protected void render(TrackingBlood entity, PoseStack poseStack, MultiBufferSource bufferSource, PathNode visualNode, RenderContext<TrackingBlood> context, float partialTick, int packedLight, float alpha) {
        SphereRendererHelper.builder()
                .radius(0.05f)
                .layers(4)
                .sides(6)
                .color(0xFFFF2020)
                .alpha(0.9f * alpha)
                .innerRatio(0.5f)
                .render(poseStack, bufferSource);
    }
}
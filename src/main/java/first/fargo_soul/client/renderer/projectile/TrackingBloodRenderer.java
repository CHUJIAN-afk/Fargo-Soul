package first.fargo_soul.client.renderer.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import first.fargo_soul.common.entity.TrackingBlood;
import first.lyra.client.render.AbstractAttachmentEntityRenderer;
import first.lyra.client.render.rendererHelper.SphereRendererHelper;
import net.minecraft.client.renderer.MultiBufferSource;

public class TrackingBloodRenderer extends AbstractAttachmentEntityRenderer<TrackingBlood> {

    @Override
    protected void render(PoseStack poseStack, MultiBufferSource bufferSource) {
        SphereRendererHelper.builder()
                .radius(0.05f)
                .layers(4)
                .sides(6)
                .color(0xFFFF2020)
                .alpha(0.9f * getAlpha())
                .innerRatio(0.5f)
                .render(poseStack, bufferSource);
    }
}

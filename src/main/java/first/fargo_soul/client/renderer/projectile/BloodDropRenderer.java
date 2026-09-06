package first.fargo_soul.client.renderer.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import first.fargo_soul.common.entity.BloodDrop;
import first.lyra.client.render.AbstractAttachmentEntityRenderer;
import first.lyra.client.render.RenderContext;
import first.lyra.client.render.rendererHelper.SphereRendererHelper;
import first.lyra.common.entity.PathNode;
import net.minecraft.client.renderer.MultiBufferSource;

public class BloodDropRenderer extends AbstractAttachmentEntityRenderer<BloodDrop> {

    @Override
    protected RenderContext<BloodDrop> createContext(BloodDrop entity, float partialTick) {
        return RenderContext.<BloodDrop>builder()
                .build();
    }

    @Override
    protected void render(BloodDrop entity, PoseStack poseStack, MultiBufferSource bufferSource, PathNode visualNode, RenderContext<BloodDrop> context, float partialTick, int packedLight, float alpha) {
        SphereRendererHelper.builder()
                .radius(0.1f)
                .layers(4)
                .sides(6)
                .color(0xFFCC2020)
                .alpha(0.85f * alpha)
                .innerRatio(0.5f)
                .render(poseStack, bufferSource);
    }
}
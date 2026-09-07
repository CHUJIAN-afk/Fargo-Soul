package first.fargo_soul.client.renderer.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import first.fargo_soul.common.entity.ShadowOrb;
import first.fargo_soul.register.FargoSoulModelRegister;
import first.lyra.client.dynamicLight.DynamicLightDispatcher;
import first.lyra.client.render.AbstractAttachmentEntityRenderer;
import first.lyra.client.render.RenderContext;
import first.lyra.client.render.RenderUtil;
import first.lyra.client.render.trail.ModelConfig;
import first.lyra.common.entity.PathNode;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.FastColor;

public class ShadowOrbRenderer extends AbstractAttachmentEntityRenderer<ShadowOrb> {

    @Override
    protected RenderContext<ShadowOrb> createContext(ShadowOrb orb, float partialTick) {
        float rot = orb.getTickCount() + partialTick;
        return RenderContext.<ShadowOrb>builder()
                .model(new ModelConfig<ShadowOrb>()
                               .translateOffset(-0.5f, -0.5f, -0.5f)
                               .rotationOffset(rot, rot, rot)
                               .scale(0.75f))
                .build();
    }

    @Override
    protected void render(ShadowOrb orb, PoseStack poseStack, MultiBufferSource bufferSource, PathNode visualNode, RenderContext<ShadowOrb> context, float partialTick, int packedLight, float alpha) {
        RenderUtil.renderStandalone(FargoSoulModelRegister.SHADOW_ORB, poseStack, bufferSource, FastColor.ARGB32.color((int) (alpha * 255), 255, 255, 255), RenderUtil.FULL_LIGHT);
        DynamicLightDispatcher.addLightSources(visualNode.pos(), 12);
    }
}

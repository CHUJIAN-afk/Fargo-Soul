package first.fargo_soul.client.renderer.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import first.fargo_soul.common.entity.ShadowOrb;
import first.fargo_soul.register.FargoSoulModelRegister;
import first.lyra.client.render.AbstractAttachmentEntityRenderer;
import first.lyra.client.render.ModelContext;
import first.lyra.client.render.RenderContext;
import first.lyra.client.render.RenderUtil;
import first.lyra.client.render.model.LyraModelRenderer;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.client.renderer.MultiBufferSource;

public class ShadowOrbRenderer extends AbstractAttachmentEntityRenderer<ShadowOrb> {

    @Override
    protected RenderContext<ShadowOrb> createContext(ShadowOrb orb, PathNode visualNode, float partialTick, int packedLight) {
        float rot = orb.getTickCount() + partialTick;
        return super.createContext(orb, visualNode, partialTick, packedLight)
                .model(new ModelContext()
                               .translateOffset(-0.5f, -0.5f, -0.5f)
                               .rotationOffset(rot, rot, rot)
                               .scale(0.75f));
    }

    @Override
    protected void render(PoseStack poseStack, MultiBufferSource bufferSource) {
        LyraModelRenderer.json(FargoSoulModelRegister.SHADOW_ORB)
                .color(context.color.argbInt())
                .light(RenderUtil.FULL_LIGHT)
                .render(poseStack, bufferSource);
    }
}

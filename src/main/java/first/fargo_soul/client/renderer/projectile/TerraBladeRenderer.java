package first.fargo_soul.client.renderer.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import first.fargo_soul.common.entity.TerraBlade;
import first.fargo_soul.register.FargoSoulModelRegister;
import first.lyra.client.render.AbstractAttachmentEntityRenderer;
import first.lyra.client.render.ModelContext;
import first.lyra.client.render.RenderContext;
import first.lyra.client.render.RenderUtil;
import first.lyra.client.render.model.LyraModelRenderer;
import first.lyra.client.render.trail.RibbonTrailContext;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.client.renderer.MultiBufferSource;

public class TerraBladeRenderer extends AbstractAttachmentEntityRenderer<TerraBlade> {

    @Override
    protected RenderContext<TerraBlade> createContext(TerraBlade blade, PathNode visualNode, float partialTick, int packedLight) {
        return super.createContext(blade, visualNode, partialTick, packedLight)
                .trail(new RibbonTrailContext<TerraBlade>()
                               .upOffset(1.015f)
                               .timer(blade.attacking ? blade.trailTimer : 0)
                               .colorRGB(0xF8E687)
                               .segmentsPerNode(4)
                               .historyLength(6))
                .model(new ModelContext()
                               .scale(1.5f)
                               .translateOffset(-0.5f, -0.5f, -0.5f)
                               .rotationOffset(0, 90, 45));
    }

    @Override
    protected void render(PoseStack poseStack, MultiBufferSource bufferSource) {
        LyraModelRenderer.json(FargoSoulModelRegister.TERRA_BLADE)
                .color(context.color.argbInt())
                .light(RenderUtil.FULL_LIGHT)
                .render(poseStack, bufferSource);
    }
}

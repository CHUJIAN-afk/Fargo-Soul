package first.fargo_soul.client.renderer.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import first.fargo_soul.common.entity.TerraBlade;
import first.fargo_soul.register.FargoSoulModelRegister;
import first.lyra.client.dynamicLight.DynamicLightDispatcher;
import first.lyra.client.render.AbstractAttachmentEntityRenderer;
import first.lyra.client.render.RenderContext;
import first.lyra.client.render.RenderUtil;
import first.lyra.client.render.trail.ModelConfig;
import first.lyra.client.render.trail.RibbonTrailConfig;
import first.lyra.common.entity.PathNode;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.FastColor;

public class TerraBladeRenderer extends AbstractAttachmentEntityRenderer<TerraBlade> {

    @Override
    protected RenderContext<TerraBlade> createContext(TerraBlade blade, float partialTick) {
        return RenderContext.<TerraBlade>builder()
                .trail(new RibbonTrailConfig<TerraBlade>()
                               .timer(blade.attacking ? blade.trailTimer : 0)
                               .colorRGB(0xF8E687)
                               .segmentsPerNode(4)
                               .historyLength(6)
                               .upOffset(1.015f))
                .model(new ModelConfig<TerraBlade>()
                        .scale(1.5f)
                        .translateOffset(-0.5f, -0.5f, -0.5f)
                        .rotationOffset(0, 90, 45))
                .build();
    }

    @Override
    protected void render(TerraBlade blade, PoseStack poseStack, MultiBufferSource bufferSource, PathNode visualNode, RenderContext<TerraBlade> context, float partialTick, int packedLight, float alpha) {
        RenderUtil.renderStandalone(FargoSoulModelRegister.TERRA_BLADE, poseStack, bufferSource, FastColor.ARGB32.color((int) (alpha * 255), 255, 255, 255), RenderUtil.FULL_LIGHT);
        DynamicLightDispatcher.addLightSources(visualNode.pos(), 8);
    }
}
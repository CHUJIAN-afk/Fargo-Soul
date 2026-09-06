package first.fargo_soul.client.renderer.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import first.fargo_soul.common.entity.ChlorophyteOrb;
import first.fargo_soul.register.FargoSoulModelRegister;
import first.lyra.client.render.AbstractAttachmentEntityRenderer;
import first.lyra.client.render.RenderContext;
import first.lyra.client.render.RenderUtil;
import first.lyra.client.render.rendererHelper.LaserRendererHelper;
import first.lyra.client.render.trail.ModelConfig;
import first.lyra.common.entity.PathNode;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class ChlorophyteOrbRenderer extends AbstractAttachmentEntityRenderer<ChlorophyteOrb> {

    @Override
    protected RenderContext<ChlorophyteOrb> createContext(ChlorophyteOrb orb, float partialTick) {
        return RenderContext.<ChlorophyteOrb>builder()
                .model(new ModelConfig<ChlorophyteOrb>()
                        .translateOffset(-0.5f, -0.5f, -0.5f))
                .build();
    }

    @Override
    protected void modelModify(ChlorophyteOrb orb, PoseStack poseStack, MultiBufferSource bufferSource, PathNode visualNode, RenderContext<ChlorophyteOrb> context, float partialTick, int packedLight, float alpha) {
        super.modelModify(orb, poseStack, bufferSource, visualNode, context, partialTick, packedLight, alpha);
        if (orb.getOwner().level().getEntity(orb.getTargetId()) instanceof LivingEntity living) {
            float length = (float) visualNode.pos().distanceTo(living.getPosition(partialTick).add(0, living.getBbHeight() / 2, 0));
            Vec3 from = visualNode.pos();
            Vec3 to = living.getBoundingBox().getCenter();
            Vec3 dir = to.subtract(from).normalize();
            poseStack.pushPose();
            Quaternionf rotation = new Quaternionf().rotateTo(new Vector3f(0, 0, -1), new Vector3f((float) dir.x, (float) dir.y, (float) dir.z));
            poseStack.mulPose(rotation);
            LaserRendererHelper.builder()
                    .length(length)
                    .radius(0.1f, 0.1f)
                    .layers(3)
                    .segments(10)
                    .color(0xFF3AFF7A)
                    .alpha(0.25f)
                    .innerRatio(0.4f)
                    .render(poseStack, bufferSource);
            poseStack.popPose();
        }
    }

    @Override
    protected void render(ChlorophyteOrb orb, PoseStack poseStack, MultiBufferSource bufferSource, PathNode visualNode, RenderContext<ChlorophyteOrb> context, float partialTick, int packedLight, float alpha) {
        RenderUtil.renderStandalone(FargoSoulModelRegister.CHLOROPHYTE_CRYSTAL, poseStack, bufferSource, FastColor.ARGB32.color((int) (alpha * 255), 255, 255, 255), RenderUtil.FULL_LIGHT);
    }
}
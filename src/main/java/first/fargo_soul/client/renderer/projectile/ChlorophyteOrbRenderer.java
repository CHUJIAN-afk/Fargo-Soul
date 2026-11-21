package first.fargo_soul.client.renderer.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import first.fargo_soul.common.entity.ChlorophyteOrb;
import first.fargo_soul.register.FargoSoulModelRegister;
import first.lyra.client.render.AbstractAttachmentEntityRenderer;
import first.lyra.client.render.ModelContext;
import first.lyra.client.render.RenderContext;
import first.lyra.client.render.RenderUtil;
import first.lyra.client.render.model.LyraModelRenderer;
import first.lyra.client.render.rendererHelper.LaserRendererHelper;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class ChlorophyteOrbRenderer extends AbstractAttachmentEntityRenderer<ChlorophyteOrb> {

    @Override
    protected RenderContext<ChlorophyteOrb> createContext(ChlorophyteOrb orb, PathNode visualNode, float partialTick, int packedLight) {
        return super.createContext(orb, visualNode, partialTick, packedLight)
                .model(new ModelContext()
                               .translateOffset(-0.5f, -0.5f, -0.5f));
    }

    @Override
    protected void modelModify(PoseStack poseStack, MultiBufferSource bufferSource) {
        super.modelModify(poseStack, bufferSource);
        ChlorophyteOrb orb = context.entity;
        PathNode visualNode = context.visualNode;
        if (orb.getOwner().level().getEntity(orb.getTargetId()) instanceof LivingEntity living) {
            float length = (float) visualNode.pos().distanceTo(living.getPosition(context.partialTick).add(0, living.getBbHeight() / 2, 0));
            Vec3 from = visualNode.pos();
            Vec3 to = living.getBoundingBox().getCenter();
            Vec3 dir = to.subtract(from).normalize();
            poseStack.pushPose();
            Quaternionf rotation = new Quaternionf().rotateTo(new Vector3f(0, 0, -1), new Vector3f((float) dir.x, (float) dir.y, (float) dir.z));
            poseStack.mulPose(rotation);
            LaserRendererHelper.builder()
                    .length(length)
                    .radius(0.2f, 0.2f)
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
    protected void render(PoseStack poseStack, MultiBufferSource bufferSource) {
        LyraModelRenderer.json(FargoSoulModelRegister.CHLOROPHYTE_CRYSTAL)
                .color(context.color.argbInt())
                .light(RenderUtil.FULL_LIGHT)
                .render(poseStack, bufferSource);
    }
}

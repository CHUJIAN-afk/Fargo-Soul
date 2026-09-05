package first.fargo_soul.client.renderer.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import first.fargo_soul.common.entity.LightningOrb;
import first.lyra.client.dynamicLight.DynamicLightDispatcher;
import first.lyra.client.render.AbstractAttachmentEntityRenderer;
import first.lyra.client.render.RenderContext;
import first.lyra.client.render.rendererHelper.LightningRendererHelper;
import first.lyra.client.render.rendererHelper.SphereRendererHelper;
import first.lyra.common.entity.PathNode;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.Set;

public class LightningOrbRenderer extends AbstractAttachmentEntityRenderer<LightningOrb> {

    @Override
    protected RenderContext<LightningOrb> createContext(LightningOrb entity, float partialTick) {
        return RenderContext.<LightningOrb>builder()
                .build();
    }

    @Override
    protected void modelModify(LightningOrb orb, PoseStack poseStack, MultiBufferSource bufferSource, PathNode visualNode, RenderContext<LightningOrb> context, float partialTick, int packedLight, float alpha) {
        super.modelModify(orb, poseStack, bufferSource, visualNode, context, partialTick, packedLight, alpha);
        Set<Integer> list = orb.getIdList();
        Player owner = orb.getOwner();
        Level level = owner.level();
        for (Integer id : list) {
            Entity entity = level.getEntity(id);
            if (entity instanceof LivingEntity living) {
                RandomSource random = living.getRandom();
                random.setSeed(orb.hashCode() + living.hashCode());
                AABB box = living.getBoundingBox();
                LightningRendererHelper.builder()
                        .from(visualNode.pos())
                        .to(box.getCenter().offsetRandom(random, (float) box.getSize() * 0.5f))
                        .renderOrigin(visualNode.pos())
                        .layers(1)
                        .segments(4)
                        .branches(0)
                        .jitter(0.1f)
                        .branchLength(0f)
                        .radius(0.02f, 0.02f)
                        .color(0x38ffec)
                        .alpha(0.5f * alpha)
                        .render(poseStack, bufferSource, random);
            }
        }
    }

    @Override
    protected void render(LightningOrb entity, PoseStack poseStack, MultiBufferSource bufferSource, PathNode visualNode, RenderContext<LightningOrb> context, float partialTick, int packedLight, float alpha) {
        SphereRendererHelper.builder()
                .radius(0.25f)
                .layers(3)
                .sides(6)
                .color(0x38ffec)
                .alpha(0.75f)
                .innerRatio(0.5f)
                .render(poseStack, bufferSource);
        DynamicLightDispatcher.addLightSources(visualNode.pos(), 8);
    }
}

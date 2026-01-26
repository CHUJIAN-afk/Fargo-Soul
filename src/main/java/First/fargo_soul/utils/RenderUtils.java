package First.fargo_soul.utils;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public abstract class RenderUtils {

    public static float getAgeInTicks(Entity attacker, float partialTick, float speed) {
        float render = attacker.tickCount * speed;
        return Mth.lerp(partialTick, render - speed, render);
    }

    public static float getAgeInTicks(Level level, float partialTick, float speed) {
        float render = level.getGameTime() * speed;
        return Mth.lerp(partialTick, render - speed, render);
    }

    public static void renderItemRings(
            Level level,
            PoseStack poseStack,
            MultiBufferSource multiBufferSource,
            List<ItemStack> renderList,
            int rings,
            float radius,
            float scale,
            float yOffset,
            int packedLight,
            float ageInTicks,
            RandomSource random,
            int seed
    ) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        List<List<ItemStack>> renderListList = new ArrayList<>();
        for (int i = 0; i < rings; i++) {
            renderListList.add(new ArrayList<>());
        }
        for (int i = 0; i < renderList.size(); i++) {
            renderListList.get(i % rings).add(renderList.get(i));
        }
        for (List<ItemStack> renderItems : renderListList) {
            poseStack.pushPose();
            poseStack.translate(0, yOffset, 0);
            random.setSeed(renderListList.indexOf(renderItems));
            poseStack.mulPose(Axis.YP.rotationDegrees(ageInTicks * random.nextFloat()));
            poseStack.mulPose(Axis.XP.rotationDegrees(ageInTicks * random.nextFloat()));
            poseStack.mulPose(Axis.ZP.rotationDegrees(ageInTicks * random.nextFloat()));
            for (ItemStack renderStack : renderItems) {
                random.setSeed(renderStack.getItem().hashCode() + seed);
                seed++;
                int i = renderItems.indexOf(renderStack);
                float angle = (float) i / renderItems.size() * Mth.TWO_PI + ageInTicks * 0.05f;
                float x = (float) Math.cos(angle) * radius;
                float z = (float) Math.sin(angle) * radius;
                poseStack.pushPose();
                poseStack.translate(x, 0, z);
                poseStack.mulPose(Axis.YP.rotationDegrees(ageInTicks * random.nextFloat()));
                poseStack.mulPose(Axis.XP.rotationDegrees(ageInTicks * random.nextFloat()));
                poseStack.mulPose(Axis.ZP.rotationDegrees(ageInTicks * random.nextFloat()));
                poseStack.scale(scale, scale, scale);
                itemRenderer.renderStatic(
                        renderStack,
                        ItemDisplayContext.FIXED,
                        packedLight,
                        OverlayTexture.NO_OVERLAY,
                        poseStack,
                        multiBufferSource,
                        level,
                        random.nextInt()
                );
                poseStack.popPose();
            }
            poseStack.popPose();
        }
    }

    public static void renderItemRing(
            Level level,
            PoseStack poseStack,
            MultiBufferSource multiBufferSource,
            List<ItemStack> renderItems,
            float radius,
            float scale,
            float yOffset,
            int packedLight,
            float ageInTicks,
            RandomSource random,
            int seed
    ) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        for (ItemStack renderStack : renderItems) {
            random.setSeed(renderStack.getItem().hashCode() + seed);
            seed++;
            int i = renderItems.indexOf(renderStack);
            float angle = (float) i / renderItems.size() * Mth.TWO_PI + ageInTicks * 0.05f;
            float x = (float) Math.cos(angle) * radius;
            float z = (float) Math.sin(angle) * radius;
            poseStack.pushPose();
            poseStack.translate(x, yOffset, z);
            poseStack.mulPose(Axis.YP.rotationDegrees(ageInTicks * random.nextFloat()));
            poseStack.mulPose(Axis.XP.rotationDegrees(ageInTicks * random.nextFloat()));
            poseStack.mulPose(Axis.ZP.rotationDegrees(ageInTicks * random.nextFloat()));
            poseStack.scale(scale, scale, scale);
            itemRenderer.renderStatic(
                    renderStack,
                    ItemDisplayContext.FIXED,
                    packedLight,
                    OverlayTexture.NO_OVERLAY,
                    poseStack,
                    multiBufferSource,
                    level,
                    random.nextInt()
            );
            poseStack.popPose();
        }
    }

}
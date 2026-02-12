package First.fargo_soul.client.renderer.blockEntityRender;

import First.fargo_soul.client.renderType.BlackHoleRenderType;
import First.fargo_soul.common.blcokEntity.CosmicCrucibleBlockEntity;
import First.fargo_soul.config.ClientConfig;
import First.fargo_soul.utils.RenderUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class CosmicCrucibleBlockEntityRenderer implements BlockEntityRenderer<CosmicCrucibleBlockEntity> {

    private final ItemRenderer itemRenderer;
    private final RandomSource random;

    public CosmicCrucibleBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
        this.random = RandomSource.create();
    }

    @Override
    public void render(@NotNull CosmicCrucibleBlockEntity blockEntity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
        Level level = blockEntity.getLevel();
        if (level != null) {
            if (ClientConfig.CosmicCrucibleBlackHoleEventHorizonRendering.get()) {
                renderBlackHoleEventHorizon(blockEntity, level, partialTick, poseStack, multiBufferSource);
            }
            if (ClientConfig.CosmicCrucibleItemRendering.get()) {
                renderItem(blockEntity, partialTick, poseStack, multiBufferSource, packedLight, level);
            }
        }
    }

    private void renderItem(@NotNull CosmicCrucibleBlockEntity blockEntity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int packedLight, Level level) {
        IItemHandler itemHandler = blockEntity.getItemHandler();
        List<ItemStack> renderList = new ArrayList<>();

        for (int i = 0; i < itemHandler.getSlots(); i++) {
            ItemStack stack = itemHandler.getStackInSlot(i);
            if (stack.isEmpty()) continue;

            int count = stack.getCount();
            while (count > 0) {
                ItemStack renderStack = stack.copy();
                renderStack.setCount(Math.min(count, stack.getMaxStackSize()));
                renderList.add(renderStack);
                count -= renderStack.getCount();
            }
        }

        if (renderList.isEmpty()) return;

        random.setSeed(blockEntity.getBlockPos().hashCode());
        float ageInTicks = RenderUtils.getAgeInTicks(level, partialTick, random.nextFloat() * 0.4f + 0.6f);
        int rings = Math.min(8, (int) Math.ceil(renderList.size() / 8.0f));

        for (int ringIndex = 0; ringIndex < rings; ringIndex++) {
            List<ItemStack> ringItems = new ArrayList<>();
            for (int i = ringIndex; i < renderList.size(); i += rings) {
                ringItems.add(renderList.get(i));
            }
            if (ringItems.isEmpty()) continue;

            float ringSpeed = 1.0f - ringIndex * 0.1f;
            float ringOffset = ringIndex * 0.2f;
            float ringRadius = 0.375f * (float) (Math.cos(ageInTicks * 0.1f + ringOffset) * 0.3f + 1.7f);

            poseStack.pushPose();
            poseStack.translate(0.5, 1.5f, 0.5f);
            poseStack.mulPose(Axis.YP.rotationDegrees(ageInTicks * ringSpeed + ringOffset));
            poseStack.mulPose(Axis.XP.rotationDegrees(ageInTicks * ringSpeed * 0.8f + ringOffset));
            poseStack.mulPose(Axis.ZP.rotationDegrees(ageInTicks * ringSpeed * 0.6f + ringOffset));

            for (int i = 0; i < ringItems.size(); i++) {
                ItemStack renderItemStack = ringItems.get(i);
                float angle = (float) i / ringItems.size() * Mth.TWO_PI + ageInTicks * 0.05f * ringSpeed;
                float x = (float) Math.cos(angle) * ringRadius;
                float z = (float) Math.sin(angle) * ringRadius;

                this.random.setSeed(renderItemStack.getItem().hashCode());
                poseStack.pushPose();
                poseStack.translate(x, 0, z);
                poseStack.mulPose(Axis.YP.rotationDegrees(ageInTicks * 3 * ringSpeed + i * 30));
                poseStack.mulPose(Axis.ZP.rotationDegrees(ageInTicks * 3 * ringSpeed + i * 30));

                float itemScale = 0.6f * (float) (Math.cos(ageInTicks * 0.1f * ringSpeed + ringOffset) * 0.2f + 1f);
                poseStack.scale(itemScale, itemScale, itemScale);

                BakedModel bakedmodel = this.itemRenderer.getModel(renderItemStack, level, null, renderItemStack.getItem().hashCode());
                ItemEntityRenderer.renderMultipleFromCount(this.itemRenderer, poseStack, multiBufferSource, LevelRenderer.getLightColor(level,blockEntity.getBlockPos().above()), renderItemStack, bakedmodel, bakedmodel.isGui3d(), this.random);
                poseStack.popPose();
            }
            poseStack.popPose();
        }
    }

    private void renderBlackHoleEventHorizon(@NotNull CosmicCrucibleBlockEntity blockEntity, Level level, float partialTick, PoseStack poseStack, MultiBufferSource buffer) {
        poseStack.pushPose();
        poseStack.translate(0.5, 1.5, 0.5);
        random.setSeed(blockEntity.getBlockPos().hashCode());
        float ageInTicks = RenderUtils.getAgeInTicks(level, partialTick, random.nextFloat() * 0.4f + 0.6f);
        float radius = (float) (Math.sin(ageInTicks * 0.025) * 0.01 + 0.19);
        int segments = 64;
        VertexConsumer builder = buffer.getBuffer(BlackHoleRenderType.BlackHoleEventHorizon);
        Matrix4f matrix = poseStack.last().pose();
        for (int i = 0; i < segments; i++) {
            float lat0 = (float) Math.PI * (-0.5f + (float) i / segments);
            float r0 = (float) Math.cos(lat0) * radius;
            float z0 = (float) Math.sin(lat0) * radius;
            float lat1 = (float) Math.PI * (-0.5f + (float) (i + 1) / segments);
            float r1 = (float) Math.cos(lat1) * radius;
            float z1 = (float) Math.sin(lat1) * radius;
            for (int j = 0; j < segments; j++) {
                float lng0 = (float) (2.0 * Math.PI * j / segments);
                float lng1 = (float) (2.0 * Math.PI * (j + 1) / segments);
                builder.addVertex(matrix, (float) Math.cos(lng0) * r0, (float) Math.sin(lng0) * r0, z0).setColor(0, 0, 0, 255);
                builder.addVertex(matrix, (float) Math.cos(lng1) * r0, (float) Math.sin(lng1) * r0, z0).setColor(0, 0, 0, 255);
                builder.addVertex(matrix, (float) Math.cos(lng1) * r1, (float) Math.sin(lng1) * r1, z1).setColor(0, 0, 0, 255);
                builder.addVertex(matrix, (float) Math.cos(lng0) * r1, (float) Math.sin(lng0) * r1, z1).setColor(0, 0, 0, 255);
            }
        }
        poseStack.popPose();
    }

}

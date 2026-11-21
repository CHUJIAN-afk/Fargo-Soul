package first.fargo_soul.register;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.client.renderer.projectile.*;
import first.lyra.client.render.AttachmentEntityRenderDispatcher;
import first.lyra.client.render.IAttachmentEntityRenderer;
import first.lyra.client.render.RenderUtil;
import first.lyra.client.render.rendererHelper.SphereRendererHelper;
import first.lyra.common.attachmentEntity.AttachmentEntity;
import first.lyra.common.attachmentEntity.AttachmentEntityType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

@EventBusSubscriber(modid = FargoSoul.MODID, value = Dist.CLIENT)
public class SummonerAttachmentEntityRenderRegister {

    @SubscribeEvent
    public static void register(FMLClientSetupEvent event) {
        register(SummonerAttachmentEntityRegister.METEOR, () -> simpleRenderer(FargoSoul.rl("textures/item/entity/meteor.png"), 1));
        register(SummonerAttachmentEntityRegister.VORTEX, SummonerAttachmentEntityRenderRegister::emptyRenderer);
        register(SummonerAttachmentEntityRegister.SPRINT, SummonerAttachmentEntityRenderRegister::emptyRenderer);
        register(SummonerAttachmentEntityRegister.SHADOW_ORB, ShadowOrbRenderer::new);
        register(SummonerAttachmentEntityRegister.LIGHTNING_ORB, LightningOrbRenderer::new);
        register(SummonerAttachmentEntityRegister.SNOW_BALL, () -> simpleRenderer(ResourceLocation.withDefaultNamespace("textures/item/snowball.png"), 0.35f));
        register(SummonerAttachmentEntityRegister.TRACKING_BLOOD, TrackingBloodRenderer::new);
        register(SummonerAttachmentEntityRegister.BLOOD_DROP, BloodDropRenderer::new);
        register(SummonerAttachmentEntityRegister.STAR, () -> sphereRenderer(0.1f, 0xE4E4A9FF));
        register(SummonerAttachmentEntityRegister.PETAL, () -> sphereRenderer(0.12f, 0xFFF2A2C4));
        register(SummonerAttachmentEntityRegister.GHOST_ORB, () -> sphereRenderer(0.1f, 0xFFB8D8FF));
        register(SummonerAttachmentEntityRegister.CHLOROPHYTE_ORB, ChlorophyteOrbRenderer::new);
        register(SummonerAttachmentEntityRegister.TERRA_BLADE, TerraBladeRenderer::new);
    }

    private static <T extends AttachmentEntity> void register(DeferredHolder<AttachmentEntityType<?>, AttachmentEntityType<T>> type, @Nullable Supplier<IAttachmentEntityRenderer<T>> renderer) {
        AttachmentEntityRenderDispatcher.register(type.get(), renderer != null ? renderer.get() : emptyRenderer());
    }

    /** 无渲染内容（仅保留实体存在） */
    private static <T extends AttachmentEntity> IAttachmentEntityRenderer<T> emptyRenderer() {
        return (entity, poseStack, bufferSource, partialTick, packedLight, renderNode) -> {
        };
    }

    private static <T extends AttachmentEntity> IAttachmentEntityRenderer<T> simpleRenderer(@Nullable ResourceLocation texture, float size) {
        return (entity, poseStack, bufferSource, partialTick, packedLight, renderNode) -> {
            if (texture != null && size > 0) {
                RenderUtil.renderImage(texture, renderNode.pos(), size, size, bufferSource, false, FastColor.ARGB32.color(255, 255, 255, 255));
            }
        };
    }

    private static <T extends AttachmentEntity> IAttachmentEntityRenderer<T> sphereRenderer(float radius, int color) {
        return (entity, poseStack, bufferSource, partialTick, packedLight, renderNode) -> SphereRendererHelper.builder()
                .radius(radius)
                .layers(2)
                .sides(6)
                .color(color)
                .alpha(0.85f)
                .innerRatio(0.5f)
                .render(poseStack, bufferSource);
    }
}

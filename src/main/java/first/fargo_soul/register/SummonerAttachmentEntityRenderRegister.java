package first.fargo_soul.register;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.client.renderer.projectile.LightningOrbRenderer;
import first.lyra.client.dynamicLight.DynamicLightDispatcher;
import first.lyra.client.render.AttachmentEntityRenderDispatcher;
import first.lyra.client.render.IAttachmentEntityRenderer;
import first.lyra.client.render.RenderUtil;
import first.lyra.client.render.SimpleRenderer;
import first.lyra.common.entity.AttachmentEntity;
import first.lyra.common.entity.AttachmentEntityType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * 附件实体渲染器注册类（仅客户端加载）。
 * <p>
 * 渲染器引用客户端类（LocalPlayer 等），必须与实体类型注册分离：
 * 若由 {@link SummonerAttachmentEntityRegister} 直接引用，
 * 服务端加载该类时会被 RuntimeDistCleaner 拦截
 * （Attempted to load class ... for invalid dist DEDICATED_SERVER）导致模组崩溃。
 * 本类通过 {@code @EventBusSubscriber(value = Dist.CLIENT)} 保证只在客户端加载执行。
 */
@EventBusSubscriber(modid = FargoSoul.MODID, value = Dist.CLIENT)
public class SummonerAttachmentEntityRenderRegister {

    @SubscribeEvent
    public static void register(FMLClientSetupEvent event) {
        register(SummonerAttachmentEntityRegister.METEOR, () -> simpleRenderer(FargoSoul.rl("textures/item/entity/meteor.png"), 1, 8));
        register(SummonerAttachmentEntityRegister.VORTEX, null);
        register(SummonerAttachmentEntityRegister.SPRINT, null);
        register(SummonerAttachmentEntityRegister.SHADOW_ORB, () -> simpleRenderer(FargoSoul.rl("textures/item/entity/meteor.png"), 1, 12));
        register(SummonerAttachmentEntityRegister.LIGHTNING_ORB, LightningOrbRenderer::new);
    }

    private static <T extends AttachmentEntity> void register(DeferredHolder<AttachmentEntityType<?>, AttachmentEntityType<T>> type, @Nullable Supplier<IAttachmentEntityRenderer<T>> renderer) {
        AttachmentEntityRenderDispatcher.register(type.get(), renderer != null ? renderer.get() : new SimpleRenderer<>(null));
    }

    private static <T extends AttachmentEntity> SimpleRenderer<T> simpleRenderer(@Nullable ResourceLocation texture, float size, int light) {
        return new SimpleRenderer<>((entity, poseStack, bufferSource, visualNode, context, partialTick, alpha) -> {
            if (texture != null && size > 0) {
                RenderUtil.renderImage(texture, visualNode.pos(), size, size, bufferSource, false, FastColor.ARGB32.color((int) (alpha * 255), 255, 255, 255));
            }
            if (light > 0) {
                DynamicLightDispatcher.addLightSources(visualNode.pos(), light);
            }
        });
    }
}

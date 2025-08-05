package First.fargo_soul.Client.Renderer;

import First.fargo_soul.Entity.Arrow.NebulaEmpoweredFlame;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.BaseItem.BaseItemsRegister;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class NebulaEmpoweredFlameRenderer extends EntityRenderer<NebulaEmpoweredFlame> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Fargo_soul.MODID, "nebula_empowered_flame");
    private final ItemStack Flame = new ItemStack(BaseItemsRegister.Flame.get());

    public NebulaEmpoweredFlameRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(@NotNull NebulaEmpoweredFlame entity, float yaw, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int light) {
        poseStack.pushPose();
        poseStack.scale(0.25f, 0.25f, 0.25f);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        itemRenderer.renderStatic(
                Flame,
                ItemDisplayContext.FIXED,
                LightTexture.FULL_BRIGHT,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                entity.level(),
                0
        );
        poseStack.popPose();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull NebulaEmpoweredFlame entity) {
        return TEXTURE;
    }
}

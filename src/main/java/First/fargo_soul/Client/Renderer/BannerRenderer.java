package First.fargo_soul.Client.Renderer;

import First.fargo_soul.Entity.Arrow.Banner;
import First.fargo_soul.Fargo_soul;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class BannerRenderer extends EntityRenderer<Banner> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Fargo_soul.MODID, "banner");
    private final ItemStack itemStack = new ItemStack(Items.YELLOW_BANNER);

    public BannerRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(@NotNull Banner spear, float yaw, float partialTick, PoseStack poseStack, @NotNull MultiBufferSource buffer, int light) {
        poseStack.pushPose();
        poseStack.scale(2, 2, 2);
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        itemRenderer.renderStatic(
                itemStack,
                ItemDisplayContext.FIXED,
                light,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                spear.level(),
                0
        );
        poseStack.popPose();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Banner entity) {
        return TEXTURE;
    }

}

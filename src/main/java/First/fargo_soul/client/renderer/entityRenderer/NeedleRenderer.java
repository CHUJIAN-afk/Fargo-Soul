package First.fargo_soul.client.renderer.entityRenderer;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.entity.projectile.NeedleProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class NeedleRenderer extends EntityRenderer<NeedleProjectile> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(FargoSoul.MODID,"cactus_soul");
    private final ItemStack needleStack = new ItemStack(Items.CACTUS);

    public NeedleRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(NeedleProjectile entity, float yaw, float partialTick, PoseStack poseStack, @NotNull MultiBufferSource buffer, int light) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, entity.xRotO, entity.getXRot())));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTick, entity.yRotO, entity.getYRot()) - 90));

        poseStack.scale(0.25f, 0.5f, 0.25f);
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        itemRenderer.renderStatic(
                needleStack,
                ItemDisplayContext.FIXED,
                light,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                entity.level(),
                0
        );
        poseStack.popPose();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull NeedleProjectile entity) {
        return TEXTURE;
    }

}

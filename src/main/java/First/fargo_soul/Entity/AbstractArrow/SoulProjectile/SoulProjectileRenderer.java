package First.fargo_soul.Entity.AbstractArrow.SoulProjectile;

import First.fargo_soul.Fargo_soul;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SoulProjectileRenderer extends EntityRenderer<SoulProjectile> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Fargo_soul.MODID,"ghost_soul");

    public SoulProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(SoulProjectile entity, float yaw, float partialTick, PoseStack poseStack, @NotNull MultiBufferSource buffer, int light) {
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull SoulProjectile entity) {
        return TEXTURE;
    }
}

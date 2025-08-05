package First.fargo_soul.Client.Renderer;

import First.fargo_soul.Entity.Arrow.Ghost;
import First.fargo_soul.Fargo_soul;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class GhostRenderer extends EntityRenderer<Ghost> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Fargo_soul.MODID,"ghost_soul");

    public GhostRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(Ghost entity, float yaw, float partialTick, PoseStack poseStack, @NotNull MultiBufferSource buffer, int light) {
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Ghost entity) {
        return TEXTURE;
    }
}

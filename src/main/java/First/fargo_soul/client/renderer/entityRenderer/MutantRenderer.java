package First.fargo_soul.client.renderer.entityRenderer;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.common.entity.livingEntity.Mutant;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class MutantRenderer extends EntityRenderer<Mutant> {

    private static final ResourceLocation TEXTURE = FargoSoul.rl("cactus_soul");

    public MutantRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Mutant mutant) {
        return TEXTURE;
    }

}

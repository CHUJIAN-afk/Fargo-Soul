package First.fargo_soul.Effect.Neutral;

import First.fargo_soul.Fargo_soul;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.NeoForgeMod;

public class GhostEffect extends MobEffect {
    public static final ResourceLocation Ghost = ResourceLocation.fromNamespaceAndPath(Fargo_soul.MODID, "ghost");

    public GhostEffect() {
        super(MobEffectCategory.NEUTRAL, 0xC0C0C0);
        addAttributeModifier(
                Attributes.MOVEMENT_SPEED,
                Ghost,
                0.5,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        addAttributeModifier(
                NeoForgeMod.CREATIVE_FLIGHT,
                Ghost,
                0.1,
                AttributeModifier.Operation.ADD_VALUE
        );
    }

}

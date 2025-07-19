package First.fargo_soul.Effect.Neutral;

import First.fargo_soul.Fargo_soul;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ShellDefenseEffect extends MobEffect {
    public static final ResourceLocation ShellDefense = ResourceLocation.fromNamespaceAndPath(Fargo_soul.MODID, "shell_defense");

    public ShellDefenseEffect() {
        super(MobEffectCategory.NEUTRAL, 0xCD853F);
        addAttributeModifier(
                Attributes.MOVEMENT_SPEED,
                ShellDefense,
                -1.0,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
    }

}

package First.fargo_soul.common.effect.neutral;

import First.fargo_soul.FargoSoul;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class SunburstEruptionEffect extends MobEffect {
    public static final ResourceLocation ShellDefense = ResourceLocation.fromNamespaceAndPath(FargoSoul.MODID, "sunburst_eruption");

    public SunburstEruptionEffect() {
        super(MobEffectCategory.NEUTRAL, 0xCD853F);
        addAttributeModifier(
                Attributes.KNOCKBACK_RESISTANCE,
                ShellDefense,
                1.0,
                AttributeModifier.Operation.ADD_VALUE
        );
    }

}

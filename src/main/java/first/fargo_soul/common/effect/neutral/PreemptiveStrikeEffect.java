package first.fargo_soul.common.effect.neutral;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.register.AttributeRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class PreemptiveStrikeEffect extends MobEffect {
    public static final ResourceLocation ShellDefense = ResourceLocation.fromNamespaceAndPath(FargoSoul.MODID, "preemptive_strike");

    public PreemptiveStrikeEffect() {
        super(MobEffectCategory.NEUTRAL, 0xFF00FF);
        addAttributeModifier(
                AttributeRegister.Damage,
                ShellDefense,
                0.5,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        addAttributeModifier(
                AttributeRegister.CriticalChance,
                ShellDefense,
                1.0,
                AttributeModifier.Operation.ADD_VALUE
        );
    }

}

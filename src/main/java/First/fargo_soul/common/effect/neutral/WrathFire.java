package First.fargo_soul.common.effect.neutral;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.register.AttributeRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class WrathFire extends MobEffect {

    public static final ResourceLocation WrathFire = ResourceLocation.fromNamespaceAndPath(FargoSoul.MODID, "wrath_fire");

    public WrathFire() {
        super(MobEffectCategory.NEUTRAL, 0xFFA500);
        addAttributeModifier(
                AttributeRegister.Damage,
                WrathFire,
                0.15,
                AttributeModifier.Operation.ADD_MULTIPLIED_BASE
        );
    }

}

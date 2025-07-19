package First.fargo_soul.Effect.Neutral;

import First.fargo_soul.Attribute.AttributeRegister;
import First.fargo_soul.Fargo_soul;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class BeetleMightEffect extends MobEffect {

    public static final ResourceLocation BeetleMight = ResourceLocation.fromNamespaceAndPath(Fargo_soul.MODID, "beetle_might");

    public BeetleMightEffect() {
        super(MobEffectCategory.NEUTRAL, 0x4B0082);
        addAttributeModifier(
                AttributeRegister.Damage,
                BeetleMight,
                0.1,
                AttributeModifier.Operation.ADD_VALUE
        );
        addAttributeModifier(
                Attributes.ATTACK_SPEED,
                BeetleMight,
                0.1,
                AttributeModifier.Operation.ADD_VALUE
        );
    }

}

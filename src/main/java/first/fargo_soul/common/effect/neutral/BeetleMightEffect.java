package first.fargo_soul.common.effect.neutral;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.register.AttributeRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class BeetleMightEffect extends MobEffect {

    public static final ResourceLocation BeetleMight = ResourceLocation.fromNamespaceAndPath(FargoSoul.MODID, "beetle_might");

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

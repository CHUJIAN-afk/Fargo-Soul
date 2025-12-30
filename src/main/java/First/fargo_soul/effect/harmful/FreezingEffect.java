package First.fargo_soul.effect.harmful;

import First.fargo_soul.FargoSoul;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class FreezingEffect extends MobEffect {
    public FreezingEffect() {
        super(MobEffectCategory.HARMFUL, 0xA0D8FF);
        addAttributeModifier(
                Attributes.MOVEMENT_SPEED,
                ResourceLocation.fromNamespaceAndPath(FargoSoul.MODID, "frostbite"),
                -1.0,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
    }

}

package First.fargo_soul.Effect.Neutral;

import First.fargo_soul.Attribute.AttributeRegister;
import First.fargo_soul.Fargo_soul;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class WrathFire extends MobEffect {

    public static final ResourceLocation WrathFire = ResourceLocation.fromNamespaceAndPath(Fargo_soul.MODID, "wrath_fire");

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

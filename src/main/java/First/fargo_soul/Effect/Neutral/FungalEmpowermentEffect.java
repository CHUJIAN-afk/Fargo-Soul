package First.fargo_soul.Effect.Neutral;

import First.fargo_soul.Attribute.AttributeRegister;
import First.fargo_soul.Fargo_soul;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class FungalEmpowermentEffect extends MobEffect {

    public static final ResourceLocation FUNGAL_EMPOWERMENT = ResourceLocation.fromNamespaceAndPath(Fargo_soul.MODID, "fungal_empowerment");

    public FungalEmpowermentEffect() {
        super(MobEffectCategory.HARMFUL, 0xA0D8FF);
        addAttributeModifier(
                AttributeRegister.CriticalChance,
                FUNGAL_EMPOWERMENT,
                0.2,
                AttributeModifier.Operation.ADD_VALUE
        );
        addAttributeModifier(
                AttributeRegister.Damage,
                FUNGAL_EMPOWERMENT,
                0.2,
                AttributeModifier.Operation.ADD_VALUE
        );
    }
}
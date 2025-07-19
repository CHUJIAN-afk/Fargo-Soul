package First.fargo_soul.Effect.Harmful;

import First.fargo_soul.Fargo_soul;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class FrostbiteEffect extends MobEffect {

    public static final ResourceLocation FROSTBITE = ResourceLocation.fromNamespaceAndPath(Fargo_soul.MODID, "frostbite");

    public FrostbiteEffect() {
        super(MobEffectCategory.HARMFUL, 0x00BFFF);
        addAttributeModifier(
                Attributes.MOVEMENT_SPEED,
                FROSTBITE,
                -0.2,
                AttributeModifier.Operation.ADD_MULTIPLIED_BASE
        );
        addAttributeModifier(
                Attributes.MAX_HEALTH,
                FROSTBITE,
                -0.1,
                AttributeModifier.Operation.ADD_MULTIPLIED_BASE
        );
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        entity.hurt(entity.damageSources().freeze(), 1.0f);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int i = 25 >> amplifier;
        if (i > 0) {
            return duration % i == 0;
        } else {
            return true;
        }
    }
}

package first.fargo_soul.common.effect.harmful;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class FlareEffect extends MobEffect {

    public FlareEffect() {
        super(MobEffectCategory.HARMFUL, 0xCD853F);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        entity.hurt(entity.damageSources().onFire(), entity.getMaxHealth() * 0.016f);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int i = 25 >> amplifier;
        if (i > 0) {
            return duration % i == 0;
        } else if (duration == 1) {
            return true;
        }
        return true;
    }

}
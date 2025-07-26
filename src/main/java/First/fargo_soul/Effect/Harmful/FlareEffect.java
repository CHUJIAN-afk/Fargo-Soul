package First.fargo_soul.Effect.Harmful;

import First.fargo_soul.Utils.MathUtils;
import First.fargo_soul.Utils.ParticleUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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
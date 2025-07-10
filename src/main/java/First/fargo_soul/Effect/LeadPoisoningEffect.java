package First.fargo_soul.Effect;

import First.fargo_soul.Curios.Souls;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public class LeadPoisoningEffect extends MobEffect {
    protected LeadPoisoningEffect() {
        super(MobEffectCategory.HARMFUL, 0x4E9331);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        entity.hurt(entity.damageSources().magic(), 1.0f);
        List<LivingEntity> livingEntityList = entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(4));
        livingEntityList.removeIf(livingEntity -> CurioUtils.isEquipped(entity, Souls.LeadSoul.get()));
        for (LivingEntity livingEntity : livingEntityList) {
            MobEffectInstance effect = entity.getEffect(EffectRegister.LeadPoisoningEffect);
            if (effect != null && effect.getDuration() > 1 && livingEntity.getEffect(EffectRegister.LeadPoisoningEffect) == null) {
                MobEffectInstance effectInstance = new MobEffectInstance(EffectRegister.LeadPoisoningEffect, effect.getDuration() / 2, amplifier);
                livingEntity.addEffect(effectInstance);
            }
        }
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

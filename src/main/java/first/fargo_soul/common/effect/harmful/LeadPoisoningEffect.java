package first.fargo_soul.common.effect.harmful;

import first.fargo_soul.common.item.terraSoul.terraPower.LeadSoul;
import first.fargo_soul.register.EffectRegister;
import first.fargo_soul.utils.CurioUtils;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class LeadPoisoningEffect extends MobEffect {
    public LeadPoisoningEffect() {
        super(MobEffectCategory.HARMFUL, 0x5A5A5A);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        entity.hurt(entity.damageSources().magic(), 1.0f);
        Level level = entity.level();
        MobEffectInstance effect = entity.getEffect(EffectRegister.LeadPoisoning);
        if (!level.isClientSide() && effect != null && effect.getDuration() > 20) {
            MobEffectInstance effectInstance = new MobEffectInstance(EffectRegister.LeadPoisoning, effect.getDuration() / 2, amplifier);
            level.getEntitiesOfClass(
                    LivingEntity.class,
                    entity.getBoundingBox().inflate(4),
                    livingEntity -> livingEntity != entity && !CurioUtils.isEquipped(livingEntity, LeadSoul.class) && livingEntity.getEffect(EffectRegister.LeadPoisoning) == null
            ).forEach(livingEntity -> livingEntity.addEffect(effectInstance));
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

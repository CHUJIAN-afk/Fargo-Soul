package first.fargo_soul.common.item.terraSoul.deathPower;

import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.terraSoul.DeathPower;
import first.fargo_soul.utils.CurioUtils;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LightLayer;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;


public class AncientShadowSoul extends SoulItem {

    public AncientShadowSoul(Properties properties) {
        super();
    }

    @Override
    public void livingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
            if (!attacker.equals(target) && CurioUtils.isEquipped(attacker, AncientShadowSoul.class)) {
                Holder<MobEffect> blindness = MobEffects.BLINDNESS;
                Holder<MobEffect> darkness = MobEffects.DARKNESS;
                int environmentLight = attacker.level().getBrightness(LightLayer.SKY, attacker.blockPosition());
                double chance = (CurioUtils.isEquipped(attacker, DeathPower.class) ? 1 : (environmentLight < 1 ? 1 : 0.1));
                RandomSource random = target.getRandom();
                if (random.nextDouble() < chance) {
                    target.addEffect(new MobEffectInstance(blindness, 200));
                }
                if (random.nextDouble() < chance) {
                    target.addEffect(new MobEffectInstance(darkness, 200));
                }
                if (target.hasEffect(blindness) || target.hasEffect(darkness)) {
                    event.setAmount(event.getAmount() * 1.75f);
                }
            }
        }
    }

    @Override
    public void effectApplicable(MobEffectEvent.Applicable event) {
        if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
            if (CurioUtils.isEquipped(target, AncientShadowSoul.class)) {
                MobEffectInstance effectInstance = event.getEffectInstance();
                if (effectInstance.is(MobEffects.BLINDNESS) || effectInstance.is(MobEffects.DARKNESS)) {
                    event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
                }
            }
        }
    }

}

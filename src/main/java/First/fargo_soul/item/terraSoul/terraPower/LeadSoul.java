package First.fargo_soul.item.terraSoul.terraPower;

import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.TerraPower;
import First.fargo_soul.register.EffectRegister;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

public class LeadSoul extends SoulItem {

    public LeadSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
            if (!attacker.equals(target) && CurioUtils.isEquipped(attacker, LeadSoul.class) && attacker.getRandom().nextDouble() < 0.1) {
                target.addEffect(new MobEffectInstance(EffectRegister.LeadPoisoning, 200, CurioUtils.isEquipped(attacker, TerraPower.class) ? 1 : 0));
            }
        }
    }

    @Override
    public void effectApplicable(MobEffectEvent.Applicable event) {
        if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
            if (CurioUtils.isEquipped(target, LeadSoul.class) && event.getEffectInstance().is(EffectRegister.LeadPoisoning)) {
                event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
            }
        }
    }

}

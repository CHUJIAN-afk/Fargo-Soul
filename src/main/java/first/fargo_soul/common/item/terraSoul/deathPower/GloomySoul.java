package first.fargo_soul.common.item.terraSoul.deathPower;

import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.terraSoul.DeathPower;
import first.fargo_soul.register.EffectRegister;
import first.fargo_soul.utils.CurioUtils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class GloomySoul extends SoulItem {

    public GloomySoul(Properties properties) {
        super();
    }

    @Override
    public void livingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof TamableAnimal animal && animal.getOwner() instanceof LivingEntity attacker) {
            if (CurioUtils.isEquipped(attacker, GloomySoul.class) && event.getEntity() instanceof LivingEntity target) {
                int amplifier = CurioUtils.isEquipped(attacker, DeathPower.class) ? 1 : 0;
                target.addEffect(new MobEffectInstance(EffectRegister.ShadowFire, 200, amplifier));
            }
        }
    }

}

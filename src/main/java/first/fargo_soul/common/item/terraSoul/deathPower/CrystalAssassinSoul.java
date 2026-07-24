package first.fargo_soul.common.item.terraSoul.deathPower;

import first.fargo_soul.common.event.modEvent.SprintEvent;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.terraSoul.DeathPower;
import first.fargo_soul.utils.CurioUtils;
import first.fargo_soul.utils.SoulUtils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class CrystalAssassinSoul extends SoulItem {

    public CrystalAssassinSoul(Properties properties) {
        super();
    }

    @Override
    public void livingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
            if (CurioUtils.isEquipped(attacker, CrystalAssassinSoul.class)) {
                SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(target, this.getClass().getSimpleName() + attacker.getStringUUID());
                if (!soulInfo.isEnabled()) {
                    soulInfo.setEnabled(true);
                    event.setAmount(event.getAmount() * 2.2f);
                    int duration = CurioUtils.isEquipped(attacker, DeathPower.class) ? 1 : 0;
                    int amplifier = CurioUtils.isEquipped(attacker, DeathPower.class) ? 140 : 60;
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, duration, amplifier));
                }
            }
        }
    }

    @Override
    public void sprintClient(SprintEvent.Client event) {
        LivingEntity livingEntity = event.getEntity();
        if (CurioUtils.isEquipped(livingEntity, CrystalAssassinSoul.class)) {
            event.setSprinting(true);
        }
    }

}





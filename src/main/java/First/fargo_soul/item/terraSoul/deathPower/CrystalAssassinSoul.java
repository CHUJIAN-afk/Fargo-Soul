package First.fargo_soul.item.terraSoul.deathPower;

import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.event.modEvent.SprintEvent;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.DeathPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class CrystalAssassinSoul extends SoulItem {

    public CrystalAssassinSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
            if (CurioUtils.isEquipped(attacker, CrystalAssassinSoul.class)) {
                SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(this.getClass().getName() + attacker.getScoreboardName());
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





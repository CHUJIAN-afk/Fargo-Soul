package First.fargo_soul.item.terraSoul.deathPower;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.DeathPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class CrystalAssassinSoul extends SoulItem {
    public static final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public CrystalAssassinSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PINK));
    }

    @EventBusSubscriber(modid = FargoSoul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                if (!attacker.equals(target) && CurioUtils.isEquipped(attacker, CrystalAssassinSoul.class)) {
                    SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(attacker.getScoreboardName());
                    if (!soulInfo.isEnabled()) {
                        event.setAmount(event.getAmount() * 2.2f);
                    }
                }
            }
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                if (!attacker.equals(target) && CurioUtils.isEquipped(attacker, CrystalAssassinSoul.class)) {
                    SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(attacker.getScoreboardName());
                    if (!soulInfo.isEnabled()) {
                        soulInfo.setEnabled(true);
                        int duration = CurioUtils.isEquipped(attacker, DeathPower.class) ? 1 : 0;
                        int amplifier = CurioUtils.isEquipped(attacker, DeathPower.class) ? 7 : 3;
                        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, duration, amplifier));
                    }
                }
            }
        }

    }

}





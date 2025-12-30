package First.fargo_soul.item.terraSoul.deathPower;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.DeathPower;
import First.fargo_soul.register.EffectRegister;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class GloomySoul extends SoulItem {

    public GloomySoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.YELLOW));
    }

    @EventBusSubscriber(modid = FargoSoul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getSource().getEntity() instanceof TamableAnimal animal && animal.getOwner() instanceof LivingEntity attacker) {
                if (CurioUtils.isEquipped(attacker, GloomySoul.class) && event.getEntity() instanceof LivingEntity target) {
                    int amplifier = CurioUtils.isEquipped(attacker, DeathPower.class) ? 1 : 0;
                    target.addEffect(new MobEffectInstance(EffectRegister.ShadowFire, 200, amplifier));
                }
            }
        }

    }

}

package First.fargo_soul.Item.Soul.TerraSoul.TerraPower.SoulStone;

import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.TerraSoul.TerraPower.TerraPower;
import First.fargo_soul.Utils.SoulUtils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientChatReceivedEvent;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class LeadSoul extends SoulItem {

    public LeadSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.BLUE));
    }

    @EventBusSubscriber
    public static class Event {

        @SubscribeEvent
        public static void Post(LivingDamageEvent.Post event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                if (!attacker.equals(target) && SoulUtils.isEquipped(attacker, LeadSoul.class) && attacker.getRandom().nextDouble() < 0.1) {
                    target.addEffect(new MobEffectInstance(EffectRegister.LeadPoisoning, 200, SoulUtils.isEquipped(attacker, TerraPower.class) ? 1 : 0));
                }
            }
        }

        @SubscribeEvent
        public static void Applicable(MobEffectEvent.Applicable event) {
            if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
                if (SoulUtils.isEquipped(target, LeadSoul.class) && event.getEffectInstance().is(EffectRegister.LeadPoisoning)) {
                    event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
                }
            }
        }

    }

}

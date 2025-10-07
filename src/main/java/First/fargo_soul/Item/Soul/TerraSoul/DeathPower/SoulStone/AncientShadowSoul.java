package First.fargo_soul.Item.Soul.TerraSoul.DeathPower.SoulStone;

import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.TerraSoul.DeathPower.DeathPower;
import First.fargo_soul.Utils.SoulUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;


public class AncientShadowSoul extends SoulItem {

    public AncientShadowSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PINK));
    }

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                if (!attacker.equals(target) && SoulUtils.isEquipped(attacker, AncientShadowSoul.class)) {
                    Holder<MobEffect> blindness = MobEffects.BLINDNESS;
                    Holder<MobEffect> darkness = MobEffects.DARKNESS;
                    if (target.getEffect(blindness) != null || target.getEffect(darkness) != null) {
                        event.setAmount(event.getAmount() * 1.75f);
                    }
                }
            }
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                if (!attacker.equals(target) && SoulUtils.isEquipped(attacker, AncientShadowSoul.class)) {
                    Holder<MobEffect> blindness = MobEffects.BLINDNESS;
                    Holder<MobEffect> darkness = MobEffects.DARKNESS;
                    double chance = (SoulUtils.isEquipped(attacker, DeathPower.class) ? 1 : (getEnvironmentLight(attacker) > 1 ? 1 : 0.1));
                    if (target.getRandom().nextDouble() < chance) {
                        target.addEffect(new MobEffectInstance(blindness, 200));
                    }
                    if (target.getRandom().nextDouble() < chance) {
                        target.addEffect(new MobEffectInstance(darkness, 200));
                    }
                }
            }
        }

        @SubscribeEvent
        public static void Applicable(MobEffectEvent.Applicable event) {
            if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
                if (SoulUtils.isEquipped(target, AncientShadowSoul.class)) {
                    MobEffectInstance effectInstance = event.getEffectInstance();
                    if (effectInstance.is(MobEffects.BLINDNESS) || effectInstance.is(MobEffects.DARKNESS)) {
                        event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
                    }
                }
            }
        }


        private static int getEnvironmentLight(LivingEntity attacker) {
            BlockPos pos = attacker.blockPosition();
            Level level = attacker.level();
            int skyLight = level.getBrightness(LightLayer.SKY, pos);
            int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
            return Math.max(skyLight, blockLight);
        }

    }

}

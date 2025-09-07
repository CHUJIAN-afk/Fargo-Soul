package First.fargo_soul.Item.Soul.TerraSoul.LifePower.SoulStone;

import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class BeetleSoul extends SoulItem {

    public BeetleSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.YELLOW));
    }


    public static void BeetleSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.BeetleSoul.get())) {
            if (player.getEffect(EffectRegister.BeetleMight) instanceof MobEffectInstance mobEffectInstance) {
                if (mobEffectInstance.getAmplifier() < 2) {
                    player.addEffect(new MobEffectInstance(EffectRegister.BeetleMight, 200, 1));
                }
            } else {
                player.addEffect(new MobEffectInstance(EffectRegister.BeetleMight, 200));
            }
        }
    }

    public static void BeetleSoulDamageHandler2(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.BeetleSoul.get())) {
            if (player.getEffect(EffectRegister.BeetleEndurance) instanceof MobEffectInstance mobEffectInstance) {
                if (mobEffectInstance.getAmplifier() < 2) {
                    player.addEffect(new MobEffectInstance(EffectRegister.BeetleEndurance, 200, 1));
                }
            } else {
                player.addEffect(new MobEffectInstance(EffectRegister.BeetleEndurance, 200));
            }
        }
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            if (livingEntity.getEffect(EffectRegister.BeetleEndurance) instanceof MobEffectInstance mobEffectInstance) {
                event.setAmount(event.getAmount() * (1 - ((mobEffectInstance.getAmplifier() + 1) * 0.15f)));
            }
        }
    }


}

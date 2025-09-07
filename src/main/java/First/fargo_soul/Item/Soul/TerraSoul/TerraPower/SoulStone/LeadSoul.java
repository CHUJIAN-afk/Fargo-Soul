package First.fargo_soul.Item.Soul.TerraSoul.TerraPower.SoulStone;

import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.CustomUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import static First.fargo_soul.Item.Soul.SoulsRegister.LeadSoul;

public class LeadSoul extends SoulItem {

    public LeadSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.BLUE));
    }


    public static void LeadSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, LeadSoul.get()) && event.getEntity() instanceof LivingEntity livingEntity) {
            if (CustomUtils.random.nextDouble() < 0.1) {
                livingEntity.addEffect(new MobEffectInstance(EffectRegister.LeadPoisoning, 200, 0));
            }
        }
    }

    public static void LeadSoulDamageHandler2(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, LeadSoul.get()) && event.getSource().getEntity() instanceof LivingEntity livingEntity) {
            LivingEntity lastHurtByMob = player.getLastHurtByMob();
            if (lastHurtByMob != null && lastHurtByMob.equals(livingEntity)) {
                event.setAmount(event.getAmount() * 0.9f);
            }
        }
    }

}

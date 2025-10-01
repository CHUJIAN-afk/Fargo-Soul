package First.fargo_soul.Item.Soul.TerraSoul.EarthPower.SoulStone;

import First.fargo_soul.Attachment.Attachment.SoulAbilityData;
import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.TerraSoul.EarthPower.EarthPower;
import First.fargo_soul.Utils.SoulUtils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class PalladiumSoul extends SoulItem {

    public PalladiumSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PINK));
    }

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                double chance = SoulUtils.isEquipped(attacker, EarthPower.class) ? 0.2 : 0.1;
                if (SoulUtils.isEquipped(attacker, PalladiumSoul.class) && attacker.getRandom().nextDouble() < chance) {
                    int maxLevel = SoulUtils.isEquipped(attacker, EarthPower.class) ? 3 : 2;
                    SoulUtils.applyOrUpdateEffect(
                            attacker,
                            MobEffects.REGENERATION,
                            59,
                            maxLevel
                    );
                }
            }
        }

        @SubscribeEvent
        public static void Heal(LivingHealEvent event) {
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                if (SoulUtils.isEquipped(attacker, PalladiumSoul.class)) {
                    SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(PalladiumSoul.class);
                    soulInfo.maxStacks = 10;
                    soulInfo.addStacks((int) event.getAmount());
                    if (soulInfo.stacks == soulInfo.maxStacks) {
                        soulInfo.removeStacks();
                        attacker.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 119));
                    }
                }
            }
        }

    }

}

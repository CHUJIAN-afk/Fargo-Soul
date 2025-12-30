package First.fargo_soul.item.terraSoul.forestPower;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.ForestPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class ShadowWoodSoul extends SoulItem {

    public ShadowWoodSoul(Item.Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.GREEN));
    }

    @EventBusSubscriber(modid = FargoSoul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Tick(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                if (CurioUtils.isEquipped(attacker, ShadowWoodSoul.class)) {
                    SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(PineWoodSoul.class);
                    soulInfo.setMaxCooldown(100);
                    if (soulInfo.getCooldown() == 0 && SoulUtils.getSoulTarget(attacker, 10) instanceof LivingEntity target) {
                        soulInfo.setCooldown(soulInfo.getMaxCooldown());
                        SoulAbilityData.SoulInfo info = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(attacker.getScoreboardName());
                        info.setEnabled(true);
                    }
                }
            }
        }

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                if (!attacker.equals(target) && CurioUtils.isEquipped(target, ShadowWoodSoul.class) && SoulUtils.canAttack(ShadowWoodSoul.class, attacker, attacker)) {
                    SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(target.getScoreboardName());
                    if (soulInfo.isEnabled()) {
                        int amount = CurioUtils.isEquipped(target, ForestPower.class) ? 3 : 2;
                        SoulUtils.attack(ShadowWoodSoul.class, attacker, target, attacker, DamageTypes.MAGIC, amount);
                        target.heal(amount);
                    }
                }
            }
        }

    }

}

package First.fargo_soul.Item.Soul.TerraSoul.ForestPower.SoulStone;

import First.fargo_soul.Attachment.Attachment.SoulAbilityData;
import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.TerraSoul.ForestPower.ForestPower;
import First.fargo_soul.Utils.SoulUtils;
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

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Tick(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                if (SoulUtils.isEquipped(attacker, ShadowWoodSoul.class)) {
                    SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(PineWoodSoul.class);
                    soulInfo.maxCooldown = 100;
                    if (soulInfo.cooldown == 0 && SoulUtils.getSoulTarget(attacker, 10) instanceof LivingEntity target) {
                        soulInfo.cooldown = soulInfo.maxCooldown;
                        SoulAbilityData.SoulInfo info = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(attacker.getScoreboardName());
                        info.enabled = true;
                    }
                }
            }
        }

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                if (SoulUtils.isEquipped(target, ShadowWoodSoul.class) && SoulUtils.canAttack(ShadowWoodSoul.class, attacker, attacker)) {
                    SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(target.getScoreboardName());
                    if (soulInfo.enabled) {
                        int amount = SoulUtils.isEquipped(target, ForestPower.class) ? 3 : 2;
                        SoulUtils.attack(ShadowWoodSoul.class, attacker, target, attacker, DamageTypes.MAGIC, amount);
                        target.heal(amount);
                    }
                }
            }
        }

    }

}

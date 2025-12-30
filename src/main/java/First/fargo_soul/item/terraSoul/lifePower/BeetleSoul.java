package First.fargo_soul.item.terraSoul.lifePower;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.LifePower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.register.AttributeRegister;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.utils.AttributeUtils;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class BeetleSoul extends SoulItem {

    public BeetleSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.YELLOW));
    }

    @EventBusSubscriber(modid = FargoSoul.MODID)
    public static class Event {

        public static void Tick(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                SoulAbilityData.SoulInfo info = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo("BeetleMight");
                AttributeUtils.ConditionAttributeModifier(
                        attacker,
                        AttributeRegister.ArmorPierce,
                        ItemRegister.BeetleSoulItem.getId(),
                        info.getStacks() * 3,
                        AttributeModifier.Operation.ADD_VALUE,
                        CurioUtils.isEquipped(attacker, BeetleSoul.class) && info.getStacks() > 0
                );
                if (CurioUtils.isEquipped(attacker, BeetleSoul.class)) {
                    if (attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo("BeetleEndurance") instanceof SoulAbilityData.SoulInfo soulInfo) {
                        soulInfo.setMaxStacks(CurioUtils.isEquipped(attacker, LifePower.class) ? 3 : 2);
                        soulInfo.setMaxCooldown(140);
                        if (attacker.tickCount % 140 == 0) {
                            soulInfo.setCooldown(soulInfo.getMaxCooldown());
                            soulInfo.addStacks();
                        }
                    }
                    if (attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo("BeetleMight") instanceof SoulAbilityData.SoulInfo soulInfo) {
                        soulInfo.setMaxStacks(CurioUtils.isEquipped(attacker, LifePower.class) ? 6 : 4);
                        soulInfo.setMaxCooldown(20);
                        if (attacker.tickCount % 20 == 0) {
                            soulInfo.setCooldown(soulInfo.getMaxCooldown());
                            soulInfo.shrinkStacks();
                        }
                    }
                }
            }
        }

        @SubscribeEvent
        public static void ChangeTarget(LivingChangeTargetEvent event) {
            if (event.getEntity() instanceof LivingEntity attacker && attacker.getType().is(EntityTypeTags.ARTHROPOD) && event.getNewAboutToBeSetTarget() instanceof LivingEntity target && !target.level().isClientSide()) {
                SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(target.getScoreboardName());
                if (CurioUtils.isEquipped(target, BeetleSoul.class) && !soulInfo.isEnabled()) {
                    event.setCanceled(true);
                }
            }
        }

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
                if (CurioUtils.isEquipped(target, BeetleSoul.class)) {
                    SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo("BeetleEndurance");
                    event.setAmount(event.getAmount() * (1 - (soulInfo.getStacks() * 0.15f)));
                    soulInfo.shrinkStacks();
                }
            }
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                if (!attacker.equals(target) && CurioUtils.isEquipped(attacker, BeetleSoul.class)) {
                    SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo("BeetleMight");
                    soulInfo.addStacks(2);
                    target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(attacker.getScoreboardName()).setEnabled(true);
                }
            }
        }

    }

}

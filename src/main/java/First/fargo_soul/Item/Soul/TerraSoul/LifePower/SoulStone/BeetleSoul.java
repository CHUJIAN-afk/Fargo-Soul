package First.fargo_soul.Item.Soul.TerraSoul.LifePower.SoulStone;

import First.fargo_soul.Attachment.Attachment.SoulAbilityData;
import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Attribute.AttributeRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Item.Soul.TerraSoul.LifePower.LifePower;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.SoulUtils;
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

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        public static void Tick(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                SoulAbilityData.SoulInfo info = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo("BeetleMight");
                AttributeUtils.ConditionAttributeModifier(
                        attacker,
                        AttributeRegister.ArmorPierce,
                        SoulsRegister.BeetleSoul.getId(),
                        info.stacks * 3,
                        AttributeModifier.Operation.ADD_VALUE,
                        SoulUtils.isEquipped(attacker, BeetleSoul.class) && info.stacks > 0
                );
                if (SoulUtils.isEquipped(attacker, BeetleSoul.class)) {
                    if (attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo("BeetleEndurance") instanceof SoulAbilityData.SoulInfo soulInfo) {
                        soulInfo.maxStacks = SoulUtils.isEquipped(attacker, LifePower.class) ? 3 : 2;
                        soulInfo.maxCooldown = 140;
                        if (attacker.tickCount % 140 == 0) {
                            soulInfo.cooldown = soulInfo.maxCooldown;
                            soulInfo.addStacks();
                        }
                    }
                    if (attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo("BeetleMight") instanceof SoulAbilityData.SoulInfo soulInfo) {
                        soulInfo.maxStacks = SoulUtils.isEquipped(attacker, LifePower.class) ? 6 : 4;
                        soulInfo.maxCooldown = 20;
                        if (attacker.tickCount % 20 == 0) {
                            soulInfo.cooldown = soulInfo.maxCooldown;
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
                if (SoulUtils.isEquipped(target, BeetleSoul.class) && !soulInfo.enabled) {
                    event.setCanceled(true);
                }
            }
        }

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
                if (SoulUtils.isEquipped(target, BeetleSoul.class)) {
                    SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo("BeetleEndurance");
                    event.setAmount(event.getAmount() * (1 - (soulInfo.stacks * 0.15f)));
                    soulInfo.shrinkStacks();
                }
            }
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                if (SoulUtils.isEquipped(attacker, BeetleSoul.class)) {
                    SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo("BeetleMight");
                    soulInfo.addStacks(2);
                    target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(attacker.getScoreboardName()).enabled = true;
                }
            }
        }

    }

}

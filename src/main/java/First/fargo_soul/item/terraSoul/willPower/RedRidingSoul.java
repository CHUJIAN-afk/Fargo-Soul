package First.fargo_soul.item.terraSoul.willPower;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.WillPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.register.AttributeRegister;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.utils.AttributeUtils;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class RedRidingSoul extends SoulItem {

    public RedRidingSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.YELLOW));
    }


    @EventBusSubscriber(modid = FargoSoul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Tick(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(RedRidingSoul.class);
                AttributeUtils.ConditionAttributeModifier(
                        attacker,
                        Attributes.MOVEMENT_SPEED,
                        ItemRegister.RedRidingSoulItem.getId(),
                        soulInfo.getStacks() * 0.01f,
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
                        CurioUtils.isEquipped(attacker, RedRidingSoul.class) && soulInfo.getStacks() > 0
                );
                AttributeUtils.ConditionAttributeModifier(
                        attacker,
                        AttributeRegister.ArmorPierce,
                        ItemRegister.RedRidingSoulItem.getId(),
                        soulInfo.getStacks() * 0.01f,
                        AttributeModifier.Operation.ADD_VALUE,
                        CurioUtils.isEquipped(attacker, RedRidingSoul.class) && soulInfo.getStacks() > 0
                );
            }
        }

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                if (!attacker.equals(target) && CurioUtils.isEquipped(attacker, RedRidingSoul.class)) {
                    SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(RedRidingSoul.class);
                    soulInfo.setMaxStacks(CurioUtils.isEquipped(attacker, WillPower.class) ? 15 : 10);
                    soulInfo.addStacks();
                    if (target.getArmorValue() > 0) {
                        event.setAmount(event.getAmount() * 1.2f);
                    }
                }
            }
            if (!event.isCanceled() && event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
                if (CurioUtils.isEquipped(target, RedRidingSoul.class)) {
                    SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(RedRidingSoul.class);
                    soulInfo.removeStacks();
                }
            }
        }

    }

}

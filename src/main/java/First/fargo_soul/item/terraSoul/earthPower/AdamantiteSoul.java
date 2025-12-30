package First.fargo_soul.item.terraSoul.earthPower;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.EarthPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.utils.AttributeUtils;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;


public class AdamantiteSoul extends SoulItem {

    public AdamantiteSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.LIME));
    }

    @EventBusSubscriber(modid = FargoSoul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Tick(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                ResourceLocation resourceLocation = ItemRegister.AdamantiteSoulItem.getId();
                SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(AdamantiteSoul.class);
                if (soulInfo.getDuration() == 0) {
                    soulInfo.removeStacks();
                }
                AttributeUtils.ConditionAttributeModifier(
                        attacker,
                        Attributes.ATTACK_SPEED,
                        resourceLocation,
                        soulInfo.getStacks() * 0.05,
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
                        CurioUtils.isEquipped(attacker, AdamantiteSoul.class) && soulInfo.getStacks() > 0
                );
                AttributeUtils.ConditionAttributeModifier(
                        attacker,
                        Attributes.MOVEMENT_SPEED,
                        resourceLocation,
                        0.15,
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
                        CurioUtils.isEquipped(attacker, EarthPower.class) && soulInfo.getStacks() > soulInfo.getMaxStacks()
                );
            }
        }

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                if (!attacker.equals(target) && CurioUtils.isEquipped(attacker, AdamantiteSoul.class)) {
                    SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(AdamantiteSoul.class);
                    soulInfo.setMaxStacks(8);
                    soulInfo.addStacks();
                    soulInfo.setDuration(100);
                    if (target instanceof Mob mob && mob.getTarget() != null && target.getRandom().nextDouble() < 0.05 && soulInfo.getStacks() == soulInfo.getMaxStacks()) {
                        mob.setTarget(null);
                    }
                }
            }
        }

    }

}

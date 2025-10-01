package First.fargo_soul.Item.Soul.TerraSoul.EarthPower.SoulStone;

import First.fargo_soul.Attachment.Attachment.SoulAbilityData;
import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Item.Soul.TerraSoul.EarthPower.EarthPower;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.SoulUtils;
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

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Tick(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                ResourceLocation resourceLocation = SoulsRegister.AdamantiteSoul.getId();
                SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(AdamantiteSoul.class);
                if (soulInfo.duration == 0) {
                    soulInfo.stacks = 0;
                }
                AttributeUtils.ConditionAttributeModifier(
                        attacker,
                        Attributes.ATTACK_SPEED,
                        resourceLocation,
                        soulInfo.stacks * 0.05,
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
                        SoulUtils.isEquipped(attacker, AdamantiteSoul.class) && soulInfo.stacks > 0
                );
                AttributeUtils.ConditionAttributeModifier(
                        attacker,
                        Attributes.MOVEMENT_SPEED,
                        resourceLocation,
                        0.15,
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
                        SoulUtils.isEquipped(attacker, EarthPower.class) && soulInfo.stacks > soulInfo.maxStacks
                );
            }
        }

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                if (SoulUtils.isEquipped(attacker, AdamantiteSoul.class)) {
                    SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(AdamantiteSoul.class);
                    soulInfo.maxStacks = 8;
                    soulInfo.addStacks();
                    soulInfo.duration = 100;
                    if (target instanceof Mob mob && mob.getTarget() != null && target.getRandom().nextDouble() < 0.05 && soulInfo.stacks == soulInfo.maxStacks) {
                        mob.setTarget(null);
                    }
                }
            }
        }

    }

}

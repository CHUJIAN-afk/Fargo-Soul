package First.fargo_soul.Item.Soul.TerraSoul.DeathPower.SoulStone;

import First.fargo_soul.Attachment.Attachment.SoulAbilityData;
import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Item.Soul.TerraSoul.DeathPower.DeathPower;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.SoulUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;


public class NinjaSoul extends SoulItem {

    public NinjaSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.GREEN));
    }

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void ChangeTarget(LivingChangeTargetEvent event) {
            if (event.getNewAboutToBeSetTarget() instanceof LivingEntity target && !target.level().isClientSide()) {
                if (SoulUtils.isEquipped(target, NinjaSoul.class) && event.getEntity() instanceof Mob mob) {
                    LivingEntity originalAboutToBeSetTarget = event.getOriginalAboutToBeSetTarget();
                    if ((originalAboutToBeSetTarget == null || !originalAboutToBeSetTarget.equals(target)) && mob.distanceTo(target) > 6) {
                        event.setCanceled(true);
                    }
                }
            }
        }

        @SubscribeEvent
        public static void Tick(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                ResourceLocation resourceLocation = SoulsRegister.NinjaSoul.getId();
                SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(NinjaSoul.class);
                soulInfo.maxStacks = SoulUtils.isEquipped(attacker, DeathPower.class) ? 500 : 300;
                soulInfo.addStacks();
                AttributeUtils.ConditionAttributeModifier(
                        attacker,
                        Attributes.ATTACK_DAMAGE,
                        resourceLocation,
                        soulInfo.stacks * 0.01,
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
                        SoulUtils.isEquipped(attacker, NinjaSoul.class)
                );
            }
        }

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(NinjaSoul.class);
                soulInfo.stacks = 0;
            }
        }

    }

}

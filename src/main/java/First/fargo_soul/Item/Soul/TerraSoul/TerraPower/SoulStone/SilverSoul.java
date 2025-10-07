package First.fargo_soul.Item.Soul.TerraSoul.TerraPower.SoulStone;

import First.fargo_soul.Attachment.Attachment.SoulAbilityData;
import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Item.Soul.TerraSoul.TerraPower.TerraPower;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.ParticleUtils;
import First.fargo_soul.Utils.SoulUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class SilverSoul extends SoulItem {

    public SilverSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.BLUE));
    }

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void SilverSoulTickHandler(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                AttributeUtils.ConditionAttributeModifier(
                        attacker,
                        Attributes.ARMOR,
                        SoulsRegister.ObsidianSoul.getId(),
                        10,
                        AttributeModifier.Operation.ADD_VALUE,
                        SoulUtils.isEquipped(attacker, SilverSoul.class) && (attacker instanceof Player || (attacker instanceof Mob mob && mob.getTarget() != null))
                );
                if (SoulUtils.isEquipped(attacker, SilverSoul.class)) {
                    SoulAbilityData.SoulInfo SoulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(SilverSoul.class);
                    if (attacker.isBlocking()) {
                        SoulInfo.maxStacks = 100;
                        SoulInfo.stacks++;
                    } else {
                        SoulInfo.removeStacks();
                    }
                }
            }
        }

        @SubscribeEvent
        public static void ShieldBlock(LivingShieldBlockEvent event) {
            if (event.getEntity() instanceof LivingEntity target && event.getDamageSource().getEntity() instanceof LivingEntity attacker && !target.level().isClientSide()) {
                if (SoulUtils.isEquipped(target, SilverSoul.class)) {
                    SoulAbilityData.SoulInfo SoulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(SilverSoul.class);
                    if (event.getBlocked() && SoulInfo.cooldown == 0 && SoulInfo.stacks > 0 && SoulInfo.stacks < (SoulUtils.isEquipped(target, TerraPower.class) ? 6 : 4)) {
                        SoulInfo.maxCooldown = 20;
                        SoulInfo.cooldown = SoulInfo.maxCooldown;
                        attacker.hurt(target.damageSources().mobAttack(target), event.getBlockedDamage() * 2.0f);
                        Level level = target.level();
                        SoulUtils.playSound(
                                level,
                                target.position(),
                                SoundEvents.ANVIL_PLACE,
                                SoundSource.PLAYERS
                        );
                        ParticleUtils.spawnParticleLine(
								(ServerLevel) level,
                                target.getBoundingBox().getCenter(),
                                attacker.getBoundingBox().getCenter(),
                                ParticleTypes.CRIT,
                                20,
                                0.1f
                        );
                        ParticleUtils.spawnParticleSphere(
								(ServerLevel) level,
                                target.getX(),
                                target.getY(),
                                target.getZ(),
                                ParticleTypes.CRIT,
                                1,
                                40,
                                0.1f
                        );
                    }
                }
            }
        }

        @SubscribeEvent
        public static void Incoming(LivingIncomingDamageEvent event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                if (SoulUtils.isEquipped(attacker, SilverSoul.class)) {
                    SoulAbilityData.SoulInfo SoulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(SilverSoul.class);
                    if (SoulInfo.cooldown > 0) {
                        event.setAmount(event.getAmount() * 1.5f);
                    }
                }
            }
        }

    }

}

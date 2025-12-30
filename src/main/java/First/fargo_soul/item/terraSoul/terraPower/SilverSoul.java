package First.fargo_soul.item.terraSoul.terraPower;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.TerraPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.utils.AttributeUtils;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.ParticleUtils;
import First.fargo_soul.utils.SoulUtils;
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

    @EventBusSubscriber(modid = FargoSoul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void SilverSoulTickHandler(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                AttributeUtils.ConditionAttributeModifier(
                        attacker,
                        Attributes.ARMOR,
                        ItemRegister.ObsidianSoulItem.getId(),
                        10,
                        AttributeModifier.Operation.ADD_VALUE,
                        CurioUtils.isEquipped(attacker, SilverSoul.class) && (attacker instanceof Player || (attacker instanceof Mob mob && mob.getTarget() != null))
                );
                if (CurioUtils.isEquipped(attacker, SilverSoul.class)) {
                    SoulAbilityData.SoulInfo SoulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(SilverSoul.class);
                    if (attacker.isBlocking()) {
                        SoulInfo.setMaxStacks(100);
                        SoulInfo.addStacks();
                    } else {
                        SoulInfo.removeStacks();
                    }
                }
            }
        }

        @SubscribeEvent
        public static void ShieldBlock(LivingShieldBlockEvent event) {
            if (event.getEntity() instanceof LivingEntity target && event.getDamageSource().getEntity() instanceof LivingEntity attacker && !target.level().isClientSide()) {
                if (CurioUtils.isEquipped(target, SilverSoul.class)) {
                    SoulAbilityData.SoulInfo SoulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(SilverSoul.class);
                    if (event.getBlocked() && SoulInfo.getCooldown() == 0 && SoulInfo.getStacks() > 0 && SoulInfo.getStacks() < (CurioUtils.isEquipped(target, TerraPower.class) ? 6 : 4)) {
                        SoulInfo.setMaxCooldown(20);
                        SoulInfo.setCooldown(SoulInfo.getMaxCooldown());
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
                if (CurioUtils.isEquipped(attacker, SilverSoul.class)) {
                    SoulAbilityData.SoulInfo SoulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(SilverSoul.class);
                    if (SoulInfo.getCooldown() > 0) {
                        event.setAmount(event.getAmount() * 1.5f);
                    }
                }
            }
        }

    }

}

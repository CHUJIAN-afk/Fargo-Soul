package First.fargo_soul.Item.Soul.TerraSoul.TerraPower.SoulStone;

import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.ParticleUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import static First.fargo_soul.Item.Soul.SoulsRegister.SilverSoul;
import static First.fargo_soul.Utils.CustomUtils.random;

public class SilverSoul extends SoulItem {
    public SilverSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.BLUE));
    }

    public static void SilverSoulTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SilverSoul.get()) && player.getAttribute(Attributes.ARMOR) instanceof AttributeInstance attributeInstance) {
            ResourceLocation resourceLocation = SilverSoul.getId();
            if (player.isBlocking()) {
                player.getPersistentData().putInt("SilverSoul", player.getPersistentData().getInt("SilverSoul") + 1);
                if (attributeInstance.getModifier(resourceLocation) == null) {
                    AttributeModifier modifier = new AttributeModifier(
                            resourceLocation,
                            10,
                            AttributeModifier.Operation.ADD_VALUE
                    );
                    attributeInstance.addPermanentModifier(modifier);
                }
            } else {
                player.getPersistentData().remove("SilverSoul");
                attributeInstance.removeModifier(resourceLocation);
            }
        }
    }

    public static void SilverSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SilverSoul.get()) && event.getEntity() instanceof LivingEntity) {
            if (player.getEffect(EffectRegister.AmazingMoment) != null && event.getSource().isDirect()) {
                player.removeEffect(EffectRegister.AmazingMoment);
                event.setAmount(event.getAmount() * 5.0f);
            }
        }
    }

    public static void SilverSoulDamageHandler2(LivingShieldBlockEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SilverSoul.get()) && event.getDamageSource().getEntity() instanceof LivingEntity livingEntity) {
            int SilverSoul = player.getPersistentData().getInt("SilverSoul");
            if (SilverSoul > 0 && SilverSoul < 20) {
                player.getCooldowns().addCooldown(player.getUseItem().getItem(), 10);
                player.getPersistentData().remove("SilverSoul");
                livingEntity.hurt(player.damageSources().playerAttack(player), event.getBlockedDamage() * 2.0f);
                player.addEffect(new MobEffectInstance(EffectRegister.AmazingMoment, 100, 0));
                player.serverLevel().playSound(
                        null,
                        player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ANVIL_PLACE,
                        SoundSource.PLAYERS,
                        1.0f,
                        random.nextFloat() * 0.4f + 0.4f
                );
                ParticleUtils.spawnParticleLine(
                        player.serverLevel(),
                        player.getBoundingBox().getCenter(),
                        livingEntity.getBoundingBox().getCenter(),
                        ParticleTypes.CRIT,
                        20,
                        0.1f
                );
                ParticleUtils.spawnParticleSphere(
                        player.serverLevel(),
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        ParticleTypes.CRIT,
                        1,
                        40,
                        0.1f
                );
            }
        }
    }




}

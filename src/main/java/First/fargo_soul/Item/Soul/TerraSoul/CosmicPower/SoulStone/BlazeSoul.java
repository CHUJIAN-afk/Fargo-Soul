package First.fargo_soul.Item.Soul.TerraSoul.CosmicPower.SoulStone;

import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.ParticleUtils;
import First.fargo_soul.Utils.CustomUtils;
import First.fargo_soul.Utils.SoulUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

public class BlazeSoul extends SoulItem {

    public BlazeSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.RED));
    }

    private long remainingTime = 0;
    private float energy = 0;
    private float maxEnergy = 0;
    private boolean isActivated = false;

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Tick(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                ResourceLocation resourceLocation = SoulsRegister.BlazeSoul.getId();
                Holder<Attribute> knockbackResistance = Attributes.KNOCKBACK_RESISTANCE;
                if (SoulUtils.getSoulItemFromSoulData(attacker, BlazeSoul.class) instanceof BlazeSoul blazeSoul) {
                    blazeSoul.maxEnergy = attacker.getMaxHealth() * 40;
                    AttributeUtils.ConditionAttributeModifier(
                            attacker,
                            knockbackResistance,
                            resourceLocation,
                            1.0,
                            AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
                            blazeSoul.isActivated
                    );
                    if (blazeSoul.isActivated) {
                        if (--blazeSoul.remainingTime < 0 || blazeSoul.energy < 0) {
                            blazeSoul.isActivated = false;
                            blazeSoul.energy = 0;
                        }
                    } else {
                        blazeSoul.remainingTime = 200;
                        if (blazeSoul.energy == blazeSoul.maxEnergy) {
                            blazeSoul.isActivated = true;
                        }
                    }
                } else {
                    AttributeUtils.removeAttributeModifier(attacker, knockbackResistance, resourceLocation);
                }
            }
        }

        @SubscribeEvent
        public static void Damage1(LivingIncomingDamageEvent event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                if (SoulUtils.getSoulItemFromSoulData(attacker, BlazeSoul.class) instanceof BlazeSoul blazeSoul) {
                    if (blazeSoul.isActivated) {
                        blazeSoul.energy -= 50;
                        List<LivingEntity> livingEntityList = SoulUtils.getNearbyLivingEntityList(target, 2);
                        livingEntityList.add(target);
                        livingEntityList.remove(attacker);
                        for (LivingEntity entity : livingEntityList) {
                            entity.addEffect(new MobEffectInstance(EffectRegister.Flare, 200));
                            entity.invulnerableTime = 0;
                            entity.hurt(attacker.damageSources().onFire(), 20);
                        }
                        Level level = attacker.level();
                        ParticleUtils.spawnParticleSphere(
                                (ServerLevel) level,
                                target.getX(),
                                target.getBoundingBox().getCenter().y(),
                                target.getZ(),
                                ParticleTypes.LAVA,
                                1f,
                                60,
                                0.5f
                        );
                        level.playSound(
                                null,
                                target.getX(),
                                target.getY(),
                                target.getZ(),
                                SoundEvents.GENERIC_EXPLODE,
                                SoundSource.PLAYERS,
                                1.0f,
                                CustomUtils.random.nextFloat() * 0.4f + 0.4f
                        );
                    } else {
                        blazeSoul.energy += event.getAmount() * 0.15f;
                        blazeSoul.energy = Math.min(blazeSoul.energy, blazeSoul.maxEnergy);
                    }
                }
            }
        }

        @SubscribeEvent
        public static void Damage2(LivingIncomingDamageEvent event) {
            if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
                if (SoulUtils.getSoulItemFromSoulData(target, BlazeSoul.class) instanceof BlazeSoul blazeSoul) {
                    float damage = blazeSoul.isActivated ? event.getAmount() * 0.5f : event.getAmount() * (1 - ((blazeSoul.energy / (blazeSoul.maxEnergy)) * 0.45f));
                    event.setAmount(damage);
                    if (blazeSoul.isActivated && event.getAmount() < target.getMaxHealth() * 0.08f) {
                        event.setCanceled(true);
                    }
                }
            }
        }

    }
}

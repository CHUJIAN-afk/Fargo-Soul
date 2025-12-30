package First.fargo_soul.item.terraSoul.cosmicPower;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.CosmicPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.utils.AttributeUtils;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.ParticleUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
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

    @EventBusSubscriber(modid = FargoSoul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Tick(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                ResourceLocation resourceLocation = ItemRegister.BlazeSoulItem.getId();
                Holder<Attribute> knockbackResistance = Attributes.KNOCKBACK_RESISTANCE;
                SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(BlazeSoul.class);
                AttributeUtils.ConditionAttributeModifier(
                        attacker,
                        knockbackResistance,
                        resourceLocation,
                        1.0,
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
                        soulInfo.isEnabled()
                );
                if (CurioUtils.isEquipped(attacker, BlazeSoul.class)) {
                    soulInfo.setMaxStacks((int) ((attacker.getMaxHealth() * 20) + 2000));
                    if (soulInfo.isEnabled()) {
                        soulInfo.shrinkStacks((int) (soulInfo.getMaxStacks() * 0.025));
                        if (soulInfo.getStacks() <= 0) {
                            soulInfo.setEnabled(false);
                        }
                    } else {
                        if (soulInfo.getStacks() >= soulInfo.getMaxStacks()) {
                            soulInfo.setEnabled(true);
                        }
                    }
                }
            }
        }

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
                if (CurioUtils.isEquipped(target, BlazeSoul.class)) {
                    SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(BlazeSoul.class);
                    float scale = soulInfo.isEnabled() ? 0.5f : (1 - (soulInfo.getStackPercentage() * 0.4f));
                    event.setAmount(event.getAmount() * scale);
                }
            }
            if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
                if (CurioUtils.isEquipped(target, BlazeSoul.class)) {
                    SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(BlazeSoul.class);
                    if (soulInfo.isEnabled()) {
                        event.setAmount(Math.max(event.getAmount() - target.getMaxHealth() * 0.1f, 0));
                    }
                }
            }
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                if (!attacker.equals(target) && CurioUtils.isEquipped(attacker, BlazeSoul.class) && SoulUtils.canAttack(BlazeSoul.class, target, target)) {
                    SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(BlazeSoul.class);
                    if (soulInfo.isEnabled()) {
                        soulInfo.shrinkStacks(100);
                        Level level = attacker.level();
                        List<LivingEntity> livingEntityList = level.getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(2));
                        livingEntityList.remove(attacker);
                        for (LivingEntity entity : livingEntityList) {
                            float amount = event.getAmount() * 2.75f + target.getMaxHealth() * 0.025f;
                            SoulUtils.attack(BlazeSoul.class, target, attacker, entity, DamageTypes.ON_FIRE, amount);
                        }
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
                        SoulUtils.playSound(
                                level,
                                target.position(),
                                SoundEvents.GENERIC_EXPLODE.value(),
                                SoundSource.PLAYERS
                        );
                    } else {
                        soulInfo.addStacks((int) (event.getAmount() * (CurioUtils.isEquipped(attacker, CosmicPower.class) ? 0.4f : 0.25f)));
                    }
                }
            }
        }

    }

}

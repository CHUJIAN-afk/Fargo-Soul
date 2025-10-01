package First.fargo_soul.Item.Soul.TerraSoul.CosmicPower.SoulStone;

import First.fargo_soul.Attachment.Attachment.SoulAbilityData;
import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Item.Soul.TerraSoul.CosmicPower.CosmicPower;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.ParticleUtils;
import First.fargo_soul.Utils.SoulUtils;
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

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Tick(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                ResourceLocation resourceLocation = SoulsRegister.BlazeSoul.getId();
                Holder<Attribute> knockbackResistance = Attributes.KNOCKBACK_RESISTANCE;
                SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(BlazeSoul.class);
                AttributeUtils.ConditionAttributeModifier(
                        attacker,
                        knockbackResistance,
                        resourceLocation,
                        1.0,
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
                        soulInfo.enabled
                );
                if (SoulUtils.isEquipped(attacker, BlazeSoul.class)) {
                    soulInfo.maxStacks = (int) ((attacker.getMaxHealth() * 20) + 2000);
                    if (soulInfo.enabled) {
                        soulInfo.shrinkStacks((int) (soulInfo.maxStacks * 0.025));
                        if (soulInfo.stacks <= 0) {
                            soulInfo.enabled = false;
                        }
                    } else {
                        if (soulInfo.stacks >= soulInfo.maxStacks) {
                            soulInfo.enabled = true;
                        }
                    }
                }
            }
        }

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
                if (SoulUtils.isEquipped(target, BlazeSoul.class)) {
                    SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(BlazeSoul.class);
                    float scale = soulInfo.enabled ? 0.5f : (1 - (soulInfo.getStackPercentage() * 0.4f));
                    event.setAmount(event.getAmount() * scale);
                }
            }
            if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
                if (SoulUtils.isEquipped(target, BlazeSoul.class)) {
                    SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(BlazeSoul.class);
                    if (soulInfo.enabled) {
                        event.setAmount(Math.max(event.getAmount() - target.getMaxHealth() * 0.1f, 0));
                    }
                }
            }
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                if (SoulUtils.isEquipped(attacker, BlazeSoul.class) && SoulUtils.canAttack(BlazeSoul.class, target, target)) {
                    SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(BlazeSoul.class);
                    if (soulInfo.enabled) {
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
                        soulInfo.stacks += (int) (event.getAmount() * (SoulUtils.isEquipped(attacker, CosmicPower.class) ? 0.4f : 0.25f));
                    }
                }
            }
        }

    }

}

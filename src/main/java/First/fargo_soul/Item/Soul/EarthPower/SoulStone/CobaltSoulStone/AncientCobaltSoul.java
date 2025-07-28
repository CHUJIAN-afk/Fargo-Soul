package First.fargo_soul.Item.Soul.EarthPower.SoulStone.CobaltSoulStone;

import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Item.Soul.SoulItem;
import First.fargo_soul.Item.Soul.Souls;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.MathUtils;
import First.fargo_soul.Utils.ParticleUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;


public class AncientCobaltSoul extends SoulItem {

    public AncientCobaltSoul(Properties properties) {
        super(properties);
    }


    public List<Component> AttributeList = List.of(
            Component.translatable("item.fargo_soul.ancient_cobalt_soul.attribute.1").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.ancient_cobalt_soul.attribute.2").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.ancient_cobalt_soul.attribute.3").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.ancient_cobalt_soul.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
    );

    @Override
    public List<Component> getAttributeList() {
        return this.AttributeList;
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, Item.TooltipContext context, ItemStack stack) {
        tooltips.addAll(AttributeList);
        tooltips.addAll(TooltipList);
        return tooltips;
    }

    public static void AncientCobaltSoulJumpHandler(LivingEvent.LivingJumpEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.AncientCobaltSoul.get())) {
            long AncientCobaltSoul = player.getPersistentData().getLong("AncientCobaltSoul");
            long TickCount = player.serverLevel().getGameTime();
            if (AncientCobaltSoul < TickCount) {
                player.getPersistentData().putLong("AncientCobaltSoul", TickCount + 30);
                List<LivingEntity> livingEntityList = player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(3));
                livingEntityList.removeIf(livingEntity1 -> livingEntity1.equals(player));
                for (LivingEntity entity : livingEntityList) {
                    entity.hurt(player.damageSources().playerAttack(player), 4);
                    entity.addEffect(new MobEffectInstance(EffectRegister.Oil, 200, 0));
                }
                ServerLevel level = player.serverLevel();
                ParticleUtils.spawnParticleSphere(
                        level,
                        player.getX(),
                        player.getBoundingBox().getCenter().y(),
                        player.getZ(),
                        ParticleTypes.EXPLOSION,
                        1f,
                        10,
                        0.2f
                );
                level.playSound(
                        null,
                        player.getX(), player.getY(), player.getZ(),
                        SoundEvents.GENERIC_EXPLODE,
                        SoundSource.PLAYERS,
                        1.0f,
                        MathUtils.random.nextFloat() * 0.4f + 0.4f
                );

            }
        }
    }

    public static void AncientCobaltSoulDamageHnadler(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity livingEntity && livingEntity.getEffect(EffectRegister.Oil) instanceof MobEffectInstance mobEffectInstance) {
            if (event.getSource().is(DamageTypes.ON_FIRE)) {
                float damage = event.getAmount();
                damage *= 1 + (mobEffectInstance.getAmplifier() + 1);
                event.setAmount(damage);
            }
        }
    }


    public static void AncientCobaltSoulTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.AncientCobaltSoul.get())) {
            if (player.getDeltaMovement().y() < 0) {
                player.setDeltaMovement(player.getDeltaMovement().add(0, -1, 0));
            }
        }
    }

}

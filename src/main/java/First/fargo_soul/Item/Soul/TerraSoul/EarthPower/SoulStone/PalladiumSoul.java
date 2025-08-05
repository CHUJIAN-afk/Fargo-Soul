package First.fargo_soul.Item.Soul.TerraSoul.EarthPower.SoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.ParticleUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

public class PalladiumSoul extends SoulItem {

    public PalladiumSoul(Properties properties) {
        super(properties);
    }

    public final List<Component> AttributeList = List.of(
            Component.translatable("item.fargo_soul.palladium_soul.attribute.1").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.palladium_soul.attribute.2").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.palladium_soul.attribute.3").withStyle(ChatFormatting.BLUE)
    );

    public final List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.palladium_soul.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
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

    public static void PalladiumSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.PalladiumSoul.get())) {
            if (event.getSource().getWeaponItem() != null) {
                long PalladiumSoulLastDamage = player.getPersistentData().getLong("PalladiumSoulLastDamage");
                long gameTime = player.serverLevel().getGameTime();
                if (PalladiumSoulLastDamage < gameTime) {
                    player.getPersistentData().putLong("PalladiumSoulLastDamage", gameTime + 5);
                    int duration = (int) (event.getAmount() * 0.35 * 20);
                    int amplifier = 0;
                    if (player.getEffect(MobEffects.REGENERATION) instanceof MobEffectInstance mobEffectInstance) {
                        duration += mobEffectInstance.getDuration();
                        amplifier = mobEffectInstance.getAmplifier();
                    }
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, Math.min(duration, 100), amplifier));
                }
            }
        }
    }

    public static void PalladiumSoulHealHandler(LivingHealEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.PalladiumSoul.get())) {
            float PalladiumSoul = player.getPersistentData().getFloat("PalladiumSoul");
            player.getPersistentData().putFloat("PalladiumSoul", PalladiumSoul + event.getAmount());
            if (PalladiumSoul > 8) {
                player.getPersistentData().putFloat("PalladiumSoul", 0);
                TargetingConditions conditions = TargetingConditions.forCombat().range(10.0);
                Monster monster = player.level().getNearestEntity(Monster.class, conditions, player, player.getX(), player.getY(), player.getZ(), player.getBoundingBox().inflate(10));
                if (monster != null) {
                    monster.hurt(player.damageSources().magic(), 8);
                    ParticleUtils.spawnParticleLine(
                            player.serverLevel(),
                            player.getEyePosition().add(0, 0.5, 0),
                            monster.position(),
                            ParticleTypes.HEART,
                            50,
                            0.0f
                    );
                }
            }
        }
    }


}

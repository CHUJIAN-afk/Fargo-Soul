package First.fargo_soul.Item.Soul.CosmicPower.SoulStone;

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
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class BlazeSoul extends SoulItem {

    public BlazeSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("攻击时积攒日耀能量，增加的日曜能量为伤害值的15%").withStyle(ChatFormatting.BLUE),
            Component.literal("日耀能量最大值为玩家最大生命值的2000%，根据积攒比例可提供最高20%伤害减免").withStyle(ChatFormatting.BLUE),
            Component.literal("攒满日耀能量获得日耀喷发能力，每次攻击会释放大型日耀喷发，同时消耗50日耀能量").withStyle(ChatFormatting.BLUE),
            Component.literal("大型日耀喷发会对范围内的所有敌人造成太阳耀斑减益并造成大量火焰伤害").withStyle(ChatFormatting.BLUE),
            Component.literal("太阳耀斑减益每秒造成敌人最大生命值1.6%的火焰伤害").withStyle(ChatFormatting.BLUE),
            Component.literal("太阳耀斑减益在结束时，产生日耀喷发，对附近的敌人造成火焰伤害").withStyle(ChatFormatting.BLUE),
            Component.literal("日曜喷发能力持续期间，免疫低于最大生命值8%的伤害，免疫击退").withStyle(ChatFormatting.BLUE),
            Component.literal("日耀喷发能力最多持续10秒，结束时清空日耀能量，或在日耀能量耗尽时提前结束").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“烫手魔石”").withStyle(ChatFormatting.DARK_GRAY)
    );

    @Override
    public List<Component> getAttributeList() {
        return this.AttributeList;
    }
    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
        tooltips.addAll(AttributeList);
        tooltips.addAll(TooltipList);
        return tooltips;
    }

    public static void BlazeSoulTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            if (player.getEffect(EffectRegister.SunburstEruption) instanceof MobEffectInstance mobEffectInstance && mobEffectInstance.getDuration() == 1) {
                player.getPersistentData().remove("BlazeSoul");
            }
        }
    }

    public static void BlazeSoulMobEffectExpiredHandler(MobEffectEvent.Expired event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            if (event.getEffectInstance() instanceof MobEffectInstance mobEffectInstance && mobEffectInstance.is(EffectRegister.SunburstEruption)) {
                player.getPersistentData().remove("BlazeSoul");
            }
        }
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            if (event.getEffectInstance() instanceof MobEffectInstance mobEffectInstance && mobEffectInstance.is(EffectRegister.Flare)) {
                List<LivingEntity> livingEntityList = livingEntity.level().getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(1));
                for (LivingEntity entity : livingEntityList) {
                    entity.hurt(livingEntity.damageSources().onFire(), 10);
                }
                if (livingEntity.level() instanceof ServerLevel level) {
                    ParticleUtils.spawnParticleSphere(
                            level,
                            livingEntity.getX(),
                            livingEntity.getBoundingBox().getCenter().y(),
                            livingEntity.getZ(),
                            ParticleTypes.LAVA,
                            1f,
                            30,
                            0.5f
                    );
                    level.playSound(
                            null,
                            livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(),
                            SoundEvents.GENERIC_EXPLODE,
                            SoundSource.PLAYERS,
                            1.0f,
                            MathUtils.random.nextFloat() * 0.4f + 0.4f
                    );
                }
            }
        }
    }

    public static void BlazeSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.BlazeSoul.get())) {
            if (event.getEntity() instanceof LivingEntity livingEntity && event.getSource().getWeaponItem() != null) {
                float BlazeSoul = player.getPersistentData().getFloat("BlazeSoul");
                if (player.getEffect(EffectRegister.SunburstEruption) == null) {
                    player.getPersistentData().putFloat("BlazeSoul", BlazeSoul + event.getAmount() * 0.15f);
                }
                //增加日曜能力
                if (BlazeSoul > player.getMaxHealth() * 20) {
                    player.addEffect(new MobEffectInstance(EffectRegister.SunburstEruption, 200));
                }
                //移除日曜能力
                if (BlazeSoul < 0) {
                    player.getPersistentData().remove("BlazeSoul");
                    player.removeEffect(EffectRegister.SunburstEruption);
                }
                if (player.getEffect(EffectRegister.SunburstEruption) != null) {
                    player.getPersistentData().putFloat("BlazeSoul", BlazeSoul - 50);
                    List<LivingEntity> livingEntityList = player.serverLevel().getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(2));
                    livingEntityList.removeIf(player::equals);
                    for (LivingEntity entity : livingEntityList) {
                        entity.addEffect(new MobEffectInstance(EffectRegister.Flare, 200));
                        entity.hurt(player.damageSources().onFire(), 20);
                        entity.invulnerableTime = 0;
                    }
                    ServerLevel level = player.serverLevel();
                    ParticleUtils.spawnParticleSphere(
                            level,
                            livingEntity.getX(),
                            livingEntity.getBoundingBox().getCenter().y(),
                            livingEntity.getZ(),
                            ParticleTypes.LAVA,
                            1f,
                            60,
                            0.5f
                    );
                    level.playSound(
                            null,
                            livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(),
                            SoundEvents.GENERIC_EXPLODE,
                            SoundSource.PLAYERS,
                            1.0f,
                            MathUtils.random.nextFloat() * 0.4f + 0.4f
                    );
                }
            }
        }
    }

    public static void BlazeSoulDamageHandler2(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.BlazeSoul.get())) {
            float BlazeSoul = player.getPersistentData().getFloat("BlazeSoul");
            float damage = event.getAmount() * (1 - ((BlazeSoul / (player.getMaxHealth() * 20)) * 0.2f));
            event.setAmount(damage);
            if (player.getEffect(EffectRegister.SunburstEruption) != null && event.getAmount() < player.getMaxHealth() * 0.08f) {
                event.setCanceled(true);
            }
        }
    }

}

package First.fargo_soul.Item.Soul.TerraSoul.CosmicPower.SoulStone;

import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.ParticleUtils;
import First.fargo_soul.Utils.CustomUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;


public class BlazeSoul extends SoulItem {

    public BlazeSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.RED));
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
                            CustomUtils.random.nextFloat() * 0.4f + 0.4f
                    );
                }
            }
        }
    }

    public static void BlazeSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.BlazeSoul.get())) {
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
                            CustomUtils.random.nextFloat() * 0.4f + 0.4f
                    );
                }
            }
        }
    }

    public static void BlazeSoulDamageHandler2(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.BlazeSoul.get())) {
            float BlazeSoul = player.getPersistentData().getFloat("BlazeSoul");
            float damage = event.getAmount() * (1 - ((BlazeSoul / (player.getMaxHealth() * 20)) * 0.2f));
            event.setAmount(damage);
            if (player.getEffect(EffectRegister.SunburstEruption) != null && event.getAmount() < player.getMaxHealth() * 0.08f) {
                event.setCanceled(true);
            }
        }
    }

}

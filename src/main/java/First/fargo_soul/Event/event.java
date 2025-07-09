package First.fargo_soul.Event;


import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.MathUtils;
import First.fargo_soul.Utils.ParticleUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

import static First.fargo_soul.Curios.CuriosRegister.*;

@EventBusSubscriber(modid = Fargo_soul.MODID, bus = EventBusSubscriber.Bus.GAME)
public class event {

    @SubscribeEvent
    public static void PearlWoodLivingIncomingDamageEventSource(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, PearlWoodSoul.get())) {
            if (event.getEntity() instanceof LivingEntity livingEntity && MathUtils.random.nextDouble() < 0.1) {
                float damage = event.getAmount();
                damage *= 1.5f;
                List<Monster> monsterList = livingEntity.level().getEntitiesOfClass(Monster.class, livingEntity.getBoundingBox().inflate(10), monster -> monster.equals(livingEntity));
                if (!monsterList.isEmpty()) {
                    Monster monster = monsterList.get(MathUtils.random.nextInt(monsterList.size()));
                    monster.hurt(player.damageSources().playerAttack(player), damage);
                    ParticleUtils.spawnParticleLine(
                            player.serverLevel(),
                            livingEntity.position(),
                            monster.position(),
                            ParticleTypes.CRIT,
                            2,
                            0.1f
                    );
                }
                event.setAmount(damage);
            }
            if (MathUtils.random.nextDouble() < 0.1 && player.invulnerableTime >= 5) {
                player.heal(player.getMaxHealth() * 0.02f);
            }
        }
    }

    @SubscribeEvent
    public static void PalmWoodLivingIncomingDamageEventSource(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, PalmWoodSoul.get()) && event.getEntity() instanceof LivingEntity livingEntity) {
            livingEntity.getPersistentData().putLong("PalmWoodSoul", player.server.getTickCount() + 10);
        }
        if (event.getEntity() instanceof LivingEntity livingEntity && event.getSource().is(DamageTypes.ON_FIRE)) {
            if (livingEntity.getServer() != null && livingEntity.getPersistentData().getLong("PalmWoodSoul") > livingEntity.getServer().getTickCount()) {
                event.setAmount(event.getAmount() * 1.5f);
            }
        }
    }
    @SubscribeEvent
    public static void ShadowWoodPlayerTickEventPost(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, ShadowWoodSoul.get())) {
            List<Monster> livingEntityList = player.level().getEntitiesOfClass(Monster.class, player.getBoundingBox().inflate(8));
            for (LivingEntity livingEntity : livingEntityList) {
                if (livingEntity.isAlive() && livingEntity.distanceTo(player) < 8.0f && MathUtils.random.nextDouble() < 0.05) {
                    livingEntity.hurt(player.damageSources().playerAttack(player), 1);
                    if (MathUtils.random.nextDouble() < 0.2) {
                        ParticleUtils.spawnParticleLine(
                                player.serverLevel(),
                                livingEntity.position(),
                                player.position(),
                                ParticleTypes.FLAME,
                                1,
                                0.1f
                        );
                        player.heal(1);
                    }
                }
            }
            ParticleUtils.spawnParticleSphere(
                    player.serverLevel(),
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    ParticleTypes.FLAME,
                    8.0f,
                    2,
                    0.1f
            );
        }
    }
    @SubscribeEvent//受到伤害
    public static void EbonyWoodLivingIncomingDamageEventBy(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, EbonyWoodSoul.get())) {
            int removeDamage = player.getPersistentData().getInt("EbonyWoodSoul") / 50;
            event.setAmount(event.getAmount() * (1 - (float) removeDamage / 100));
        }
    }
    @SubscribeEvent//造成伤害
    public static void EbonyWoodLivingIncomingDamageEventSource(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, EbonyWoodSoul.get())) {
            int addDamage = player.getPersistentData().getInt("EbonyWoodSoul") / 50;
            event.setAmount(event.getAmount() + addDamage);
        }
    }
    @SubscribeEvent//腐化值增加，光环粒子生成
    public static void EbonyWoodPlayerTickEventPost(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, EbonyWoodSoul.get())) {
            Level level = player.level();
            int size = level.getEntitiesOfClass(Monster.class, player.getBoundingBox().inflate(12.5)).size();
            if (size == 0) {
                player.getPersistentData().putInt("EbonyWoodSoul", Math.max(player.getPersistentData().getInt("EbonyWoodSoul") - 1, 0));
            }
            player.getPersistentData().putInt("EbonyWoodSoul", Math.min(player.getPersistentData().getInt("EbonyWoodSoul") + size, 250));
            ParticleUtils.spawnParticleSphere(
                    player.serverLevel(),
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    ParticleTypes.SOUL,
                    12.5f,
                    5,
                    0.1f
            );
        }
    }

    @SubscribeEvent
    public static void RosewoodSoul(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && !player.onGround() && CurioUtils.isEquipped(player, RosewoodSoul.get())) {
            float damage = event.getAmount();
            event.setAmount(damage * 0.9f);
            if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
                livingEntity.hurt(player.damageSources().playerAttack(player), damage * 0.5f);
                Vec3 delta = player.position().subtract(livingEntity.position()).normalize();
                livingEntity.addDeltaMovement(delta);
            }
        }
    }

    @SubscribeEvent
    public static void PineWoodSoul(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, PineWoodSoul.get()) && player.tickCount % 20 == 0) {
            TargetingConditions conditions = TargetingConditions.forCombat().range(30.0);
            Monster monster = player.level().getNearestEntity(Monster.class, conditions, player, player.getX(), player.getY(), player.getZ(), player.getBoundingBox().inflate(10));
            if (monster != null) {
                Snowball snowball = new Snowball(
                        player.level(),
                        player.getX(),
                        player.getY() + 2.0,
                        player.getZ()
                );
                double rand = 0.5 - MathUtils.random.nextDouble(1);
                Vec3 toMonster = monster.position().add(rand * MathUtils.random.nextDouble(), rand * MathUtils.random.nextDouble() - 1, rand * MathUtils.random.nextDouble()).subtract(player.getX(), player.getY(), player.getZ()).normalize();
                snowball.shoot(
                        toMonster.x,
                        toMonster.y,
                        toMonster.z,
                        2.0f,
                        1.0f
                );
                snowball.setOwner(player);
                player.level().addFreshEntity(snowball);
                ParticleUtils.spawnParticleSphere(
                        player.serverLevel(),
                        player.getX(),
                        player.getY() + 2.0,
                        player.getZ(),
                        ParticleTypes.ITEM_SNOWBALL,
                        0.2f,
                        5,
                        0.2f
                );
            }
        }
    }

}

package First.fargo_soul.Event;


import First.fargo_soul.Curios.Souls;
import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.MathUtils;
import First.fargo_soul.Utils.ParticleUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

import java.util.List;

import static First.fargo_soul.Curios.Souls.*;

@EventBusSubscriber(modid = Fargo_soul.MODID, bus = EventBusSubscriber.Bus.GAME)
public class event {

    @SubscribeEvent
    public static void ObsidianSoulLivingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, ObsidianSoul.get())) {
            if (event.getSource().type().msgId().contains("fire")) {
                event.setCanceled(true);
            }
            if (event.getSource().is(DamageTypes.LAVA)) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void AshWoodSoulLivingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, AshWood.get())) {
            if (event.getSource().is(DamageTypes.LAVA)) {
                event.setAmount(event.getAmount() * 0.25f);
            }
        }
    }

    @SubscribeEvent
    public static void AshWoodSoulPlayerTickEventPost(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, AshWood.get()) && player.tickCount % 40 == 0) {
            TargetingConditions conditions = TargetingConditions.forCombat().range(30.0);
            Monster monster = player.level().getNearestEntity(Monster.class, conditions, player, player.getX(), player.getY(), player.getZ(), player.getBoundingBox().inflate(10));
            if (monster != null) {
                double rand = 0.5 - MathUtils.random.nextDouble(1);
                Vec3 toMonster = monster.position().add(rand * MathUtils.random.nextDouble(), rand * MathUtils.random.nextDouble() - 1, rand * MathUtils.random.nextDouble()).subtract(player.getX(), player.getY(), player.getZ()).normalize();
                SmallFireball fireball = new SmallFireball(
                        player.level(),
                        player.getX(),
                        player.getY() + 2.0,
                        player.getZ(),
                        toMonster
                );
                fireball.setOwner(player);
                player.level().addFreshEntity(fireball);
                ParticleUtils.spawnParticleSphere(
                        player.serverLevel(),
                        player.getX() + rand * MathUtils.random.nextDouble(),
                        player.getY() + 2.0,
                        player.getZ() + rand * MathUtils.random.nextDouble(),
                        ParticleTypes.FLAME,
                        0.2f,
                        5,
                        0.2f
                );
            }
        }
    }

    @SubscribeEvent
    public static void TungstenSoulLivingIncomingDamageEventSource(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, TungstenSoul.get()) && event.getEntity() instanceof LivingEntity livingEntity) {
            if (player.getPersistentData().getLong("TungstenSoul") > player.server.getTickCount()) return;
            player.getPersistentData().putLong("TungstenSoul", player.server.getTickCount() + 50);
            List<LivingEntity> livingEntityList = livingEntity.level().getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(3));
            livingEntityList.removeIf(livingEntity1 -> livingEntity1.equals(player));
            for (LivingEntity entity : livingEntityList) {
                entity.hurt(player.damageSources().playerAttack(player), event.getAmount() * 0.5f);
            }
            ServerLevel level = player.serverLevel();
            ParticleUtils.spawnParticleSphere(
                    level,
                    livingEntity.getX(),
                    livingEntity.getBoundingBox().getCenter().y(),
                    livingEntity.getZ(),
                    ParticleTypes.EXPLOSION,
                    (float) 3,
                    livingEntityList.size() * 2,
                    0.2f
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


    @SubscribeEvent
    public static void TungstenSoulCurioChangeEvent(CurioChangeEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && player.getAttribute(Attributes.ENTITY_INTERACTION_RANGE) instanceof AttributeInstance attributeInstance) {
            ResourceLocation resourceLocation = TungstenSoul.getId();
            if (CurioUtils.isEquipped(player, TungstenSoul.get()) && attributeInstance.getModifier(resourceLocation) == null) {
                AttributeModifier modifier = new AttributeModifier(
                        resourceLocation,
                        5.0,
                        AttributeModifier.Operation.ADD_VALUE
                );
                attributeInstance.addPermanentModifier(modifier);
            } else {
                attributeInstance.removeModifier(resourceLocation);
            }
        }
    }

    @SubscribeEvent
    public static void SilverSoulRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SilverSoul.get()) && player.getAttribute(Attributes.ARMOR) instanceof AttributeInstance attributeInstance) {
            ResourceLocation resourceLocation = SilverSoul.getId();
            if (event.getItemStack().getItem() instanceof ShieldItem && player.getAttribute(Attributes.ARMOR) != null) {
                AttributeModifier modifier = new AttributeModifier(
                        resourceLocation,
                        5.0,
                        AttributeModifier.Operation.ADD_VALUE
                );
                attributeInstance.addPermanentModifier(modifier);
                player.getPersistentData().putInt("SilverSoul", player.getPersistentData().getInt("SilverSoul") + 1);
            } else {
                player.getPersistentData().remove("SilverSoul");
                attributeInstance.removeModifier(resourceLocation);
            }
        }
    }

    @SubscribeEvent//造成伤害
    public static void SilverSoulLivingIncomingDamageEventSource(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SilverSoul.get()) && event.getEntity() instanceof LivingEntity livingEntity) {
            if (player.getEffect(EffectRegister.AmazingMomentEffect) != null && event.getSource().isDirect()) {
                player.removeEffect(EffectRegister.AmazingMomentEffect);
                event.setAmount(event.getAmount() * 5.0f);
            }
        }
    }

    @SubscribeEvent//受到伤害
    public static void SilverSoulLivingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SilverSoul.get()) && event.getSource().getEntity() instanceof LivingEntity livingEntity) {
            int SilverSoul = player.getPersistentData().getInt("SilverSoul");
            if (player.isBlocking() && SilverSoul > 10 && SilverSoul < 30) {
                player.getPersistentData().remove("SilverSoul");
                livingEntity.hurt(player.damageSources().playerAttack(player), event.getAmount() * 2.0f);
                player.addEffect(new MobEffectInstance(EffectRegister.AmazingMomentEffect, 100, 0));
            }
        }
    }

    @SubscribeEvent//造成伤害
    public static void LeadSoulLivingIncomingDamageEventSource(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, LeadSoul.get()) && event.getEntity() instanceof LivingEntity livingEntity) {
            if (MathUtils.random.nextDouble() < 0.1) {
                livingEntity.addEffect(new MobEffectInstance(EffectRegister.LeadPoisoningEffect, 200, 0));
            }
        }
    }

    @SubscribeEvent//受到伤害
    public static void LeadSoulLivingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, LeadSoul.get()) && event.getSource().getEntity() instanceof LivingEntity livingEntity) {
            LivingEntity lastHurtByMob = player.getLastHurtByMob();
            if (lastHurtByMob != null && lastHurtByMob.equals(livingEntity)) {
                event.setAmount(event.getAmount() * 0.9f);
            }
        }
    }

    @SubscribeEvent
    public static void IronSoulItemEntityPickupEvent(ItemEntityPickupEvent.Post event) {
        if (event.getPlayer() instanceof ServerPlayer player && CurioUtils.isEquipped(player, IronSoul.get())) {
            player.getPersistentData().putLong("IronSoul", player.server.getTickCount() + 100);
        }
    }

    @SubscribeEvent
    public static void IronSoulLivingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, IronSoul.get())) {
            if (player.getPersistentData().getLong("IronSoul") > player.server.getTickCount()) {
                event.setAmount(event.getAmount() * 0.8f);
            }
        }
    }

    @SubscribeEvent
    public static void IronSoulPlayerTickEvent(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, IronSoul.get())) {
            List<ItemEntity> itemEntityList = player.serverLevel().getEntitiesOfClass(ItemEntity.class, player.getBoundingBox().inflate(5));
            for (ItemEntity itemEntity : itemEntityList) {
                double factor = (5 - itemEntity.distanceTo(player)) / 5;
                Vec3 delta = player.position().subtract(itemEntity.position()).normalize().scale(factor);
                itemEntity.addDeltaMovement(delta);
            }
        }
    }

    @SubscribeEvent//受到伤害
    public static void TinSoulLivingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            if (!CurioUtils.isEquipped(player, Souls.TinSoul.get())) {
                player.getPersistentData().remove("TinSoul");
                return;
            }
            double TinSoul = player.getPersistentData().getDouble("TinSoul");
            TinSoul *= 0.5;
            if (TinSoul < 0.05) {
                TinSoul = 0.05;
            }
            player.getPersistentData().putDouble("TinSoul", TinSoul);
        }
    }

    @SubscribeEvent//造成伤害
    public static void TinSoulLivingIncomingDamageEventSource(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.TinSoul.get())) {
            double TinSoul = player.getPersistentData().getDouble("TinSoul");
            if (TinSoul == 0) {
                TinSoul = 0.05;
            }
            if (MathUtils.random.nextDouble() < TinSoul && event.getEntity() instanceof LivingEntity livingEntity) {
                TinSoul += 0.05;
                player.getPersistentData().putDouble("TinSoul", TinSoul);
                event.setAmount(event.getAmount() * 2.5f);
                ParticleUtils.spawnParticleSphere(
                        player.serverLevel(),
                        livingEntity.getX(),
                        livingEntity.getY(),
                        livingEntity.getZ(),
                        ParticleTypes.CRIT,
                        0.5f,
                        8,
                        0.5f,
                        0.01f
                );
            }
        }
    }

    @SubscribeEvent
    public static void CopperSoulLivingIncomingDamageEventSource(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, CopperSoul.get())) {
            long serverTickCount = player.server.getTickCount();
            long LastCopperSoul = player.getPersistentData().getLong("LastCopperSoul");
            if (event.getEntity() instanceof LivingEntity livingEntity && serverTickCount > LastCopperSoul) {
                double random = 0.1;
                if ((player.level().canSeeSky(livingEntity.blockPosition()) && player.level().isRaining() || livingEntity.isInWater())) {
                    random = 0.2;
                }
                if (MathUtils.random.nextDouble() < random) {
                    LastCopperSoul = serverTickCount + 100;
                    player.getPersistentData().putLong("LastCopperSoul", LastCopperSoul);
                    livingEntity.hurt(player.damageSources().lightningBolt(), 10);
                    ParticleUtils.spawnParticleLine(
                            player.serverLevel(),
                            player.getEyePosition().add(0, 0.5, 0),
                            livingEntity.getEyePosition(),
                            ParticleTypes.ELECTRIC_SPARK,
                            5,
                            0.1f
                    );
                }
            }
        }
    }

    @SubscribeEvent
    public static void PearlWoodLivingIncomingDamageEventSource(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, PearlWoodSoul.get())) {
            if (event.getEntity() instanceof LivingEntity livingEntity && MathUtils.random.nextDouble() < 0.1) {
                float damage = event.getAmount();
                damage *= 1.5f;
                List<Monster> monsterList = livingEntity.level().getEntitiesOfClass(Monster.class, livingEntity.getBoundingBox().inflate(10), monster -> !monster.equals(livingEntity));
                if (!monsterList.isEmpty()) {
                    Monster monster = monsterList.get(MathUtils.random.nextInt(monsterList.size()));
                    monster.hurt(player.damageSources().playerAttack(player), damage);
                    ParticleUtils.spawnParticleLine(
                            player.serverLevel(),
                            livingEntity.getEyePosition(),
                            monster.getEyePosition(),
                            ParticleTypes.ENCHANT,
                            5,
                            0.1f
                    );
                }
                event.setAmount(damage);
            }
            if (MathUtils.random.nextDouble() < 0.1) {
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
        if (event.getEntity() instanceof ServerPlayer player) {
            if (!CurioUtils.isEquipped(player, EbonyWoodSoul.get())) {
                player.getPersistentData().putInt("EbonyWoodSoul", 0);
                return;
            }
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

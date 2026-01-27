package First.fargo_soul.common.event.neoForgeEvent;


import First.fargo_soul.FargoSoul;
import First.fargo_soul.common.attachment.SoulAbilityData;
import First.fargo_soul.common.blcokEntity.CosmicCrucibleBlockEntity;
import First.fargo_soul.common.dataComponents.SoulRarity;
import First.fargo_soul.common.item.TerraSoul;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.config.ServerSoulConfig;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.register.AttributeRegister;
import First.fargo_soul.utils.AttributeUtils;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EventBusSubscriber(modid = FargoSoul.MODID)
public class Event {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void colorChange(ServerTickEvent.Pre event) {
        SoulRarity expert = SoulRarity.EXPERT;
        if (expert.getColor() == -1) expert.setColor(0xFF0000);
        int expertColor = expert.getColor();
        int er = (expertColor >> 16) & 0xFF;
        int eg = (expertColor >> 8) & 0xFF;
        int eb = expertColor & 0xFF;
        int ediscoStyle = (expertColor >> 24) & 0xFF;
        switch (ediscoStyle) {
            case 0: if (eg < 255) eg = Math.min(eg + 7, 255); if (eg == 255) { er = 248; ediscoStyle = 1; } break;
            case 1: if (er > 0) er = Math.max(er - 7, 0); if (er == 0) { eb = 7; ediscoStyle = 2; } break;
            case 2: if (eb < 255) eb = Math.min(eb + 7, 255); if (eb == 255) { eg = 248; ediscoStyle = 3; } break;
            case 3: if (eg > 0) eg = Math.max(eg - 7, 0); if (eg == 0) { er = 7; ediscoStyle = 4; } break;
            case 4: if (er < 255) er = Math.min(er + 7, 255); if (er == 255) { eb = 248; ediscoStyle = 5; } break;
            case 5: if (eb > 0) eb = Math.max(eb - 7, 0); if (eb == 0) ediscoStyle = 0; break;
        }
        expert.setColor((ediscoStyle << 24) | (er << 16) | (eg << 8) | eb);

        SoulRarity master = SoulRarity.MASTER;
        if (master.getColor() == -2) master.setColor(0xFF0000);
        int masterColor = master.getColor();
        int mr = (masterColor >> 16) & 0xFF;
        int mg = (masterColor >> 8) & 0xFF;
        int mb = masterColor & 0xFF;
        int mdiscoStyle = (masterColor >> 24) & 0xFF;
        int speed = 14;
        switch (mdiscoStyle) {
            case 0: if (mg < 255) mg = Math.min(mg + speed, 255); if (mg == 255) { mr = 241; mdiscoStyle = 1; } break;
            case 1: if (mr > 0) mr = Math.max(mr - speed, 0); if (mr == 0) { mb = 14; mdiscoStyle = 2; } break;
            case 2: if (mb < 255) mb = Math.min(mb + speed, 255); if (mb == 255) { mg = 241; mdiscoStyle = 3; } break;
            case 3: if (mg > 0) mg = Math.max(mg - speed, 0); if (mg == 0) { mr = 14; mdiscoStyle = 4; } break;
            case 4: if (mr < 255) mr = Math.min(mr + speed, 255); if (mr == 255) { mb = 241; mdiscoStyle = 5; } break;
            case 5: if (mb > 0) mb = Math.max(mb - speed, 0); if (mb == 0) mdiscoStyle = 0; break;
        }
        master.setColor((mdiscoStyle << 24) | (mr << 16) | (mg << 8) | mb);
    }

    @SubscribeEvent
    public static void soulDrop(LivingDropsEvent event) {
        SoulUtils.RegisterSoulList.forEach(soulItem -> soulItem.drop(event));
    }

    @SubscribeEvent
    public static void soulShieldBlock(LivingShieldBlockEvent event) {
        SoulUtils.RegisterSoulList.forEach(soulItem -> soulItem.shieldBlock(event));
    }

    @SubscribeEvent
    public static void soulItemUseFinish(LivingEntityUseItemEvent.Finish event) {
        SoulUtils.RegisterSoulList.forEach(soulItem -> soulItem.itemUseFinish(event));
    }

    @SubscribeEvent
    public static void soulPickup(ItemEntityPickupEvent.Post event) {
        SoulUtils.RegisterSoulList.forEach(soulItem -> soulItem.pickup(event));
    }

    @SubscribeEvent
    public static void soulCriticalHit(CriticalHitEvent event) {
        SoulUtils.RegisterSoulList.forEach(soulItem -> soulItem.criticalHit(event));
    }

    @SubscribeEvent
    public static void soulHeal(LivingHealEvent event) {
        SoulUtils.RegisterSoulList.forEach(soulItem -> soulItem.heal(event));
    }

    @SubscribeEvent
    public static void soulTargetChange(LivingChangeTargetEvent event) {
        SoulUtils.RegisterSoulList.forEach(soulItem -> soulItem.targetChange(event));
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void soulDeath(LivingDeathEvent event) {
        for (SoulItem soulItem : SoulUtils.RegisterSoulList) {
            if (!event.isCanceled()) {
                soulItem.death(event);
            }
        }
    }

    @SubscribeEvent
    public static void soulApplicable(MobEffectEvent.Applicable event) {
        SoulUtils.RegisterSoulList.forEach(soulItem -> soulItem.effectApplicable(event));
    }

    @SubscribeEvent
    public static void soulTick(EntityTickEvent.Post event) {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            for (SoulItem soulItem : SoulUtils.RegisterSoulList) {
                soulItem.tick(livingEntity);
            }
        }
    }

    @SubscribeEvent
    public static void soulDamage(LivingIncomingDamageEvent event) {
        if (event.getEntity() != event.getSource().getEntity()) {
            for (SoulItem soulItem : SoulUtils.RegisterSoulList) {
                soulItem.hurt(event);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void attributeDamage(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            event.setAmount((float) (event.getAmount() * (1 + attacker.getAttributeValue(AttributeRegister.Damage))));
        }
    }

    @SubscribeEvent
    public static void EntityAttributeModificationEvent(EntityAttributeModificationEvent event) {
        event.getTypes().forEach(entityType -> {
            event.add(entityType, AttributeRegister.CriticalChance);
            event.add(entityType, AttributeRegister.CriticalDamage);
            event.add(entityType, AttributeRegister.ArmorPierce);
            event.add(entityType, AttributeRegister.Damage);
        });
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        CosmicCrucibleBlockEntity.registerCapabilities(event);
    }

    @SubscribeEvent
    public static void mobSpawn(FinalizeSpawnEvent event) {
        ServerLevelAccessor level = event.getLevel();
        MobSpawnType spawnType = event.getSpawnType();
        boolean c1 = spawnType.equals(MobSpawnType.NATURAL);
        boolean c2 = ServerSoulConfig.AllowCreaturesThatSpawnThroughUnnaturalPathsToCarrySouls.get();
        if (!level.isClientSide() && !spawnType.equals(MobSpawnType.CHUNK_GENERATION) && (c1 || c2)) {
            List<SoulItem> RegisterSoulList = SoulUtils.RegisterSoulList;
            Mob mob = event.getEntity();
            boolean condition1 = (mob instanceof Enemy || !mob.getType().getCategory().isFriendly()) && ServerSoulConfig.AllowHostileMobSoul.get();
            boolean condition2 = mob.getType().getCategory().isFriendly() && ServerSoulConfig.AllowFriendlyMobSoul.get();
            RandomSource random = mob.getRandom();
            SoulAbilityData.SoulInfo soulInfo = mob.getData(AttachmentRegister.SoulAbilityData).getSoulInfo("mob_soul");
            if ((condition1 || condition2) && random.nextDouble() < ServerSoulConfig.SoulChance.get() && !soulInfo.isEnabled()) {
                soulInfo.setEnabled(c1);
                Set<SoulItem> soulItems = new HashSet<>();
                for (int i = 0; i < 4; i++) {
                    soulItems.add(RegisterSoulList.get(random.nextInt(RegisterSoulList.size())));
                }
                List<SoulItem> soulItemList = soulItems.stream().toList();
                List<SoulItem> soulFromList = CurioUtils.getSoulFromList(soulItemList);
                if (!soulFromList.isEmpty()) {
                    mob.getData(AttachmentRegister.SoulListData).setSoulItemList(soulFromList);
                    mob.syncData(AttachmentRegister.SoulListData);
                    soulInfo.setMaxStacks(soulFromList.size());
                    soulInfo.setStacks(soulFromList.size());
                    AttributeUtils.condition(
                            mob,
                            Attributes.MAX_HEALTH,
                            ResourceLocation.fromNamespaceAndPath(FargoSoul.MODID, "soul_health_add"),
                            soulInfo.getStacks() * ServerSoulConfig.SoulHpIncreasePerSoul.get(),
                            AttributeModifier.Operation.ADD_VALUE,
                            soulInfo.isEnabled()
                    );
                    AttributeUtils.condition(
                            mob,
                            Attributes.MAX_HEALTH,
                            ResourceLocation.fromNamespaceAndPath(FargoSoul.MODID, "soul_health_total"),
                            soulInfo.getStacks() * ServerSoulConfig.SoulHpMultiplierPerSoul.get(),
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
                            soulInfo.isEnabled()
                    );
                    AttributeUtils.condition(
                            mob,
                            Attributes.SCALE,
                            ResourceLocation.fromNamespaceAndPath(FargoSoul.MODID, "soul_scale"),
                            soulInfo.getStacks() * ServerSoulConfig.SoulSizeMultiplierPerSoul.get(),
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
                            soulInfo.isEnabled()
                    );
                    mob.heal(mob.getMaxHealth());
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void drop(LivingDropsEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Mob mob && !mob.level().isClientSide()) {
            SoulAbilityData.SoulInfo soulInfo = mob.getData(AttachmentRegister.SoulAbilityData).getSoulInfo("mob_soul");
            if (soulInfo.isEnabled() || ServerSoulConfig.AllowCreaturesThatSpawnThroughUnnaturalPathsToDropSouls.get()) {
                Level level = mob.level();
                double chance = ServerSoulConfig.SoulDropChance.get();
                RandomSource random = mob.getRandom();
                Collection<ItemEntity> drops = event.getDrops();
                List<SoulItem> itemList = mob.getData(AttachmentRegister.SoulListData).getSoulItemList();
                if (!itemList.isEmpty()) {
                    itemList.stream()
                            .filter(soulItem -> random.nextDouble() < chance)
                            .map(soulItem -> new ItemEntity(
                                    level,
                                    mob.getX(),
                                    mob.getY(),
                                    mob.getZ(),
                                    soulItem.getDefaultInstance()
                            ))
                            .forEach(drops::add);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void hurt(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() == event.getEntity() && CurioUtils.isEquipped(event.getEntity(), TerraSoul.class)) {
            event.setCanceled(true);
            return;
        }
        if (event.getSource().getDirectEntity() instanceof Projectile projectile && !projectile.level().isClientSide()) {
            if (projectile.getData(AttachmentRegister.SoulAbilityData).getSoulInfo("noInvulnerable").isEnabled()) {
                if (event.getEntity() == projectile.getOwner()) {
                    event.setCanceled(true);
                    return;
                }
                event.getEntity().invulnerableTime = 0;
            }
        }
    }

    @SubscribeEvent
    public static void in(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        if (player.getMainHandItem().is(Items.DEBUG_STICK) && event.getHand().equals(InteractionHand.MAIN_HAND)) {
            if (event.getTarget() instanceof LivingEntity target) {
                if (player.getOffhandItem().getItem() instanceof SoulItem soulItem) {
                    target.getData(AttachmentRegister.SoulListData).getSoulItemList().addAll(CurioUtils.getSoulFromSoul(soulItem));
                    target.syncData(AttachmentRegister.SoulListData);
                }
                if (player.getOffhandItem().isEmpty()) {
                    target.getData(AttachmentRegister.SoulListData).getSoulItemList().removeIf(soulItem -> true);
                    target.syncData(AttachmentRegister.SoulListData);
                }
            }
        }
    }

}

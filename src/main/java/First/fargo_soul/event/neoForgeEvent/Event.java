package First.fargo_soul.event.neoForgeEvent;


import First.fargo_soul.FargoSoul;
import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.blcokEntity.CosmicCrucibleBlockEntity;
import First.fargo_soul.config.ServerSoulConfig;
import First.fargo_soul.event.modEvent.AddItemTagEvent;
import First.fargo_soul.item.TerraSoul;
import First.fargo_soul.item.base.SoulItem;
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
import net.minecraft.world.item.Item;
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
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EventBusSubscriber(modid = FargoSoul.MODID)
public class Event {

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
            SoulUtils.AttributeSoulList.forEach(soulItem -> soulItem.getAttributeModifiers().forEach((attribute, attributeModifier) -> AttributeUtils.condition(livingEntity, attribute, attributeModifier.id(), attributeModifier.amount(), attributeModifier.operation(), CurioUtils.isEquipped(livingEntity, soulItem.getClass()))));
            SoulUtils.RegisterSoulList.forEach(soulItem -> soulItem.tick(livingEntity));
        }
    }

    @SubscribeEvent
    public static void soulDamage(LivingIncomingDamageEvent event) {
        if (event.getEntity() != event.getSource().getEntity()) {
            SoulUtils.RegisterSoulList.forEach(soulItem -> soulItem.hurt(event));
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
    public static void addTags(AddItemTagEvent event) {
        List<Item> list = SoulUtils.RegisterSoulList.stream().map(Item::asItem).toList();
        list.forEach(item -> event.add(ResourceLocation.fromNamespaceAndPath(CuriosApi.MODID, "soul"), item));
        list.forEach(item -> event.add(ResourceLocation.fromNamespaceAndPath(CuriosApi.MODID, "accessory"), item));
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

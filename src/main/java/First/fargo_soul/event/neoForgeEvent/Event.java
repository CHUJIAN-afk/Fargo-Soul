package First.fargo_soul.event.neoForgeEvent;


import First.fargo_soul.FargoSoul;
import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.blcokEntity.CosmicCrucibleBlockEntity;
import First.fargo_soul.config.ServerSoulConfig;
import First.fargo_soul.event.modEvent.AddItemTagEvent;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.willPower.RedRidingSoul;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.utils.AttributeUtils;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static First.fargo_soul.event.neoForgeEvent.ClientEvent.getSprintList;

@EventBusSubscriber(modid = FargoSoul.MODID)
public class Event {

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
                    AttributeUtils.ConditionAttributeModifier(
                            mob,
                            Attributes.MAX_HEALTH,
                            ResourceLocation.fromNamespaceAndPath(FargoSoul.MODID, "soul_health_add"),
                            soulInfo.getStacks() * ServerSoulConfig.SoulHpIncreasePerSoul.get(),
                            AttributeModifier.Operation.ADD_VALUE,
                            soulInfo.isEnabled()
                    );
                    AttributeUtils.ConditionAttributeModifier(
                            mob,
                            Attributes.MAX_HEALTH,
                            ResourceLocation.fromNamespaceAndPath(FargoSoul.MODID, "soul_health_total"),
                            soulInfo.getStacks() * ServerSoulConfig.SoulHpMultiplierPerSoul.get(),
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
                            soulInfo.isEnabled()
                    );
                    AttributeUtils.ConditionAttributeModifier(
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

    @SubscribeEvent
    public static void mobsterSprint(EntityTickEvent.Post event) {
        if (event.getEntity() instanceof Mob mob && !mob.level().isClientSide()) {
            SoulAbilityData soulAbilityData = SoulAbilityData.getSoulAbilityData(mob);
            SoulAbilityData.SoulInfo soulInfo = soulAbilityData.getSoulInfo("Sprint");
            if (CurioUtils.isEquipped(mob, getSprintList())) {
                soulInfo.setMaxCooldown(100);
                if (mob.getTarget() instanceof LivingEntity target && soulInfo.isReady()) {
                    soulInfo.setCooldown(soulInfo.getMaxCooldown());
                    mob.getLookControl().setLookAt(target);
                    double factor = 1.5;
                    if (CurioUtils.isEquipped(mob, RedRidingSoul.class)) {
                        SoulAbilityData.SoulInfo info = soulAbilityData.getSoulInfo(RedRidingSoul.class);
                        if (info.getStacks() == info.getMaxStacks()) {
                            factor *= 1.5f;
                        }
                    }
                    mob.addDeltaMovement(mob.getLookAngle().scale(factor));
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void hurt(LivingIncomingDamageEvent event) {
        if (event.getSource().getDirectEntity() instanceof Projectile projectile && !projectile.level().isClientSide()) {
            if (projectile.getData(AttachmentRegister.SoulAbilityData).getSoulInfo("noInvulnerable").isEnabled()) {
                event.getEntity().invulnerableTime = 0;
            }
        }
    }


}

package first.fargo_soul.common.attachment;

import first.fargo_soul.register.FargoSoulAttachmentRegister;
import first.lyra.common.attachment.ImmunityData;
import first.lyra.register.LyraAttachmentRegister;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Targeting;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/** 服务端目标缓存，按需每 tick 更新一次并由仆从共享。 */
public class SoulTargetCache {

    public static SoulTargetCache get(Player player) {
        SoulTargetCache cache = player.getData(FargoSoulAttachmentRegister.SOUL_TARGET_CACHE);
        if (cache.level != player.level() || cache.lastTick != player.tickCount) {
            cache.tick(player);
        }
        return cache;
    }

    private final Long2ObjectOpenHashMap<List<LivingEntity>> spatialGroups = new Long2ObjectOpenHashMap<>();
    private Level level;
    private int lastTick;

    /**
     * 以指定位置和半径查询实体（走空间分组，不做全量遍历），支持过滤条件。
     *
     * @param pos      查询中心坐标
     * @param radius   查询半径
     * @param filter   额外过滤条件，可为 null
     * @return 半径内且满足过滤条件的存活实体列表
     */
    public List<LivingEntity> getEntitiesInRadius(Vec3 pos, float radius, Predicate<LivingEntity> filter) {
        List<LivingEntity> result = new ArrayList<>();
        if (radius <= 0) {
            return result;
        }
        double radiusSq = radius * radius;
        int minCellX = Mth.floor(pos.x - radius) >> 4;
        int maxCellX = Mth.floor(pos.x + radius) >> 4;
        int minCellY = Mth.floor(pos.y - radius) >> 4;
        int maxCellY = Mth.floor(pos.y + radius) >> 4;
        int minCellZ = Mth.floor(pos.z - radius) >> 4;
        int maxCellZ = Mth.floor(pos.z + radius) >> 4;
        for (int cellX = minCellX; cellX <= maxCellX; cellX++) {
            for (int cellY = minCellY; cellY <= maxCellY; cellY++) {
                for (int cellZ = minCellZ; cellZ <= maxCellZ; cellZ++) {
                    List<LivingEntity> cell = spatialGroups.get(SectionPos.asLong(cellX, cellY, cellZ));
                    if (cell == null) {
                        continue;
                    }
                    for (LivingEntity entity : cell) {
                        if (entity.isAlive() && entity.getBoundingBox().getCenter().distanceToSqr(pos) <= radiusSq) {
                            if (filter == null || filter.test(entity)) {
                                result.add(entity);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    public float getDistance(LivingEntity living1, LivingEntity living2) {
        return (float) living1.getBoundingBox().getCenter().distanceTo(living2.getBoundingBox().getCenter());
    }

    /**
     * 更新缓存。每tick调用一次。
     *
     * @param player 玩家
     */
    public void tick(Player player) {
        spatialGroups.clear();
        level = player.level();
        lastTick = player.tickCount;
        Vec3 center = player.getBoundingBox().getCenter();
        List<LivingEntity> livingEntityList = player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(128));
        for (LivingEntity livingEntity : livingEntityList) {
            if (isTarget(player, livingEntity)) {
                Vec3 pos = livingEntity.getBoundingBox().getCenter();
                if (center.distanceToSqr(pos) < 128 * 128) {
                    long key = SectionPos.asLong(Mth.floor(pos.x) >> 4, Mth.floor(pos.y) >> 4, Mth.floor(pos.z) >> 4);
                    spatialGroups.computeIfAbsent(key, k -> new ArrayList<>()).add(livingEntity);
                }
            }
        }
    }

    public static boolean isTarget(@NotNull Player player, @NotNull LivingEntity target) {
        if (player != target && target.isAlive()) {
            if (target instanceof Enemy) {
                return true;
            }
            if (target instanceof Targeting targeting && targeting.getTarget() == player) {
                return true;
            }
            if (target.hasData(LyraAttachmentRegister.ImmunityData) && ImmunityData.get(target).isActive(player.getUUID())) {
                return true;
            }
            if (player.hasData(LyraAttachmentRegister.ImmunityData) && ImmunityData.get(player).isActive(target.getUUID())) {
                return true;
            }
        }
        return false;
    }
}

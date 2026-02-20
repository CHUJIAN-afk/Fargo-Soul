package First.fargo_soul.utils;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.common.attachment.SoulAbilityData;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.register.AttachmentRegister;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Targeting;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class SoulUtils {

    public static final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    public static final List<SoulItem> RegisterSoulList = BuiltInRegistries.ITEM.stream().filter(item -> item instanceof SoulItem).map(item -> (SoulItem) item).toList();
    public static final List<SoulItem> AttributeSoulList = RegisterSoulList.stream().filter(soulItem -> !soulItem.getAttributeModifiers().isEmpty()).toList();

    public static SoulAbilityData.SoulInfo getSoulInfo(Entity entity, Class<?> clazz) {
        return getSoulInfo(entity, FargoSoul.rl(clazz.getSimpleName()));
    }

    public static SoulAbilityData.SoulInfo getSoulInfo(Entity entity, String id) {
        return getSoulInfo(entity, FargoSoul.rl(id));
    }

    /**
     * 获取实体的指定能力数据
     *
     * @param entity   实体
     * @param location 能力数据键
     * @return 能力数据
     */
    public static SoulAbilityData.SoulInfo getSoulInfo(Entity entity, ResourceLocation location) {
        return entity.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(location);
    }

    /**
     * 添加物品实体到维度，设置物品实体的运动方向为随机方向
     *
     * @param level     维度
     * @param itemStack 物品栈
     * @param center    中心位置
     */
    public static void addItemEetity(Level level, ItemStack itemStack, Vec3 center) {
        ItemEntity itemEntity = new ItemEntity(level, center.x, center.y, center.z, itemStack);
        itemEntity.setDeltaMovement(new Vec3(getRandom().nextFloat(-0.5F, 0.5F), getRandom().nextFloat(-0.5F, 0.5F), getRandom().nextFloat(0.5F)));
        if (itemEntity != null) {
            addEntity(level, itemEntity);
        }
    }

    /**
     * 判断目标是否最近被攻击者攻击
     *
     * @param attacker
     * @param target
     * @return 目标是否最近被攻击者攻击
     */
    public static boolean recentlyAttacked(LivingEntity attacker, LivingEntity target) {
        return getSoulInfo(target, attacker.getStringUUID() + "damaged").isActive();
    }

    /**
     * 判断目标是否为可以是攻击者的目标
     *
     * @param attacker 攻击者
     * @param target   目标
     * @return 目标可以是攻击者的目标
     */
    public static boolean isTarget(LivingEntity attacker, LivingEntity target) {
        if (attacker != target) {
            boolean isActive0 = attacker instanceof Player && target instanceof Enemy;
            boolean isActive1 = attacker instanceof Targeting mob && mob.getTarget() == target;
            boolean isActive2 = attacker instanceof Enemy && !(target instanceof Enemy);
            boolean isActive3 = target instanceof Targeting targeting && targeting.getTarget() == attacker;
            boolean isActive4 = recentlyAttacked(target, attacker);
            boolean isActive5 = recentlyAttacked(attacker, target);
            return isActive0 || isActive1 || isActive2 || isActive3 || isActive4 || isActive5;
        }
        return false;
    }

    /**
     * 获取在指定区域内可以是攻击者的目标列表
     *
     * @param attacker 攻击者
     * @param area     区域
     * @return 目标列表
     */
    public static List<LivingEntity> getTargetList(LivingEntity attacker, AABB area) {
        return attacker.level().getEntitiesOfClass(LivingEntity.class, area, target -> isTarget(attacker, target));
    }

    /**
     * 获取在攻击者周围内可以是攻击者的目标列表
     *
     * @param attacker 攻击者
     * @param range    范围
     * @return 目标列表
     */
    public static List<LivingEntity> getTargetList(LivingEntity attacker, double range) {
        List<LivingEntity> targetList = getTargetList(attacker, attacker.getBoundingBox().inflate(range));
        targetList.removeIf(living -> living.distanceTo(attacker) > range);
        return targetList;
    }

    /**
     * 从攻击者周围随机向周围发射射弹
     *
     * @param attacker   攻击者
     * @param projectile 射弹实体
     * @param owner      射弹所有者
     */
    public static void randomShoot(LivingEntity attacker, Projectile projectile, LivingEntity owner) {
        Level level = attacker.level();
        double theta = getRandom().nextDouble() * Math.PI * 2;
        double phi = Math.acos(2 * getRandom().nextDouble() - 1);
        double r = 0.5 + getRandom().nextDouble() * 0.3;
        Vec3 offset = new Vec3(r * Math.sin(phi) * Math.cos(theta), r * Math.sin(phi) * Math.sin(theta), r * Math.cos(phi));
        Vec3 spawnPos = attacker.position().add(offset);
        Vec3 velocity = offset.normalize().scale(0.8);
        projectile.setOwner(owner);
        projectile.moveTo(spawnPos.x, spawnPos.y, spawnPos.z, attacker.getYRot(), attacker.getXRot());
        projectile.shoot(velocity.x, velocity.y, velocity.z, 0.6f, 6.0f);
        addEntity(level, projectile);
    }

    /**
     * 获取实体的护甲物品列表
     *
     * @param entity 实体
     * @return 护甲物品列表
     */
    public static List<Item> getArmorList(LivingEntity entity) {
        List<Item> list = new ArrayList<>();
        entity.getArmorSlots().forEach(itemStack -> list.add(itemStack.getItem()));
        return list;
    }

    /**
     * 添加实体到维度
     *
     * @param level  维度
     * @param entity 实体
     */
    public static void addEntity(Level level, Entity entity) {
        MinecraftServer server = level.getServer();
        if (server != null && entity.getId() > 0) {
            if (entity instanceof AbstractArrow abstractArrow) {
                abstractArrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            }
            server.execute(() -> level.addFreshEntity(entity));
        }
    }

    /**
     * 播放冷却结束音效
     *
     * @param attacker 攻击者实体
     * @param event    音效事件
     * @param second   延迟秒数
     */
    public static void CooldownEndSound(LivingEntity attacker, SoundEvent event, int second) {
        Level level = attacker.level();
        executorService.schedule(() -> playSound(level, attacker.position(), event, attacker.getSoundSource()), second, TimeUnit.SECONDS);
    }

    /**
     * 设置实体无视目标无敌帧
     *
     * @param projectile 射弹实体
     */
    public static void setAbilityInvulnerable(Entity entity) {
        getSoulInfo(entity, "noInvulnerable").setEnabled(true);
    }

    /**
     * 从攻击者周围向目标方向发射射弹
     *
     * @param projectile 射弹实体
     * @param attacker   攻击者实体
     * @param target     目标实体
     */
    public static void shootTargetFromAttaker(Projectile projectile, LivingEntity attacker, LivingEntity target) {
        shootTargetFromAttaker(projectile, attacker, target, 1, 1);
    }

    /**
     * 从基础值中添加随机误差
     *
     * @param baseValue  基础值
     * @param errorRange 误差范围
     * @return 带有误差的随机值
     */
    public static double getRandomWithError(double baseValue, double errorRange) {
        return baseValue + (getRandom().nextFloat(-1, 1) * errorRange);
    }

    /**
     * 从攻击者位置向目标位置发射射弹
     *
     * @param projectile 射弹实体
     * @param attacker   攻击者实体
     * @param target     目标实体
     * @param distance   起始位置与攻击者实体的随机距离范围系数
     * @param speed      射弹速度系数
     */
    public static void shootTargetFromAttaker(Projectile projectile, LivingEntity attacker, LivingEntity target, double distance, double speed) {
        double size = attacker.getBoundingBox().getSize() * distance;
        double x = getRandomWithError(attacker.getX(), size);
        double y = getRandomWithError(attacker.getY(), size);
        double z = getRandomWithError(attacker.getZ(), size);
        Vec3 pos = new Vec3(x, y, z);
        projectile.setPos(pos);
        Vec3 toTarget = target.getHitbox().getCenter().subtract(pos).normalize().scale(speed);
        projectile.setOwner(attacker);
        projectile.setDeltaMovement(toTarget);
        addEntity(attacker.level(), projectile);
    }

    /**
     * 播放音效
     *
     * @param level       维度
     * @param center      音效中心位置
     * @param soundEvent  音效事件
     * @param soundSource 音效源
     */
    public static void playSound(Level level, Vec3 center, SoundEvent soundEvent, SoundSource soundSource) {
        level.playSound(null, center.x(), center.y(), center.z(), soundEvent, soundSource, 1.0f, getRandom().nextFloat(0.4f, 0.8f));
    }

    /**
     * 获取玩家目标方块
     *
     * @param player      玩家实体
     * @param maxDistance 最大检测距离
     * @return 目标方块结果
     */
    public static BlockHitResult getTargetedBlock(Player player, double maxDistance) {
        Vec3 eyePos = player.getEyePosition();
        Vec3 lookVec = player.getLookAngle();
        Vec3 endPos = eyePos.add(lookVec.scale(maxDistance));
        ClipContext clipContext = new ClipContext(eyePos, endPos, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player);
        return player.level().clip(clipContext);
    }

    /**
     * 获取攻击者周围可能的实体目标
     *
     * @param attacker 攻击者实体
     * @param distance 检测距离
     * @return 目标实体
     */
    public static LivingEntity getSoulTarget(LivingEntity attacker, float distance) {
        LivingEntity target = null;
        List<LivingEntity> targetList = getTargetList(attacker, distance);
        if (!targetList.isEmpty()) {
            target = targetList.get(attacker.getRandom().nextInt(targetList.size()));
        }
        return target;
    }

    /**
     * 应用或更新实体的状态效果
     *
     * @param entity   实体
     * @param effect   状态效果
     * @param duration 持续时间
     * @param maxLevel 最大等级
     */
    public static void applyOrUpdateEffect(LivingEntity entity, Holder<MobEffect> effect, int duration, int maxLevel) {
        if (entity.getEffect(effect) instanceof MobEffectInstance existingEffect) {
            int newAmplifier = Math.min(existingEffect.getAmplifier() + 1, maxLevel);
            entity.removeEffect(effect);
            entity.addEffect(new MobEffectInstance(effect, duration, newAmplifier));
        } else {
            entity.addEffect(new MobEffectInstance(effect, duration));
        }
    }

    public static boolean attack(Class<?> type, LivingEntity by, LivingEntity attacker, LivingEntity target, ResourceKey<DamageType> damageTypeResourceKey, float amount) {
        return attack(type.getSimpleName(), by, attacker, target, damageTypeResourceKey, amount);
    }

    /**
     * 在伤害事件可使用的伤害处理，可防止递归，此伤害无视无敌帧
     *
     * @param type                  标识符
     * @param by                    伤害引起者，一般为攻击者
     * @param attacker              攻击者
     * @param target                目标
     * @param damageTypeResourceKey 伤害类型
     * @param amount                数值
     * @return
     */
    public static boolean attack(String type, LivingEntity by, LivingEntity attacker, LivingEntity target, ResourceKey<DamageType> damageTypeResourceKey, float amount) {
        long gameTime = target.level().getGameTime();
        ConcurrentHashMap<Long, CopyOnWriteArrayList<String>> damageData = target.getData(AttachmentRegister.SoulDamageData).getDamageData();
        String key = type + by.getStringUUID() + target.getStringUUID();
        damageData.keySet().removeIf(time -> time != gameTime);
        CopyOnWriteArrayList<String> stringList = damageData.computeIfAbsent(gameTime, k -> new CopyOnWriteArrayList<>());
        if (!stringList.contains(key)) {
            stringList.add(key);
            int invulnerableTime = target.invulnerableTime;
            target.invulnerableTime = 0;
            DamageSources damageSources = target.level().damageSources();
            DamageSource damageSource = damageSources.source(damageTypeResourceKey, attacker != null ? attacker : null);
            target.hurt(damageSource, amount);
            target.invulnerableTime = invulnerableTime;
            return true;
        }
        return false;
    }

    /**
     * 获取线程安全的随机数生成器
     *
     * @return 随机数生成器
     */
    public static Random getRandom() {
        return ThreadLocalRandom.current();
    }

}

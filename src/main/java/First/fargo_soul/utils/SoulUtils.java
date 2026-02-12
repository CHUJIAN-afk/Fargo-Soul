package First.fargo_soul.utils;

import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.register.AttachmentRegister;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class SoulUtils {

	public static final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
	public static final Random random = ThreadLocalRandom.current();
	public static final List<SoulItem> RegisterSoulList = BuiltInRegistries.ITEM.stream().filter(item -> item instanceof SoulItem).map(item -> (SoulItem) item).toList();
	public static final List<SoulItem> AttributeSoulList = RegisterSoulList.stream().filter(soulItem -> !soulItem.getAttributeModifiers().isEmpty()).toList();
	public static final List<EntityType<?>> ProjectileList = BuiltInRegistries.ENTITY_TYPE.stream().toList();

	public static void addItemEetity(Level level, ItemStack itemStack, Vec3 center) {
		ItemEntity itemEntity = new ItemEntity(level, center.x, center.y, center.z, itemStack);
		itemEntity.setDeltaMovement(new Vec3(random.nextFloat(-0.5F, 0.5F), random.nextFloat(-0.5F, 0.5F), random.nextFloat(0.5F)));
		if (itemEntity != null) {
			addEntity(level, itemEntity);
		}
	}

	public static List<LivingEntity> getTargetList(LivingEntity attacker, double range) {
		return attacker.level().getEntitiesOfClass(LivingEntity.class, attacker.getBoundingBox().inflate(range), living -> {
			boolean isActive0 = attacker instanceof Player && living instanceof Enemy;
			boolean isActive1 = attacker instanceof Mob mob && living.equals(mob.getTarget());
			boolean isActive2 = attacker instanceof Enemy && !(living instanceof Enemy);
			return (isActive0 || isActive1 || isActive2) && living.distanceTo(attacker) <= range;
		});
	}

	public static void randomShoot(LivingEntity attacker, Projectile projectile, LivingEntity owner) {
		Level level = attacker.level();
		double theta = random.nextDouble() * Math.PI * 2;
		double phi = Math.acos(2 * random.nextDouble() - 1);
		double r = 0.5 + random.nextDouble() * 0.3;
		Vec3 offset = new Vec3(r * Math.sin(phi) * Math.cos(theta), r * Math.sin(phi) * Math.sin(theta), r * Math.cos(phi));
		Vec3 spawnPos = attacker.position().add(offset);
		Vec3 velocity = offset.normalize().scale(0.8);
		projectile.setOwner(owner);
		projectile.moveTo(spawnPos.x, spawnPos.y, spawnPos.z, attacker.getYRot(), attacker.getXRot());
		projectile.shoot(velocity.x, velocity.y, velocity.z, 0.6f, 6.0f);
		addEntity(level, projectile);
	}

	public static List<Item> getArmorList(LivingEntity entity) {
		List<Item> list = new ArrayList<>();
		entity.getArmorSlots().forEach(itemStack -> list.add(itemStack.getItem()));
		return list;
	}

	public static void addEntity(Level level, Entity entity) {
		MinecraftServer server = level.getServer();
		if (server != null && entity.getId() > 0) {
			if (entity instanceof AbstractArrow abstractArrow) {
				abstractArrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
			}
			server.execute(() -> level.addFreshEntity(entity));
		}
	}

	public static void CooldownEndSound(LivingEntity attacker, SoundEvent event, int second) {
		Level level = attacker.level();
		executorService.schedule(() -> playSound(level, attacker.position(), event, attacker.getSoundSource()), second, TimeUnit.SECONDS);
	}

	/**
	 * 设置射弹无视敌人无敌帧
	 * @param projectile 射弹实体
	 */
	public static void setAbilityInvulnerable(Projectile projectile) {
		projectile.getData(AttachmentRegister.SoulAbilityData).getSoulInfo("noInvulnerable").setEnabled(true);
	}

	public static void shootTargetFromAttaker(Projectile projectile, LivingEntity attacker, LivingEntity target) {
		shootTargetFromAttaker(projectile, attacker, target, 1, 1);
	}

	public static double getRandomWithError(double baseValue, double errorRange) {
		return baseValue + (random.nextFloat(-1, 1) * errorRange);
	}

	/**
	 * 从攻击者位置向目标位置发射射弹，随机距离和速度
	 * @param projectile 射弹实体
	 * @param attacker 攻击者实体
	 * @param target 目标实体
	 * @param distance 随机距离范围
	 * @param speed 射弹速度
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
	 * @param level 等级对象
	 * @param center 音效中心位置
	 * @param soundEvent 音效事件
	 * @param soundSource 音效源
	 */
	public static void playSound(Level level, Vec3 center, SoundEvent soundEvent, SoundSource soundSource) {
		level.playSound(
				null,
				center.x(),
				center.y(),
				center.z(),
				soundEvent,
				soundSource,
				1.0f,
				random.nextFloat(0.4f, 0.8f)
		);
	}

	public static BlockHitResult getTargetedBlock(Player player, double maxDistance) {
		Vec3 eyePos = player.getEyePosition();
		Vec3 lookVec = player.getLookAngle();
		Vec3 endPos = eyePos.add(lookVec.scale(maxDistance));
		ClipContext clipContext = new ClipContext(eyePos, endPos, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player);
		return player.level().clip(clipContext);
	}

	public static LivingEntity getSoulTarget(LivingEntity attacker, float distance) {
		LivingEntity target = null;
		if (attacker instanceof Player player) {
			List<LivingEntity> livingEntityList = player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(distance), livingEntity -> {
				boolean a = livingEntity instanceof OwnableEntity ownableEntity && player.equals(ownableEntity.getOwner());
				boolean b = livingEntity instanceof Enemy && player.distanceTo(livingEntity) < distance;
				return !a && b;
			});
			if (!livingEntityList.isEmpty()) {
				target = livingEntityList.get(player.getRandom().nextInt(livingEntityList.size()));
			}
		} else if (attacker instanceof Mob mob && mob.getTarget() instanceof LivingEntity target1) {
			if (mob.distanceTo(target1) < distance) {
				target = target1;
			}
		}
		return target;
	}

	public static void applyOrUpdateEffect(LivingEntity entity, Holder<MobEffect> effect, int duration, int maxLevel) {
		if (entity.getEffect(effect) instanceof MobEffectInstance existingEffect) {
			int newAmplifier = Math.min(existingEffect.getAmplifier() + 1, maxLevel);
			entity.removeEffect(effect);
			entity.addEffect(new MobEffectInstance(effect, duration, newAmplifier));
		} else {
			entity.addEffect(new MobEffectInstance(effect, duration));
		}
	}

	public static <T extends SoulItem> boolean canAttack(Class<T> type, LivingEntity by, LivingEntity target) {
		long gameTime = target.level().getGameTime();
		Map<Long, List<String>> damageData = target.getData(AttachmentRegister.SoulDamageData).getDamageData();
		String key = type.getName() + by.getScoreboardName() + target.getScoreboardName();
		List<String> stringList = damageData.computeIfAbsent(gameTime, k -> new ArrayList<>());
        return !stringList.contains(key);
    }

	public static <T extends SoulItem> void attack(Class<T> type, LivingEntity by, LivingEntity attacker, LivingEntity target, ResourceKey<DamageType> damageTypeResourceKey, float amount) {
		long gameTime = target.level().getGameTime();
		Map<Long, List<String>> damageData = target.getData(AttachmentRegister.SoulDamageData).getDamageData();
		String key = type.getName() + by.getScoreboardName() + target.getScoreboardName();
		List<String> stringList = damageData.computeIfAbsent(gameTime, k -> new ArrayList<>());
		if (!stringList.contains(key)) {
			stringList.add(key);
			attack(attacker, target, damageTypeResourceKey, amount);
		} else {
			stringList.add(key);
		}
	}

	public static void attack(LivingEntity attacker, LivingEntity target, ResourceKey<DamageType> damageTypeResourceKey, float amount) {
		target.invulnerableTime = 0;
		DamageSources damageSources = target.level().damageSources();
		DamageSource damageSource = damageSources.source(damageTypeResourceKey, attacker != null ? attacker : target);
		target.hurt(damageSource, amount);
	}

}

package First.fargo_soul.Utils;

import First.fargo_soul.Attachment.Attachment.SoulData;
import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class SoulUtils {

	public static final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
	public static final Random random = new Random();

	public static float getAgeInTicks(LivingEntity attacker, float partialTick, float speed) {
		float render = attacker.tickCount * speed;
		return Mth.lerp(partialTick, render - speed, render);
	}

	public static void addEntity(Level level, Entity entity) {
		MinecraftServer server = level.getServer();
		if (server != null) {
			server.execute(() -> level.addFreshEntity(entity));
		}
	}

	public static void CooldownEndSound(LivingEntity attacker, SoundEvent event, int second) {
		Level level = attacker.level();
		executorService.schedule(() -> playSound(
				level,
				attacker.position(),
				event,
				SoundSource.PLAYERS
		), second, TimeUnit.SECONDS);
	}

	public static void setAbilityInvulnerable(Projectile projectile) {
		projectile.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(SoulItem.class).enabled = true;
	}

	public static void shootTargetFromAttaker(Projectile projectile, LivingEntity attacker, LivingEntity target) {
		shootTargetFromAttaker(projectile, attacker, target, 1, 1);
	}

	public static void shootTargetFromAttaker(Projectile projectile, LivingEntity attacker, LivingEntity target, double distance, double speed) {
		double size = attacker.getBoundingBox().getSize() * distance;
		double x = attacker.getRandomX(size);
		double y = attacker.getRandomY() + size;
		double z = attacker.getRandomZ(size);
		Vec3 pos = new Vec3(x, y, z);
		projectile.setPos(pos);
		Vec3 toTarget = target.getHitbox().getCenter().subtract(pos).normalize().scale(speed);
		projectile.setOwner(attacker);
		projectile.setDeltaMovement(toTarget);
		addEntity(attacker.level(), projectile);
	}

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

	public static List<SoulItem> getSoulItemList(LivingEntity livingEntity) {
		List<SoulItem> soulItemList = new ArrayList<>();
		getAllCurioItems(getSoulInventory(livingEntity)).forEach(soulItem -> {
			if (!soulItemList.contains(soulItem)) {
				soulItemList.add(soulItem);
			}
		});
		return soulItemList;
	}

	public static <T extends SoulItem> boolean isEquipped(LivingEntity livingEntity, Class<T> type) {
		SoulData soulData = livingEntity.getData(AttachmentRegister.SoulData.get());
		if (soulData.getSoulItemList() == null) {
			soulData.setSoulItemList(getSoulItemList(livingEntity));
		}
		for (SoulItem soulItem : getSoulItemList(livingEntity)) {
			if (soulItem.getClass().equals(type)) {
				return true;
			}
		}
		return false;
	}

	public static void updateSoulList(LivingEntity livingEntity) {
		SoulData soulData = livingEntity.getData(AttachmentRegister.SoulData.get());
		soulData.setSoulItemList(getSoulItemList(livingEntity));
	}

	public static boolean isEquippedAny(LivingEntity livingEntity, List<Class<? extends SoulItem>> types) {
		for (Class<? extends SoulItem> type : types) {
			if (isEquipped(livingEntity, type)) {
				return true;
			}
		}
		return false;
	}

	public static List<SoulItem> getSoulInventory(LivingEntity livingEntity) {
		List<SoulItem> OringinCurioList = new ArrayList<>();
		if (livingEntity instanceof Player player) {
			Inventory inventory = player.getInventory();
			if (inventory.contains(SoulsRegister.TerraSoul.get().getDefaultInstance())) {
				NonNullList<ItemStack> list = inventory.items;
				list.forEach(itemStack -> {
					Item item = itemStack.getItem();
					if (item instanceof SoulItem soulItem) {
						OringinCurioList.add(soulItem);
					}
				});
			}
		}
		Optional<ICuriosItemHandler> curiosItemHandler = CuriosApi.getCuriosInventory(livingEntity);
		if (curiosItemHandler.isPresent()) {
			IItemHandlerModifiable iItemHandlerModifiable = curiosItemHandler.get().getEquippedCurios();
			int size = iItemHandlerModifiable.getSlots();
			for (int i = 0; i < size; i++) {
				ItemStack stack = iItemHandlerModifiable.getStackInSlot(i);
				if (stack.getItem() instanceof SoulItem soulItem) {
					OringinCurioList.add(soulItem);
				}
			}
		}
		return OringinCurioList;
	}

	public static List<SoulItem> getAllCurioItems(List<SoulItem> originList) {
		List<SoulItem> result = new ArrayList<>();
		for (SoulItem item : originList) {
			if (!result.contains(item)) {
				result.add(item);
			}
			List<SoulItem> soulItems = item.getSoulItemList();
			if (!soulItems.isEmpty()) {
				result.addAll(getAllCurioItems(soulItems));
			}
		}
		return result;
	}

	public static <T extends SoulItem> boolean canAttack(Class<T> type, LivingEntity by, LivingEntity target) {
		List<String> damageContainer = target.getData(AttachmentRegister.DamageData).getDamageContainer();
		String string = type.getPackageName() + by.getId() + by.level().getGameTime();
		return !damageContainer.contains(string);
	}

	public static <T extends SoulItem> void attack(Class<T> type, LivingEntity by, LivingEntity attacker, LivingEntity target, ResourceKey<DamageType> damageTypeResourceKey, float amount) {
		List<String> damageContainer = target.getData(AttachmentRegister.DamageData).getDamageContainer();
		String string = type.getPackageName() + by.getId() + attacker.level().getGameTime();
		damageContainer.add(string);
		attack(attacker, target, damageTypeResourceKey, amount);
	}

	public static void attack(LivingEntity attacker, LivingEntity target, ResourceKey<DamageType> damageTypeResourceKey, float amount) {
		target.invulnerableTime = 0;
		target.hurt(attacker.level().damageSources().source(damageTypeResourceKey, attacker), amount);
	}

	public <T> void addItemToTag(Function<ResourceLocation, Optional<? extends T>> idToValue, Map<ResourceLocation, Collection<T>> tags, ResourceLocation itemKey, ResourceLocation tagKey) {
		if (idToValue.apply(itemKey).isPresent()) {
			tags.computeIfAbsent(tagKey, k -> new ArrayList<>()).add(idToValue.apply(itemKey).get());
		}
	}


}

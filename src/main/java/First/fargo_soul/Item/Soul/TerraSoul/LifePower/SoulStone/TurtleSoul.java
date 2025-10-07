package First.fargo_soul.Item.Soul.TerraSoul.LifePower.SoulStone;

import First.fargo_soul.Attachment.Attachment.SoulAbilityData;
import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Utils.SoulUtils;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class TurtleSoul extends SoulItem {

	public TurtleSoul(Properties properties) {
		super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.YELLOW));
	}

	@EventBusSubscriber(modid = Fargo_soul.MODID)
	public static class Event {

		@SubscribeEvent
		public static void Tick(EntityTickEvent.Post event) {
			if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
				if (SoulUtils.isEquipped(attacker, TurtleSoul.class)) {
					SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(TurtleSoul.class);
					soulInfo.maxCooldown = 2400;
					boolean superLowHealth = attacker.getHealth() / attacker.getMaxHealth() < 0.25;
					soulInfo.enabled = superLowHealth && soulInfo.cooldown == 0;
				}
			}
		}

		@SubscribeEvent
		public static void Damage(LivingIncomingDamageEvent event) {
			if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
				if (event.getSource().is(DamageTypes.CACTUS) && SoulUtils.isEquipped(target, TurtleSoul.class)) {
					event.setCanceled(true);
				}
			}
			if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
				if (!attacker.equals(target) && SoulUtils.isEquipped(target, TurtleSoul.class)) {
					SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(TurtleSoul.class);
					if (soulInfo.enabled) {
						event.setAmount(Math.max(event.getAmount() - target.getMaxHealth() * 0.04f, 0));
						if (event.getAmount() > target.getHealth()) {
							event.setCanceled(true);
							soulInfo.cooldown = soulInfo.maxCooldown;
						}
					}
				}
			}
			if (event.getSource().getDirectEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
				if (!attacker.equals(target) && SoulUtils.isEquipped(target, TurtleSoul.class) && SoulUtils.canAttack(TurtleSoul.class, attacker, attacker)) {
					float newDamage = event.getAmount();
					float scale = (1 - (float) (attacker.getBoundingBox().getCenter().distanceTo(target.getBoundingBox().getCenter()) * 0.1)) * 0.6f;
					boolean lowHealth = target.getHealth() / target.getMaxHealth() < 0.5;
					newDamage *= lowHealth ? 0.8f : Math.max(0.1f, scale);
					if (lowHealth) {
						Vec3 knockback = target.getBoundingBox().getCenter().subtract(target.getBoundingBox().getCenter()).normalize().scale(scale);
						attacker.knockback(1, knockback.x(), knockback.y());
					}
					SoulUtils.attack(TurtleSoul.class, attacker, target, attacker, DamageTypes.CACTUS, newDamage);
					/*
					double chance = SoulUtils.isEquipped(target, LifePower.class) ? 0.2 : 0.1;
					if (!event.isCanceled() && event.getAmount() > 0 && target.getRandom().nextDouble() < chance) {
						Level level = target.level();
						for (int i = 0; i < (SoulUtils.isEquipped(target, LifePower.class) ? 16 : 8); i++) {
							addNeedle(level, target, target, target.getBoundingBox().getCenter(), 0.6f);
						}
					}
					*/
				}
			}
		}
/*
		@SubscribeEvent
		public static void CactusSoulDeathHandler(LivingDeathEvent event) {
			if (event.getSource().getDirectEntity() instanceof NeedleProjectile needleProjectile && event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
				if (needleProjectile.getOwner() instanceof LivingEntity attacker && SoulUtils.isEquipped(attacker, TurtleSoul.class)) {
					Level level = attacker.level();
					for (int i = 0; i < (SoulUtils.isEquipped(attacker, LifePower.class) ? 120 : 80); i++) {
						//addNeedle(level, attacker, target, target.getBoundingBox().getCenter(), 0.9f);
					}
				}
			}
		}
*/
		/*
		private static void addNeedle(Level level, LivingEntity target, LivingEntity target1, Vec3 target2, float velocity) {
			NeedleProjectile needle = new NeedleProjectile(EntityRegister.Needle.get(), level);
			needle.setOwner(target);
			double baseDamage = SoulUtils.isEquipped(target1, LifePower.class) ? 4 : 2;
			needle.setBaseDamage(baseDamage);
			double theta = level.random.nextDouble() * Math.PI * 2;
			double phi = Math.acos(2 * level.random.nextDouble() - 1);
			double r = 0.5 + level.random.nextDouble() * 0.3;
			Vec3 offset = new Vec3(r * Math.sin(phi) * Math.cos(theta), r * Math.sin(phi) * Math.sin(theta), r * Math.cos(phi));
			Vec3 spawnPos = target2.add(offset);
			Vec3 vec3 = offset.normalize().scale(0.8);
			needle.moveTo(spawnPos.x, spawnPos.y, spawnPos.z, target1.getYRot(), target1.getXRot());
			needle.shoot(vec3.x, vec3.y, vec3.z, velocity, 6.0f);
			level.addFreshEntity(needle);
			SoulAbilityData.SoulInfo soulInfo = needle.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(SoulItem.class);
			soulInfo.enabled = true;
		}
*/
	}

}

package First.fargo_soul.Item.Soul.TerraSoul.LifePower.SoulStone;

import First.fargo_soul.Attachment.Attachment.SoulAbilityData;
import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Entity.EntityRegister;
import First.fargo_soul.Entity.Projectile.NeedleProjectile;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.TerraSoul.LifePower.LifePower;
import First.fargo_soul.Utils.SoulUtils;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
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
					soulInfo.setMaxCooldown(2400);
					boolean superLowHealth = attacker.getHealth() / attacker.getMaxHealth() < 0.25;
					soulInfo.setEnabled(superLowHealth && soulInfo.isReady());
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
					if (soulInfo.isEnabled()) {
						event.setAmount(Math.max(event.getAmount() - target.getMaxHealth() * 0.04f, 0));
						if (event.getAmount() > target.getHealth()) {
							event.setCanceled(true);
							soulInfo.setCooldown(soulInfo.getMaxCooldown());
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
					double chance = SoulUtils.isEquipped(target, LifePower.class) ? 0.2 : 0.1;
					if (!event.isCanceled() && event.getAmount() > 0 && target.getRandom().nextDouble() < chance) {
						Level level = target.level();
						for (int i = 0; i < (SoulUtils.isEquipped(target, LifePower.class) ? 16 : 8); i++) {
							NeedleProjectile needle = new NeedleProjectile(EntityRegister.Needle.get(), level);
							SoulUtils.randomShoot(target, needle,target);
							SoulUtils.setAbilityInvulnerable(needle);
						}
					}
				}
			}
		}

		@SubscribeEvent
		public static void CactusSoulDeathHandler(LivingDeathEvent event) {
			if (event.getSource().getDirectEntity() instanceof NeedleProjectile needleProjectile && event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
				if (needleProjectile.getOwner() instanceof LivingEntity attacker && SoulUtils.isEquipped(attacker, TurtleSoul.class)) {
					Level level = attacker.level();
					for (int i = 0; i < (SoulUtils.isEquipped(attacker, LifePower.class) ? 120 : 80); i++) {
						NeedleProjectile needle = new NeedleProjectile(EntityRegister.Needle.get(), level);
						SoulUtils.randomShoot(target, needle, attacker);
						SoulUtils.setAbilityInvulnerable(needle);
					}
				}
			}
		}

	}

}

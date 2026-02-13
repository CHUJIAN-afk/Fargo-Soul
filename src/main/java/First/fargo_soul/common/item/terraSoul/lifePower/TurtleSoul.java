package First.fargo_soul.common.item.terraSoul.lifePower;

import First.fargo_soul.client.gui.SoulGuiRenderManager;
import First.fargo_soul.client.gui.SoulRenderType;
import First.fargo_soul.common.attachment.SoulAbilityData;
import First.fargo_soul.common.entity.projectile.Needle;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.common.item.terraSoul.LifePower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.register.EntityRegister;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class TurtleSoul extends SoulItem {

	public TurtleSoul(Properties properties) {
		super(properties);
	}

	@Override
	public void tick(LivingEntity ticker) {
		if (!ticker.level().isClientSide()) {
			if (CurioUtils.isEquipped(ticker, TurtleSoul.class)) {
				SoulAbilityData.SoulInfo soulInfo = ticker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(TurtleSoul.class);
				soulInfo.setMaxCooldown(2400);
				boolean superLowHealth = ticker.getHealth() / ticker.getMaxHealth() < 0.25;
				soulInfo.setEnabled(superLowHealth && soulInfo.isReady());
			}
		}
	}

	@Override
	public void hurt(LivingIncomingDamageEvent event) {
		if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
			if (event.getSource().is(DamageTypes.CACTUS) && CurioUtils.isEquipped(target, TurtleSoul.class)) {
				event.setCanceled(true);
			}
		}
		if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
			if (!attacker.equals(target) && CurioUtils.isEquipped(target, TurtleSoul.class)) {
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
			if (CurioUtils.isEquipped(target, TurtleSoul.class)) {
				float newDamage = event.getAmount();
				float scale = (1 - (float) (attacker.getBoundingBox().getCenter().distanceTo(target.getBoundingBox().getCenter()) * 0.1)) * 0.6f;
				boolean lowHealth = target.getHealth() / target.getMaxHealth() < 0.5;
				newDamage *= lowHealth ? 0.8f : Math.max(0.1f, scale);
				if (lowHealth) {
					Vec3 knockback = target.getBoundingBox().getCenter().subtract(target.getBoundingBox().getCenter()).normalize().scale(scale);
					attacker.knockback(1, knockback.x(), knockback.y());
				}
				SoulUtils.attack(TurtleSoul.class, target, target, attacker, DamageTypes.CACTUS, newDamage);
				double chance = CurioUtils.isEquipped(target, LifePower.class) ? 0.2 : 0.1;
				if (!event.isCanceled() && event.getAmount() > 0 && target.getRandom().nextDouble() < chance) {
					Level level = target.level();
					for (int i = 0; i < (CurioUtils.isEquipped(target, LifePower.class) ? 16 : 8); i++) {
						Needle needle = new Needle(EntityRegister.NeedleEntity.get(), level);
						SoulUtils.randomShoot(target, needle,target);
						SoulUtils.setAbilityInvulnerable(needle);
					}
				}
			}
		}
	}

	@Override
	public void death(LivingDeathEvent event) {
		if (event.getSource().getDirectEntity() instanceof Needle needleProjectile && event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
			if (needleProjectile.getOwner() instanceof LivingEntity attacker && CurioUtils.isEquipped(attacker, TurtleSoul.class)) {
				Level level = attacker.level();
				for (int i = 0; i < (CurioUtils.isEquipped(attacker, LifePower.class) ? 120 : 80); i++) {
					Needle needle = new Needle(EntityRegister.NeedleEntity.get(), level);
					SoulUtils.randomShoot(target, needle, attacker);
					SoulUtils.setAbilityInvulnerable(needle);
				}
			}
		}
	}

	@Override
	public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
		soulRenderManager.add(this, TurtleSoul.class, SoulRenderType.Cooldown);
	}

}

package First.fargo_soul.Item.Soul.UniverseSoul.SharpshooterSoul.Soul;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class MeltRocketBag extends SoulItem {

	public MeltRocketBag(Properties properties) {
		super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PINK));
	}
/*
	public static void MeltRocketBagTickHandler(PlayerTickEvent.Post event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			DeferredItem<SoulItem> MeltRocketBag = SoulsRegister.MeltRocketBag;
			ResourceLocation resourceLocation = MeltRocketBag.getId();
			boolean equipped = CurioUtils.isEquipped(player, MeltRocketBag.get());
			AttributeModifier.Operation addMultipliedBase = AttributeModifier.Operation.ADD_MULTIPLIED_BASE;
			AttributeUtils.ConditionAttributeModifier(player, AttributeRegister.RangedDamage, resourceLocation, 0.1, addMultipliedBase, equipped);
			AttributeUtils.ConditionAttributeModifier(player, AttributeRegister.RangedSpeed, resourceLocation, 0.2, addMultipliedBase, equipped);
		}
	}

	public static void MeltRocketBagProjectileImpactHandler(ProjectileImpactEvent event) {
		if (event.getProjectile().getOwner() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.MeltRocketBag.get())) {
			if (event.getEntity() instanceof LivingEntity livingEntity && livingEntity.getRemainingFireTicks() == 0) {
				livingEntity.setRemainingFireTicks(60);
			}
		}
	}
*/
}

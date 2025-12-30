package First.fargo_soul.item.universeSoul.BerserkerSoul.Soul;

import First.fargo_soul.item.base.SoulItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class FireGloves extends SoulItem {

	public FireGloves(Properties properties) {
		super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.LIME));
	}
/*
	public static void FireGlovesTickHandler(PlayerTickEvent.Post event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			DeferredItem<SoulItem> fireGloves = ItemRegister.FireGloves;
			ResourceLocation resourceLocation = fireGloves.getId();
			boolean equipped = CurioUtils.isEquipped(player, fireGloves.get());
			AttributeModifier.Operation addMultipliedBase = AttributeModifier.Operation.ADD_MULTIPLIED_BASE;
			AttributeUtils.ConditionAttributeModifier(player, Attributes.ATTACK_KNOCKBACK, resourceLocation, 1, addMultipliedBase, equipped);
			AttributeUtils.ConditionAttributeModifier(player, Attributes.ATTACK_DAMAGE, resourceLocation, 0.12, addMultipliedBase, equipped);
			AttributeUtils.ConditionAttributeModifier(player, Attributes.ATTACK_SPEED, resourceLocation, 0.12, addMultipliedBase, equipped);
			AttributeUtils.ConditionAttributeModifier(player, Attributes.ENTITY_INTERACTION_RANGE, resourceLocation, 0.5, addMultipliedBase, equipped);
		}
	}

	public static void FireGlovesDamageHandler(LivingIncomingDamageEvent event) {
		if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, ItemRegister.FireGloves.get())) {
			if (event.getEntity() instanceof LivingEntity livingEntity && livingEntity.getRemainingFireTicks() < 120) {
				livingEntity.setRemainingFireTicks(livingEntity.getRemainingFireTicks() + 60);
			}
		}
	}
*/



}
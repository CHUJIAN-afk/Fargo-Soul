package First.fargo_soul.item.universeSoul.BerserkerSoul.Soul;

import First.fargo_soul.item.base.SoulItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class BerserkerGloves extends SoulItem {

	public BerserkerGloves(Properties properties) {
		super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PINK));
	}
/*
	public static void BerserkerGlovesTickHandler(PlayerTickEvent.Post event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			DeferredItem<SoulItem> berserkerGloves = ItemRegister.BerserkerGloves;
			ResourceLocation resourceLocation = berserkerGloves.getId();
			boolean equipped = CurioUtils.isEquipped(player, berserkerGloves.get());
			AttributeModifier.Operation addMultipliedBase = AttributeModifier.Operation.ADD_MULTIPLIED_BASE;
			AttributeUtils.ConditionAttributeModifier(player, Attributes.ARMOR, resourceLocation, 8, AttributeModifier.Operation.ADD_VALUE, equipped);
			AttributeUtils.ConditionAttributeModifier(player, Attributes.ATTACK_KNOCKBACK, resourceLocation, 1, addMultipliedBase, equipped);
			AttributeUtils.ConditionAttributeModifier(player, Attributes.ATTACK_SPEED, resourceLocation, 0.12, addMultipliedBase, equipped);
			AttributeUtils.ConditionAttributeModifier(player, Attributes.ENTITY_INTERACTION_RANGE, resourceLocation, 0.5, addMultipliedBase, equipped);
		}
	}

*/







}

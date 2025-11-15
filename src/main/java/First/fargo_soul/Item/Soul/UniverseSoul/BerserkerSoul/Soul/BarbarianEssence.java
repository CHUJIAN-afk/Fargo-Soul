package First.fargo_soul.Item.Soul.UniverseSoul.BerserkerSoul.Soul;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class BarbarianEssence extends SoulItem {
	public BarbarianEssence(Properties properties) {
		super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.YELLOW));
	}
/*
	public static void BarbarianEssenceTickHandler(PlayerTickEvent.Post event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			DeferredItem<SoulItem> barbarianEssence = SoulsRegister.BarbarianEssence;
			ResourceLocation resourceLocation = barbarianEssence.getId();
			boolean equipped = CurioUtils.isEquipped(player, barbarianEssence.get());
			AttributeModifier.Operation addMultipliedBase = AttributeModifier.Operation.ADD_MULTIPLIED_BASE;
			AttributeUtils.ConditionAttributeModifier(player, AttributeRegister.Damage, resourceLocation, 0.18, addMultipliedBase, equipped);
			AttributeUtils.ConditionAttributeModifier(player, Attributes.ATTACK_SPEED, resourceLocation, 0.10, addMultipliedBase, equipped);
			AttributeUtils.ConditionAttributeModifier(player, AttributeRegister.CriticalChance, resourceLocation, 0.08, addMultipliedBase, equipped);
		}
	}
*/
}

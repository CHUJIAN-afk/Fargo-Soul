package First.fargo_soul.Item.Soul.UniverseSoul.SharpshooterSoul.Soul;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class MarksmanEssence extends SoulItem {

	public MarksmanEssence(Properties properties) {
		super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.LIME));
	}
/*
	public static void MarksmanEssenceTickHandler(PlayerTickEvent.Post event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			DeferredItem<SoulItem> MarksmanEssence = SoulsRegister.MarksmanEssence;
			ResourceLocation resourceLocation = MarksmanEssence.getId();
			boolean equipped = CurioUtils.isEquipped(player, MarksmanEssence.get());
			AttributeModifier.Operation addMultipliedBase = AttributeModifier.Operation.ADD_MULTIPLIED_BASE;
			AttributeUtils.ConditionAttributeModifier(player, AttributeRegister.RangedDamage, resourceLocation, 0.22, addMultipliedBase, equipped);
			AttributeUtils.ConditionAttributeModifier(player, AttributeRegister.RangedSpeed, resourceLocation, 0.20, addMultipliedBase, equipped);
			AttributeUtils.ConditionAttributeModifier(player, AttributeRegister.CriticalChance, resourceLocation, 0.10, addMultipliedBase, equipped);
		}
	}
*/
}

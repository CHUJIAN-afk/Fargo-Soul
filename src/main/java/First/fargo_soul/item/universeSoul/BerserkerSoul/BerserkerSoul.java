package First.fargo_soul.item.universeSoul.BerserkerSoul;

import First.fargo_soul.item.base.SoulItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class BerserkerSoul extends SoulItem {

	public BerserkerSoul(Properties properties) {
		super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PURPLE));
	}
/*
	@Override
	public List<SoulItem> getSoulItemList() {
		return List.of(
				BarbarianEssence.get(),
				BerserkerGloves.get(),
				CelestialShell.get(),
				FireGloves.get(),
				StingerNecklace.get()
		);
	}

	public static void BerserkerSoulTickHandler(PlayerTickEvent.Post event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			DeferredItem<SoulItem> berserkerSoul = ItemRegister.BerserkerSoul;
			ResourceLocation resourceLocation = berserkerSoul.getId();
			boolean equipped = CurioUtils.isEquipped(player, berserkerSoul.get());
			AttributeModifier.Operation addMultipliedBase = AttributeModifier.Operation.ADD_MULTIPLIED_BASE;
			AttributeModifier.Operation addValue = AttributeModifier.Operation.ADD_VALUE;
			AttributeUtils.ConditionAttributeModifier(player, AttributeRegister.Damage, resourceLocation, 0.22, addValue, equipped);
			AttributeUtils.ConditionAttributeModifier(player, AttributeRegister.CriticalChance, resourceLocation, 0.1, addValue, equipped);
			AttributeUtils.ConditionAttributeModifier(player, Attributes.ATTACK_KNOCKBACK, resourceLocation, 1, addMultipliedBase, equipped);
			AttributeUtils.ConditionAttributeModifier(player, Attributes.ATTACK_SPEED, resourceLocation, 0.20, addMultipliedBase, equipped);
		}
	}
*/


}

package First.fargo_soul.item.universeSoul.SharpshooterSoul;

import First.fargo_soul.item.base.SoulItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class SharpshooterSoul extends SoulItem {

	public SharpshooterSoul(Properties properties) {
		super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PURPLE));
	}
/*
	@Override
	public List<SoulItem> getSoulItemList() {
		return List.of(
				MarksmanEssence.get(),
				MeltRocketBag.get(),
				ScoutScope.get(),
				StalkerQuiver.get()
		);
	}

	public static void SharpshooterSoulTickHandler(PlayerTickEvent.Post event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			DeferredItem<SoulItem> SharpshooterSoul = ItemRegister.SharpshooterSoul;
			ResourceLocation resourceLocation = SharpshooterSoul.getId();
			boolean equipped = CurioUtils.isEquipped(player, SharpshooterSoul.get());
			AttributeModifier.Operation addMultipliedBase = AttributeModifier.Operation.ADD_MULTIPLIED_BASE;
			AttributeUtils.ConditionAttributeModifier(player, AttributeRegister.RangedDamage, resourceLocation, 0.2, addMultipliedBase, equipped);
			AttributeUtils.ConditionAttributeModifier(player, AttributeRegister.RangedSpeed, resourceLocation, 0.1, addMultipliedBase, equipped);
		}
	}
*/
}

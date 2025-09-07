package First.fargo_soul.Item.Soul.UniverseSoul;

import First.fargo_soul.Attribute.AttributeRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

import static First.fargo_soul.Item.Soul.SoulsRegister.BerserkerSoul;
import static First.fargo_soul.Item.Soul.SoulsRegister.SharpshooterSoul;

public class UniverseSoul extends SoulItem {

	public UniverseSoul(Properties properties) {
		super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.MASTER));
	}

	@Override
	public List<SoulItem> getSoulItemList() {
		return List.of(
				BerserkerSoul.get(),
				SharpshooterSoul.get()
		);
	}

	public static void UniverseSoulTickHandler(PlayerTickEvent.Post event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			DeferredItem<SoulItem> berserkerSoul = SoulsRegister.BerserkerSoul;
			ResourceLocation resourceLocation = berserkerSoul.getId();
			boolean equipped = CurioUtils.isEquipped(player, berserkerSoul.get());
			AttributeModifier.Operation addMultipliedBase = AttributeModifier.Operation.ADD_MULTIPLIED_BASE;
			AttributeModifier.Operation addValue = AttributeModifier.Operation.ADD_VALUE;
			AttributeModifier.Operation addMultipliedTotal = AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL;
			AttributeUtils.ConditionAttributeModifier(player, AttributeRegister.Damage, resourceLocation, 0.5, addValue, equipped);
			AttributeUtils.ConditionAttributeModifier(player, Attributes.ATTACK_SPEED, resourceLocation, 0.5, addMultipliedBase, equipped);
			AttributeUtils.ConditionAttributeModifier(player, AttributeRegister.CriticalChance, resourceLocation, 0.25, addValue, equipped);
			AttributeUtils.ConditionAttributeModifier(player, Attributes.ATTACK_KNOCKBACK, resourceLocation, 1, addMultipliedTotal, equipped);
		}
	}

}

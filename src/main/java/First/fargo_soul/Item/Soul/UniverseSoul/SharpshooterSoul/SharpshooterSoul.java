package First.fargo_soul.Item.Soul.UniverseSoul.SharpshooterSoul;

import First.fargo_soul.Attribute.AttributeRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

import static First.fargo_soul.Item.Soul.SoulsRegister.*;

public class SharpshooterSoul extends SoulItem {

	public SharpshooterSoul(Properties properties) {
		super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PURPLE));
	}

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
			DeferredItem<SoulItem> SharpshooterSoul = SoulsRegister.SharpshooterSoul;
			ResourceLocation resourceLocation = SharpshooterSoul.getId();
			boolean equipped = CurioUtils.isEquipped(player, SharpshooterSoul.get());
			AttributeModifier.Operation addMultipliedBase = AttributeModifier.Operation.ADD_MULTIPLIED_BASE;
			AttributeUtils.ConditionAttributeModifier(player, AttributeRegister.RangedDamage, resourceLocation, 0.2, addMultipliedBase, equipped);
			AttributeUtils.ConditionAttributeModifier(player, AttributeRegister.RangedSpeed, resourceLocation, 0.1, addMultipliedBase, equipped);
		}
	}

}

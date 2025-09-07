package First.fargo_soul.Item.Soul.UniverseSoul.BerserkerSoul.Soul;

import First.fargo_soul.Attribute.AttributeRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingBreatheEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class CelestialShell extends SoulItem {

	public CelestialShell(Properties properties) {
		super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.YELLOW));
	}

	public static void CelestialShellTickHandler(PlayerTickEvent.Post event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			AttributeModifier.Operation addMultipliedBase = AttributeModifier.Operation.ADD_MULTIPLIED_BASE;
			AttributeModifier.Operation addValue = AttributeModifier.Operation.ADD_VALUE;
			CelestialShellAddAttributeModifiers(player, Attributes.ATTACK_SPEED, 0.1, addMultipliedBase);
			CelestialShellAddAttributeModifiers(player, AttributeRegister.Damage, 0.1, addValue);
			CelestialShellAddAttributeModifiers(player, AttributeRegister.CriticalChance, 0.02, addValue);
			CelestialShellAddAttributeModifiers(player, Attributes.ARMOR, 8, addValue);
			CelestialShellAddAttributeModifiers(player, Attributes.BLOCK_BREAK_SPEED, 0.15, addMultipliedBase);
			CelestialShellNightAddAttributeModifiers(player, AttributeRegister.CriticalChance, 0.02, addValue);
			CelestialShellNightAddAttributeModifiers(player, AttributeRegister.Damage, 0.051, addValue);
			CelestialShellNightAddAttributeModifiers(player, Attributes.ATTACK_SPEED, 0.051, addMultipliedBase);
			CelestialShellNightAddAttributeModifiers(player, Attributes.MOVEMENT_SPEED, 0.05, addMultipliedBase);
			CelestialShellNightAddAttributeModifiers(player, Attributes.ARMOR, 3, addValue);
			CelestialShellNightAddAttributeModifiers(player, Attributes.JUMP_STRENGTH, 0.05, addMultipliedBase);
			if (player.tickCount % 20 == 0 && CurioUtils.isEquipped(player, SoulsRegister.CelestialShell.get())) {
				player.heal(player.serverLevel().isNight() ? 0.75f : 0.5f);
			}
			CelestialShellAddAttributeModifiers(player, Attributes.WATER_MOVEMENT_EFFICIENCY, 0.5, addMultipliedBase);
		}
	}
	public static void CelestialShellBreathHandler(LivingBreatheEvent event) {
		if (event.getEntity() instanceof Player player && CurioUtils.isEquipped(player, SoulsRegister.CelestialShell.get())) {
			event.setCanBreathe(true);
		}
	}

	private static void CelestialShellAddAttributeModifiers(ServerPlayer player, Holder<Attribute> attributeHolder, double amount, AttributeModifier.Operation operation) {
		AttributeUtils.ConditionAttributeModifier(player, attributeHolder, SoulsRegister.CelestialShell.getId(), amount, operation, CurioUtils.isEquipped(player, SoulsRegister.CelestialShell.get()));
	}

	private static void CelestialShellNightAddAttributeModifiers(ServerPlayer player, Holder<Attribute> attributeHolder, double amount, AttributeModifier.Operation operation) {
		AttributeUtils.ConditionAttributeModifier(player, attributeHolder, SoulsRegister.CelestialShell.getId(), amount, operation, CurioUtils.isEquipped(player, SoulsRegister.CelestialShell.get()) && player.serverLevel().isNight());
	}


}
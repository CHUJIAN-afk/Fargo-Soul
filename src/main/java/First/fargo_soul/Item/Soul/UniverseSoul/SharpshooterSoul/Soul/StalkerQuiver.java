package First.fargo_soul.Item.Soul.UniverseSoul.SharpshooterSoul.Soul;

import First.fargo_soul.Attribute.AttributeRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.monster.Monster;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class StalkerQuiver extends SoulItem {

	public StalkerQuiver(Properties properties) {
		super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PINK));
	}

	public static void StalkerQuiverTickHandler(PlayerTickEvent.Post event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			DeferredItem<SoulItem> StalkerQuiver = SoulsRegister.StalkerQuiver;
			ResourceLocation resourceLocation = StalkerQuiver.getId();
			boolean equipped = CurioUtils.isEquipped(player, StalkerQuiver.get());
			AttributeModifier.Operation addMultipliedBase = AttributeModifier.Operation.ADD_MULTIPLIED_BASE;
			AttributeUtils.ConditionAttributeModifier(player, AttributeRegister.RangedDamage, resourceLocation, 0.1, addMultipliedBase, equipped);
			AttributeUtils.ConditionAttributeModifier(player, AttributeRegister.RangedSpeed, resourceLocation, 0.2, addMultipliedBase, equipped);
		}
	}

	public static void StalkerQuiverTargetChangeHandler(LivingChangeTargetEvent event) {
		if (event.getNewAboutToBeSetTarget() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.StalkerQuiver.get())) {
			if (player.isShiftKeyDown() && event.getEntity() instanceof Monster monster) {
				if (monster.distanceTo(player) > 4) {
					event.setCanceled(true);
				}
			}
		}
	}

}

package First.fargo_soul.Item.Soul.UniverseSoul.BerserkerSoul.Soul;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class FireGloves extends SoulItem {

	public FireGloves(Properties properties) {
		super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.LIME));
	}

	public static void FireGlovesTickHandler(PlayerTickEvent.Post event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			DeferredItem<SoulItem> fireGloves = SoulsRegister.FireGloves;
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
		if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.FireGloves.get())) {
			if (event.getEntity() instanceof LivingEntity livingEntity && livingEntity.getRemainingFireTicks() < 120) {
				livingEntity.setRemainingFireTicks(livingEntity.getRemainingFireTicks() + 60);
			}
		}
	}




}
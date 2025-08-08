package First.fargo_soul.Item.Soul.UniverseSoul.SharpshooterSoul.Soul;

import First.fargo_soul.Attribute.AttributeRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

public class StalkerQuiver extends SoulItem {

	public StalkerQuiver(Properties properties) {
		super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PINK));
	}

	public final List<Component> AttributeList = List.of(
			Component.literal("增加10%远程伤害").withStyle(ChatFormatting.BLUE),
			Component.literal("增加10%暴击率").withStyle(ChatFormatting.BLUE),
			Component.literal("潜行时降低敌人发现你的范围").withStyle(ChatFormatting.BLUE)
	);
	public final List<Component> TooltipList = List.of(
			Component.literal("“发现敌人”").withStyle(ChatFormatting.DARK_GRAY)
	);

	@Override
	public List<Component> getAttributeList() {
		return AttributeList;
	}

	@Override
	public List<Component> getTooltipList() {
		return TooltipList;
	}

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

}

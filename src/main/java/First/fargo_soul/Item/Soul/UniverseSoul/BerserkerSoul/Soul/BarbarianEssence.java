package First.fargo_soul.Item.Soul.UniverseSoul.BerserkerSoul.Soul;

import First.fargo_soul.Attribute.AttributeRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

public class BarbarianEssence extends SoulItem {
	public BarbarianEssence(Properties properties) {
		super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.YELLOW));
	}

	public final List<Component> AttributeList = List.of(
			Component.literal("增加18%伤害").withStyle(ChatFormatting.BLUE),
			Component.literal("增加10%攻速").withStyle(ChatFormatting.BLUE),
			Component.literal("增加8%暴击率").withStyle(ChatFormatting.BLUE)
	);

	public final List<Component> TooltipList = List.of(
			Component.literal("“这只是个开始……”").withStyle(ChatFormatting.DARK_GRAY)
	);

	@Override
	public List<Component> getAttributeList() {
		return this.AttributeList;
	}

	@Override
	public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
		tooltips.addAll(AttributeList);
		tooltips.addAll(TooltipList);
		return tooltips;
	}

	public static void BarbarianEssenceTickHandler(PlayerTickEvent.Post event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			ResourceLocation resourceLocation = Utils.FargoResource("BarbarianEssence");
			boolean equipped = CurioUtils.isEquipped(player, SoulsRegister.BarbarianEssence.get());
			AttributeModifier.Operation addMultipliedBase = AttributeModifier.Operation.ADD_MULTIPLIED_BASE;
			AttributeUtils.ConditionAttributeModifier(player, AttributeRegister.Damage, resourceLocation, 0.18, addMultipliedBase, equipped);
			AttributeUtils.ConditionAttributeModifier(player, Attributes.ATTACK_SPEED, resourceLocation, 0.10, addMultipliedBase, equipped);
			AttributeUtils.ConditionAttributeModifier(player, AttributeRegister.CriticalChance, resourceLocation, 0.08, addMultipliedBase, equipped);
		}
	}

}

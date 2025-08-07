package First.fargo_soul.Item.Soul.UniverseSoul.BerserkerSoul.Soul;

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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

public class BerserkerGloves extends SoulItem {

	public BerserkerGloves(Properties properties) {
		super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PINK));
	}

	public final List<Component> AttributeList = List.of(
			Component.literal("防御力提高8").withStyle(ChatFormatting.BLUE),
			Component.literal("提高近战击退力").withStyle(ChatFormatting.BLUE),
			Component.literal("近战速度提高12%").withStyle(ChatFormatting.BLUE),
			Component.literal("加大近战武器的尺寸").withStyle(ChatFormatting.BLUE),
			Component.literal("大幅延长攻击距离").withStyle(ChatFormatting.BLUE)
	);

	@Override
	public List<Component> getAttributeList() {
		return this.AttributeList;
	}

	@Override
	public List<Component> getAttributesTooltip(List<Component> tooltips, Item.TooltipContext context, ItemStack stack) {
		tooltips.addAll(AttributeList);
		tooltips.addAll(TooltipList);
		return tooltips;
	}

	public static void BerserkerGlovesTickHandler(PlayerTickEvent.Post event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			ResourceLocation resourceLocation = Utils.FargoResource("BerserkerGloves");
			boolean equipped = CurioUtils.isEquipped(player, SoulsRegister.BerserkerGloves.get());
			AttributeModifier.Operation addMultipliedBase = AttributeModifier.Operation.ADD_MULTIPLIED_BASE;
			AttributeUtils.ConditionAttributeModifier(player, Attributes.ARMOR, resourceLocation, 8, AttributeModifier.Operation.ADD_VALUE, equipped);
			AttributeUtils.ConditionAttributeModifier(player, Attributes.ATTACK_KNOCKBACK, resourceLocation, 1, addMultipliedBase, equipped);
			AttributeUtils.ConditionAttributeModifier(player, Attributes.ATTACK_SPEED, resourceLocation, 0.12, addMultipliedBase, equipped);
			AttributeUtils.ConditionAttributeModifier(player, Attributes.ENTITY_INTERACTION_RANGE, resourceLocation, 0.5, addMultipliedBase, equipped);
		}
	}









}

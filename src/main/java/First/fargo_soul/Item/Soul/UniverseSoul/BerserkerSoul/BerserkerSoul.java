package First.fargo_soul.Item.Soul.UniverseSoul.BerserkerSoul;

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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static First.fargo_soul.Item.Soul.SoulsRegister.*;

public class BerserkerSoul extends SoulItem {

	public BerserkerSoul(Properties properties) {
		super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PURPLE));
	}

	public final List<SoulItem> soulItemList = List.of(
			BarbarianEssence.get(),
			BerserkerGloves.get(),
			CelestialShell.get(),
			FireGloves.get(),
			StingerNecklace.get()
	);

	public final List<Component> list1 = soulItemList.stream()
					.flatMap(curioItem -> curioItem.getAttributeList().stream())
					.collect(Collectors.toList());


	public final List<Component> list2 = List.of(
			Component.translatable("增加22%伤害").withStyle(ChatFormatting.BLUE),
			Component.translatable("增加20%攻速").withStyle(ChatFormatting.BLUE),
			Component.translatable("增加10%暴击率").withStyle(ChatFormatting.BLUE),
			Component.translatable("增加近战击退").withStyle(ChatFormatting.BLUE)
	);

	public final List<Component> AttributeList = Stream.concat(
			list1.stream(),
			list2.stream()
	).collect(Collectors.toList());


	@Override
	public List<SoulItem> getCurioItemList() {
		return this.soulItemList;
	}

	@Override
	public List<Component> getAttributeList() {
		return this.AttributeList;
	}

	public final List<Component> TooltipList = List.of(
			Component.translatable("“吾之传说生者弗能传颂”").withStyle(ChatFormatting.DARK_GRAY)
	);

	@Override
	public List<Component> getAttributesTooltip(List<Component> tooltips, Item.TooltipContext context, ItemStack stack) {
		tooltips.addAll(AttributeList);
		tooltips.addAll(TooltipList);
		return tooltips;
	}


	public static void BerserkerSoulTickHandler(PlayerTickEvent.Post event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			ResourceLocation resourceLocation = Utils.FargoResource("BerserkerSoul");
			boolean equipped = CurioUtils.isEquipped(player, SoulsRegister.BerserkerSoul.get());
			AttributeModifier.Operation addMultipliedBase = AttributeModifier.Operation.ADD_MULTIPLIED_BASE;
			AttributeModifier.Operation addValue = AttributeModifier.Operation.ADD_VALUE;
			AttributeUtils.ConditionAttributeModifier(player, AttributeRegister.Damage, resourceLocation, 0.22, addValue, equipped);
			AttributeUtils.ConditionAttributeModifier(player, AttributeRegister.CriticalChance, resourceLocation, 0.1, addValue, equipped);
			AttributeUtils.ConditionAttributeModifier(player, Attributes.ATTACK_KNOCKBACK, resourceLocation, 1, addMultipliedBase, equipped);
			AttributeUtils.ConditionAttributeModifier(player, Attributes.ATTACK_SPEED, resourceLocation, 0.20, addMultipliedBase, equipped);
		}
	}



}

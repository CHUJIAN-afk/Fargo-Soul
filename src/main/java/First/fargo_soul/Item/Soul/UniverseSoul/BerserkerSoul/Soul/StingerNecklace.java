package First.fargo_soul.Item.Soul.UniverseSoul.BerserkerSoul.Soul;

import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

public class StingerNecklace extends SoulItem {

	public StingerNecklace(Properties properties) {
		super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PINK));
	}

	public final List<Component> AttributeList = List.of(
			Component.literal("盔甲穿透力提高5点").withStyle(ChatFormatting.BLUE),
			Component.literal("受到伤害后将使用者浸入蜂蜜中").withStyle(ChatFormatting.BLUE)
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


	public static void StingerNecklaceDamageHandler2(LivingDamageEvent event) {
		if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.StingerNecklace.get())) {
			if (player.getEffect(EffectRegister.Honey) instanceof MobEffectInstance mobEffectInstance) {
				player.addEffect(new MobEffectInstance(EffectRegister.Honey, Math.min(mobEffectInstance.getDuration() + 20, 200)));
			} else {
				player.addEffect(new MobEffectInstance(EffectRegister.Honey, 20));
			}
		}
	}



}

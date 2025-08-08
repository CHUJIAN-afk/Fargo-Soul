package First.fargo_soul.Item.Soul.UniverseSoul.SharpshooterSoul;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

import static First.fargo_soul.Item.Soul.SoulsRegister.MarksmanEssence;
import static First.fargo_soul.Item.Soul.SoulsRegister.MeltRocketBag;

public class SharpshooterSoul extends SoulItem {

	public SharpshooterSoul(Properties properties) {
		super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PURPLE));
	}

	@Override
	public List<SoulItem> getSoulItemList() {
		return List.of(
				MarksmanEssence.get(),
				MeltRocketBag.get()
		);
	}

	public final List<Component> TooltipList = List.of(
			Component.literal("“预备，瞄准，开火”").withStyle(ChatFormatting.DARK_GRAY)
	);

	@Override
	public List<Component> getTooltipList() {
		return TooltipList;
	}




}

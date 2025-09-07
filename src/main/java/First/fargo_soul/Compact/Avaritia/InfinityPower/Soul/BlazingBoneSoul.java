package First.fargo_soul.Compact.Avaritia.InfinityPower.Soul;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class BlazingBoneSoul extends SoulItem {

	public BlazingBoneSoul(Properties properties) {
		super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.ORANGE));
	}

}

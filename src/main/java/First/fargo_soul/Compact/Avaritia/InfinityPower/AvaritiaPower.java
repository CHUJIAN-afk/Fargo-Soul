package First.fargo_soul.Compact.Avaritia.InfinityPower;

import First.fargo_soul.Compact.Avaritia.AvaritiaSoulsRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

public class AvaritiaPower extends SoulItem {

	public AvaritiaPower(Properties properties) {
		super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.MASTER));
	}

	@Override
	public List<SoulItem> getSoulItemList() {
		return List.of(
				AvaritiaSoulsRegister.BlazingBone_Soul.get(),
				AvaritiaSoulsRegister.CrystalMatrix_Soul.get(),
				AvaritiaSoulsRegister.Infinity_Soul.get(),
				AvaritiaSoulsRegister.Neutron_Soul.get()
		);
	}
}

package First.fargo_soul.Item.Soul.UniverseSoul.BerserkerSoul.Soul;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class StingerNecklace extends SoulItem {

	public StingerNecklace(Properties properties) {
		super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PINK));
	}
/*
	public static void StingerNecklaceDamageHandler2(LivingDamageEvent event) {
		if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.StingerNecklace.get())) {
			if (player.getEffect(EffectRegister.Honey) instanceof MobEffectInstance mobEffectInstance) {
				player.addEffect(new MobEffectInstance(EffectRegister.Honey, Math.min(mobEffectInstance.getDuration() + 20, 200)));
			} else {
				player.addEffect(new MobEffectInstance(EffectRegister.Honey, 20));
			}
		}
	}
*/


}

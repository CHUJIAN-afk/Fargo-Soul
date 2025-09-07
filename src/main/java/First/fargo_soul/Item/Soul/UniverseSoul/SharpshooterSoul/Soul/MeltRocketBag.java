package First.fargo_soul.Item.Soul.UniverseSoul.SharpshooterSoul.Soul;

import First.fargo_soul.Attribute.AttributeRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class MeltRocketBag extends SoulItem {

	public MeltRocketBag(Properties properties) {
		super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PINK));
	}

	public static void MeltRocketBagTickHandler(PlayerTickEvent.Post event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			DeferredItem<SoulItem> MeltRocketBag = SoulsRegister.MeltRocketBag;
			ResourceLocation resourceLocation = MeltRocketBag.getId();
			boolean equipped = CurioUtils.isEquipped(player, MeltRocketBag.get());
			AttributeModifier.Operation addMultipliedBase = AttributeModifier.Operation.ADD_MULTIPLIED_BASE;
			AttributeUtils.ConditionAttributeModifier(player, AttributeRegister.RangedDamage, resourceLocation, 0.1, addMultipliedBase, equipped);
			AttributeUtils.ConditionAttributeModifier(player, AttributeRegister.RangedSpeed, resourceLocation, 0.2, addMultipliedBase, equipped);
		}
	}

	public static void MeltRocketBagProjectileImpactHandler(ProjectileImpactEvent event) {
		if (event.getProjectile().getOwner() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.MeltRocketBag.get())) {
			if (event.getEntity() instanceof LivingEntity livingEntity && livingEntity.getRemainingFireTicks() == 0) {
				livingEntity.setRemainingFireTicks(60);
			}
		}
	}

}

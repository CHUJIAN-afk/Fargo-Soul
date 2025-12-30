package First.fargo_soul.item.universeSoul.SharpshooterSoul.Soul;

import First.fargo_soul.item.base.SoulItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class ScoutScope extends SoulItem {

	public ScoutScope(Properties properties) {
		super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PINK));
	}
/*
	public static void ScoutScopeTickHandler(PlayerTickEvent.Post event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			DeferredItem<SoulItem> MeltRocketBag = ItemRegister.MeltRocketBag;
			ResourceLocation resourceLocation = MeltRocketBag.getId();
			boolean equipped = CurioUtils.isEquipped(player, MeltRocketBag.get());
			AttributeModifier.Operation addMultipliedBase = AttributeModifier.Operation.ADD_MULTIPLIED_BASE;
			AttributeUtils.ConditionAttributeModifier(player, AttributeRegister.RangedDamage, resourceLocation, 0.1, addMultipliedBase, equipped);
			AttributeUtils.ConditionAttributeModifier(player, AttributeRegister.RangedSpeed, resourceLocation, 0.1, addMultipliedBase, equipped);
		}
	}

	public static void ScoutScopeTargetChangeHandler(LivingChangeTargetEvent event) {
		if (event.getNewAboutToBeSetTarget() instanceof ServerPlayer player && CurioUtils.isEquipped(player, ItemRegister.MeltRocketBag.get())) {
			if (player.isShiftKeyDown() && event.getEntity() instanceof Monster monster) {
				if (monster.distanceTo(player) > 4) {
					event.setCanceled(true);
				}
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void ScoutScopeInputHandler(InputEvent.Key event) {
		if (KeyRegister.ScoutingKey.consumeClick() && Minecraft.getInstance().player instanceof LocalPlayer player && CurioUtils.isEquipped(player, ItemRegister.ScoutScope.get())) {
			player.playSound(SoundEvents.SPYGLASS_USE, 1.0F, 1.0F);
			List<Monster> monsterList = player.level().getEntitiesOfClass(Monster.class, player.getBoundingBox().inflate(16));
			for (Monster monster : monsterList) {
				CompoundTag persistentData = monster.getPersistentData();
				if (!persistentData.getBoolean("ScoutScope")) {
					persistentData.putBoolean("ScoutScope", true);
					ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
					executorService.schedule(() -> persistentData.remove("ScoutScope"), 20, TimeUnit.SECONDS);
				}
			}
		}
	}
*/
}

package First.fargo_soul.Item.Soul.UniverseSoul.SharpshooterSoul.Soul;

import First.fargo_soul.Attribute.AttributeRegister;
import First.fargo_soul.Client.KeyBinding;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.monster.Monster;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScoutScope extends SoulItem {

	public ScoutScope(Properties properties) {
		super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PINK));
	}

	public static void ScoutScopeTickHandler(PlayerTickEvent.Post event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			DeferredItem<SoulItem> MeltRocketBag = SoulsRegister.MeltRocketBag;
			ResourceLocation resourceLocation = MeltRocketBag.getId();
			boolean equipped = CurioUtils.isEquipped(player, MeltRocketBag.get());
			AttributeModifier.Operation addMultipliedBase = AttributeModifier.Operation.ADD_MULTIPLIED_BASE;
			AttributeUtils.ConditionAttributeModifier(player, AttributeRegister.RangedDamage, resourceLocation, 0.1, addMultipliedBase, equipped);
			AttributeUtils.ConditionAttributeModifier(player, AttributeRegister.RangedSpeed, resourceLocation, 0.1, addMultipliedBase, equipped);
		}
	}

	public static void ScoutScopeTargetChangeHandler(LivingChangeTargetEvent event) {
		if (event.getNewAboutToBeSetTarget() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.MeltRocketBag.get())) {
			if (player.isShiftKeyDown() && event.getEntity() instanceof Monster monster) {
				if (monster.distanceTo(player) > 4) {
					event.setCanceled(true);
				}
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void ScoutScopeInputHandler(InputEvent.Key event) {
		if (KeyBinding.ScoutingKey.consumeClick() && Minecraft.getInstance().player instanceof LocalPlayer player && CurioUtils.isEquipped(player, SoulsRegister.ScoutScope.get())) {
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

}

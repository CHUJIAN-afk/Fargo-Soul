package First.fargo_soul.Item.Soul.BaseSoul;

import First.fargo_soul.Client.Tooltip.SoulTooltipComponent;
import First.fargo_soul.DataComponent.DataComponents.SoulComponent;
import First.fargo_soul.DataComponent.DataComponentsRegister;
import First.fargo_soul.Event.SoulCoreItemEntityTickEvent;
import First.fargo_soul.Recipe.RecipeParser;
import com.mojang.datafixers.util.Either;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class SoulCoreItem extends Item {

	public SoulCoreItem(Properties properties) {
		super(properties.stacksTo(1).durability(0).component(ConfluenceMagicLib.MOD_RARITY, ModRarity.MASTER));
	}

	@Override
	public @NotNull Component getName(@NotNull ItemStack stack) {
		if (stack.get(DataComponentsRegister.SoulData.get()) instanceof SoulComponent soulComponent) {
			Item item = soulComponent.item();
			MutableComponent ResultComponent;
			if (item instanceof SoulItem soulItem && soulItem.components().get(ConfluenceMagicLib.MOD_RARITY.get()) instanceof ModRarity modRarity) {
				ResultComponent = Component.translatable(item.getDescriptionId()).withColor(modRarity.color());
				ResultComponent.append(Component.translatable(" 容器").withStyle(ChatFormatting.DARK_RED));
			} else {
				ResultComponent = Component.translatable(item.getDescriptionId()).withStyle(ChatFormatting.BLUE);
			}
			return ResultComponent;
		}
		return super.getName(stack);
	}

	@EventBusSubscriber
	public static class Event {

		@SubscribeEvent
		public static void Tick(PlayerTickEvent.Post event){
			Player player = event.getEntity();
			if (!player.level().isClientSide()) {
				AbstractContainerMenu containerMenu = player.containerMenu;
				NonNullList<Slot> slots = containerMenu.slots;
				for (Slot slot : slots) {
					ItemStack itemStack = slot.getItem();
					DataComponentType<SoulComponent> soulComponent = DataComponentsRegister.SoulData.get();
					if (itemStack.getItem() instanceof SoulCoreItem && !itemStack.has(soulComponent) && RecipeParser.getRandomRecipe() instanceof SoulComponent randomRecipe) {
						itemStack.set(soulComponent, randomRecipe);
					}
				}
			}
		}

		@OnlyIn(Dist.CLIENT)
		@SubscribeEvent
		public static void GatherComponents(RenderTooltipEvent.GatherComponents event) {
			ItemStack itemStack = event.getItemStack();
			if (itemStack.getItem() instanceof SoulCoreItem && itemStack.get(DataComponentsRegister.SoulData.get()) instanceof SoulComponent soulComponent) {
				Item item = soulComponent.item();
				List<Either<FormattedText, TooltipComponent>> tooltipElements = event.getTooltipElements();
				int size = tooltipElements.size();
				if (item instanceof SoulItem soulItem) {
					tooltipElements.add(Math.min(size, 1), Either.right(new SoulTooltipComponent((soulItem.getSoulItemList().size() + 1) * 16, 16, 1.5f, soulItem)));
				} else {
					tooltipElements.add(Math.min(size, 1), Either.right(new SoulTooltipComponent(16, 16, 1.5f, item)));
				}
			}
		}

		@OnlyIn(Dist.CLIENT)
		private static final Map<Integer, List<Component>> Tooltips = new HashMap<>();

		@OnlyIn(Dist.CLIENT)
		@SubscribeEvent
		public static void Tooltip(ItemTooltipEvent event) {
			ItemStack itemStack = event.getItemStack();
			if (itemStack.getItem() instanceof SoulCoreItem) {
				if (itemStack.get(DataComponentsRegister.SoulData.get()) instanceof SoulComponent(
						String levelId,
						String biomeId,
						String blockId,
						String weather,
						String time,
						int minHeight,
						int maxHeight,
						Item item,
						Map<Item, Integer> materials
				)) {
					List<Component> toolTip = event.getToolTip();
					boolean shiftDown = event.getFlags().hasShiftDown();
					int hashCode = item.hashCode() + itemStack.hashCode();
					if (item instanceof SoulItem soulItem) {
						toolTip.add(Component.translatable("key.shift.tooltip.1").withStyle(ChatFormatting.DARK_GRAY).append(Component.literal("Shift").withStyle(shiftDown ? ChatFormatting.WHITE : ChatFormatting.GRAY)).append(Component.translatable("key.shift.tooltip.2").withStyle(ChatFormatting.DARK_GRAY)));
						if (shiftDown) {
							toolTip.addAll(SoulItem.Event.addAttributeList(soulItem));
						}
					}
					if (!(item instanceof SoulItem) || !shiftDown) {
						if (Tooltips.containsKey(hashCode)) {
							toolTip.addAll(Tooltips.get(hashCode));
						} else {// 硬编码字符串声明 - 这些键需要在JSON语言文件中定义
							final String TRANSLATION_KEY_CONDITIONS = "fargo_soul.soul_core.condition.conditions";
							final String TRANSLATION_KEY_LEVEL = "fargo_soul.soul_core.condition.level";
							final String TRANSLATION_KEY_BIOME = "fargo_soul.soul_core.condition.biome";
							final String TRANSLATION_KEY_BLOCK = "fargo_soul.soul_core.condition.block";
							final String TRANSLATION_KEY_WEATHER = "fargo_soul.soul_core.condition.weather";
							final String TRANSLATION_KEY_TIME = "fargo_soul.soul_core.condition.time";
							final String TRANSLATION_KEY_HEIGHT = "fargo_soul.soul_core.condition.height";
							final String TRANSLATION_KEY_MATERIALS = "fargo_soul.soul_core.condition.materials";
							final String TRANSLATION_KEY_SYMBOL = "fargo_soul.soul_core.condition.symbol";

							final String TRANSLATION_KEY_WEATHER_CLEAR = "fargo_soul.soul_core.weather.clear";
							final String TRANSLATION_KEY_WEATHER_RAIN = "fargo_soul.soul_core.weather.rain";
							final String TRANSLATION_KEY_WEATHER_THUNDER = "fargo_soul.soul_core.weather.thunder";

							final String TRANSLATION_KEY_TIME_DAWN = "fargo_soul.soul_core.time.dawn";
							final String TRANSLATION_KEY_TIME_NOON = "fargo_soul.soul_core.time.noon";
							final String TRANSLATION_KEY_TIME_DUSK = "fargo_soul.soul_core.time.dusk";
							final String TRANSLATION_KEY_TIME_MIDNIGHT = "fargo_soul.soul_core.time.midnight";

							List<Component> toolTips = new ArrayList<>();
							String string = Component.translatable(TRANSLATION_KEY_SYMBOL).getString();
							MutableComponent levelCondition = Component.translatable(TRANSLATION_KEY_LEVEL);
							MutableComponent biomeCondition = Component.translatable(TRANSLATION_KEY_BIOME);
							MutableComponent blockCondition = Component.translatable(TRANSLATION_KEY_BLOCK);
							MutableComponent weatherCondition = Component.translatable(TRANSLATION_KEY_WEATHER);
							MutableComponent timeCondition = Component.translatable(TRANSLATION_KEY_TIME);
							MutableComponent heightCondition = Component.translatable(TRANSLATION_KEY_HEIGHT);

							if (!levelId.equals("any")) {
								String dimensionTranslationKey = "dimension." + levelId.replace(':', '.');
								MutableComponent component = levelCondition.withStyle(ChatFormatting.GRAY);
								component.append(Component.translatable(dimensionTranslationKey).withStyle(ChatFormatting.GOLD));
								toolTips.add(component);
							}
							if (!biomeId.equals("any")) {
								String biomeTranslationKey = "biome." + biomeId.replace(':', '.');
								MutableComponent component = biomeCondition.withStyle(ChatFormatting.GRAY);
								component.append(Component.translatable(biomeTranslationKey).withStyle(ChatFormatting.DARK_GREEN));
								toolTips.add(component);
							}
							if (!blockId.equals("any")) {
								MutableComponent component = blockCondition.withStyle(ChatFormatting.GRAY);
								component.append(BuiltInRegistries.BLOCK.get(ResourceLocation.parse(blockId)).getName().withStyle(ChatFormatting.LIGHT_PURPLE));
								toolTips.add(component);
							}
							if (!weather.equals("any")) {
								String weatherTranslationKey = switch (weather) {
									case "clear" -> TRANSLATION_KEY_WEATHER_CLEAR;
									case "rain" -> TRANSLATION_KEY_WEATHER_RAIN;
									case "thunder" -> TRANSLATION_KEY_WEATHER_THUNDER;
									default -> "";
								};
								ChatFormatting weatherColor = switch (weather) {
									case "clear" -> ChatFormatting.YELLOW;
									case "rain" -> ChatFormatting.AQUA;
									case "thunder" -> ChatFormatting.DARK_PURPLE;
									default -> ChatFormatting.WHITE;
								};
								MutableComponent component = weatherCondition.withStyle(ChatFormatting.GRAY);
								component.append(Component.translatable(weatherTranslationKey).withStyle(weatherColor));
								toolTips.add(component);
							}
							if (!time.equals("any")) {
								String timeTranslationKey = switch (time) {
									case "dawn" -> TRANSLATION_KEY_TIME_DAWN;
									case "noon" -> TRANSLATION_KEY_TIME_NOON;
									case "dusk" -> TRANSLATION_KEY_TIME_DUSK;
									case "midnight" -> TRANSLATION_KEY_TIME_MIDNIGHT;
									default -> "";
								};
								ChatFormatting timeColor = switch (time) {
									case "dawn" -> ChatFormatting.DARK_AQUA;
									case "noon" -> ChatFormatting.YELLOW;
									case "dusk" -> ChatFormatting.GOLD;
									case "midnight" -> ChatFormatting.DARK_BLUE;
									default -> ChatFormatting.WHITE;
								};
								MutableComponent component = timeCondition.withStyle(ChatFormatting.GRAY);
								component.append(Component.translatable(timeTranslationKey).withStyle(timeColor));
								toolTips.add(component);
							}
							if (minHeight < maxHeight) {
								MutableComponent component = heightCondition.withStyle(ChatFormatting.GRAY);
								component.append(Component.literal(String.valueOf(minHeight)).withStyle(ChatFormatting.DARK_GREEN));
								component.append(Component.literal(" ~ ").withStyle(ChatFormatting.GRAY));
								component.append(Component.literal(String.valueOf(maxHeight)).withStyle(ChatFormatting.RED));
								toolTips.add(component);
							}
							if (!toolTips.isEmpty()) {
								toolTips.addFirst(Component.translatable(TRANSLATION_KEY_CONDITIONS).withStyle(ChatFormatting.YELLOW));
							}
							if (!materials.isEmpty()) {
								toolTips.add(Component.translatable(TRANSLATION_KEY_MATERIALS).withStyle(ChatFormatting.YELLOW));
								List<Map.Entry<Item, Integer>> sortedMaterials = materials.entrySet().stream().sorted((entry1, entry2) -> {
									Integer value1 = entry1.getValue();
									Integer value2 = entry2.getValue();
									if (entry1.getKey() instanceof SoulItem) value1--;
									if (entry2.getKey() instanceof SoulItem) value2--;
									return Integer.compare(value1, value2);
								}).toList();
								for (Map.Entry<Item, Integer> sortedMaterial : sortedMaterials) {
									Item key = sortedMaterial.getKey();
									Integer value = sortedMaterial.getValue();
									MutableComponent materialComponent = Component.literal(string + value + "x ").withStyle(ChatFormatting.GRAY);
									MutableComponent translatable = Component.translatable(key.getDescriptionId());
									if (key instanceof SoulItem soulItem && soulItem.components().get(ConfluenceMagicLib.MOD_RARITY.get()) instanceof ModRarity modRarity) {
										translatable.withColor(modRarity.color());
									} else {
										translatable.withStyle(ChatFormatting.WHITE);
									}
									materialComponent.append(translatable);
									toolTips.add(materialComponent);
								}
							}
							Tooltips.put(hashCode, toolTips);
							toolTip.addAll(toolTips);
						}
					}
				}
			}
		}

		@SubscribeEvent
		public static void Tick(SoulCoreItemEntityTickEvent event) {
			ItemEntity itemEntity = event.getItemEntity();
			Level level = itemEntity.level();
			if (!level.isClientSide()) {
				ItemStack itemStack = itemEntity.getItem();
				DataComponentType<SoulComponent> soulComponent = DataComponentsRegister.SoulData.get();
				if (itemStack.get(soulComponent) instanceof SoulComponent(
						String levelId,
						String biomeId,
						String blockId,
						String weather,
						String time,
						int minHeight,
						int maxHeight,
						Item item,
						Map<Item, Integer> materials
				)) {
					boolean condition1 = levelId.equals("any") || level.dimension().location().toString().equals(levelId);
					boolean condition2 = biomeId.equals("any") || getBiomeId(level, itemEntity.blockPosition()).equals(biomeId);
					boolean condition3 = blockId.equals("any") || (level.getBlockState(itemEntity.blockPosition()).getBlock().equals(BuiltInRegistries.BLOCK.get(ResourceLocation.parse(blockId))) || level.getBlockState(itemEntity.getOnPos()).getBlock().equals(BuiltInRegistries.BLOCK.get(ResourceLocation.parse(blockId))));
					boolean condition4 = weather.equals("any") || switch (weather) {
						case "clear" -> !level.isRaining();
						case "rain" -> level.isRaining();
						case "thunder" -> level.isThundering();
						default -> false;
					};
					boolean condition5 = time.equals("any") || switch (time) {
						case "dawn" -> level.getDayTime() >= 0 && level.getDayTime() < 1200;
						case "noon" -> level.getDayTime() >= 6000 && level.getDayTime() < 7200;
						case "dusk" -> level.getDayTime() >= 12000 && level.getDayTime() < 13200;
						case "midnight" -> level.getDayTime() >= 18000 && level.getDayTime() < 19200;
						default -> false;
					};
					boolean condition6 = (minHeight > maxHeight) || (itemEntity.getY() > minHeight && itemEntity.getY() < maxHeight);
					if (condition1 && condition2 && condition3 && condition4 && condition5 && condition6) {
						List<ItemEntity> entities = level.getEntitiesOfClass(ItemEntity.class, new AABB(itemEntity.blockPosition()));
						boolean containAll = true;
						for (Item item1 : materials.keySet()) {
							int requiredCount = materials.get(item1);
							int foundCount = 0;
							for (ItemEntity entity : entities) {
								ItemStack entityItem = entity.getItem();
								if (item1.equals(entityItem.getItem())) {
									foundCount += entityItem.getCount();
								}
							}
							if (foundCount < requiredCount) {
								containAll = false;
								break;
							}
						}
						if (containAll) {
							for (Item item1 : materials.keySet()) {
								int toRemove = materials.get(item1);
								Iterator<ItemEntity> iterator = entities.iterator();
								while (iterator.hasNext() && toRemove > 0) {
									ItemEntity entity = iterator.next();
									ItemStack entityItem = entity.getItem();
									if (item1.equals(entityItem.getItem())) {
										int entityCount = entityItem.getCount();
										if (entityCount <= toRemove) {
											toRemove -= entityCount;
											entityItem.shrink(entityCount);
										} else {
											entityItem.shrink(toRemove);
											toRemove = 0;
										}
									}
								}
							}
							itemEntity.setItem(item.getDefaultInstance());
						}
					}
				}
			}
		}

	}

	private static String getBiomeId(Level level, BlockPos blockPos) {
		AtomicReference<String> BiomeId = new AtomicReference<>("");
		Optional<ResourceKey<Biome>> biomeResourceKey = level.getBiome(blockPos).unwrapKey();
		biomeResourceKey.ifPresent(resourceKey -> BiomeId.set(resourceKey.location().toString()));
		return BiomeId.get();
	}

}

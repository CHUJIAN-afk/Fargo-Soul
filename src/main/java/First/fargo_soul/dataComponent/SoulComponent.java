package First.fargo_soul.dataComponent;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public record SoulComponent(
		String levelId,
		String biomeId,
		String blockId,
		String weather,
		String time,
		int MinHeight,
		int MaxHeight,
		Item item,
		Map<Item, Integer> map
) implements DataComponentType<SoulComponent> {

	public static final Codec<SoulComponent> SoulCodec = RecordCodecBuilder.create(instance -> {
		Products.P9<RecordCodecBuilder.Mu<SoulComponent>, String, String, String, String, String, Integer, Integer, String, Map<String, Integer>> data = instance.group(
				Codec.STRING.fieldOf("level").forGetter(SoulComponent::levelId),
				Codec.STRING.fieldOf("biome").forGetter(SoulComponent::biomeId),
				Codec.STRING.fieldOf("block").forGetter(SoulComponent::blockId),
				Codec.STRING.fieldOf("weather").forGetter(SoulComponent::weather),
				Codec.STRING.fieldOf("time").forGetter(SoulComponent::time),
				Codec.INT.fieldOf("minHeight").forGetter(SoulComponent::MinHeight),
				Codec.INT.fieldOf("maxHeight").forGetter(SoulComponent::MaxHeight),
				Codec.STRING.fieldOf("item").forGetter(component -> BuiltInRegistries.ITEM.getKey(component.item).toString()),
				Codec.unboundedMap(Codec.STRING, Codec.INT).fieldOf("map").forGetter(component -> {
					Map<String, Integer> map = new HashMap<>();
					component.map.forEach(((item1, integer) -> map.put(BuiltInRegistries.ITEM.getKey(item1).toString(), integer)));
					return map;
				})
		);
		return data.apply(instance, (level, biome, block, weather, time, MinHeight, MaxHeight, item, itemList) -> {
			SoulComponent soulComponent;
			Item Item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(item));
			Map<Item, Integer> hashMap = new HashMap<>();
			itemList.forEach((itemId, size) -> hashMap.put(BuiltInRegistries.ITEM.get(ResourceLocation.parse(itemId)), size));
			soulComponent = new SoulComponent(
					level,
					biome,
					block,
					weather,
					time,
					MinHeight,
					MaxHeight,
					Item,
					hashMap
			);
			return soulComponent;
		});
	});

	public static final StreamCodec<RegistryFriendlyByteBuf, SoulComponent> SoulStreamCodec = new StreamCodec<>() {
		@Override
		public @NotNull SoulComponent decode(@NotNull RegistryFriendlyByteBuf buf) {
			String level = ByteBufCodecs.STRING_UTF8.decode(buf);
			String biome = ByteBufCodecs.STRING_UTF8.decode(buf);
			String block = ByteBufCodecs.STRING_UTF8.decode(buf);
			String weather = ByteBufCodecs.STRING_UTF8.decode(buf);
			String time = ByteBufCodecs.STRING_UTF8.decode(buf);
			int MinHeight = ByteBufCodecs.INT.decode(buf);
			int MaxHeight = ByteBufCodecs.INT.decode(buf);
			ResourceLocation itemLocation = ResourceLocation.STREAM_CODEC.decode(buf);
			Item item = BuiltInRegistries.ITEM.get(itemLocation);
			int mapSize = buf.readVarInt();
			Map<Item, Integer> materials = new HashMap<>();
			for (int i = 0; i < mapSize; i++) {
				Item mapItem = BuiltInRegistries.ITEM.get(ResourceLocation.STREAM_CODEC.decode(buf));
				int count = buf.readVarInt();
				materials.put(mapItem, count);
			}
			return new SoulComponent(
					level,
					biome,
					block,
					weather,
					time,
					MinHeight,
					MaxHeight,
					item,
					materials
			);
		}

		@Override
		public void encode(@NotNull RegistryFriendlyByteBuf buf, SoulComponent value) {
			ByteBufCodecs.STRING_UTF8.encode(buf, value.levelId());
			ByteBufCodecs.STRING_UTF8.encode(buf, value.biomeId());
			ByteBufCodecs.STRING_UTF8.encode(buf, value.blockId());
			ByteBufCodecs.STRING_UTF8.encode(buf, value.weather());
			ByteBufCodecs.STRING_UTF8.encode(buf, value.time());
			ByteBufCodecs.INT.encode(buf, value.MinHeight());
			ByteBufCodecs.INT.encode(buf, value.MaxHeight());
			ResourceLocation.STREAM_CODEC.encode(buf, BuiltInRegistries.ITEM.getKey(value.item()));
			Map<Item, Integer> map = value.map();
			buf.writeVarInt(map.size());
			for (Map.Entry<Item, Integer> entry : map.entrySet()) {
				ResourceLocation.STREAM_CODEC.encode(buf, BuiltInRegistries.ITEM.getKey(entry.getKey()));
				buf.writeVarInt(entry.getValue());
			}
		}

	};

	@Override
	public @Nullable Codec<SoulComponent> codec() {
		return SoulCodec;
	}

	@Override
	public @NotNull StreamCodec<? super RegistryFriendlyByteBuf, SoulComponent> streamCodec() {
		return SoulStreamCodec;
	}

}

package First.fargo_soul.Attachment.Attachment;

import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SoulAbilityData implements INBTSerializable<CompoundTag> {

	private final Map<String, SoulInfo> SoulInfo = new HashMap<>();
	private final Map<String, SoulInfo> ClientSoulInfo = new HashMap<>();

	public @NotNull SoulInfo getSoulInfo(String id) {
		return SoulInfo.computeIfAbsent(id, map -> new SoulInfo());
	}

	public @NotNull SoulInfo getClientSoulInfo(String id) {
		return ClientSoulInfo.computeIfAbsent(id, map -> new SoulInfo());
	}

	public <T extends SoulItem> SoulInfo getSoulInfo(Class<T> type) {
		return getSoulInfo(type.getName());
	}

	public static <T extends SoulItem> void updateMaxCooldown(LivingEntity attacker, Class<T> type, int maxCooldown) {
		attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(type).maxCooldown = maxCooldown;
	}

	@Override
	public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
		return serialize(SoulInfo);
	}

	@Override
	public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag tag) {
		deserialize(SoulInfo, tag);
	}

	private static @NotNull CompoundTag serialize(Map<String, SoulInfo> SoulInfo) {
		CompoundTag SoulAbilityData = new CompoundTag();
		ListTag instTag = new ListTag();
		SoulInfo.forEach((key, info) -> {
			CompoundTag listTag = new CompoundTag();
			listTag.putInt("cooldown", info.cooldown);
			listTag.putInt("maxCooldown", info.maxCooldown);
			listTag.putInt("duration", info.duration);
			listTag.putInt("durationReduction", info.duration);
			listTag.putInt("minStacks", info.minStacks);
			listTag.putInt("stacks", info.stacks);
			listTag.putInt("maxStacks", info.maxStacks);
			listTag.putBoolean("enabled", info.enabled);
			CompoundTag infoTag = new CompoundTag();
			infoTag.put(key, listTag);
			instTag.add(infoTag);
		});
		SoulAbilityData.put("SoulAbilityData", instTag);
		return SoulAbilityData;
	}

	private static void deserialize(Map<String, SoulInfo> SoulInfo, CompoundTag tag) {
		SoulInfo.clear();
		ListTag soulAbilityData = tag.getList("SoulAbilityData", Tag.TAG_COMPOUND);
		for (Tag t : soulAbilityData) {
			if (t instanceof CompoundTag infoTag) {
				for (String key : infoTag.getAllKeys()) {
					CompoundTag listTag = infoTag.getCompound(key);
					SoulInfo info = new SoulInfo();
					info.cooldown = listTag.getInt("cooldown");
					info.maxCooldown = listTag.getInt("maxCooldown");
					info.duration = listTag.getInt("duration");
					info.durationReduction = listTag.getInt("durationReduction");
					info.minStacks = listTag.getInt("minStacks");
					info.stacks = listTag.getInt("stacks");
					info.maxStacks = listTag.getInt("maxStacks");
					info.enabled = listTag.getBoolean("enabled");
					SoulInfo.put(key, info);
				}
			}
		}
	}

	@EventBusSubscriber(modid = Fargo_soul.MODID)
	public static class Event {

		@OnlyIn(Dist.CLIENT)
		@SubscribeEvent(priority = EventPriority.LOWEST)
		public static void Tick(PlayerTickEvent.Post event) {
			Player player = event.getEntity();
			SoulAbilityData soulAbilityData = player.getData(AttachmentRegister.SoulAbilityData);
			Collection<SoulAbilityData.SoulInfo> soulInfos = soulAbilityData.ClientSoulInfo.values();
			for (SoulAbilityData.SoulInfo soulInfo : soulInfos) {
				if (soulInfo.maxCooldown != -1) {
					soulInfo.cooldown = Math.min(soulInfo.cooldown, soulInfo.maxCooldown);
					soulInfo.cooldown = Math.max(--soulInfo.cooldown, 0);
				}
				soulInfo.duration = Math.max(soulInfo.duration - soulInfo.durationReduction, 0);
			}
		}

		@SubscribeEvent(priority = EventPriority.LOWEST)
		public static void Tick(EntityTickEvent.Post event) {
			if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
				SoulAbilityData soulAbilityData = attacker.getData(AttachmentRegister.SoulAbilityData);
				Collection<SoulAbilityData.SoulInfo> soulInfos = soulAbilityData.SoulInfo.values();
				for (SoulAbilityData.SoulInfo soulInfo : soulInfos) {
					if (soulInfo.maxCooldown != -1) {
						soulInfo.cooldown = Math.min(soulInfo.cooldown, soulInfo.maxCooldown);
						soulInfo.cooldown = Math.max(--soulInfo.cooldown, 0);
					}
					soulInfo.duration = Math.max(--soulInfo.duration, 0);
				}
				PacketDistributor.sendToAllPlayers(new Packet(attacker.getId(), serialize(soulAbilityData.SoulInfo)));
			}
		}

	}

	public record Packet(int id, CompoundTag tag) implements CustomPacketPayload {

		public static final Type<Packet> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Fargo_soul.MODID, "soul_ability_data"));
		public static final StreamCodec<ByteBuf, Packet> STREAM_CODEC = StreamCodec.composite(
				ByteBufCodecs.INT,
				Packet::id,
				ByteBufCodecs.COMPOUND_TAG,
				Packet::tag,
				Packet::new
		);

		@Override
		public @NotNull Type<? extends CustomPacketPayload> type() {
			return TYPE;
		}

		public void handle(IPayloadContext context) {
			context.enqueueWork(() -> {
				Player player = context.player();
				if (player.isLocalPlayer() && player.level().getEntity(id) instanceof LivingEntity livingEntity) {
					deserialize(livingEntity.getData(AttachmentRegister.SoulAbilityData).SoulInfo, tag);
				}
			});
		}

	}

	public static final class SoulInfo {
		public int cooldown;
		public int maxCooldown;
		public int duration;
		public int durationReduction;
		public int minStacks;
		public int stacks;
		public int maxStacks;
		public boolean enabled;

		public SoulInfo() {
			this.cooldown = -1;
			this.maxCooldown = -1;
			this.duration = 0;
			this.durationReduction = 1;
			this.minStacks = 0;
			this.stacks = 0;
			this.maxStacks = 0;
			this.enabled = false;
		}

		public void removeStacks() {
			stacks = 0;
		}

		public void addStacks() {
			addStacks(1);
		}

		public void addStacks(int amount) {
			stacks = Math.min(stacks + amount, maxStacks);
		}

		public void shrinkStacks() {
			shrinkStacks(1);
		}

		public void shrinkStacks(int amount) {
			stacks = Math.max(stacks - amount, minStacks);
		}

		public float getStackPercentage() {
			return maxStacks > 0 ? (float) stacks / maxStacks : 0f;
		}

	}

}

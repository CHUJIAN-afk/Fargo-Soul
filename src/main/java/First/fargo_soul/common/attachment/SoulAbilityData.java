package First.fargo_soul.common.attachment;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.register.AttachmentRegister;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EventBusSubscriber(modid = FargoSoul.MODID)
public class SoulAbilityData implements INBTSerializable<CompoundTag>, AttachmentSyncHandler<SoulAbilityData> {

	private final Map<String, SoulInfo> SoulInfoList;

	public SoulAbilityData() {
		this.SoulInfoList = new HashMap<>();
	}

	public SoulAbilityData(Map<String, SoulInfo> SoulInfoList) {
		this.SoulInfoList = SoulInfoList;
	}

	public static SoulAbilityData getSoulAbilityData(LivingEntity livingEntity) {
		return livingEntity.getData(AttachmentRegister.SoulAbilityData);
	}

	public static SoulInfo getSoulInfo(LivingEntity livingEntity, String id) {
		return getSoulAbilityData(livingEntity).getSoulInfo(id);
	}

	public static <T extends SoulItem> SoulInfo getSoulInfo(LivingEntity livingEntity, Class<T> type) {
		return getSoulAbilityData(livingEntity).getSoulInfo(type.getName());
	}

	public Map<String, SoulInfo> getSoulInfoList() {
		return SoulInfoList;
	}

	public <T extends SoulItem> SoulInfo getSoulInfo(Class<T> type) {
		return getSoulInfo(type.getName(), false);
	}

	public @NotNull SoulInfo getSoulInfo(String id) {
		return getSoulInfo(id, false);
	}

	public @NotNull SoulInfo getSoulInfo(String id, boolean client) {
		return SoulInfoList.computeIfAbsent(id, map -> new SoulInfo(client));
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void update(EntityTickEvent.Post event) {
		if (event.getEntity() instanceof LivingEntity ticker && ticker.hasData(AttachmentRegister.SoulAbilityData)) {
			Map<String, SoulInfo> soulInfoList = ticker.getData(AttachmentRegister.SoulAbilityData).getSoulInfoList();
			Level level = ticker.level();
			boolean syncData = false;
			for (SoulInfo soulInfo : soulInfoList.values()) {
				if ((level.isClientSide() && soulInfo.isClient() && ticker instanceof Player) || (!level.isClientSide() && !soulInfo.isClient())) {
					if (soulInfo.getMaxCooldown() != -1) soulInfo.shrinkCooldown();
					if (soulInfo.getStacks() > soulInfo.getMaxStacks()) soulInfo.setStacks(soulInfo.getMaxStacks());
					if (soulInfo.getDuration() > 0) soulInfo.shrinkDuration();
					if (!syncData && soulInfo.isChange()) syncData = true;
				}
			}
			if (!level.isClientSide() && syncData) {
				ticker.syncData(AttachmentRegister.SoulAbilityData);
			}
		}
	}

	@Override
	public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
		CompoundTag SoulAbilityData = new CompoundTag();
		ListTag instTag = new ListTag();
		SoulInfoList.forEach((key, info) -> {
			CompoundTag listTag = new CompoundTag();
			listTag.putInt("cooldown", info.cooldown);
			listTag.putInt("maxCooldown", info.maxCooldown);
			listTag.putInt("duration", info.duration);
			listTag.putInt("maxDuration", info.maxDuration);
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

	@Override
	public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag tag) {
		SoulInfoList.clear();
		ListTag soulAbilityData = tag.getList("SoulAbilityData", Tag.TAG_COMPOUND);
		for (Tag t : soulAbilityData) {
			if (t instanceof CompoundTag infoTag) {
				for (String key : infoTag.getAllKeys()) {
					CompoundTag listTag = infoTag.getCompound(key);
					SoulInfo info = new SoulInfo();
					info.cooldown = listTag.getInt("cooldown");
					info.maxCooldown = listTag.getInt("maxCooldown");
					info.duration = listTag.getInt("duration");
					info.maxDuration = listTag.getInt("maxDuration");
					info.minStacks = listTag.getInt("minStacks");
					info.stacks = listTag.getInt("stacks");
					info.maxStacks = listTag.getInt("maxStacks");
					info.enabled = listTag.getBoolean("enabled");
					SoulInfoList.put(key, info);
				}
			}
		}
	}

	@Override
	public void write(@NotNull RegistryFriendlyByteBuf buf, @NotNull SoulAbilityData data, boolean clientPacket) {
		List<Map.Entry<String, SoulInfo>> entryList = data.SoulInfoList.entrySet().stream().filter(entry -> entry.getValue().isChange()).toList();
		buf.writeVarInt(entryList.size());
		for (Map.Entry<String, SoulInfo> entry : entryList) {
			String key = entry.getKey();
			SoulInfo info = entry.getValue();
			info.setChange(false);
			buf.writeUtf(key);
			buf.writeVarInt(info.cooldown);
			buf.writeVarInt(info.maxCooldown);
			buf.writeVarInt(info.duration);
			buf.writeVarInt(info.maxDuration);
			buf.writeVarInt(info.minStacks);
			buf.writeVarInt(info.stacks);
			buf.writeVarInt(info.maxStacks);
			buf.writeBoolean(info.enabled);
		}
	}

	@Override
	public @Nullable SoulAbilityData read(@NotNull IAttachmentHolder holder, @NotNull RegistryFriendlyByteBuf buf, @Nullable SoulAbilityData data) {
		Map<String, SoulInfo> soulInfo = new HashMap<>();
		int size = buf.readVarInt();
		for (int i = 0; i < size; i++) {
			String key = buf.readUtf();
			SoulInfo info = new SoulInfo();
			info.cooldown = buf.readVarInt();
			info.maxCooldown = buf.readVarInt();
			info.duration = buf.readVarInt();
			info.maxDuration = buf.readVarInt();
			info.minStacks = buf.readVarInt();
			info.stacks = buf.readVarInt();
			info.maxStacks = buf.readVarInt();
			info.enabled = buf.readBoolean();
			soulInfo.put(key, info);
		}
		SoulAbilityData result = data != null ? data : new SoulAbilityData();
		result.SoulInfoList.putAll(soulInfo);
		return result;
	}

	public static class SoulInfo {
		private int cooldown;
		private int maxCooldown;
		private int duration;
		private int maxDuration;
		private int minStacks;
		private int stacks;
		private int maxStacks;
		private boolean enabled;
		private boolean change;
		private final boolean client;

		public SoulInfo() {
			this(false);
		}

		public SoulInfo(boolean client) {
			this.cooldown = -1;
			this.maxCooldown = -1;
			this.duration = 0;
			this.maxDuration = 0;
			this.minStacks = 0;
			this.stacks = 0;
			this.maxStacks = 0;
			this.enabled = false;
			this.change = true;
			this.client = client;
		}

		public boolean isClient() {
			return client;
		}

		public boolean isReady() {
			return cooldown == 0;
		}

		public int getCooldown() {
			return cooldown;
		}

		public void setCooldown(int cooldown) {
			int target = Math.min(Math.max(cooldown, 0), maxCooldown);
			if (this.cooldown != target) {
				this.cooldown = target;
				this.change = true;
			}
		}

		public void shrinkCooldown(int amount) {
			setCooldown(cooldown - amount);
		}

		public void shrinkCooldown() {
			shrinkCooldown(1);
		}

		public int getMaxCooldown() {
			return maxCooldown;
		}

		public void setMaxCooldown(int maxCooldown) {
			if (this.maxCooldown != maxCooldown) {
				this.maxCooldown = maxCooldown;
				this.change = true;
			}
		}

		public int getDuration() {
			return duration;
		}

		public void shrinkDuration() {
			shrinkDuration(1);
		}

		public void shrinkDuration(int shrink) {
			setDuration(getDuration() - shrink);
		}

		public void setDuration(int duration) {
			if (duration >= 0 && this.duration != duration) {
				this.duration = duration;
				if (duration > maxDuration) {
					setMaxDuration(duration);
				}
				this.change = true;
			}
		}

		public int getMinStacks() {
			return minStacks;
		}

		public void setMinStacks(int minStacks) {
			if (this.minStacks != minStacks) {
				this.minStacks = minStacks;
				this.change = true;
			}
		}

		public void removeStacks() {
			setStacks(0);
		}

		public int getStacks() {
			return stacks;
		}

		public void setStacks(int stacks) {
			int target = Math.min(Math.max(stacks, minStacks), maxStacks);
			if (this.stacks != target) {
				this.stacks = target;
				this.change = true;
			}
		}

		public void addStacks(int amount) {
			setStacks(stacks + amount);
		}

		public void addStacks() {
			addStacks(1);
		}

		public void shrinkStacks(int amount) {
			setStacks(stacks - amount);
		}

		public void shrinkStacks() {
			shrinkStacks(1);
		}

		public float getStackPercentage() {
			return maxStacks > 0 ? (float) stacks / maxStacks : 0f;
		}

		public int getMaxStacks() {
			return maxStacks;
		}

		public void setMaxStacks(int maxStacks) {
			if (this.maxStacks != maxStacks) {
				this.maxStacks = maxStacks;
				this.change = true;
			}
		}

		public boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(boolean enabled) {
			if (this.enabled != enabled) {
				this.enabled = enabled;
				this.change = true;
			}
		}

		public boolean isChange() {
			return change;
		}

		public void setChange(boolean change) {
			this.change = change;
		}

        public int getMaxDuration() {
            return maxDuration;
		}

		public void setMaxDuration(int maxDuration) {
			if (this.maxDuration != maxDuration) {
				this.maxDuration = maxDuration;
				this.change = true;
			}
		}
	}

}

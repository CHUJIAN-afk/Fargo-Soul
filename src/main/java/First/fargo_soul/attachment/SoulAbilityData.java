package First.fargo_soul.attachment;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.register.AttachmentRegister;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

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
		if (event.getEntity() instanceof LivingEntity attacker && attacker.hasData(AttachmentRegister.SoulAbilityData)) {
			Map<String, SoulInfo> soulInfoList = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfoList();
			if (attacker.level().isClientSide() && attacker instanceof Player) {
				soulInfoList.values().stream()
						.filter(SoulInfo::isClient)
						.forEach(soulInfo -> {
							if (soulInfo.getMaxCooldown() != -1) {
								soulInfo.shrinkCooldown();
							}
							if (soulInfo.getDuration() > 0) {
								soulInfo.shrinkDuration();
							}
						});
			}
			if (!attacker.level().isClientSide()) {
				AtomicBoolean syncData = new AtomicBoolean(false);
				soulInfoList.values().stream()
						.filter(soulInfo -> !soulInfo.isClient())
						.forEach(soulInfo -> {
							if (soulInfo.getMaxCooldown() != -1) {
								soulInfo.shrinkCooldown();
							}
							if (soulInfo.getDuration() > 0) {
								soulInfo.shrinkDuration();
							}
							if (soulInfo.isChange()) {
								soulInfo.setChange(false);
								syncData.set(true);
							}
						});
				int tickCount = attacker instanceof Player ? 2 : 20;
				if (syncData.get() && attacker.tickCount % tickCount == 0) {
					attacker.syncData(AttachmentRegister.SoulAbilityData);
				}
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
		Map<String, SoulInfo> soulInfo = data.SoulInfoList;
		buf.writeVarInt(soulInfo.size());
		soulInfo.forEach((key, info) -> {
			buf.writeUtf(key);
			buf.writeVarInt(info.cooldown);
			buf.writeVarInt(info.maxCooldown);
			buf.writeVarInt(info.duration);
			buf.writeVarInt(info.minStacks);
			buf.writeVarInt(info.stacks);
			buf.writeVarInt(info.maxStacks);
			buf.writeBoolean(info.enabled);
		});
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
			info.minStacks = buf.readVarInt();
			info.stacks = buf.readVarInt();
			info.maxStacks = buf.readVarInt();
			info.enabled = buf.readBoolean();
			soulInfo.put(key, info);
		}
		if (data != null) {
			data.SoulInfoList.putAll(soulInfo);
			return data;
		} else {
			return new SoulAbilityData(soulInfo);
		}
	}

	public static class SoulInfo {
		private int cooldown;
		private int maxCooldown;
		private int duration;
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
			if(this.enabled != enabled) {
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

	}

}

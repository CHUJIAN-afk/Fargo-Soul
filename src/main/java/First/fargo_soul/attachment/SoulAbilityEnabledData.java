package First.fargo_soul.attachment;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.register.AttachmentRegister;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class SoulAbilityEnabledData implements INBTSerializable<CompoundTag>, AttachmentSyncHandler<SoulAbilityEnabledData> {

    private final Map<SoulItem, Boolean> abilityList;

    public SoulAbilityEnabledData() {
        this.abilityList = new HashMap<>();
    }

    public SoulAbilityEnabledData(Map<SoulItem, Boolean> abilityList) {
        this.abilityList = abilityList;
    }

    public boolean isEnabled(SoulItem soulItem) {
        return abilityList.computeIfAbsent(soulItem, item -> true);
    }

    public void setEnabled(SoulItem soulItem, boolean enabled) {
        abilityList.put(soulItem, enabled);
    }

    public Map<SoulItem, Boolean> getAbility() {
        return abilityList;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        CompoundTag compoundTag = new CompoundTag();
        ListTag listTag = new ListTag();
        for (Map.Entry<SoulItem, Boolean> entry : abilityList.entrySet()) {
            CompoundTag tag = new CompoundTag();
            String string = BuiltInRegistries.ITEM.getKey(entry.getKey()).toString();
            tag.putString("item", string);
            tag.putBoolean("enabled", entry.getValue());
            listTag.add(tag);
        }
        compoundTag.put("SoulAbilityEnabledData", listTag);
        return compoundTag;
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag compoundTag) {
        if (compoundTag.contains("SoulAbilityEnabledData", Tag.TAG_LIST)) {
            ListTag listTag = compoundTag.getList("SoulAbilityEnabledData", Tag.TAG_COMPOUND);
            for (Tag tag : listTag) {
                if (tag instanceof CompoundTag compound) {
                    String itemKey = compound.getString("item");
                    boolean enabled = compound.getBoolean("enabled");
                    Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(itemKey));
                    if (item instanceof SoulItem soulItem) {
                        abilityList.put(soulItem, enabled);
                    }
                }
            }
        }
    }

    @Override
    public void write(RegistryFriendlyByteBuf buf, SoulAbilityEnabledData data, boolean clientPacket) {
        buf.writeVarInt(data.abilityList.size());
        for (Map.Entry<SoulItem, Boolean> entry : data.abilityList.entrySet()) {
            ResourceLocation key = BuiltInRegistries.ITEM.getKey(entry.getKey());
            buf.writeResourceLocation(key);
            buf.writeBoolean(entry.getValue());
        }
    }

    @Override
    public @Nullable SoulAbilityEnabledData read(@NotNull IAttachmentHolder holder, @NotNull RegistryFriendlyByteBuf buf, @Nullable SoulAbilityEnabledData data) {
        Map<SoulItem, Boolean> abilityList = new HashMap<>();
        int size = buf.readVarInt();
        for (int i = 0; i < size; i++) {
            ResourceLocation key = buf.readResourceLocation();
            boolean enabled = buf.readBoolean();
            Item item = BuiltInRegistries.ITEM.get(key);
            if (item instanceof SoulItem soulItem) {
                abilityList.put(soulItem, enabled);
            }
        }
        return new SoulAbilityEnabledData(abilityList);
    }

    @EventBusSubscriber(modid = FargoSoul.MODID)
    public static class Event {

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void death(PlayerEvent.Clone event) {
            if (event.isWasDeath()) {
                SoulAbilityEnabledData original = event.getOriginal().getData(AttachmentRegister.AbilityEnabledData);
                event.getEntity().setData(AttachmentRegister.AbilityEnabledData, original);
            }
        }

    }

}




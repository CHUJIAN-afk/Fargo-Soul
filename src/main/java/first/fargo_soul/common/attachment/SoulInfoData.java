package first.fargo_soul.common.attachment;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.soulInfo.SoulInfo;
import first.fargo_soul.common.soulInfo.SoulInfoType;
import first.fargo_soul.register.FargoSoulAttachmentRegister;
import first.fargo_soul.register.FargoSoulRegisters;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
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
import java.util.function.Supplier;

@EventBusSubscriber(modid = FargoSoul.MODID)
public record SoulInfoData(Map<SoulInfoType<?>, SoulInfo> data) implements AttachmentSyncHandler<SoulInfoData>, INBTSerializable<CompoundTag> {

    @SuppressWarnings("unchecked")
    @Nullable
    public static <T extends SoulInfo> T getSoulInfo(LivingEntity living, Supplier<SoulInfoType<T>> type) {
        return (T) living.getData(FargoSoulAttachmentRegister.SOUL_INFO_DATA).data().get(type.get());
    }

    public static void putSoulInfo(LivingEntity living, SoulInfo soulInfo) {
        living.getData(FargoSoulAttachmentRegister.SOUL_INFO_DATA).data().put(soulInfo.getType(), soulInfo);
    }

    public static void removeSoulInfo(LivingEntity living, Supplier<SoulInfoType<?>> type) {
        living.getData(FargoSoulAttachmentRegister.SOUL_INFO_DATA).data().remove(type.get());
    }

    @SubscribeEvent
    public static void tick(EntityTickEvent.Post event) {
        if (event.getEntity() instanceof LivingEntity living) {
            Map<SoulInfoType<?>, SoulInfo> data = living.getData(FargoSoulAttachmentRegister.SOUL_INFO_DATA).data();
            if (!living.level().isClientSide()){
                data.values().removeIf(soulInfo -> {
                    soulInfo.tick(living);
                    boolean remove = soulInfo.isRemove();
                    if (remove) {
                        soulInfo.onRemove(living);
                    }
                    return remove;
                });
                living.syncData(FargoSoulAttachmentRegister.SOUL_INFO_DATA);
            } else {
                data.values().removeIf(soulInfo -> {
                    if (soulInfo.getType().isClientSide()) {
                        soulInfo.tick(living);
                        boolean remove = soulInfo.isRemove();
                        if (remove) {
                            soulInfo.onRemove(living);
                        }
                        return remove;
                    }
                    return false;
                });
            }
        }
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        CompoundTag tag = new CompoundTag();

        for (Map.Entry<SoulInfoType<?>, SoulInfo> entry : data.entrySet()) {
            if (entry.getKey().isClientSide()) {
                continue;
            }
            ResourceLocation key = FargoSoulRegisters.SOUL_INFO_TYPE.getKey(entry.getKey());
            if (key == null) {
                continue;
            }
            tag.put(key.toString(), entry.getValue().serializeNBT(provider));
        }

        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag tag) {
        data.keySet().removeIf(soulInfoType -> !soulInfoType.isClientSide());

        for (String key : tag.getAllKeys()) {
            SoulInfoType<?> type = FargoSoulRegisters.SOUL_INFO_TYPE.get(ResourceLocation.tryParse(key));
            if (type == null) {
                continue;
            }
            SoulInfo soulInfo = type.supplier().get();
            soulInfo.deserializeNBT(provider, tag.getCompound(key));
            data.put(type, soulInfo);
        }
    }

    @Override
    public void write(@NotNull RegistryFriendlyByteBuf registryFriendlyByteBuf, @NotNull SoulInfoData soulInfoData, boolean b) {
        CompoundTag tag = new CompoundTag();
        for (Map.Entry<SoulInfoType<?>, SoulInfo> entry : soulInfoData.data().entrySet()) {
            if (entry.getKey().isClientSide()) {
                continue;
            }
            ResourceLocation key = FargoSoulRegisters.SOUL_INFO_TYPE.getKey(entry.getKey());
            if (key == null) {
                continue;
            }
            tag.put(key.toString(), entry.getValue().serializeNBT(registryFriendlyByteBuf.registryAccess()));
        }
        registryFriendlyByteBuf.writeNbt(tag);
    }

    @Override
    public @NotNull SoulInfoData read(@NotNull IAttachmentHolder iAttachmentHolder, @NotNull RegistryFriendlyByteBuf registryFriendlyByteBuf, @Nullable SoulInfoData soulInfoData) {
        SoulInfoData infoData = soulInfoData != null ? soulInfoData : new SoulInfoData(new HashMap<>());
        CompoundTag tag = registryFriendlyByteBuf.readNbt();
        infoData.deserializeNBT(registryFriendlyByteBuf.registryAccess(), tag != null ? tag : new CompoundTag());
        return infoData;
    }
}

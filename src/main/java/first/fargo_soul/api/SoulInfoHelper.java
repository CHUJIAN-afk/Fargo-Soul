package first.fargo_soul.api;

import first.fargo_soul.common.attachment.soulInfoData.soulInfo.AbstractSoulInfo;
import first.fargo_soul.common.attachment.soulInfoData.soulInfo.SoulInfoType;
import first.fargo_soul.register.AttachmentRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.function.Supplier;

public class SoulInfoHelper {

    private final LivingEntity living;

    private SoulInfoHelper(LivingEntity living){
        this.living = living;
    }

    public static SoulInfoHelper get(LivingEntity living){
        return new SoulInfoHelper(living);
    }

    @Nullable
    public <T extends AbstractSoulInfo> T getInfo(ResourceLocation location, Supplier<SoulInfoType<T>> supplier) {
        return getInfo(location, supplier.get());
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public <T extends AbstractSoulInfo> T getInfo(ResourceLocation location, SoulInfoType<T> type) {
        return (T) living.getData(AttachmentRegister.SOUL_INFO_DATA)
                .getData()
                .get(type)
                .get(location);
    }

    public void putInfo(ResourceLocation location, AbstractSoulInfo abstractSoulInfo) {
        living.getData(AttachmentRegister.SOUL_INFO_DATA)
                .getData()
                .computeIfAbsent(abstractSoulInfo.getType(), k -> new HashMap<>())
                .put(location, abstractSoulInfo);
    }

    public <T extends AbstractSoulInfo> void removeInfo(ResourceLocation location, Supplier<SoulInfoType<T>> type) {
        AbstractSoulInfo abstractSoulInfo = living.getData(AttachmentRegister.SOUL_INFO_DATA)
                .getData()
                .getOrDefault(type.get(), new HashMap<>())
                .get(location);
        if (abstractSoulInfo != null) {
            abstractSoulInfo.setRemove(true);
        }
    }
}

package first.fargo_soul.common.attachment.soulInfoData;

import first.fargo_soul.common.attachment.soulInfoData.soulInfo.AbstractSoulInfo;
import first.fargo_soul.common.attachment.soulInfoData.soulInfo.SoulInfoType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SoulInfoData {

    private final Map<SoulInfoType<?>, Map<ResourceLocation, AbstractSoulInfo>> data = new HashMap<>();

    public Map<SoulInfoType<?>, Map<ResourceLocation, AbstractSoulInfo>> getData() {
        return data;
    }

    public void tick(LivingEntity living) {
        Collection<Map<ResourceLocation, AbstractSoulInfo>> values = data.values();
        values.removeIf(map -> {
            Collection<AbstractSoulInfo> soulInfos = map.values();
            soulInfos.removeIf(abstractSoulInfo -> {
                abstractSoulInfo.tick(living);
                boolean remove = abstractSoulInfo.isRemove();
                if (remove) {
                    abstractSoulInfo.onRemove(living);
                }
                return remove;
            });
            return map.isEmpty();
        });
    }
}

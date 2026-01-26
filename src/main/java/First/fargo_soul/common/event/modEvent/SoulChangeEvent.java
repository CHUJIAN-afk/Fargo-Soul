package First.fargo_soul.common.event.modEvent;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.Event;

public class SoulChangeEvent extends Event {

    private final LivingEntity livingEntity;

    public SoulChangeEvent(LivingEntity livingEntity) {
        this.livingEntity = livingEntity;
    }

    public LivingEntity getLivingEntity() {
        return livingEntity;
    }

}

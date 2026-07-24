package first.fargo_soul.common.attachment.soulInfoData.soulInfo;

import net.minecraft.world.entity.LivingEntity;

public class DurationSoulInfo extends AbstractSoulInfo {

    private int duration;

    @Override
    public void tick(LivingEntity living) {
        super.tick(living);
        if (--duration <= 0) {
            setRemove(true);
        }
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}

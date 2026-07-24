package first.fargo_soul.common.attachment.soulInfoData.soulInfo;

import net.minecraft.world.entity.LivingEntity;

public abstract class AbstractSoulInfo {

    private boolean remove = false;

    public abstract SoulInfoType<? extends AbstractSoulInfo> getType();

    public void tick(LivingEntity living) {

    }

    public void onRemove(LivingEntity living) {

    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public boolean isRemove() {
        return remove;
    }
}

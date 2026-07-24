package first.fargo_soul.common.attachment.soulInfoData.soulInfo;

import first.fargo_soul.register.SoulInfoRegister;
import net.minecraft.world.entity.LivingEntity;

public class EnabledSoulInfo extends AbstractSoulInfo {

    private boolean enabled = false;

    @Override
    public SoulInfoType<EnabledSoulInfo> getType() {
        return SoulInfoRegister.ENABLED.get();
    }

    @Override
    public void tick(LivingEntity living) {
        super.tick(living);
        if (!enabled) {
            setRemove(true);
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

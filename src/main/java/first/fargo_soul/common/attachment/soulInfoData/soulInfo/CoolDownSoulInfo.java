package first.fargo_soul.common.attachment.soulInfoData.soulInfo;

import net.minecraft.world.entity.LivingEntity;

public class CoolDownSoulInfo extends AbstractSoulInfo {

    private int cooldown;

    @Override
    public void tick(LivingEntity living) {
        super.tick(living);
        if (--cooldown <= 0) {
            setRemove(true);
        }
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
}

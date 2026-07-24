package first.fargo_soul.common.attachment.soulInfoData.soulInfo;

import net.minecraft.world.entity.LivingEntity;

public class StackSoulInfo extends AbstractSoulInfo {

    private int stack = 0;

    @Override
    public void tick(LivingEntity living) {
        super.tick(living);
        if (stack <= 0) {
            setRemove(true);
        }
    }

    public int getStack() {
        return stack;
    }

    public void setStack(int stack) {
        this.stack = stack;
    }
}

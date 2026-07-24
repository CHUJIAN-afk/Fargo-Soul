package first.fargo_soul.api;

import first.fargo_soul.common.attachment.InvincibleData;
import first.fargo_soul.common.attachment.TargetCache;
import first.fargo_soul.register.AttachmentRegister;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Targeting;
import net.minecraft.world.entity.monster.Enemy;

import java.util.ArrayList;
import java.util.List;

public class TargetHelper {

    private final LivingEntity living;

    private TargetHelper(LivingEntity living) {
        this.living = living;
    }

    public static TargetHelper get(LivingEntity living) {
        return new TargetHelper(living);
    }

    public List<LivingEntity> getTargetList(double distance) {
        List<LivingEntity> list = new ArrayList<>();
        TargetCache cache = living.getData(AttachmentRegister.TARGET_CACHE);
        List<LivingEntity> entities = cache.getEntities();
        for (LivingEntity entity : entities) {
            if (cache.getDistance(living, entity) < distance && isTarget(entity)) {
                list.add(entity);
            }
        }
        return list;
    }

    /**
     * 判断生物是否为有效攻击目标
     */
    public boolean isTarget(LivingEntity target) {
        if (target != null && living != target && target.isAlive()) {
            if (living instanceof Enemy && !(target instanceof Enemy)) {
                return true;
            }
            if (target instanceof Targeting targeting && targeting.getTarget() == living) {
                return true;
            }
            if (InvincibleData.get(target).hasAttack(living.getUUID())) {
                return true;
            }
            if (InvincibleData.get(living).hasAttack(target.getUUID())) {
                return true;
            }
        }
        return false;
    }
}

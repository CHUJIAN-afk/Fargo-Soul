package first.fargo_soul.common.entity.goal.terrablade;

import first.fargo_soul.common.entity.TerraBlade;
import first.lyra.common.attachmentEntity.AttachmentEntityGoal;

import java.util.Collections;

public class BladeIdleGoal extends AttachmentEntityGoal<TerraBlade> {

    public BladeIdleGoal(TerraBlade minion) {
        super(minion);
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public void start() {
        minion.attacking = false;
    }

    @Override
    public void tick() {
        minion.setPath(Collections.singletonList(minion.getCurrentPathNode().lerp(minion.getInterpolatedIdleState(1), 0.35f)));
    }
}

package first.fargo_soul.common.entity.goal.terrablade;

import first.lyra.common.minion.MinionGoal;
import first.fargo_soul.common.entity.TerraBlade;

import java.util.Collections;

public class BladeIdleGoal extends MinionGoal<TerraBlade> {

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

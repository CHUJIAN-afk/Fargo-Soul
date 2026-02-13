package First.fargo_soul.common.event.modEvent;

import First.fargo_soul.common.item.base.SoulItem;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

import java.util.List;

public class SoulContainerChangeEvent extends Event implements ICancellableEvent {

    private final Player player;
    private List<SoulItem> targetList;

    public SoulContainerChangeEvent(Player player, List<SoulItem> targetList) {
        this.player = player;
        this.targetList = targetList;
    }

    public Player getPlayer() {
        return player;
    }

    public List<SoulItem> getTargetList() {
        return targetList;
    }

    public void setTargetList(List<SoulItem> targetList) {
        this.targetList = targetList;
    }

}

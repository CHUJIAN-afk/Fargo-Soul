package first.fargo_soul.common.event.modEvent;

import first.fargo_soul.common.item.base.SoulItem;
import net.neoforged.bus.api.Event;

import java.util.List;

public class SoulBranchEvent extends Event {

    private final SoulItem soulItem;
    private final List<SoulItem> origin;
    private List<SoulItem> list;

    public SoulBranchEvent(SoulItem soulItem, List<SoulItem> origin) {
        this.soulItem = soulItem;
        this.origin = origin;
        this.list = origin;
    }

    public SoulItem getSoulItem() {
        return soulItem;
    }

    public List<SoulItem> getOriginList() {
        return origin;
    }

    public List<SoulItem> getList() {
        return list;
    }

    public void setList(List<SoulItem> list) {
        this.list = list;
    }

}

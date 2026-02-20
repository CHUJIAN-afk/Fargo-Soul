package First.fargo_soul.common.attachment;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class SoulDamageData {

    private final ConcurrentHashMap<Long, CopyOnWriteArrayList<String>> damageData;

    public SoulDamageData() {
        this.damageData = new ConcurrentHashMap<>();
    }

    public ConcurrentHashMap<Long, CopyOnWriteArrayList<String>> getDamageData() {
        return damageData;
    }


}

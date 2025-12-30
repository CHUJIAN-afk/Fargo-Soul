package First.fargo_soul.attachment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoulDamageData {

    private final Map<Long, List<String>> damageData;

    public SoulDamageData() {
        this.damageData = new HashMap<>();
    }

    public Map<Long, List<String>> getDamageData() {
        return damageData;
    }


}

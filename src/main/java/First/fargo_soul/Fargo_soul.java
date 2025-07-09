package First.fargo_soul;

import First.fargo_soul.Curios.CuriosRegister;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;


@Mod(Fargo_soul.MODID)
public class Fargo_soul {
    public static final String MODID = "fargo_soul";

    public Fargo_soul(IEventBus eventBus) {
        CuriosRegister.SoulItems.register(eventBus);
        CuriosRegister.CREATIVE_MODE_TAB_DEFERRED_REGISTER.register(eventBus);
    }

}

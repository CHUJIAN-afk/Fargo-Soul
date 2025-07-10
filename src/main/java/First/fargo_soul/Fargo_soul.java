package First.fargo_soul;

import First.fargo_soul.Client.SoulRenderer;
import First.fargo_soul.Curios.Souls;
import First.fargo_soul.Effect.EffectRegister;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import static First.fargo_soul.Curios.Souls.SoulItems;


@Mod(Fargo_soul.MODID)
public class Fargo_soul {
    public static final String MODID = "fargo_soul";

    public Fargo_soul(final IEventBus eventBus) {
        SoulItems.register(eventBus);
        Souls.CREATIVE_MODE_TAB_DEFERRED_REGISTER.register(eventBus);
        EffectRegister.EFFECTS.register(eventBus);
        eventBus.addListener(this::clientSetup);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        SoulItems.getRegistry().get().forEach(soul -> CuriosRendererRegistry.register(soul, SoulRenderer::new));
    }

}

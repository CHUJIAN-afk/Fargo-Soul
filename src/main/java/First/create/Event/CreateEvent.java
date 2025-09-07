package First.create.Event;


import First.fargo_soul.Client.Renderer.PlayerRenderer;
import First.fargo_soul.Fargo_soul;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

public class CreateEvent {

    @EventBusSubscriber(modid = Fargo_soul.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvent {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            //机械动力联动
            if (First.create.Create.CreateCompact.isLoadCreate()) {
                First.create.Create.CreateSoulsRegister.CreateSouls.getRegistry().get().forEach(item -> CuriosRendererRegistry.register(item, PlayerRenderer::new));
            }
        }

    }

}

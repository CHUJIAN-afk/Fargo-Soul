package First.fargo_soul.Client;


import First.fargo_soul.Client.Corlor.MasterColorAnimation;
import First.fargo_soul.Fargo_soul;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = Fargo_soul.MODID,bus = EventBusSubscriber.Bus.GAME,value = Dist.CLIENT)
public class ClientEvent {
    @SubscribeEvent
    public static void clientTick$Post(ClientTickEvent.Pre event) {
        MasterColorAnimation.INSTANCE.updateColor();
    }
}

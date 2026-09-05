package first.fargo_soul.register;


import first.fargo_soul.FargoSoul;
import first.fargo_soul.network.KeyHandlePacket;
import first.fargo_soul.network.SprintPacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = FargoSoul.MODID)
public class FargoSoulNetworkPacketRegister {

    @SubscribeEvent
    public static void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(KeyHandlePacket.TYPE, KeyHandlePacket.STREAM_CODEC, KeyHandlePacket::handle);
        registrar.playToServer(SprintPacket.TYPE, SprintPacket.STREAM_CODEC, SprintPacket::handle);
    }
}

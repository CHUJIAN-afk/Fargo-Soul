package First.fargo_soul.Network;


import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Network.Packet.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = Fargo_soul.MODID, bus = EventBusSubscriber.Bus.MOD)
public class PacketRegister {

    //网络通信注册
    @SubscribeEvent
    public static void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(PenetratingNinjaSoulPacket.TYPE, PenetratingNinjaSoulPacket.STREAM_CODEC, PenetratingNinjaSoulPacket::handle);
        registrar.playToServer(MonkSoulPacket.TYPE, MonkSoulPacket.STREAM_CODEC, MonkSoulPacket::handle);
        registrar.playToServer(GoldSoulPacket.TYPE, GoldSoulPacket.STREAM_CODEC, GoldSoulPacket::handle);
        registrar.playToServer(StardustSoulPacket.TYPE, StardustSoulPacket.STREAM_CODEC, StardustSoulPacket::handle);
        registrar.playToServer(VortexSoulPacket.TYPE, VortexSoulPacket.STREAM_CODEC, VortexSoulPacket::handle);
    }

}

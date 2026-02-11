package First.fargo_soul.register;


import First.fargo_soul.FargoSoul;
import First.fargo_soul.network.KeyHandlePacket;
import First.fargo_soul.network.OpenSoulContainerPacket;
import First.fargo_soul.network.SoulContainerButtonPacket;
import First.fargo_soul.network.SprintPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = FargoSoul.MODID)
public class NetworkPacketRegister {

    @SubscribeEvent
    public static void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(KeyHandlePacket.TYPE, KeyHandlePacket.STREAM_CODEC, KeyHandlePacket::handle);
        registrar.playToServer(SprintPacket.TYPE, SprintPacket.STREAM_CODEC, SprintPacket::handle);
        registrar.playToServer(SoulContainerButtonPacket.TYPE, SoulContainerButtonPacket.STREAM_CODEC, SoulContainerButtonPacket::handle);
        registrar.playToServer(OpenSoulContainerPacket.TYPE, OpenSoulContainerPacket.STREAM_CODEC, OpenSoulContainerPacket::handle);
    }

    public static void playToServer(CustomPacketPayload payload) {
        PacketDistributor.sendToServer(payload);
    }

}

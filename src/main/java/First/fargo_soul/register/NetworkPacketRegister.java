package First.fargo_soul.register;


import First.fargo_soul.FargoSoul;
import First.fargo_soul.client.screen.ConfigScreen;
import First.fargo_soul.item.terraSoul.cosmicPower.StardustSoul;
import First.fargo_soul.item.terraSoul.cosmicPower.VortexSoul;
import First.fargo_soul.item.terraSoul.deathPower.PenetratingNinjaSoul;
import First.fargo_soul.item.terraSoul.spiritPower.ForbiddenSoul;
import First.fargo_soul.item.terraSoul.willPower.GoldSoul;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = FargoSoul.MODID)
public class NetworkPacketRegister {

    @SubscribeEvent
    public static void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(StardustSoul.Packet.TYPE, StardustSoul.Packet.STREAM_CODEC, StardustSoul.Packet::handle);
        registrar.playToServer(VortexSoul.Packet.TYPE, VortexSoul.Packet.STREAM_CODEC, VortexSoul.Packet::handle);
        registrar.playToServer(PenetratingNinjaSoul.Packet.TYPE, PenetratingNinjaSoul.Packet.STREAM_CODEC, PenetratingNinjaSoul.Packet::handle);
        registrar.playToServer(ForbiddenSoul.Packet.TYPE, ForbiddenSoul.Packet.STREAM_CODEC, ForbiddenSoul.Packet::handle);
        registrar.playToServer(GoldSoul.Packet.TYPE, GoldSoul.Packet.STREAM_CODEC, GoldSoul.Packet::handle);
        registrar.playToServer(ConfigScreen.Packet.TYPE, ConfigScreen.Packet.STREAM_CODEC, ConfigScreen.Packet::handle);
    }

}

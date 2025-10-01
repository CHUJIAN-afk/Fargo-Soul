package First.fargo_soul.Network;


import First.fargo_soul.Attachment.Attachment.SoulAbilityData;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.TerraSoul.CosmicPower.SoulStone.StardustSoul;
import First.fargo_soul.Item.Soul.TerraSoul.CosmicPower.SoulStone.VortexSoul;
import First.fargo_soul.Item.Soul.TerraSoul.DeathPower.SoulStone.PenetratingNinjaSoul;
import First.fargo_soul.Item.Soul.TerraSoul.SpiritPower.SoulStone.ForbiddenSoul;
import First.fargo_soul.Item.Soul.TerraSoul.WillPower.Soulstone.GoldSoul;
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

        registrar.playToClient(SoulAbilityData.Packet.TYPE, SoulAbilityData.Packet.STREAM_CODEC, SoulAbilityData.Packet::handle);
        registrar.playToServer(StardustSoul.Packet.TYPE, StardustSoul.Packet.STREAM_CODEC, StardustSoul.Packet::handle);
        registrar.playToServer(VortexSoul.Packet.TYPE, VortexSoul.Packet.STREAM_CODEC, VortexSoul.Packet::handle);
        registrar.playToServer(PenetratingNinjaSoul.Packet.TYPE, PenetratingNinjaSoul.Packet.STREAM_CODEC, PenetratingNinjaSoul.Packet::handle);
        registrar.playToServer(ForbiddenSoul.Packet.TYPE, ForbiddenSoul.Packet.STREAM_CODEC, ForbiddenSoul.Packet::handle);
        registrar.playToServer(GoldSoul.Packet.TYPE, GoldSoul.Packet.STREAM_CODEC, GoldSoul.Packet::handle);

    }

}

package First.fargo_soul.register;


import First.fargo_soul.FargoSoul;
import First.fargo_soul.client.screen.SoulScreen;
import First.fargo_soul.event.modEvent.SprintEvent;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.utils.SoulUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = FargoSoul.MODID)
public class NetworkPacketRegister {

    @SubscribeEvent
    public static void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(KeyPressPacket.TYPE, KeyPressPacket.STREAM_CODEC, KeyPressPacket::handle);
        registrar.playToServer(SprintPacket.TYPE, SprintPacket.STREAM_CODEC, SprintPacket::handle);
        registrar.playToServer(SoulScreen.Packet.TYPE, SoulScreen.Packet.STREAM_CODEC, SoulScreen.Packet::handle);
    }

    public record KeyPressPacket(int key) implements CustomPacketPayload {

        public static final Type<KeyPressPacket> TYPE = new Type<>(FargoSoul.rl("key_press"));
        public static final StreamCodec<ByteBuf, KeyPressPacket> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.INT, KeyPressPacket::key, KeyPressPacket::new);

        @Override
        public @NotNull Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }

        public void handle(IPayloadContext context) {
            context.enqueueWork(() -> {
                Player player = context.player();
                for (SoulItem soulItem : SoulUtils.RegisterSoulList) {
                    soulItem.keyPressed(player, key);
                }
            });
        }

    }

    public record SprintPacket() implements CustomPacketPayload {

        public static final Type<SprintPacket> TYPE = new Type<>(FargoSoul.rl("sprint"));
        public static final StreamCodec<ByteBuf, SprintPacket> STREAM_CODEC = StreamCodec.unit(new SprintPacket());

        @Override
        public @NotNull Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }

        public void handle(IPayloadContext context) {
            context.enqueueWork(() -> {
                Player player = context.player();
                SprintEvent.Server event = new SprintEvent.Server(player);
                NeoForge.EVENT_BUS.post(event);
                for (SoulItem soulItem : SoulUtils.RegisterSoulList) {
                    soulItem.sprintServer(event);
                }
            });
        }

    }

}

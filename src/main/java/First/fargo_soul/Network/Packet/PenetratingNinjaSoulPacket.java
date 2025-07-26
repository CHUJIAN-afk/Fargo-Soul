package First.fargo_soul.Network.Packet;

import First.fargo_soul.Fargo_soul;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public record PenetratingNinjaSoulPacket() implements CustomPacketPayload {

    public static final Type<PenetratingNinjaSoulPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Fargo_soul.MODID, "penetrating_ninja_soul"));
    public static final StreamCodec<ByteBuf, PenetratingNinjaSoulPacket> STREAM_CODEC = CustomPacketPayload.codec(
            PenetratingNinjaSoulPacket::encode,
            PenetratingNinjaSoulPacket::decode
    );

    private void encode(ByteBuf byteBuf) {
    }

    private static PenetratingNinjaSoulPacket decode(ByteBuf byteBuf) {
        return new PenetratingNinjaSoulPacket();
    }

    @Override
    public @NotNull Type<PenetratingNinjaSoulPacket> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer serverPlayer) {
                serverPlayer.noPhysics = true;
                ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                executorService.schedule(() -> {
                    serverPlayer.noPhysics = false;
                }, 1, TimeUnit.SECONDS);
            }
        }).exceptionally(e -> {
            context.disconnect(Component.translatable("neoforge.network.invalid_flow", e.getMessage()));
            return null;
        });
    }
}


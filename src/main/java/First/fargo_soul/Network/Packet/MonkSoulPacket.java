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

public record MonkSoulPacket() implements CustomPacketPayload {

    public static final Type<MonkSoulPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Fargo_soul.MODID, "monk_soul"));
    public static final StreamCodec<ByteBuf, MonkSoulPacket> STREAM_CODEC = CustomPacketPayload.codec(
            MonkSoulPacket::encode,
            MonkSoulPacket::decode
    );

    private void encode(ByteBuf byteBuf) {
    }

    private static MonkSoulPacket decode(ByteBuf byteBuf) {
        return new MonkSoulPacket();
    }

    @Override
    public @NotNull Type<MonkSoulPacket> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer serverPlayer) {
                serverPlayer.getPersistentData().putBoolean("MonkSoulDamage", true);
                serverPlayer.setInvulnerable(true);
                ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                executorService.schedule(() -> {
                    serverPlayer.getPersistentData().remove("MonkSoulDamage");
                    serverPlayer.setInvulnerable(false);
                }, 500, TimeUnit.MILLISECONDS);
            }
        }).exceptionally(e -> {
            context.disconnect(Component.translatable("neoforge.network.invalid_flow", e.getMessage()));
            return null;
        });
    }
}

package First.fargo_soul.Network.Packet;

import First.fargo_soul.Fargo_soul;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public record StardustSoulPacket(boolean isActivated) implements CustomPacketPayload {

    public static final Type<StardustSoulPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Fargo_soul.MODID, "stardust_soul"));
    public static final StreamCodec<ByteBuf, StardustSoulPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            StardustSoulPacket::isActivated,
            StardustSoulPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            if (isActivated && context.player().getServer() instanceof MinecraftServer server && !server.tickRateManager().isFrozen()) {
                server.tickRateManager().setFrozen(true);
                ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                executorService.schedule(() -> server.tickRateManager().setFrozen(false), 6, TimeUnit.SECONDS);
            }
        });
    }
}

package First.fargo_soul.Network.Packet;

import First.fargo_soul.Fargo_soul;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
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

public record StardustSoulPacket() implements CustomPacketPayload {

    public static final Type<StardustSoulPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Fargo_soul.MODID, "stardust_soul"));
    public static final StreamCodec<ByteBuf, StardustSoulPacket> STREAM_CODEC = CustomPacketPayload.codec(
            StardustSoulPacket::encode,
            StardustSoulPacket::decode
    );

    private void encode(@SuppressWarnings("unused") ByteBuf byteBuf) {
    }

    private static StardustSoulPacket decode(@SuppressWarnings("unused") ByteBuf byteBuf) {
        return new StardustSoulPacket();
    }

    @Override
    public @NotNull Type<StardustSoulPacket> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                ServerLevel level = player.serverLevel();
                long gameTime = level.getGameTime();
                long StardustSoul = player.getPersistentData().getLong("StardustSoul");
                if (StardustSoul < gameTime) {
                    player.getPersistentData().putLong("StardustSoul", gameTime + 72000);
                    if (player.getServer() instanceof MinecraftServer server && !server.tickRateManager().isFrozen()) {
                        server.tickRateManager().setFrozen(true);
                        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                        executorService.schedule(() -> server.tickRateManager().setFrozen(false), 6, TimeUnit.SECONDS);
                    }
                }
            }
        }).exceptionally(e -> {
            context.disconnect(Component.translatable("neoforge.network.invalid_flow", e.getMessage()));
            return null;
        });
    }
}

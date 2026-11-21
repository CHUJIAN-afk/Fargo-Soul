package first.fargo_soul.network;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.attachment.SoulItemData;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record KeyHandlePacket(ResourceLocation key) implements CustomPacketPayload {

    public static final Type<KeyHandlePacket> TYPE = new Type<>(FargoSoul.rl("key_handle"));
    public static final StreamCodec<ByteBuf, KeyHandlePacket> STREAM_CODEC = StreamCodec.composite(ResourceLocation.STREAM_CODEC, KeyHandlePacket::key, KeyHandlePacket::new);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            SoulItemData.forEach(player, soulItem -> soulItem.keyHandle(player, key));
        });
    }
}

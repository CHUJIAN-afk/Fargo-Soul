package First.fargo_soul.networkPacket;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.utils.SoulUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

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

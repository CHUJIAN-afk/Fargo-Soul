package First.fargo_soul.networkPacket;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.common.event.modEvent.SprintEvent;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.utils.SoulUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

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

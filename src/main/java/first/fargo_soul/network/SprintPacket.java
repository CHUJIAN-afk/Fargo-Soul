package first.fargo_soul.network;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.attachment.SoulItemData;
import first.fargo_soul.common.entity.Sprint;
import first.lyra.api.LyraHelper;
import io.netty.buffer.ByteBuf;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.network.codec.StreamCodec;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.entity.player.Player;
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
            SoulItemData.forEach(player, soulItem -> soulItem.sprintServer(player));
            Sprint sprint = new Sprint();
            sprint.init(new PathNode(player.getBoundingBox().getCenter(), 0, 0, 0));
            sprint.setOwner(player);
            LyraHelper.get(player).add(sprint);
        });
    }
}

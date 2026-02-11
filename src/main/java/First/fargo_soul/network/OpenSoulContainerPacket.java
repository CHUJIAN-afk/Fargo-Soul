package First.fargo_soul.network;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.common.menu.SoulContainer;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record OpenSoulContainerPacket() implements CustomPacketPayload {

    public static final Type<OpenSoulContainerPacket> TYPE = new Type<>(FargoSoul.rl("open_soul_container"));
    public static final StreamCodec<ByteBuf, OpenSoulContainerPacket> STREAM_CODEC = StreamCodec.unit(new OpenSoulContainerPacket());

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player instanceof ServerPlayer serverPlayer) {
                if (!(serverPlayer.containerMenu instanceof SoulContainer)) {
                    serverPlayer.openMenu(new SimpleMenuProvider(SoulContainer::new, Component.translatable("curios.identifier.soul")));
                } else {
                    serverPlayer.doCloseContainer();
                }
            }
        });
    }

}

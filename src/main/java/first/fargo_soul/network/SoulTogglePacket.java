package first.fargo_soul.network;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.register.FargoSoulAttachmentRegister;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** 客户端关闭魂石配置界面后同步禁用的魂石列表并触发服务端数据刷新。 */
public record SoulTogglePacket(List<ResourceLocation> disabled) implements CustomPacketPayload {

    public static final Type<SoulTogglePacket> TYPE = new Type<>(FargoSoul.rl("soul_toggle"));
    public static final StreamCodec<ByteBuf, SoulTogglePacket> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC.apply(ByteBufCodecs.list()),
            SoulTogglePacket::disabled,
            SoulTogglePacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            Set<SoulItem> disabledSouls = new HashSet<>();
            for (ResourceLocation id : disabled) {
                if (BuiltInRegistries.ITEM.get(id) instanceof SoulItem soulItem) {
                    disabledSouls.add(soulItem);
                }
            }
            player.getData(FargoSoulAttachmentRegister.SOUL_ITEM_DATA).applyDisabled(player, disabledSouls);
        });
    }
}

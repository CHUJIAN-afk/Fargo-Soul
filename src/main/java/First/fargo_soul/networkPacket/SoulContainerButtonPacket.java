package First.fargo_soul.networkPacket;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.common.attachment.SoulAbilityEnabledData;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.register.AttachmentRegister;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record SoulContainerButtonPacket(ResourceLocation resourceLocation, boolean is) implements CustomPacketPayload {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(FargoSoul.MODID, "ability_data_config");
    public static final Type<SoulContainerButtonPacket> TYPE = new Type<>(ID);

    public static final StreamCodec<ByteBuf, SoulContainerButtonPacket> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC,
            SoulContainerButtonPacket::resourceLocation,
            ByteBufCodecs.BOOL,
            SoulContainerButtonPacket::is,
            SoulContainerButtonPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            SoulItem soulItem = (SoulItem) BuiltInRegistries.ITEM.get(resourceLocation);
            SoulAbilityEnabledData enabledData = player.getData(AttachmentRegister.AbilityEnabledData);
            enabledData.setEnabled(soulItem, is);
            player.syncData(AttachmentRegister.AbilityEnabledData);
        });
    }

}

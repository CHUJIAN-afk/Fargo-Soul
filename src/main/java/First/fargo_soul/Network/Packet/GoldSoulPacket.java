package First.fargo_soul.Network.Packet;

import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.AttributeUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public record GoldSoulPacket() implements CustomPacketPayload {

    public static final Type<GoldSoulPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Fargo_soul.MODID, "gold_soul"));
    public static final StreamCodec<ByteBuf, GoldSoulPacket> STREAM_CODEC = CustomPacketPayload.codec(
            GoldSoulPacket::encode,
            GoldSoulPacket::decode
    );

    private void encode(@SuppressWarnings("unused") ByteBuf byteBuf) {
    }

    private static GoldSoulPacket decode(@SuppressWarnings("unused") ByteBuf byteBuf) {
        return new GoldSoulPacket();
    }

    @Override
    public @NotNull Type<GoldSoulPacket> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                long GoldSoul = player.getPersistentData().getLong("GoldSoul");
                long gameTime = player.serverLevel().getGameTime();
                if (GoldSoul < gameTime) {
                    player.getPersistentData().putLong("GoldSoul", gameTime + 1100);
                    if (!player.getPersistentData().getBoolean("GoldSoulDamage")) {
                        player.getPersistentData().putBoolean("GoldSoulDamage", true);
                        player.setInvulnerable(true);
                        AttributeUtils.addAttributeModifier(player, Attributes.MOVEMENT_SPEED, SoulsRegister.GoldSoul.getId(), -1.0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
                        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                        executorService.schedule(() -> {
                            player.getPersistentData().remove("GoldSoulDamage");
                            player.setInvulnerable(false);
                            AttributeUtils.removeAttributeModifier(player, Attributes.MOVEMENT_SPEED, SoulsRegister.GoldSoul.getId());
                        }, 5, TimeUnit.SECONDS);
                    }
                }
            }
        }).exceptionally(e -> {
            context.disconnect(Component.translatable("neoforge.network.invalid_flow", e.getMessage()));
            return null;
        });
    }
}

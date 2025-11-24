package First.fargo_soul.Item.Soul.TerraSoul.CosmicPower.SoulStone;

import First.fargo_soul.Attachment.Attachment.SoulAbilityData;
import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Client.KeyBinding;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Item.Soul.TerraSoul.CosmicPower.CosmicPower;
import First.fargo_soul.Utils.SoulUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StardustSoul extends SoulItem {

    public StardustSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.RED));
    }

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                if (SoulUtils.isEquipped(attacker, StardustSoul.class)) {
                    if (attacker.getServer() instanceof MinecraftServer server && server.tickRateManager().isFrozen()) {
                        event.setAmount(event.getAmount() * 3);
                    }
                }
            }
        }

        @SubscribeEvent
        public static void CurioChangeEvent(CurioChangeEvent event) {
            SoulAbilityData.updateMaxCooldown(event.getEntity(), StardustSoul.class, 3600);
        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void Input(InputEvent.Key event) {
            if (event.getKey() == KeyBinding.StardustSoulKey.getKey().getValue()) {
                if (Minecraft.getInstance().player instanceof LocalPlayer player) {
                    PacketDistributor.sendToServer(new Packet(SoulUtils.isEquipped(player, StardustSoul.class)));
                }
            }
        }

    }

    public record Packet(boolean isEquipped) implements CustomPacketPayload {

        public static final Type<Packet> TYPE = new Type<>(SoulsRegister.StardustSoul.getId());
        public static final StreamCodec<ByteBuf, Packet> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.BOOL,
                Packet::isEquipped,
                Packet::new
        );

        @Override
        public @NotNull Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }

        public void handle(IPayloadContext context) {
            context.enqueueWork(() -> {
                if (isEquipped) {
                    Player player = context.player();
                    SoulAbilityData.SoulInfo soulInfo = player.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(StardustSoul.class);
                    if (soulInfo.isReady() && player.getServer() instanceof MinecraftServer server && !server.tickRateManager().isFrozen()) {
                        soulInfo.setCooldown(soulInfo.getMaxCooldown());
                        server.tickRateManager().setFrozen(true);
                        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                        int delay = SoulUtils.isEquipped(player, CosmicPower.class) ? 10 : 6;
                        executorService.schedule(() -> server.tickRateManager().setFrozen(false), delay, TimeUnit.SECONDS);
                    }
                }
            });
        }

    }

}

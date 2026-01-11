package First.fargo_soul.item.terraSoul.cosmicPower;

import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.CosmicPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.register.KeyRegister;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.RenderUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StardustSoul extends SoulItem {

    public StardustSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
            if (CurioUtils.isEquipped(attacker, StardustSoul.class)) {
                if (attacker.getServer() instanceof MinecraftServer server && server.tickRateManager().isFrozen()) {
                    event.setAmount(event.getAmount() * 3);
                }
            }
        }
    }

    @Override
    public void tick(LivingEntity ticker) {
        if (!ticker.level().isClientSide() && CurioUtils.isEquipped(ticker, StardustSoul.class)) {
            SoulAbilityData.getSoulInfo(ticker, StardustSoul.class).setMaxCooldown(3600);
        }
    }

    @Override
    public void keyPressed(Player player, int key) {
        if (key == KeyRegister.StardustSoulKey.getKey().getValue()) {
            PacketDistributor.sendToServer(new Packet(CurioUtils.isEquipped(player, StardustSoul.class)));
        }
    }

    public record Packet(boolean isEquipped) implements CustomPacketPayload {

        public static final Type<Packet> TYPE = new Type<>(ItemRegister.StardustSoulItem.getId());
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
                        int delay = CurioUtils.isEquipped(player, CosmicPower.class) ? 10 : 6;
                        executorService.schedule(() -> server.tickRateManager().setFrozen(false), delay, TimeUnit.SECONDS);
                    }
                }
            });
        }

    }

    @Override
    public List<Component> getGuiTooltip(Player player) {
        List<Component> tooltip = new ArrayList<>();
        SoulAbilityData.SoulInfo soulInfo = SoulAbilityData.getSoulInfo(player, StardustSoul.class);
        tooltip.add(RenderUtils.createCooldownTooltip(this, "时间冻结冷却", soulInfo));
        return tooltip;
    }

}

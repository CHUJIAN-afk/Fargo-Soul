package First.fargo_soul.item.terraSoul.spiritPower;

import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.SpiritPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.register.KeyRegister;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.ParticleUtils;
import First.fargo_soul.utils.RenderUtils;
import First.fargo_soul.utils.SoulUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ForbiddenSoul extends SoulItem {

    public ForbiddenSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(LivingEntity ticker) {
        if (!ticker.level().isClientSide()) {
            SoulAbilityData.getSoulInfo(ticker, ForbiddenSoul.class).setMaxCooldown(600);
        }
    }

    @Override
    public void keyPressed(Player player, int key) {
        if (KeyRegister.ForbiddenKey.consumeClick()) {
            PacketDistributor.sendToServer(new Packet(CurioUtils.isEquipped(player, ForbiddenSoul.class)));
        }
    }

    @Override
    public List<Component> getGuiTooltip(Player player) {
        List<Component> tooltip = super.getGuiTooltip(player);
        tooltip.add(RenderUtils.createCooldownTooltip(this, "风暴冷却", SoulAbilityData.getSoulInfo(player, ForbiddenSoul.class)));
        return tooltip;
    }

    public record Packet(boolean isEquipped) implements CustomPacketPayload {

        public static final Type<Packet> TYPE = new Type<>(ItemRegister.ForbiddenSoulItem.getId());
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
                    SoulAbilityData.SoulInfo soulInfo = player.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(ForbiddenSoul.class);
                    HitResult hitResult = player.pick(20.0, 0, false);
                    if (soulInfo.isReady() && hitResult instanceof BlockHitResult blockHitResult) {
                        soulInfo.setCooldown(soulInfo.getMaxCooldown());
                        BlockPos pos = blockHitResult.getBlockPos();
                        Vec3 blockPos = new Vec3(pos.getX(), pos.getY(), pos.getZ());
                        for (int i = 1; i < (CurioUtils.isEquipped(player, SpiritPower.class) ? 8 : 5); i++) {
                            SoulUtils.executorService.schedule(() -> {
                                int value = CurioUtils.isEquipped(player, SpiritPower.class) ? 6 : 4;
                                List<LivingEntity> livingEntityList = player.level().getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(value), livingEntity -> !CurioUtils.isEquipped(livingEntity, ForbiddenSoul.class));
                                for (LivingEntity livingEntity : livingEntityList) {
                                    Vec3 delta = blockPos.subtract(livingEntity.position()).normalize();
                                    livingEntity.addDeltaMovement(delta);
                                }
                                ParticleUtils.spawnParticleSphere(
                                        (ServerLevel) player.level(),
                                        blockPos,
                                        ParticleTypes.DUST_PLUME,
                                        5f,
                                        400,
                                        0.0f
                                );
                            }, i, TimeUnit.SECONDS);
                        }
                    }
                }
            });
        }

    }

}

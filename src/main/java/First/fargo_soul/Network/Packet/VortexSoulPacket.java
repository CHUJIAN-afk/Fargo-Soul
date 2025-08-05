package First.fargo_soul.Network.Packet;

import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Utils.ParticleUtils;
import First.fargo_soul.Utils.Utils;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static First.fargo_soul.Item.Soul.TerraSoul.SpiritPower.SoulStone.ForbiddenSoul.executorService;

public record VortexSoulPacket() implements CustomPacketPayload {

    public static final Type<VortexSoulPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Fargo_soul.MODID, "vortex_soul"));
    public static final StreamCodec<ByteBuf, VortexSoulPacket> STREAM_CODEC = CustomPacketPayload.codec(
            VortexSoulPacket::encode,
            VortexSoulPacket::decode
    );

    private void encode(ByteBuf byteBuf) {
    }

    private static VortexSoulPacket decode(ByteBuf byteBuf) {
        return new VortexSoulPacket();
    }

    @Override
    public @NotNull Type<VortexSoulPacket> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                ServerLevel level = player.serverLevel();
                long gameTime = level.getGameTime();
                long VortexSoul = player.getPersistentData().getLong("VortexSoul");
                if (VortexSoul < gameTime) {
                    HitResult hitResult = Utils.getTargetedBlock(player, 512);
                    if (hitResult instanceof BlockHitResult blockHitResult) {
                        BlockPos pos = blockHitResult.getBlockPos();
                        if (!level.getBlockState(pos).is(Blocks.AIR) && pos.getY() > level.getMinBuildHeight()) {
                            player.getPersistentData().putLong("VortexSoul", gameTime + 300);
                            Direction hitFace = blockHitResult.getDirection();
                            double adjustX = pos.getX() + hitFace.getStepX();
                            double adjustY = pos.getY() + hitFace.getStepY();
                            double adjustZ = pos.getZ() + hitFace.getStepZ();
                            player.teleportTo(adjustX, adjustY, adjustZ);
                            Vec3 blockPos = new Vec3(pos.getX(), pos.getY(), pos.getZ());
                            for (int i = 1; i < 40; i++) {
                                executorService.schedule(() -> {
                                    List<LivingEntity> livingEntityList = player.serverLevel().getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(8), livingEntity -> !livingEntity.equals(player));
                                    for (LivingEntity livingEntity : livingEntityList) {
                                        Vec3 delta = blockPos.subtract(livingEntity.position()).normalize();
                                        livingEntity.addDeltaMovement(delta);
                                        livingEntity.hurt(player.damageSources().magic(), 2);
                                    }
                                    List<ItemEntity> itemEntityList = player.serverLevel().getEntitiesOfClass(ItemEntity.class, new AABB(pos).inflate(8));
                                    for (ItemEntity itemEntity : itemEntityList) {
                                        Vec3 delta = blockPos.subtract(itemEntity.position()).normalize();
                                        itemEntity.addDeltaMovement(delta);
                                    }
                                    ParticleUtils.spawnParticleSphere(player.serverLevel(), blockPos, ParticleTypes.DUST_PLUME, 7f, 800, 0.2f);
                                }, i * 250, TimeUnit.MILLISECONDS);
                            }
                            level.playSound(
                                    null,
                                    player.getX(), player.getY(), player.getZ(),
                                    SoundEvents.ENDERMAN_TELEPORT,
                                    SoundSource.PLAYERS,
                                    1.0f,
                                    Utils.random.nextFloat() * 0.4f + 0.4f
                            );
                        }
                    }
                }
            }
        }).exceptionally(e -> {
            context.disconnect(Component.translatable("neoforge.network.invalid_flow", e.getMessage()));
            return null;
        });
    }




}

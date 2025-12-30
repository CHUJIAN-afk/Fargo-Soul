package First.fargo_soul.item.terraSoul.cosmicPower;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.CosmicPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.register.KeyRegister;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.ParticleUtils;
import First.fargo_soul.utils.SoulUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class VortexSoul extends SoulItem {

    public VortexSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.RED));
    }

    @EventBusSubscriber(modid = FargoSoul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void CurioChangeEvent(CurioChangeEvent event) {
            event.getEntity().getData(AttachmentRegister.SoulAbilityData).getSoulInfo(VortexSoul.class).setMaxCooldown(400);
        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void VortexSoulInputHandler(InputEvent.Key event) {
            if (event.getKey() == KeyRegister.VortexSoulKey.getKey().getValue()) {
                if (Minecraft.getInstance().player instanceof LocalPlayer player) {
                    PacketDistributor.sendToServer(new Packet(CurioUtils.isEquipped(player, VortexSoul.class)));
                }
            }
        }

    }

    public record Packet(boolean isEquipped) implements CustomPacketPayload {

        public static final Type<Packet> TYPE = new Type<>(ItemRegister.VortexSoulItem.getId());
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
                    Level level = player.level();
                    SoulAbilityData.SoulInfo soulInfo = player.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(VortexSoul.class);
                    int maxDistance = CurioUtils.isEquipped(player, CosmicPower.class) ? 1024 : 512;
                    HitResult hitResult = SoulUtils.getTargetedBlock(player, maxDistance);
                    if (soulInfo.isReady() && hitResult instanceof BlockHitResult blockHitResult) {
                        soulInfo.setCooldown(soulInfo.getMaxCooldown());
                        BlockPos pos = blockHitResult.getBlockPos();
                        if (!level.getBlockState(pos).is(Blocks.AIR) && pos.getY() > level.getMinBuildHeight()) {
                            Direction hitFace = blockHitResult.getDirection();
                            double adjustX = pos.getX() + hitFace.getStepX();
                            double adjustY = pos.getY() + hitFace.getStepY();
                            double adjustZ = pos.getZ() + hitFace.getStepZ();
                            player.teleportTo(adjustX, adjustY, adjustZ);
                            Vec3 blockPos = new Vec3(pos.getX(), pos.getY(), pos.getZ());
                            for (int i = 1; i < 40; i++) {
                                SoulUtils.executorService.schedule(() -> {
                                    List<LivingEntity> livingEntityList = level.getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(8), livingEntity -> !livingEntity.equals(player));
                                    for (LivingEntity livingEntity : livingEntityList) {
                                        Vec3 delta = blockPos.subtract(livingEntity.position()).normalize();
                                        livingEntity.addDeltaMovement(delta);
                                        livingEntity.hurt(player.damageSources().magic(), 2);
                                    }
                                    List<ItemEntity> itemEntityList = level.getEntitiesOfClass(ItemEntity.class, new AABB(pos).inflate(8));
                                    for (ItemEntity itemEntity : itemEntityList) {
                                        Vec3 delta = blockPos.subtract(itemEntity.position()).normalize();
                                        itemEntity.addDeltaMovement(delta);
                                    }
                                    ParticleUtils.spawnParticleSphere((ServerLevel) level, blockPos, ParticleTypes.DUST_PLUME, 7f, 800, 0.2f);
                                }, i * 250, TimeUnit.MILLISECONDS);
                            }
                            SoulUtils.playSound(
                                    level,
                                    player.position(),
                                    SoundEvents.ENDERMAN_TELEPORT,
                                    SoundSource.PLAYERS
                            );
                        }
                    }
                }
            });
        }

    }




}

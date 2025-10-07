package First.fargo_soul.Item.Soul.TerraSoul.NaturePower.SoulStone;

import First.fargo_soul.Attachment.Attachment.SoulAbilityData;
import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.TerraSoul.NaturePower.NaturePower;
import First.fargo_soul.Utils.ParticleUtils;
import First.fargo_soul.Utils.SoulUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class FrostSoul extends SoulItem {

    public FrostSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PINK));
    }

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void render(RenderLivingEvent.Post<?, ?> event) {
            LivingEntity attacker = event.getEntity();
            if (SoulUtils.isEquipped(attacker, FrostSoul.class)) {
                MultiBufferSource multiBufferSource = event.getMultiBufferSource();
                PoseStack poseStack = event.getPoseStack();
                int packedLight = event.getPackedLight();
                Minecraft minecraft = Minecraft.getInstance();
                ItemRenderer itemRenderer = minecraft.getItemRenderer();
                float ageInTicks = SoulUtils.getAgeInTicks(attacker, event.getPartialTick(), 3);
                SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(FrostSoul.class);
                int stacks = soulInfo.stacks;
                if (stacks > 0) {
                    double size = attacker.getBoundingBox().getSize();
                    float scale = (float) (size);
                    for (int i = 0; i < stacks; i++) {
                        poseStack.pushPose();
                        float angle = ageInTicks * 0.008f + ((float) Math.PI * 2 / stacks * i);
                        float verticalOffset = (float) Math.cos(angle + i) * 0.05f;
                        float x = (float) (Math.sin(angle)) * scale;
                        float z = (float) (Math.cos(angle)) * scale;
                        AABB boundingBox = attacker.getBoundingBox();
                        poseStack.translate(
                                x,
                                boundingBox.getYsize() * 0.5f + verticalOffset,
                                z
                        );
                        poseStack.mulPose(Axis.YP.rotationDegrees(angle * 180 / (float) Math.PI));
                        poseStack.scale(0.4f * scale, 0.4f * scale, 0.6f * scale);
                        itemRenderer.renderStatic(
                                Items.SNOWBALL.getDefaultInstance(),
                                ItemDisplayContext.FIXED,
                                packedLight,
                                OverlayTexture.NO_OVERLAY,
                                poseStack,
                                multiBufferSource,
                                attacker.level(),
                                attacker.getId()
                        );
                        poseStack.popPose();
                    }
                }
            }
        }

        @SubscribeEvent
        public static void Tick(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                if (SoulUtils.isEquipped(attacker, FrostSoul.class)) {
                    SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(FrostSoul.class);
                    Level level = attacker.level();
                    BlockPos onPos = attacker.getOnPos();
                    boolean cold = level.getBiome(onPos).value().coldEnoughToSnow(onPos);
                    soulInfo.maxCooldown = cold ? 8 : 20;
                    soulInfo.maxStacks = SoulUtils.isEquipped(attacker, NaturePower.class) ? 20 : 10;
                    if (soulInfo.cooldown == 0) {
                        soulInfo.cooldown = soulInfo.maxCooldown;
                        soulInfo.addStacks();
                    }
                    if (attacker.tickCount % (cold ? 2 : 5) == 0 && soulInfo.stacks > 0) {
                        if (SoulUtils.getSoulTarget(attacker, 10) instanceof LivingEntity target) {
                            soulInfo.shrinkStacks();
                            Snowball snowball = new Snowball(EntityType.SNOWBALL, level);
                            SoulUtils.shootTargetFromAttaker(snowball, attacker, target, 0.5, 2);
                            SoulUtils.setAbilityInvulnerable(snowball);
                            ParticleUtils.spawnParticleSphere(
                                    (ServerLevel) level,
                                    snowball.position(),
                                    ParticleTypes.ITEM_SNOWBALL,
                                    0.2f,
                                    10,
                                    0.5f
                            );
                            SoulUtils.playSound(
                                    level,
                                    snowball.position(),
                                    SoundEvents.SNOWBALL_THROW,
                                    SoundSource.PLAYERS
                            );
                        }
                    }
                }
            }
        }

    }

}

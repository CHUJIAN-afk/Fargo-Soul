package First.fargo_soul.item.terraSoul.naturePower;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.NaturePower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.ParticleUtils;
import First.fargo_soul.utils.RenderUtils;
import First.fargo_soul.utils.SoulUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;


public class GreenSoul extends SoulItem {

    public GreenSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.LIME));
    }

    @EventBusSubscriber(modid = FargoSoul.MODID)
    public static class Event {

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void render(RenderLivingEvent.Post<?, ?> event) {
            LivingEntity attacker = event.getEntity();
            if (CurioUtils.isEquipped(attacker, GreenSoul.class)) {
                MultiBufferSource multiBufferSource = event.getMultiBufferSource();
                PoseStack poseStack = event.getPoseStack();
                Minecraft minecraft = Minecraft.getInstance();
                ItemRenderer itemRenderer = minecraft.getItemRenderer();
                float ageInTicks = RenderUtils.getAgeInTicks(attacker, event.getPartialTick(), 0.02f);
                poseStack.pushPose();
                double y = attacker.getBoundingBox().getYsize();
                float scale = (float) (attacker.getBoundingBox().getSize() + 0.25);
                float floatingOffset = (float) Math.sin(ageInTicks) * scale * 0.1f + scale * 0.5f;
                poseStack.translate(0, y + floatingOffset, 0);
                poseStack.mulPose(Axis.YP.rotationDegrees(ageInTicks * 180 / (float) Math.PI));
                poseStack.scale(scale * 0.25f, scale * 0.25f, scale * 0.25f);
                itemRenderer.renderStatic(
                        ItemRegister.GreenCrystal.get().getDefaultInstance(),
                        ItemDisplayContext.FIXED,
                        LightTexture.FULL_BRIGHT,
                        OverlayTexture.NO_OVERLAY,
                        poseStack,
                        multiBufferSource,
                        attacker.level(),
                        attacker.getId()
                );
                poseStack.popPose();
            }
        }

        @SubscribeEvent
        public static void GreenSoulTickHandler(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                if (CurioUtils.isEquipped(attacker, GreenSoul.class)) {
                    boolean equipped = CurioUtils.isEquipped(attacker, NaturePower.class);
                    SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(GreenSoul.class);
                    soulInfo.setMaxCooldown(equipped ? 30 : 60);
                    if (soulInfo.isReady() && SoulUtils.getSoulTarget(attacker, 10) instanceof LivingEntity target) {
                        soulInfo.setCooldown(soulInfo.getMaxCooldown());
                        Level level = attacker.level();
                        SoulUtils.attack(attacker, target, DamageTypes.MAGIC, 4);
                        target.addEffect(new MobEffectInstance(MobEffects.GLOWING, 59));
                        if (equipped) {
                            target.addEffect(new MobEffectInstance(MobEffects.POISON, 39));
                        }
                        Vec3 position = attacker.position();
                        double size = attacker.getBoundingBox().getYsize();
                        ParticleUtils.spawnParticleLine(
                                (ServerLevel) level,
                                position.add(0, size, 0),
                                target.getBoundingBox().getCenter(),
                                ParticleTypes.ELECTRIC_SPARK,
                                10,
                                0
                        );
                        ParticleUtils.spawnParticleLine(
                                (ServerLevel) level,
                                position.add(0, size * 0.9, 0),
                                position.add(0, size * 1.3, 0),
                                ParticleTypes.ELECTRIC_SPARK,
                                100,
                                0.5f
                        );
                        SoulUtils.playSound(
                                level,
                                position,
                                SoundEvents.ILLUSIONER_CAST_SPELL,
                                SoundSource.PLAYERS
                        );
                    }
                }
            }
        }

	}

}




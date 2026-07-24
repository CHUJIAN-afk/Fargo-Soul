package first.fargo_soul.common.item.terraSoul.naturePower;

import first.fargo_soul.client.gui.SoulGuiRenderManager;
import first.fargo_soul.client.gui.SoulRenderType;
import first.fargo_soul.common.event.modEvent.PlayerFlyEvent;
import first.fargo_soul.common.event.modEvent.SprintEvent;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.terraSoul.NaturePower;
import first.fargo_soul.register.FargoSoulItemRegister;
import first.fargo_soul.utils.CurioUtils;
import first.fargo_soul.utils.ParticleUtils;
import first.fargo_soul.utils.RenderUtils;
import first.fargo_soul.utils.SoulUtils;
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
import net.neoforged.neoforge.client.event.RenderLivingEvent;


public class GreenSoul extends SoulItem {

    public GreenSoul(Properties properties) {
        super();
    }

    @Override
    public void render(RenderLivingEvent.Post<?, ?> event) {
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
                    FargoSoulItemRegister.GreenCrystal.get().getDefaultInstance(),
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

    @Override
    public void tick(LivingEntity ticker, boolean isClient) {
        if (!ticker.level().isClientSide()) {
            if (CurioUtils.isEquipped(ticker, GreenSoul.class)) {
                boolean equipped = CurioUtils.isEquipped(ticker, NaturePower.class);
                SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(ticker, GreenSoul.class);
                soulInfo.setMaxCooldown(equipped ? 30 : 60);
                if (soulInfo.isReady() && SoulUtils.getSoulTarget(ticker, 10) instanceof LivingEntity target) {
                    soulInfo.setCooldown(soulInfo.getMaxCooldown());
                    Level level = ticker.level();
                    SoulUtils.attack(this.getClass(), ticker, ticker, target, DamageTypes.MAGIC, 4);
                    target.addEffect(new MobEffectInstance(MobEffects.GLOWING, 59));
                    if (equipped) {
                        target.addEffect(new MobEffectInstance(MobEffects.POISON, 39));
                    }
                    Vec3 position = ticker.position();
                    double size = ticker.getBoundingBox().getYsize();
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

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, GreenSoul.class, SoulRenderType.Cooldown);
    }

    @Override
    public void fly(PlayerFlyEvent event) {
        if (event.isAllowingFly()) {
            event.addMaxFlyTime(40);
        }
    }

    @Override
    public void sprintClient(SprintEvent.Client event) {
        LivingEntity livingEntity = event.getEntity();
        if (CurioUtils.isEquipped(livingEntity, GreenSoul.class)) {
            event.setSprinting(true);
        }
    }

}




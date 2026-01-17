package First.fargo_soul.item.terraSoul.naturePower;

import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.client.gui.SoulGuiRenderManager;
import First.fargo_soul.client.gui.SoulRenderType;
import First.fargo_soul.event.modEvent.PlayerFlyEvent;
import First.fargo_soul.event.modEvent.SprintEvent;
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
import net.neoforged.neoforge.client.event.RenderLivingEvent;


public class GreenSoul extends SoulItem {

    public GreenSoul(Properties properties) {
        super(properties);
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

    @Override
    public void tick(LivingEntity ticker) {
        if (!ticker.level().isClientSide()) {
            if (CurioUtils.isEquipped(ticker, GreenSoul.class)) {
                boolean equipped = CurioUtils.isEquipped(ticker, NaturePower.class);
                SoulAbilityData.SoulInfo soulInfo = ticker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(GreenSoul.class);
                soulInfo.setMaxCooldown(equipped ? 30 : 60);
                if (soulInfo.isReady() && SoulUtils.getSoulTarget(ticker, 10) instanceof LivingEntity target) {
                    soulInfo.setCooldown(soulInfo.getMaxCooldown());
                    Level level = ticker.level();
                    SoulUtils.attack(ticker, target, DamageTypes.MAGIC, 4);
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




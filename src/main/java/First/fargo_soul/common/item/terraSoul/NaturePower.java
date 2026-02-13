package First.fargo_soul.common.item.terraSoul;

import First.fargo_soul.client.gui.SoulGuiRenderManager;
import First.fargo_soul.client.gui.SoulRenderType;
import First.fargo_soul.common.attachment.SoulAbilityData;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.register.EffectRegister;
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
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

import static First.fargo_soul.register.ItemRegister.*;

public class NaturePower extends SoulItem {

    public NaturePower(Properties properties) {
        super(properties);
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                CrimsonSoulItem.get(),
                FrostSoulItem.get(),
                GreenSoulItem.get(),
                LavaSoulItem.get(),
                MushroomSoulItem.get(),
                RainCloudSoulItem.get()
        );
    }

    @Override
    public void render(RenderLivingEvent.Post<?, ?> event) {
        LivingEntity attacker = event.getEntity();
        if (CurioUtils.isEquipped(attacker, NaturePower.class)) {
            MultiBufferSource multiBufferSource = event.getMultiBufferSource();
            PoseStack poseStack = event.getPoseStack();
            Minecraft minecraft = Minecraft.getInstance();
            ItemRenderer itemRenderer = minecraft.getItemRenderer();
            float ageInTicks = RenderUtils.getAgeInTicks(attacker, event.getPartialTick(), 0.2f);
            double y = attacker.getBoundingBox().getYsize() / 3;
            float radius = (float) (attacker.getBoundingBox().getSize() + 0.25);
            float floatingOffset = (float) Math.sin(ageInTicks) * radius * 0.1f + radius * 0.5f;
            for (int i = 0; i < 5; i++) {
                poseStack.pushPose();
                float angle = ageInTicks * 180 / (float) Math.PI + i * 72;
                float x = (float) Math.cos(Math.toRadians(angle)) * radius;
                float z = (float) Math.sin(Math.toRadians(angle)) * radius;
                poseStack.translate(x, y + floatingOffset, z);
                poseStack.mulPose(Axis.YP.rotationDegrees(angle));
                poseStack.scale(radius * 0.5f, radius * 0.5f, radius * 0.5f);
                itemRenderer.renderStatic(
                        ItemRegister.GreenCrystal.get().getDefaultInstance(),
                        ItemDisplayContext.FIXED,
                        LightTexture.FULL_BRIGHT,
                        OverlayTexture.NO_OVERLAY,
                        poseStack,
                        multiBufferSource,
                        attacker.level(),
                        attacker.getId() * 100 + i
                );
                poseStack.popPose();
            }
        }
    }

    @Override
    public void tick(LivingEntity ticker) {
        Level level = ticker.level();
        if (!level.isClientSide() && CurioUtils.isEquipped(ticker, NaturePower.class)) {
            {
                SoulAbilityData.SoulInfo soulInfo = SoulAbilityData.getSoulInfo(ticker, "NaturePowerGreenCrystal");
                soulInfo.setMaxCooldown(15);
                if (soulInfo.isReady() && SoulUtils.getSoulTarget(ticker, 10) instanceof LivingEntity target) {
                    soulInfo.setCooldown(soulInfo.getMaxCooldown());
                    SoulUtils.attack(this.getClass(), ticker, ticker, target, DamageTypes.MAGIC, 20);
                    RandomSource random = ticker.getRandom();
                    ItemStack itemStack = random.nextBoolean() ? Items.RED_MUSHROOM.getDefaultInstance() : Items.BROWN_MUSHROOM.getDefaultInstance();
                    itemStack.setCount(5);
                    SoulUtils.addItemEetity(level, itemStack, target.getBoundingBox().getCenter());
                    for (int i = 0; i < 5; i++) {
                        SoulUtils.attack(this.getClass(), ticker,ticker, target, DamageTypes.MAGIC, 4);
                    }
                    target.addEffect(new MobEffectInstance(MobEffects.GLOWING, 59));
                    target.addEffect(new MobEffectInstance(MobEffects.POISON, 39));
                    Vec3 position = ticker.position();
                    double size = ticker.getBoundingBox().getSize();
                    ParticleUtils.spawnParticleLine(
                            (ServerLevel) level,
                            position.add(random.nextDouble() * size, 0, random.nextDouble() * size),
                            target.getBoundingBox().getCenter(),
                            ParticleTypes.ELECTRIC_SPARK,
                            20,
                            0
                    );
                    SoulUtils.playSound(
                            level,
                            position,
                            SoundEvents.ILLUSIONER_CAST_SPELL,
                            ticker.getSoundSource()
                    );
                }
            }
            {
                SoulAbilityData.SoulInfo soulInfo = SoulAbilityData.getSoulInfo(ticker, "NaturePowerBlood");
                soulInfo.setMaxCooldown(80);
                soulInfo.setMaxStacks(4);
                if (soulInfo.isReady() && soulInfo.getStacks() < soulInfo.getMaxStacks()) {
                    soulInfo.setCooldown(soulInfo.getMaxCooldown());
                    soulInfo.addStacks();
                }
                if (ticker.tickCount % 10 == 0) {
                    float heal = 0;
                    int range = ticker.hasEffect(EffectRegister.FungalEmpowerment) ? 15 : 10;
                    for (LivingEntity living : SoulUtils.getTargetList(ticker, range)) {
                        float amount = 2 * (soulInfo.getStacks() + 1);
                        SoulUtils.attack(this.getClass(), ticker, ticker, living, DamageTypes.MOB_ATTACK, amount);
                        heal += amount;
                    }
                    if (ticker.getHealth() < ticker.getMaxHealth()) {
                        ticker.heal(heal);
                    }
                }
            }
        }
    }

    @Override
    public void itemUseFinish(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
            if (event.getItem().is(Items.MUSHROOM_STEW) && CurioUtils.isEquipped(attacker, NaturePower.class)) {
                attacker.heal(50);
            }
        }
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity target && CurioUtils.isEquipped(target, NaturePower.class)) {
            if (event.getSource().getDirectEntity() instanceof Projectile) {
                event.setAmount(event.getAmount() - 15);
                if (event.getAmount() <= 0) {
                    event.setCanceled(true);
                }
            }
            SoulAbilityData.SoulInfo soulInfo = SoulAbilityData.getSoulInfo(target, "NaturePowerBlood");
            soulInfo.setCooldown(soulInfo.getMaxCooldown());
            soulInfo.removeStacks();
        }
        if (event.getEntity() instanceof LivingEntity target && event.getSource().getEntity() instanceof LivingEntity attacker && CurioUtils.isEquipped(attacker, NaturePower.class)) {
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40));
        }
    }

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, "NaturePowerBlood", SoulRenderType.Stack);
        soulRenderManager.add(this, "NaturePowerBlood", SoulRenderType.Cooldown);
        soulRenderManager.add(this, "NaturePowerGreenCrystal", SoulRenderType.Cooldown);
    }
}
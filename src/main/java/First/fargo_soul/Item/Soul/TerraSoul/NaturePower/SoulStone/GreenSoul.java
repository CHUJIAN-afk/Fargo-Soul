package First.fargo_soul.Item.Soul.TerraSoul.NaturePower.SoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.ParticleUtils;
import First.fargo_soul.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class GreenSoul extends SoulItem {
    public static final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public GreenSoul(Properties properties) {
        super(properties);
    }

    public final List<Component> AttributeList = List.of(
            Component.translatable("item.fargo_soul.green_soul.attribute.1").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.green_soul.attribute.2").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.green_soul.attribute.3").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.green_soul.attribute.4").withStyle(ChatFormatting.BLUE)
    );

    public final List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.green_soul.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
    );

    @Override
    public List<Component> getAttributeList() {
        return this.AttributeList;
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, Item.TooltipContext context, ItemStack stack) {
        tooltips.addAll(AttributeList);
        tooltips.addAll(TooltipList);
        return tooltips;
    }

    private static Movement zza = Movement.NONE;
    private static int sprintingTime = 0;
    private static boolean zzKeyDown = false;
    private static int cooldown = 0;

    private enum Movement {
        NONE,
        UP
    }

    @OnlyIn(Dist.CLIENT)
    public static void GreenSoulMovementInputHandler(MovementInputUpdateEvent event) {
        if (event.getEntity() instanceof LocalPlayer player && CurioUtils.isEquipped(player, SoulsRegister.GreenSoul.get())) {
        if (cooldown > 0) {
            cooldown--;
            return;
        }
        Input input = event.getInput();
        if (sprintingTime > 0) sprintingTime--;
        if (zza == Movement.NONE) {
            if (input.up) {
                zza = Movement.UP;
                sprintingTime = 7;
                zzKeyDown = true;
            }
        } else if (zzKeyDown) {
            if (!input.up) zzKeyDown = false;
        } else if (sprintingTime > 0) {
            if (zza == Movement.UP && input.forwardImpulse >= 0.8) {
                Vec3 viewVector = player.getLookAngle();
                player.addDeltaMovement(viewVector.scale(1.2));
                zza = Movement.NONE;
                cooldown = 20;
            }
        } else if (sprintingTime == 0) {
            zza = Movement.NONE;
        }
    }}

    public static void GreenSoulTickHandler2(PlayerTickEvent.Post event){
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.GreenSoul.get())) {
            if (player.isFallFlying()){
                List<LivingEntity> livingEntityList = player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(2));
                livingEntityList.removeIf(livingEntity -> CurioUtils.isEquipped(livingEntity, SoulsRegister.GreenSoul.get()));
                for (LivingEntity livingEntity : livingEntityList) {
                    MobEffectInstance effectInstance = new MobEffectInstance(MobEffects.POISON, 60);
                    livingEntity.addEffect(effectInstance);
                }
                double x = player.getX();
                double y = player.getY();
                double z = player.getZ();
                executorService.schedule((() -> ParticleUtils.spawnParticleSphere(
                        player.serverLevel(),
                        x,
                        y,
                        z,
                        ParticleTypes.GLOW_SQUID_INK,
                        2.0f,
                        20,
                        0.5f
                )), 200, TimeUnit.MILLISECONDS);
            }
        }
    }

    public static void GreenSoulTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.GreenSoul.get())) {
            if (player.tickCount % 20 == 0) {
                TargetingConditions conditions = TargetingConditions.forCombat().range(10.0);
                LivingEntity target = player.level().getNearestEntity(LivingEntity.class, conditions, player, player.getX(), player.getY(), player.getZ(), player.getBoundingBox().inflate(10));
                if (target != null) {
                    target.hurt(player.damageSources().playerAttack(player), 4);
                    ParticleUtils.spawnParticleLine(
                            player.serverLevel(),
                            player.position().add(0, 2, 0),
                            target.getBoundingBox().getCenter(),
                            ParticleTypes.ELECTRIC_SPARK,
                            10,
                            0
                    );
                    ParticleUtils.spawnParticleLine(
                            player.serverLevel(),
                            player.position().add(0, 1.8, 0),
                            player.position().add(0, 2.6, 0),
                            ParticleTypes.ELECTRIC_SPARK,
                            100,
                            0.5f
                    );
                    player.serverLevel().playSound(
                            null,
                            player.getX(), player.getY(), player.getZ(),
                            SoundEvents.ILLUSIONER_CAST_SPELL,
                            SoundSource.PLAYERS,
                            1.0f,
                            Utils.random.nextFloat() * 0.4f + 0.4f
                    );
                }
            }
        }
    }




}

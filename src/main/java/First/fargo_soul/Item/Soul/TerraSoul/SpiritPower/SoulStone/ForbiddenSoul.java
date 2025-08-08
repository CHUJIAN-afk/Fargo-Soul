package First.fargo_soul.Item.Soul.TerraSoul.SpiritPower.SoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.KeyUtils;
import First.fargo_soul.Utils.ParticleUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ForbiddenSoul extends SoulItem {
    public static final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public ForbiddenSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PINK));
    }

    public final List<Component> AttributeList = List.of(
            Component.translatable("item.fargo_soul.forbidden_soul.attribute.1").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.forbidden_soul.attribute.2").withStyle(ChatFormatting.BLUE)
    );

    public final List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.forbidden_soul.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
    );


    @Override
    public List<Component> getAttributeList() {
        return AttributeList;
    }

    @Override
    public List<Component> getTooltipList() {
        return TooltipList;
    }

    public static void ForbiddenSoulTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.ForbiddenSoul.get())) {
            long GameTime = player.serverLevel().getGameTime();
            if (player.getPersistentData().getLong("ForbiddenSoul") < GameTime) {
                if (KeyUtils.isShift(player)) {
                    player.getPersistentData().putLong("ForbiddenSoul", GameTime + 800);
                    HitResult hitResult = player.pick(20.0, 0, false);
                    if (hitResult instanceof BlockHitResult blockHitResult) {
                        BlockPos pos = blockHitResult.getBlockPos();
                        Vec3 blockPos = new Vec3(pos.getX(), pos.getY(), pos.getZ());
                        for (int i = 1; i < 5; i++) {
                            executorService.schedule(() -> {
                                List<LivingEntity> livingEntityList = player.serverLevel().getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(4));
                                for (LivingEntity livingEntity : livingEntityList) {
                                    if (!livingEntity.equals(player)) {
                                        Vec3 delta = blockPos.subtract(livingEntity.position()).normalize();
                                        livingEntity.addDeltaMovement(delta);
                                    } else {
                                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 0));
                                    }
                                }
                                ParticleUtils.spawnParticleSphere(player.serverLevel(), blockPos, ParticleTypes.DUST_PLUME, 5f, 400, 0.0f);
                            }, i, TimeUnit.SECONDS);
                        }
                    }
                }
            }
        }
    }

}

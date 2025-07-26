package First.fargo_soul.Item.Soul.CosmicPower.SoulStone;

import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Entity.AbstractArrow.AbstractArrowRegister;
import First.fargo_soul.Entity.AbstractArrow.NebulaEmpoweredFlame.NebulaEmpoweredFlame;
import First.fargo_soul.Item.Soul.SoulItem;
import First.fargo_soul.Item.Soul.Souls;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.MathUtils;
import First.fargo_soul.Utils.ParticleUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

public class NebulaSoul  extends SoulItem {

    public NebulaSoul (Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("召唤星云射击，对敌人造成魔法伤害，该效果有三秒冷却时间").withStyle(ChatFormatting.BLUE),
            Component.literal("星云射击命中敌人时产生星云强化焰，玩家靠近时可吸取强化焰").withStyle(ChatFormatting.BLUE),
            Component.literal("星云强化焰分为生命强化焰和伤害强化焰，生命强化焰增加1每秒生命恢复，伤害强化焰增加15%伤害").withStyle(ChatFormatting.BLUE),
            Component.literal("强化增益叠加上限为3，已有强化焰时拾取强化焰会刷新持续时间").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“创生之柱照耀着你”").withStyle(ChatFormatting.DARK_GRAY)
    );

    @Override
    public List<Component> getAttributeList() {
        return this.AttributeList;
    }
    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
        tooltips.addAll(AttributeList);
        tooltips.addAll(TooltipList);
        return tooltips;
    }

    public static void NebulaSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player) {
            Holder<MobEffect> wrathFire = EffectRegister.WrathFire;
            if (player.getEffect(wrathFire) instanceof MobEffectInstance mobEffectInstance) {
                event.setAmount(event.getAmount() * (1 + (mobEffectInstance.getAmplifier() + 1) * 0.15f));
            }
        }
    }

    public static void NebulaSoulDamageHandler2(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            if (event.getSource().getEntity() instanceof NebulaEmpoweredFlame nebulaEmpoweredFlame){
                if ( player.equals(nebulaEmpoweredFlame.getOwner())){
                    event.setCanceled(true);
                }
            }
        }
    }

    public static void NebulaSoulTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.NebulaSoul.get())) {
            if (player.tickCount % 60 == 0) {
                LivingEntity target = player.level().getNearestEntity(LivingEntity.class, TargetingConditions.forCombat().range(20), player, player.getX(), player.getY(), player.getZ(), player.getBoundingBox().inflate(10));
                if (target != null) {
                    target.hurt(player.damageSources().magic(), 8);
                    ServerLevel level = player.serverLevel();
                    NebulaEmpoweredFlame nebulaEmpoweredFlame = new NebulaEmpoweredFlame(AbstractArrowRegister.NebulaEmpoweredFlame.get(), level);
                    nebulaEmpoweredFlame.setPos(new Vec3(target.getRandomX(2), target.getRandomY() + 2, target.getRandomZ(2)));
                    nebulaEmpoweredFlame.setOwner(player);
                    level.addFreshEntity(nebulaEmpoweredFlame);
                    ParticleUtils.spawnParticleLine(
                            level,
                            new Vec3(target.getRandomX(256), level.getMaxBuildHeight(), target.getRandomZ(256)),
                            target.getBoundingBox().getCenter(),
                            ParticleTypes.DRAGON_BREATH,
                            20,
                            0
                    );
                    level.playSound(
                            null,
                            player.getX(), player.getY(), player.getZ(),
                            SoundEvents.EVOKER_CAST_SPELL,
                            SoundSource.PLAYERS,
                            1.0f,
                            MathUtils.random.nextFloat() * 0.4f + 0.4f
                    );
                }
            }

        }
    }












}

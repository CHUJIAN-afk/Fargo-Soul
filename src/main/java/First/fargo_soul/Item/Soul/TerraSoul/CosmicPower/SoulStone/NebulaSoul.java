package First.fargo_soul.Item.Soul.TerraSoul.CosmicPower.SoulStone;

import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Entity.Arrow.NebulaEmpoweredFlame;
import First.fargo_soul.Entity.EntityRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.ParticleUtils;
import First.fargo_soul.Utils.Utils;
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
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

public class NebulaSoul extends SoulItem {

    public NebulaSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.RED));
    }

    public final List<Component> AttributeList = List.of(
            Component.translatable("item.fargo_soul.nebula_soul.attribute.1").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.nebula_soul.attribute.2").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.nebula_soul.attribute.3").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.nebula_soul.attribute.4").withStyle(ChatFormatting.BLUE)
    );

    public final List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.nebula_soul.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
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
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.NebulaSoul.get())) {
            if (player.tickCount % 60 == 0) {
                LivingEntity target = player.level().getNearestEntity(LivingEntity.class, TargetingConditions.forCombat().range(20), player, player.getX(), player.getY(), player.getZ(), player.getBoundingBox().inflate(10));
                if (target != null) {
                    target.hurt(player.damageSources().magic(), 8);
                    ServerLevel level = player.serverLevel();
                    NebulaEmpoweredFlame nebulaEmpoweredFlame = new NebulaEmpoweredFlame(EntityRegister.NebulaEmpoweredFlame.get(), level);
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
                            Utils.random.nextFloat() * 0.4f + 0.4f
                    );
                }
            }

        }
    }












}

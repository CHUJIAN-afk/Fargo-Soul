package First.fargo_soul.Item.Soul.TerraSoul.CosmicPower.SoulStone;

import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Entity.Arrow.NebulaEmpoweredFlame;
import First.fargo_soul.Entity.EntityRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.ParticleUtils;
import First.fargo_soul.Utils.CustomUtils;
import First.fargo_soul.Utils.SoulUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

public class NebulaSoul extends SoulItem {

    public NebulaSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.RED));
    }

    private float energy = 0;
    private final float maxEnergy = 60;

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Tick(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                if (SoulUtils.getSoulItemFromSoulData(attacker, NebulaSoul.class) instanceof NebulaSoul nebulaSoul && ++nebulaSoul.energy >= nebulaSoul.maxEnergy) {
                    Level level = attacker.level();
                    LivingEntity target = null;
                    if (attacker instanceof Player) {
                        List<LivingEntity> livingEntityList = level.getEntitiesOfClass(LivingEntity.class, attacker.getBoundingBox().inflate(20));
                        livingEntityList.remove(attacker);
                        livingEntityList.removeIf(livingEntity -> !(livingEntity instanceof Enemy));
                        if (!livingEntityList.isEmpty()) {
                            target = livingEntityList.get(attacker.getRandom().nextInt(livingEntityList.size()));
                        }
                    } else {
                        List<Player> playerList = level.getEntitiesOfClass(Player.class, attacker.getBoundingBox().inflate(20));
                        if (!playerList.isEmpty()) {
                            target = playerList.get(attacker.getRandom().nextInt(playerList.size()));
                        }
                    }
                    if (target != null) {
                        target.invulnerableTime = 0;
                        target.hurt(attacker.damageSources().magic(), 8);
                        SoulUtils.applyOrUpdateEffect(
                                attacker,
                                attacker.getRandom().nextBoolean() ? EffectRegister.VitalityBoostedBlaze : EffectRegister.WrathFire,
                                200,
                                2
                        );
                        ParticleUtils.spawnParticleLine(
                                (ServerLevel) level,
                                new Vec3(target.getRandomX(256), level.getMaxBuildHeight(), target.getRandomZ(256)),
                                target.getBoundingBox().getCenter(),
                                ParticleTypes.DRAGON_BREATH,
                                20,
                                0
                        );
                        level.playSound(
                                null,
                                target.getX(), target.getY(), target.getZ(),
                                SoundEvents.EVOKER_CAST_SPELL,
                                SoundSource.PLAYERS,
                                1.0f,
                                CustomUtils.random.nextFloat() * 0.4f + 0.4f
                        );
                    }
                }
            }
        }

    }

}

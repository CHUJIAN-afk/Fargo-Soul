package first.fargo_soul.common.item.terraSoul.forestPower;

import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.terraSoul.ForestPower;
import first.fargo_soul.utils.CurioUtils;
import first.fargo_soul.utils.ParticleUtils;
import first.fargo_soul.utils.SoulUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

public class PalmWoodSoul extends SoulItem {

    public PalmWoodSoul(Properties properties) {
        super();
    }

    @Override
    public void livingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
            if (event.getSource().is(DamageTypes.ON_FIRE)) {
                List<LivingEntity> livingEntityList = target.level().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(3), livingEntity -> CurioUtils.isEquipped(livingEntity, PalmWoodSoul.class));
                if (!livingEntityList.isEmpty()) {
                    event.setAmount(event.getAmount() * 1.5f);
                }
            }
        }
        if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
            if (!attacker.equals(target) && CurioUtils.isEquipped(attacker, PalmWoodSoul.class)) {
                int remainingFireTicks = target.getRemainingFireTicks() + 40;
                remainingFireTicks = Math.min(remainingFireTicks, 600);
                target.setRemainingFireTicks(remainingFireTicks);
                if (target.getRemainingFireTicks() > 0 && CurioUtils.isEquipped(attacker, ForestPower.class) && attacker.getRandom().nextDouble() < 0.05) {
                    Level level = attacker.level();
                    for (LivingEntity livingEntity : SoulUtils.getTargetList(attacker, target.getBoundingBox().inflate(1))) {
                        SoulUtils.attack(this.getClass(), attacker, attacker, livingEntity, DamageTypes.PLAYER_EXPLOSION, 4);
                    }
                    ParticleUtils.spawnParticleSphere(
                            (ServerLevel) level,
                            target.getX(),
                            target.getBoundingBox().getCenter().y(),
                            target.getZ(),
                            ParticleTypes.EXPLOSION,
                            1f,
                            10,
                            0.2f
                    );
                    SoulUtils.playSound(
                            level,
                            target.position(),
                            SoundEvents.GENERIC_EXPLODE.value(),
                            SoundSource.PLAYERS
                    );
                }
            }
        }
    }

}

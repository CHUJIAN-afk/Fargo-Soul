package First.fargo_soul.common.item.terraSoul.earthPower;

import First.fargo_soul.common.attachment.SoulAbilityData;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.common.item.terraSoul.EarthPower;
import First.fargo_soul.register.EffectRegister;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.ParticleUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;


public class OrichalcumSoul extends SoulItem {

    public OrichalcumSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
            double chance = CurioUtils.isEquipped(attacker, EarthPower.class) ? 0.4 : 0.2;
            if (!attacker.equals(target) && CurioUtils.isEquipped(attacker, OrichalcumSoul.class) && target.getRandom().nextDouble() < chance) {
                Level level = attacker.level();
                SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(attacker, OrichalcumSoul.class);
                soulInfo.setMaxCooldown(2);
                if (soulInfo.isReady()) {
                    soulInfo.setCooldown(soulInfo.getMaxCooldown());
                    float amount = 1 + (event.getAmount() * 0.05f);
                    int amplifier = CurioUtils.isEquipped(attacker, EarthPower.class) ? 1 : 0;
                    SoulUtils.attack(this.getClass(), attacker, attacker, target, DamageTypes.MAGIC, amount);
                    target.addEffect(new MobEffectInstance(EffectRegister.OrichalcumPoisoning, 99, amplifier));
                    double size = target.getBoundingBox().getSize() * 3;
                    Vec3 start = new Vec3(target.getRandomX(size), target.getRandomY() + size, target.getRandomZ(size));
                    ParticleUtils.spawnMovingParticleLine(
                            (ServerLevel) level,
                            start,
                            target.getBoundingBox().getCenter(),
                            ParticleTypes.CHERRY_LEAVES,
                            2,
                            0.5f,
                            0.005,
                            10,
                            50
                    );
                }
            }
        }
        if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
            if (event.getSource().is(Tags.DamageTypes.IS_POISON) && target.getEffect(EffectRegister.OrichalcumPoisoning) != null) {
                event.setAmount(event.getAmount() * 3.5f);
            }
        }
    }

    @Override
    public void effectApplicable(MobEffectEvent.Applicable event) {
        if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
            if (CurioUtils.isEquipped(attacker, OrichalcumSoul.class) && event.getEffectInstance().is(EffectRegister.OrichalcumPoisoning)) {
                event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
            }
        }
    }

}

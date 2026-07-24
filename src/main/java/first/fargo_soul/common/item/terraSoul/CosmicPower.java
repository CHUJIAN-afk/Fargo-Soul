package first.fargo_soul.common.item.terraSoul;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.api.SoulInfoHelper;
import first.fargo_soul.api.TargetHelper;
import first.fargo_soul.common.attachment.InvincibleData;
import first.fargo_soul.common.attachment.soulInfoData.soulInfo.CoolDownSoulInfo;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.register.SoulInfoRegister;
import first.fargo_soul.utils.ParticleUtils;
import first.fargo_soul.utils.SoulUtils;
import net.minecraft.client.player.Input;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CosmicPower extends SoulItem {

    public CosmicPower(Properties properties) {
        super(properties);
    }

    @Override
    public void movementInput(MovementInputUpdateEvent event) {
        Player player = event.getEntity();
        Input input = event.getInput();
        Vec3 deltaMovement = player.getDeltaMovement();
        double y = deltaMovement.y();
        if (y < 0 && input.shiftKeyDown) {
            if (y > -1.5) {
                player.setDeltaMovement(new Vec3(deltaMovement.x(), -1.5, deltaMovement.z()));
            }
        }
    }

    @Override
    public void target(@NotNull LivingEntity target, @Nullable Entity attacker, @NotNull DamageContainer container, boolean isClient) {
        if (!isClient) {
            SoulInfoHelper helper = SoulInfoHelper.get(target);
            CoolDownSoulInfo coolDownSoulInfo = helper.getInfo(FargoSoul.rl("cosmic_power_armor"), SoulInfoRegister.COOLDOWN);
            if (coolDownSoulInfo == null) {
                coolDownSoulInfo = new CoolDownSoulInfo();
                coolDownSoulInfo.setCooldown((int) (0.25 * 20));
                helper.putInfo(FargoSoul.rl("cosmic_power_armor"), coolDownSoulInfo);

                container.setNewDamage(container.getNewDamage() * 0.8f);

                AttributeInstance instance = target.getAttribute(Attributes.ATTACK_DAMAGE);
                if (instance != null) {
                    double damage = instance.getValue() * 2;
                    TargetHelper targetHelper = TargetHelper.get(target);
                    List<LivingEntity> targetList = targetHelper.getTargetList(3);
                    for (LivingEntity living : targetList) {
                        DamageSources damageSources = target.damageSources();
                        InvincibleData.attack(living)
                                .attacker(target.getUUID())
                                .damageSource(damageSources.inFire())
                                .damageAmount((float) damage)
                                .apply();
                    }
                }
            }
        }
    }

    @Override
    public void attack(@NotNull LivingEntity target, @NotNull LivingEntity attacker, @NotNull DamageContainer container, boolean isClient) {
        if (!isClient) {
            Level level = attacker.level();
            SoulInfoHelper helper = SoulInfoHelper.get(attacker);
            CoolDownSoulInfo coolDownSoulInfo = helper.getInfo(FargoSoul.rl("cosmic_power_shooter"), SoulInfoRegister.COOLDOWN);
            if (coolDownSoulInfo == null) {
                coolDownSoulInfo = new CoolDownSoulInfo();
                coolDownSoulInfo.setCooldown((int) (0.25f * 20));
                helper.putInfo(FargoSoul.rl("cosmic_power_shooter"), coolDownSoulInfo);
                AttributeInstance instance = target.getAttribute(Attributes.ATTACK_DAMAGE);
                if (instance != null) {
                    float damage = (float) (instance.getValue() * 1.5);
                    DamageSources damageSources = attacker.damageSources();
                    InvincibleData.attack(target)
                            .attacker(target.getUUID())
                            .damageSource(damageSources.magic())
                            .damageAmount(damage)
                            .apply();
                    AABB box = target.getBoundingBox();
                    ParticleUtils.spawnMovingParticleLine((ServerLevel) level, new Vec3(target.getRandomX(256), target.getY() + level.getMaxBuildHeight(), target.getRandomZ(256)), box.getCenter(), ParticleTypes.DRAGON_BREATH, 100, 0.25f, 0, 5, 50);
                    SoulUtils.playSound(level, target.position(), SoundEvents.EVOKER_CAST_SPELL, attacker.getSoundSource());
                }
            }
            RandomSource random = attacker.getRandom();
            if (random.nextBoolean()) {
                container.setNewDamage(container.getNewDamage() * 1.25f);
            }
            if (random.nextBoolean()) {
                container.setNewDamage(container.getNewDamage() + (target.getMaxHealth() - target.getHealth()) * 0.05f);
            }
            if (random.nextBoolean()) {
                attacker.heal(container.getNewDamage() * 0.15f);
            }
            if (random.nextBoolean()) {
                TargetHelper targetHelper = TargetHelper.get(attacker);
                List<LivingEntity> livingEntityList = targetHelper.geLivingEntityList();
                for (LivingEntity living : livingEntityList) {
                    if (living.distanceTo(target) < 3 && targetHelper.isTarget(living)) {
                        DamageSource source = container.getSource();
                        InvincibleData.attack(living)
                                .attacker(target.getUUID())
                                .damageSource(new DamageSource(source.typeHolder(), living.position()))
                                .damageAmount(container.getNewDamage() * 0.25f)
                                .apply();
                    }
                }
                attacker.heal(container.getNewDamage() * 0.15f);
            }
        }
    }
}

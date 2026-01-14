package First.fargo_soul.item.terraSoul.earthPower;

import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.client.gui.SoulGuiLayer;
import First.fargo_soul.client.gui.SoulRenderType;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.EarthPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.register.EffectRegister;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.ParticleUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

import java.util.List;


public class CobaltSoul extends SoulItem {

    public CobaltSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(LivingEntity ticker) {
        if (!ticker.level().isClientSide()) {
            SoulAbilityData.getSoulInfo(ticker, CobaltSoul.class).setMaxCooldown(60);
            if (CurioUtils.isEquipped(ticker, CobaltSoul.class)) {
                Level level = ticker.level();
                List<LivingEntity> targetList = level.getEntitiesOfClass(LivingEntity.class, ticker.getBoundingBox().inflate(2));
                targetList.remove(ticker);
                for (LivingEntity target : targetList) {
                    target.addEffect(new MobEffectInstance(EffectRegister.Oil, 219));
                }
            }
        }
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
            boolean equipped = CurioUtils.isEquipped(target, EarthPower.class);
            SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(CobaltSoul.class);
            soulInfo.setMaxCooldown(equipped ? 40 : 60);
            if (CurioUtils.isEquipped(target, CobaltSoul.class) && soulInfo.isReady()) {
                soulInfo.setCooldown(soulInfo.getMaxCooldown());
                Level level = target.level();
                int value = equipped ? 3 : 2;
                List<LivingEntity> targetList = target.level().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(value));
                targetList.remove(target);
                for (LivingEntity entity : targetList) {
                    float amount = equipped ? 6 : 4;
                    entity.hurt(entity.damageSources().mobAttack(entity), amount);
                    int duration = equipped ? 900 : 600;
                    entity.addEffect(new MobEffectInstance(EffectRegister.Oil, duration, 0));
                }
                ParticleUtils.spawnParticleSphere(
                        (ServerLevel) level,
                        target.getX(),
                        target.getBoundingBox().getCenter().y(),
                        target.getZ(),
                        ParticleTypes.EXPLOSION,
                        equipped ? 1.5f : 1f,
                        equipped ? 15 : 10,
                        equipped ? 0.3f : 0.2f
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

    @Override
    public void effectApplicable(MobEffectEvent.Applicable event) {
        if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
            if (CurioUtils.isEquipped(attacker, CobaltSoul.class) && event.getEffectInstance().is(EffectRegister.Oil)) {
                event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
            }
        }
    }

    @Override
    public void getSoulRenderInfo(SoulGuiLayer.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, CobaltSoul.class, SoulRenderType.Cooldown);
    }

}

package First.fargo_soul.item.terraSoul;

import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.client.gui.SoulGuiRenderManager;
import First.fargo_soul.client.gui.SoulRenderType;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.register.AttributeRegister;
import First.fargo_soul.register.EffectRegister;
import First.fargo_soul.utils.AttributeUtils;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.ParticleUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;

import java.util.List;

import static First.fargo_soul.register.ItemRegister.*;

public class TerraPower extends SoulItem {

    public TerraPower(Properties properties) {
        super(properties);
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                CopperSoulItem.get(),
                IronSoulItem.get(),
                LeadSoulItem.get(),
                ObsidianSoulItem.get(),
                SilverSoulItem.get(),
                TinSoulItem.get(),
                TungstenSoulItem.get()
        );
    }

    @Override
    public void criticalHit(CriticalHitEvent event) {
        Player player = event.getEntity();
        Entity target = event.getTarget();
        Level level = player.level();
        if (!level.isClientSide() && CurioUtils.isEquipped(player, TerraPower.class)) {
            SoulAbilityData.SoulInfo soulInfo = SoulAbilityData.getSoulInfo(player, TerraPower.class);
            if (soulInfo.isReady() && player.getRandom().nextDouble() < 0.2) {
                soulInfo.addStacks();
                LightningBolt lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                lightning.setPos(target.getBoundingBox().getCenter());
                lightning.setDamage(40);
                SoulUtils.addEntity(level, lightning);
                List<LivingEntity> targetList = level.getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(2), living -> living != player);
                for (LivingEntity living : targetList) {
                    SoulUtils.attack(TerraPower.class, living, player, living, DamageTypes.PLAYER_EXPLOSION, 8);
                    living.addEffect(new MobEffectInstance(EffectRegister.LeadPoisoning, 100, 2));
                }
                ParticleUtils.spawnParticleSphere(
                        (ServerLevel) level,
                        target.getX(),
                        target.getBoundingBox().getCenter().y(),
                        target.getZ(),
                        ParticleTypes.EXPLOSION,
                        1.5f,
                        15,
                        0.3f
                );
                SoulUtils.playSound(
                        level,
                        target.position(),
                        SoundEvents.GENERIC_EXPLODE.value(),
                        target.getSoundSource()
                );
            }
        }
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()){
            SoulAbilityData.SoulInfo soulInfo = SoulAbilityData.getSoulInfo(target, TerraPower.class);
            soulInfo.removeStacks();
        }
    }

    @Override
    public void tick(LivingEntity ticker) {
        if (!ticker.level().isClientSide()) {
            SoulAbilityData.SoulInfo soulInfo = SoulAbilityData.getSoulInfo(ticker, TerraPower.class);
            soulInfo.setMaxStacks(4);
            soulInfo.setMaxCooldown(soulInfo.getStacks() == soulInfo.getMaxStacks() ? 60 : 100);
            AttributeUtils.condition(
                    ticker,
                    AttributeRegister.CriticalChance,
                    AttributeUtils.value(TerraPowerItem.getId(), 0.1 * soulInfo.getStacks()),
                    soulInfo.getStacks() > 0 && CurioUtils.isEquipped(ticker, TerraPower.class)
            );
        }
    }

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, TerraPower.class, SoulRenderType.Stack);
        soulRenderManager.add(this, TerraPower.class, SoulRenderType.Cooldown);
    }

}
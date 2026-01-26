package First.fargo_soul.common.item.terraSoul;

import First.fargo_soul.common.attachment.SoulAbilityData;
import First.fargo_soul.client.gui.SoulGuiRenderManager;
import First.fargo_soul.client.gui.SoulRenderType;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.ParticleUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

import static First.fargo_soul.register.ItemRegister.*;

public class ForestPower extends SoulItem {

    public ForestPower(Properties properties) {
        super(properties);
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                EbonyWoodSoulItem.get(),
                PalmWoodSoulItem.get(),
                PearlWoodSoulItem.get(),
                PineWoodSoulItem.get(),
                RoseWoodSoulItem.get(),
                ShadowWoodSoulItem.get(),
                WoodSoulItem.get()
        );
    }

    @Override
    public void tick(LivingEntity ticker) {
        Level level = ticker.level();
        if (!level.isClientSide() && CurioUtils.isEquipped(ticker, ForestPower.class)) {
            SoulAbilityData.getSoulInfo(ticker, ForestPower.class).setMaxCooldown(160);
            SoulAbilityData.getSoulInfo(ticker, "ForestPowerSnowball").setMaxCooldown(5);
            for (LivingEntity living : SoulUtils.getTargetList(ticker, 5)) {
                if (!CurioUtils.isEquipped(living, ForestPower.class)) {
                    living.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 1));
                }
            }
        }
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        Entity source = event.getSource().getEntity();
        if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
            if (CurioUtils.isEquipped(target, ForestPower.class)) {
                if (target.onGround()) {
                    SoulAbilityData.SoulInfo soulInfo = SoulAbilityData.getSoulInfo(target, ForestPower.class);
                    if (soulInfo.isReady()) {
                        soulInfo.setCooldown(soulInfo.getMaxCooldown());
                        if (source instanceof LivingEntity attacker) {
                            SoulUtils.attack(target, attacker, DamageTypes.CACTUS, 2 * event.getAmount());
                        }
                        event.setAmount(event.getAmount() * 0.7f);
                    }
                }
                if (target instanceof ServerPlayer player && source instanceof LivingEntity entity) {
                    if (player.getStats().getValue(Stats.ENTITY_KILLED, entity.getType()) > 0) {
                        event.setAmount(event.getAmount() * 0.8f);
                    }
                }
            }
        }
        if (event.getEntity() instanceof LivingEntity target && source instanceof LivingEntity attacker) {
            Level level = target.level();
            if (!level.isClientSide()) {
                if (CurioUtils.isEquipped(attacker, ForestPower.class)) {
                    if (target.hasEffect(MobEffects.WITHER)) {
                        event.setAmount(event.getAmount() + 15);
                    }
                    SoulAbilityData.SoulInfo soulInfo = SoulAbilityData.getSoulInfo(attacker, "ForestPowerSnowball");
                    if (soulInfo.isReady() && target.distanceTo(attacker) < 5) {
                        soulInfo.setCooldown(soulInfo.getMaxCooldown());
                        Snowball snowball = new Snowball(EntityType.SNOWBALL, level);
                        SoulUtils.shootTargetFromAttaker(snowball, attacker, target, 2, 4);
                        SoulUtils.setAbilityInvulnerable(snowball);
                        ParticleUtils.spawnParticleSphere(
                                (ServerLevel) level,
                                snowball.position(),
                                ParticleTypes.ITEM_SNOWBALL,
                                0.2f,
                                10,
                                0.5f
                        );
                        SoulUtils.playSound(
                                level,
                                snowball.position(),
                                SoundEvents.SNOWBALL_THROW,
                                SoundSource.PLAYERS
                        );
                    }
                }
            }
        }
    }

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, "ForestPowerSnowball", SoulRenderType.Cooldown);
        soulRenderManager.add(this, ForestPower.class, SoulRenderType.Cooldown);
    }

}

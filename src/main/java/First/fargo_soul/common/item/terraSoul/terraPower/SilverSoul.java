package First.fargo_soul.common.item.terraSoul.terraPower;

import First.fargo_soul.client.gui.SoulGuiRenderManager;
import First.fargo_soul.client.gui.SoulRenderType;
import First.fargo_soul.common.attachment.SoulAbilityData;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.common.item.terraSoul.TerraPower;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.utils.AttributeUtils;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.ParticleUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;

public class SilverSoul extends SoulItem {

    public SilverSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(LivingEntity ticker) {
        if (!ticker.level().isClientSide()) {
            AttributeUtils.condition(
                    ticker,
                    Attributes.ARMOR,
                    ItemRegister.SilverSoulItem.getId(),
                    10,
                    AttributeModifier.Operation.ADD_VALUE,
                    CurioUtils.isEquipped(ticker, SilverSoul.class) && (ticker instanceof Player && ticker.isBlocking() || (ticker instanceof Mob mob && mob.getTarget() != null))
            );
            SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(ticker, SilverSoul.class);
            soulInfo.setMaxCooldown(20);
            if (CurioUtils.isEquipped(ticker, SilverSoul.class)) {
                if (ticker.isBlocking()) {
                    soulInfo.setMaxStacks(100);
                    soulInfo.addStacks();
                } else {
                    soulInfo.removeStacks();
                }
            }
        }
    }

    @Override
    public void shieldBlock(LivingShieldBlockEvent event) {
        if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
            LivingEntity attacker = event.getDamageSource().getEntity() instanceof LivingEntity ? (LivingEntity) event.getDamageSource().getEntity() : null;
            if (CurioUtils.isEquipped(target, SilverSoul.class)) {
                SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(target, SilverSoul.class);
                if (event.getBlocked() && soulInfo.isReady() && soulInfo.getStacks() > 0 && soulInfo.getStacks() < (CurioUtils.isEquipped(target, TerraPower.class) ? 6 : 4)) {
                    soulInfo.setDuration(20);
                    soulInfo.setCooldown(soulInfo.getMaxCooldown());
                    if (attacker != null) {
                        attacker.hurt(target.damageSources().mobAttack(target), event.getBlockedDamage() * 2.0f);
                    }
                    Level level = target.level();
                    SoulUtils.playSound(
                            level,
                            target.position(),
                            SoundEvents.ANVIL_PLACE,
                            SoundSource.PLAYERS
                    );
                    if (attacker != null) {
                        ParticleUtils.spawnParticleLine(
                                (ServerLevel) level,
                                target.getBoundingBox().getCenter(),
                                attacker.getBoundingBox().getCenter(),
                                ParticleTypes.CRIT,
                                20,
                                0.1f
                        );
                    }
                    ParticleUtils.spawnParticleSphere(
                            (ServerLevel) level,
                            target.getX(),
                            target.getY(),
                            target.getZ(),
                            ParticleTypes.CRIT,
                            1,
                            40,
                            0.1f
                    );
                }
            }
        }
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
            if (CurioUtils.isEquipped(attacker, SilverSoul.class)) {
                SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(attacker, SilverSoul.class);
                if (soulInfo.getDuration() > 0) {
                    event.setAmount(event.getAmount() * 1.5f);
                }
            }
        }
    }

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, SilverSoul.class, SoulRenderType.Cooldown);
        soulRenderManager.add(this, SilverSoul.class, SoulRenderType.Duration);
    }

}

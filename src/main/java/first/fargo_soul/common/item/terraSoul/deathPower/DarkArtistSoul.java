package first.fargo_soul.common.item.terraSoul.deathPower;

import first.fargo_soul.client.gui.SoulGuiRenderManager;
import first.fargo_soul.client.gui.SoulRenderType;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.terraSoul.DeathPower;
import first.fargo_soul.utils.CurioUtils;
import first.fargo_soul.utils.ParticleUtils;
import first.fargo_soul.utils.SoulUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class DarkArtistSoul extends SoulItem {

    public DarkArtistSoul(Properties properties) {
        super();
    }

    @Override
    public void tick(LivingEntity ticker, boolean isClient) {
        if (!ticker.level().isClientSide()) {
            SoulUtils.getSoulInfo(ticker, DarkArtistSoul.class).setMaxCooldown(200);
        }
    }

    @Override
    public void livingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
            if (CurioUtils.isEquipped(target, DarkArtistSoul.class)) {
                attacker.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200));
                attacker.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 200));
            }
            if (CurioUtils.isEquipped(attacker, DarkArtistSoul.class)) {
                Level level = attacker.level();
                //箭矢
                SoulAbilityData.SoulInfo DarkArtistArrow = SoulUtils.getSoulInfo(attacker, "DarkArtistArrow");
                DarkArtistArrow.setMaxCooldown(200);
                if (DarkArtistArrow.isReady()) {
                    DarkArtistArrow.setCooldown(DarkArtistArrow.getMaxCooldown());
                    for (int i = 0; i < 8; i++) {
                        Arrow arrow = new Arrow(EntityType.ARROW, level);
                        arrow.setBaseDamage(event.getAmount() * (CurioUtils.isEquipped(attacker, DeathPower.class) ? 0.25 : 0.15f));
                        SoulUtils.shootTargetFromAttaker(arrow, attacker, target, 2, 3);
                        SoulUtils.setAbilityInvulnerable(arrow);
                        SoulUtils.playSound(
                                level,
                                arrow.position(),
                                SoundEvents.ARROW_SHOOT,
                                SoundSource.PLAYERS
                        );
                    }
                }
                //火球
                SoulAbilityData.SoulInfo DarkArtistFireball = SoulUtils.getSoulInfo(attacker, "DarkArtistFireball");
                DarkArtistFireball.setMaxCooldown(20);
                double chance = CurioUtils.isEquipped(attacker, DeathPower.class) ? 0.2 : 0.1;
                if (DarkArtistFireball.isReady() && attacker.getRandom().nextDouble() < chance) {
                    DarkArtistFireball.setCooldown(DarkArtistFireball.getMaxCooldown());
                    SmallFireball fireball = new SmallFireball(EntityType.SMALL_FIREBALL, level);
                    SoulUtils.shootTargetFromAttaker(fireball, attacker, target);
                    SoulUtils.setAbilityInvulnerable(fireball);
                    ParticleUtils.spawnParticleSphere(
                            (ServerLevel) level,
                            fireball.position(),
                            ParticleTypes.LAVA,
                            0.2f,
                            5,
                            0.2f
                    );
                }
            }
        }
    }

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, "DarkArtistArrow", SoulRenderType.Cooldown);
        soulRenderManager.add(this, "DarkArtistFireball", SoulRenderType.Cooldown);
    }

}


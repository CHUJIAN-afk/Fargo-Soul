package first.fargo_soul.common.entity;

import first.fargo_soul.common.attachment.SoulTargetCache;
import first.fargo_soul.register.FargoSoulMobEffectRegister;
import first.fargo_soul.register.SummonerAttachmentEntityRegister;
import first.lyra.common.attachmentEntity.IEntityCollision;
import first.lyra.utils.ParticleHelper;
import first.lyra.common.projectile.Projectile;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SnowBall extends Projectile implements IEntityCollision<SnowBall> {

    public SnowBall() {
        super(SummonerAttachmentEntityRegister.SNOW_BALL);
        setGravity(0);
        setDrag(0.97f);
        setMaxTickCount(60);
    }

    @Override
    public void tick() {
        super.tick();
        Level level = owner.level();
        Vec3 pos = getPos();
        ParticleHelper.create(level)
                .type(ParticleTypes.ITEM_SNOWBALL)
                .pos(pos)
                .offset(0.2)
                .count(2)
                .speed(0.5)
                .spread(Math.TAU)
                .emit();
    }

    @Override
    public @NotNull AABB getHitbox() {
        return new AABB(-0.1, -0.1, -0.1, 0.1, 0.1, 0.1);
    }

    @Override
    public boolean isValidCollisionTarget(SnowBall entity, LivingEntity target) {
        return SoulTargetCache.isTarget(owner, target);
    }

    @Override
    public void onCollisionAttack(List<HitContext> hitContexts) {
        HitContext first = hitContexts.getFirst();
        LivingEntity living = first.entity();
        living.addEffect(new MobEffectInstance(FargoSoulMobEffectRegister.Chill, 70));
        attack(living, getDamage(), 5);
        setCurrentPathNode(getCurrentPathNode().modifyPos(first.hitPoint()));
        setRemove();
    }

    @Override
    public DamageSource getDamageSource() {
        Player owner = getOwner();
        return owner.damageSources().playerAttack(owner);
    }
}

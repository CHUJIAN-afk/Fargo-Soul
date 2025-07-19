package First.fargo_soul.Entity.Projectile.IceSpike;

import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Entity.Projectile.ProjectileRegister;
import First.fargo_soul.Item.ProjectileItem.ProjectileItems;
import First.fargo_soul.Item.Soul.Souls;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;


public class IceSpike extends ThrowableItemProjectile {

    public IceSpike(EntityType<? extends IceSpike> type, Level level) {
        super(type, level);
    }

    public IceSpike(Level level, LivingEntity shooter) {
        super(ProjectileRegister.IceSpike.get(), shooter, level);
    }

    public IceSpike(Level level, double x, double y, double z) {
        super(ProjectileRegister.IceSpike.get(), x, y, z, level);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ProjectileItems.IceSpike.get();
    }

    private ParticleOptions getParticle() {
        ItemStack itemstack = this.getItem();
        return !itemstack.isEmpty() && !itemstack.is(this.getDefaultItem()) ? new ItemParticleOption(ParticleTypes.ITEM, itemstack) : ParticleTypes.SNOWFLAKE;
    }

    public void handleEntityEvent(byte id) {
        if (id == 3) {
            ParticleOptions particleoptions = this.getParticle();
            for(int i = 0; i < 8; ++i) {
                this.level().addParticle(particleoptions, this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F, 0.0F);
            }
        }
    }

    protected void onHitEntity(@NotNull EntityHitResult result) {
        super.onHitEntity(result);
        if (result.getEntity() instanceof LivingEntity livingEntity) {
            int damage = livingEntity instanceof Blaze ? 6 : 1;
            livingEntity.hurt(this.damageSources().thrown(this, this.getOwner()), (float) damage);
            if (!CurioUtils.isEquipped(livingEntity, Souls.FrostSoul.get())) {
                if (livingEntity.getEffect(EffectRegister.Freezing) == null) {
                    livingEntity.addEffect(new MobEffectInstance(EffectRegister.Freezing, 5));
                }
                livingEntity.addEffect(new MobEffectInstance(EffectRegister.Frostbite, 120));
            }
        }
    }

    protected void onHit(@NotNull HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

}


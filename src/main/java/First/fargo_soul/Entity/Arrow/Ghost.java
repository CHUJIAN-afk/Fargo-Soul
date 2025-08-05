package First.fargo_soul.Entity.Arrow;

import First.fargo_soul.Utils.ParticleUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class Ghost extends AbstractArrow {
    private static final EntityDataAccessor<Byte> DATA_PIERCE_LEVEL_ID = SynchedEntityData.defineId(Ghost.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Byte> DATA_CRITICAL_ID = SynchedEntityData.defineId(Ghost.class, EntityDataSerializers.BYTE);

    public Ghost(EntityType<? extends Ghost> type, Level level) {
        super(type, level);
        this.pickup = Pickup.DISALLOWED;
        this.setNoGravity(true);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_PIERCE_LEVEL_ID, (byte) 10);
        builder.define(DATA_CRITICAL_ID, (byte) 11);
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        if (this.level() instanceof ServerLevel && result.getEntity() instanceof LivingEntity livingEntity) {
            if (!livingEntity.equals(this.getOwner())) {
                livingEntity.hurt(this.damageSources().thrown(this, this.getOwner()), 2.0f);
                this.discard();
            }
        }
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult result) {
        super.onHitBlock(result);
        this.discard();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickCount >= 100) {
            this.discard();
        }
        if (level() instanceof ServerLevel level) {
            ParticleUtils.spawnParticleSphere(
                    level,
                    position(),
                    ParticleTypes.SOUL,
                    0.2f,
                    1,
                    0.2f
            );
        }
    }

    @Override
    protected @NotNull ItemStack getDefaultPickupItem() {
        return Items.SOUL_SAND.getDefaultInstance();
    }

}

package First.fargo_soul.Entity.AbstractArrow.NeedleProjectile;

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

public class NeedleProjectile extends AbstractArrow {

    private static final EntityDataAccessor<Byte> DATA_PIERCE_LEVEL_ID = SynchedEntityData.defineId(NeedleProjectile.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Byte> DATA_CRITICAL_ID = SynchedEntityData.defineId(NeedleProjectile.class, EntityDataSerializers.BYTE);

    public NeedleProjectile(EntityType<? extends NeedleProjectile> type, Level level) {
        super(type, level);
        this.pickup = Pickup.CREATIVE_ONLY;
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
            livingEntity.hurt(this.damageSources().thrown(this, this.getOwner()), 1.0f);
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult result) {
        super.onHitBlock(result);
        this.discard();
    }

    @Override
    protected @NotNull ItemStack getDefaultPickupItem() {
        return Items.CACTUS.getDefaultInstance();
    }

}
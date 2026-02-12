package First.fargo_soul.common.entity.projectile;

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

public class Needle extends AbstractArrow {

    public Needle(EntityType<? extends Needle> type, Level level) {
        super(type, level);
        this.pickup = Pickup.CREATIVE_ONLY;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        if (this.level() instanceof ServerLevel && result.getEntity() instanceof LivingEntity livingEntity) {
            if (!livingEntity.equals(this.getOwner())) {
                livingEntity.hurt(this.damageSources().thrown(this, this.getOwner()), 1.0f);
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
    protected @NotNull ItemStack getDefaultPickupItem() {
        return Items.CACTUS.getDefaultInstance();
    }

}
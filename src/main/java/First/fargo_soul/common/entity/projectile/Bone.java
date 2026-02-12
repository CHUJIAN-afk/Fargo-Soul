package First.fargo_soul.common.entity.projectile;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class Bone extends AbstractArrow {

    public Bone(EntityType<? extends Bone> type, Level level) {
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
                livingEntity.hurt(this.damageSources().thrown(this, this.getOwner()), 0.5f);
                this.discard();
            }
        }
    }

    @Override
    protected @NotNull ItemStack getDefaultPickupItem() {
        return Items.BONE.getDefaultInstance();
    }

}

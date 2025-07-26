package First.fargo_soul.Entity.AbstractArrow.NebulaEmpoweredFlame;

import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Utils.MathUtils;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NebulaEmpoweredFlame extends AbstractArrow {
    private static final EntityDataAccessor<Byte> DATA_PIERCE_LEVEL_ID = SynchedEntityData.defineId(NebulaEmpoweredFlame.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Byte> DATA_CRITICAL_ID = SynchedEntityData.defineId(NebulaEmpoweredFlame.class, EntityDataSerializers.BYTE);

    public NebulaEmpoweredFlame(EntityType<? extends NebulaEmpoweredFlame> type, Level level) {
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
    public void tick() {
        super.tick();
        if (this.tickCount >= 300) {
            this.discard();
        }
        List<Player> players = this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(10), player -> player.equals(this.getOwner()));
        if (!players.isEmpty()) {
            Player player = players.getFirst();
            Vec3 delta = player.getEyePosition().subtract(this.position()).normalize().scale(0.05);
            this.getDeltaMovement().add(delta);
            this.move(MoverType.SELF, delta);
            if (player.getEyePosition().distanceTo(this.position()) < 0.75) {
                if (MathUtils.random.nextBoolean()) {
                    Holder<MobEffect> vitalityBoostedBlaze = EffectRegister.VitalityBoostedBlaze;
                    if (player.getEffect(vitalityBoostedBlaze) instanceof MobEffectInstance mobEffectInstance) {
                        player.removeEffect(vitalityBoostedBlaze);
                        player.addEffect(new MobEffectInstance(vitalityBoostedBlaze, 200, Math.min(mobEffectInstance.getAmplifier() + 1, 2)));
                    } else {
                        player.addEffect(new MobEffectInstance(vitalityBoostedBlaze, 200));
                    }
                } else {
                    Holder<MobEffect> wrathFire = EffectRegister.WrathFire;
                    if (player.getEffect(wrathFire) instanceof MobEffectInstance mobEffectInstance) {
                        player.removeEffect(wrathFire);
                        player.addEffect(new MobEffectInstance(wrathFire, 200, Math.min(mobEffectInstance.getAmplifier() + 1, 2)));
                    } else {
                        player.addEffect(new MobEffectInstance(wrathFire, 200));
                    }
                }
                this.discard();
            }
        }
    }

    @Override
    protected boolean canHitEntity(@NotNull Entity target) {
        return false;
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        this.discard();
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult result) {
    }

    @Override
    protected @NotNull ItemStack getDefaultPickupItem() {
        return Items.DIRT.getDefaultInstance();
    }

}

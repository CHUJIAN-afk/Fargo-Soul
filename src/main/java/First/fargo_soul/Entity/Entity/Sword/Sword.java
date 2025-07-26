package First.fargo_soul.Entity.Entity.Sword;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Sword extends Entity {
    private static final EntityDataAccessor<Integer> OWNER_ID = SynchedEntityData.defineId(Sword.class, EntityDataSerializers.INT);

    public Sword(EntityType<?> type, Level level) {
        super(type, level);
        this.setNoGravity(true);
        this.setInvulnerable(true);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(OWNER_ID, -1);
    }

    public void setOwner(Player player) {
        this.entityData.set(OWNER_ID, player.getId());
    }

    public Player getOwner() {
        int id = this.entityData.get(OWNER_ID);
        return id == -1 ? null : (Player) level().getEntity(id);
    }

    @Override
    public void tick() {
        super.tick();
        if (getOwner() instanceof LivingEntity owner) {
            Vec3 ownerPos = owner.getEyePosition();
            Vec3 delta = ownerPos.subtract(position());
            float followRange = 1.0f;
            double distance = delta.length();
            if (distance > followRange) {
                Vec3 movement = delta.normalize().scale(Math.min(distance * 0.05f, 0.4));
                Vec3 deltaMovement = this.getDeltaMovement();
                Vec3 vec3 = new Vec3(
                        Mth.lerp(0.1, deltaMovement.x(), movement.x()),
                        Mth.lerp(0.1, deltaMovement.y(), movement.y()),
                        Mth.lerp(0.1, deltaMovement.z(), movement.z())
                );
                this.setDeltaMovement(vec3);
                this.lookAt(EntityAnchorArgument.Anchor.EYES, owner.getEyePosition());
            } else {
                Vec3 currentMotion = this.getDeltaMovement();
                this.setDeltaMovement(currentMotion.scale(0.8));
            }
            this.move(MoverType.SELF, this.getDeltaMovement());
        } else {
            Player player = this.level().getNearestPlayer(this, 64);
            if (player != null) {
                setOwner(player);
            } else {
                this.discard();
            }
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("Owner")) {
            this.entityData.set(OWNER_ID, tag.getInt("Owner"));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        if (getOwner() != null) {
            tag.putInt("Owner", getOwner().getId());
        }
    }
}

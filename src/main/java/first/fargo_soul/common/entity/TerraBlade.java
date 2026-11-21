package first.fargo_soul.common.entity;

import first.fargo_soul.common.attachment.SoulItemData;
import first.fargo_soul.common.entity.goal.terrablade.BladeAttackGoal;
import first.fargo_soul.common.entity.goal.terrablade.BladeIdleGoal;
import first.fargo_soul.common.entity.goal.terrablade.BladePrepGoal;
import first.fargo_soul.register.FargoSoulItemRegister;
import first.fargo_soul.register.SummonerAttachmentEntityRegister;
import first.lyra.api.LyraHelper;
import first.lyra.common.attachment.TargetCache;
import first.lyra.common.attachmentEntity.AttachmentEntityGoalSelector;
import first.lyra.common.attachmentEntity.IEntityCollision;
import first.lyra.common.attachmentEntity.PathNode;
import first.lyra.common.attachmentEntity.SyncFieldDispatcher;
import first.lyra.common.minion.Minion;
import first.lyra.common.minion.MinionSlotType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TerraBlade extends Minion implements IEntityCollision<TerraBlade> {

    public boolean attacking = false;

    public int trailTimer = 0;
    public float idleBlend = 0f;
    public float idleBlendO = 0f;
    public final Set<LivingEntity> hitTargets = new HashSet<>();
    public boolean spiritPower = false;
    public boolean ancientHolySoul = false;

    public TerraBlade() {
        super(SummonerAttachmentEntityRegister.TERRA_BLADE);
        setSlotCost(0);
        setSlotType(MinionSlotType.None);
        setDamage(8);
    }

    @Override
    protected void registerSyncFields(SyncFieldDispatcher fields) {
        super.registerSyncFields(fields);
        fields.field(ByteBufCodecs.BOOL, () -> attacking, value -> attacking = value);
        fields.field(ByteBufCodecs.FLOAT, () -> idleBlend, value -> idleBlend = value);
        fields.field(ByteBufCodecs.FLOAT, () -> idleBlendO, value -> idleBlendO = value);
        fields.field(ByteBufCodecs.INT, () -> trailTimer, value -> trailTimer = value);
    }

    @Override
    public void registerGoals(AttachmentEntityGoalSelector goalSelector) {
        goalSelector.addGoal(0, new BladeAttackGoal(this));
        goalSelector.addGoal(1, new BladePrepGoal(this));
        goalSelector.addGoal(2, new BladeIdleGoal(this));
    }

    @Override
    public LivingEntity searchTarget() {
        int distance = this.getSearchDistance();
        if (distance > 0) {
            TargetCache targetCache = getTargetCache();
            List<LivingEntity> targets = targetCache.getEntitiesInRadius(this.getPos(), targetCache.getSummonSearchRange(this.getOwner(), (float) distance), (living) -> (attacking || targetCache.isVisibility(this.owner, living)) && targetCache.isTarget(living));
            if (!targets.isEmpty()) {
                return targetCache.getNewTarget(this, targets, 8, false);
            }
        }
        return null;
    }

    @Override
    public int getSearchDistance() {
        return 32;
    }

    @Override
    public @NotNull AABB getHitbox() {
        return new AABB(-0.15, -0.06, -0.5, 0.15, 0.06, 1.05);
    }

    @Override
    public boolean canCollideAttack() {
        return getTargetCache().isTarget(getTarget());
    }

    @Override
    public void onCollisionAttack(List<HitContext> hitContexts) {
        for (HitContext hit : hitContexts) {
            if (this.hitTargets.add(hit.entity())) {
                attack(hit.entity(), getDamage(), 0);
            }
        }
    }

    @Override
    public boolean isValidCollisionTarget(TerraBlade entity, LivingEntity target) {
        return getTargetCache().isTarget(target);
    }

    @Override
    public void tick() {
        super.tick();
        idleBlendO = idleBlend;
        if (getGoalSelector().getCurrentGoal() instanceof BladeIdleGoal) {
            trailTimer = Math.max(trailTimer - 1, 0);
            idleBlend = Math.min(1.0f, idleBlend + 0.1f);
        } else {
            trailTimer = Math.min(trailTimer + 1, 12);
            idleBlend = Math.max(0.0f, idleBlend - 0.25f);
        }
        if (spiritPower && !SoulItemData.isEquipped(getOwner(), FargoSoulItemRegister.SpiritPowerItem)) {
            setRemove();
        }
        if (ancientHolySoul && !SoulItemData.isEquipped(getOwner(), FargoSoulItemRegister.AncientHolySoulItem)) {
            setRemove();
        }
    }

    @Override
    public PathNode getRenderNode(float partialTick) {
        PathNode renderNode = super.getRenderNode(partialTick);
        return renderNode.lerp(getInterpolatedIdleState(partialTick), Mth.lerp(partialTick, idleBlendO, idleBlend));
    }

    public PathNode getInterpolatedIdleState(float partialTick) {
        float bodyYaw = Mth.rotLerp(partialTick, owner.yBodyRotO, owner.yBodyRot);
        float headYaw = Mth.rotLerp(partialTick, owner.yHeadRotO, owner.yHeadRot);
        float playerYaw = Mth.wrapDegrees(bodyYaw + Mth.wrapDegrees(headYaw - bodyYaw) * 0.5f);
        float rad = (float) Math.toRadians(-playerYaw + 180);
        float backX = (float) Math.sin(rad);
        float backZ = (float) Math.cos(rad);
        float rightX = (float) Math.cos(rad);
        float rightZ = (float) -Math.sin(rad);
        int order = getOrder();
        double localZ = 0.75 + order * 0.12;
        double floatSpeed = 0.08 + order * 0.01;
        double floatAngle = (owner.tickCount + partialTick) * floatSpeed + order * 1.33;
        Vec3 playerPos = owner.getPosition(partialTick);
        Vec3 targetPos = playerPos.add(localZ * backX + Math.cos(floatAngle) * 0.075 * rightX, owner.getBbHeight() * 0.6 + Math.sin(floatAngle) * 0.075, localZ * backZ + Math.cos(floatAngle) * 0.075 * rightZ);
        return new PathNode(targetPos, playerYaw - 90, 75 - order * 5f, 100);
    }

    @Override
    public int getOrder() {
        return LyraHelper.get(this.owner).getEntityData().get(this.getType()).indexOf(this);
    }

    @Override
    public int getSameSize() {
        return LyraHelper.get(this.owner).getEntityData().get(this.getType()).size();
    }
}

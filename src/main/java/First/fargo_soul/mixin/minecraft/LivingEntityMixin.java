package First.fargo_soul.mixin.minecraft;

import First.fargo_soul.item.terraSoul.cosmicPower.WizardSoul;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(
            method = "doPush",
            at = @At("HEAD"),
            cancellable = true
    )
    private void doPush(Entity entity, CallbackInfo ci) {
        if (entity instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
            if (CurioUtils.isEquipped(attacker, WizardSoul.class)) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "travel", at = @At("HEAD"), cancellable = true)
    private void handlePlayerControl(Vec3 travelVector, CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        // 检查是否有玩家在骑乘且玩家是第一个乘客（控制者）
        if (self.isAlive() && self.getControllingPassenger() instanceof Player player) {
            // 复制玩家的旋转角度给生物，使生物朝向玩家看的地方
            self.setYRot(player.getYRot());
            self.yRotO = self.getYRot();
            self.setXRot(player.getXRot() * 0.5F);
            self.setYRot(self.getYRot());
            self.setXRot(self.getXRot());
            self.yBodyRot = self.getYRot();
            self.yHeadRot = self.yBodyRot;
            // 获取玩家的输入方向
            float strafe = player.xxa * 0.5F; // 左右移动
            float vertical = player.yya;    // 上下移动（通常不用）
            float forward = player.zza;     // 前进后退
            if (forward <= 0.0F) {
                forward *= 0.25F; // 后退减速
            }
            // 设置移动参数：速度、摩擦力因子等
            self.setSpeed((float) self.getAttributeValue(Attributes.MOVEMENT_SPEED));
            // 调用移动逻辑：传入玩家的输入方向
            // Vec3(左右, 上下, 前后)
            self.travel(new Vec3(strafe, vertical, forward));
            // 关键：取消原有的 travel 逻辑（AI 逻辑），防止冲突
            ci.cancel();
        }
    }

}

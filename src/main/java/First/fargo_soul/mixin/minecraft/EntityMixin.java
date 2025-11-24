package First.fargo_soul.mixin.minecraft;


import First.fargo_soul.Attachment.Attachment.SoulAbilityData;
import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.TerraSoul.CosmicPower.SoulStone.MeteorSoul;
import First.fargo_soul.Item.Soul.TerraSoul.DeathPower.SoulStone.PenetratingNinjaSoul;
import First.fargo_soul.Item.Soul.TerraSoul.WillPower.Soulstone.ValhallaKnightSoul;
import First.fargo_soul.Item.Soul.TerraSoul.WillPower.WillPower;
import First.fargo_soul.Utils.SoulUtils;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.List;

@Mixin(Entity.class)
public class EntityMixin {

    @WrapWithCondition(
            method = "push(Lnet/minecraft/world/entity/Entity;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;push(DDD)V",
                    ordinal = 0
            )
    )
    private boolean push(Entity instance, double x, double y, double z) {
        Entity entity = (Entity) (Object) this;
        if (entity instanceof LivingEntity attacker) {
            return !SoulUtils.isEquipped(attacker, MeteorSoul.class);
        }
        return true;
    }

    @Inject(
            method = "isColliding",
            at = @At(
                    "RETURN"
            ),
            cancellable = true
    )
    private void bypassCollision(BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (((Entity) (Object) this) instanceof Player player && SoulUtils.isEquipped(player, PenetratingNinjaSoul.class)) {
            SoulAbilityData.SoulInfo soulInfo = player.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(PenetratingNinjaSoul.class);
            if (soulInfo.getDuration() > 0) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(
            method = "getSharedFlag(I)Z",
            at = @At("RETURN"),
            cancellable = true
    )
    private void getDefaultGravity(int flag, CallbackInfoReturnable<Boolean> cir) {
        Entity entity = (Entity) (Object) this;
        if (flag == 6 && entity instanceof ItemEntity itemEntity && itemEntity.getItem().getItem() instanceof SoulItem) {
            cir.setReturnValue(true);
        }
        if (flag == 6 && entity instanceof LivingEntity livingEntity && livingEntity.getPersistentData().getBoolean("ScoutScope")) {
            cir.setReturnValue(true);
        }
    }

    @ModifyArgs(
            method = "push(Lnet/minecraft/world/entity/Entity;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;push(DDD)V",
                    ordinal = 1
            )
    )
    private void push(Args args) {
        if ((Object)this instanceof LivingEntity attacker) {
            List<Entity> passengers = attacker.getPassengers();
            LivingEntity passenger = null;
            if (!passengers.isEmpty()) {
                for (Entity passenger1 : passengers) {
                    if (passenger1 instanceof LivingEntity livingEntity && SoulUtils.isEquipped(livingEntity, ValhallaKnightSoul.class) && SoulUtils.isEquipped(livingEntity, WillPower.class)) {
                        passenger = livingEntity;
                        break;
                    }
                }
            }
            if (passenger != null) {
                args.set(0, (double) args.get(0) * 10);
                args.set(2, (double) args.get(2) * 10);
            }
        }
    }

}

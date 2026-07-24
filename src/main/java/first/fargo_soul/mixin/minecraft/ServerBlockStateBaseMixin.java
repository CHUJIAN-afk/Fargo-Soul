package first.fargo_soul.mixin.minecraft;


import first.fargo_soul.common.item.terraSoul.deathPower.PenetratingNinjaSoul;
import first.fargo_soul.utils.SoulUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public class ServerBlockStateBaseMixin {

    @Inject(
            method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;",
            at = @At("RETURN"),
            cancellable = true
    )
    private void getCollisionShape(BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (context instanceof EntityCollisionContext entityCollisionContext && entityCollisionContext.getEntity() instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.connection instanceof ServerGamePacketListenerImpl && pos.getCenter().y() >= serverPlayer.position().y()) {
                SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(serverPlayer, PenetratingNinjaSoul.class);
                if (soulInfo.getDuration() > 0) {
                    cir.setReturnValue(Shapes.empty());
                }
            }
        }
    }

}

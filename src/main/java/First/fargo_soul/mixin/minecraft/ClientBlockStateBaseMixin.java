package First.fargo_soul.mixin.minecraft;

import First.fargo_soul.common.attachment.SoulAbilityData;
import First.fargo_soul.common.item.terraSoul.deathPower.PenetratingNinjaSoul;
import First.fargo_soul.register.AttachmentRegister;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
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
public class ClientBlockStateBaseMixin {

    @Inject(
            method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;",
            at = @At("RETURN"),
            cancellable = true
    )
    private void getCollisionShape(BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (context instanceof EntityCollisionContext entityCollisionContext && entityCollisionContext.getEntity() instanceof LocalPlayer localPlayer) {
            if (localPlayer.connection instanceof ClientPacketListener && pos.getCenter().y() >= localPlayer.position().y()) {
                SoulAbilityData.SoulInfo soulInfo = localPlayer.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(PenetratingNinjaSoul.class);
                if (soulInfo.getDuration() > 0) {
                    cir.setReturnValue(Shapes.empty());
                }
            }
        }
    }

}

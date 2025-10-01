package First.fargo_soul.mixin.fargo;


import First.fargo_soul.Item.Soul.TerraSoul.LifePower.SoulStone.PumpkinSoul;
import First.fargo_soul.Utils.SoulUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(StemBlock.class)
public class StemBlockMixin {

	@Inject(
			method = "randomTick",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/server/level/ServerLevel;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z",
					shift = At.Shift.AFTER
			)
	)
	private void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
		if (state.getBlock().defaultBlockState().is(Blocks.PUMPKIN_STEM)) {
			List<LivingEntity> livingEntityList = level.getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(5), attacker -> SoulUtils.isEquipped(attacker, PumpkinSoul.class));
			if (!livingEntityList.isEmpty()) {
				IntegerProperty age = StemBlock.AGE;
				level.setBlock(pos, state.setValue(age, Math.min(state.getValue(age) + 2, 7)), 2);
			}
		}
	}

}

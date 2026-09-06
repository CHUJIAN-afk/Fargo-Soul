package first.fargo_soul.common.blcokEntity;

import first.fargo_soul.register.FargoSoulBlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CosmicCrucibleBlockEntity extends BlockEntity {

    public CosmicCrucibleBlockEntity(BlockPos pos, BlockState state) {
        super(FargoSoulBlockEntityRegister.CosmicCrucible.get(), pos, state);
    }
}

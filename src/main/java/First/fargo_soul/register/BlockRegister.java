package First.fargo_soul.register;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.common.block.CosmicCrucibleBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockRegister {

    private static final DeferredRegister<Block> Register = DeferredRegister.create(Registries.BLOCK, FargoSoul.MODID);

    public static final DeferredHolder<Block, CosmicCrucibleBlock> CosmicCrucible =
            Register.register("cosmic_crucible", () -> {
                BlockBehaviour.Properties properties = BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON)
                        .lightLevel(blockState -> 2)
                        .strength(0, 3600000)
                        .emissiveRendering((state, level, pos) -> true);
                return new CosmicCrucibleBlock(properties);
            });

    public static void register(IEventBus eventBus) {
        Register.register(eventBus);
    }

}

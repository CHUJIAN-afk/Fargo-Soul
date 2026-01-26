package First.fargo_soul.register;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.common.blcokEntity.CosmicCrucibleBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockEntityRegister {

    private static final DeferredRegister<BlockEntityType<?>> Register = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, FargoSoul.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CosmicCrucibleBlockEntity>> CosmicCrucible =
            Register.register("cosmic_crucible", () -> BlockEntityType.Builder.of(
                    CosmicCrucibleBlockEntity::new,
                    BlockRegister.CosmicCrucible.get()
            ).build(null));

    public static void register(IEventBus eventBus) {
        Register.register(eventBus);
    }

}

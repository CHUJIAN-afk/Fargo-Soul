package first.fargo_soul.common.block;

import com.mojang.serialization.MapCodec;
import first.fargo_soul.common.blcokEntity.CosmicCrucibleBlockEntity;
import first.fargo_soul.register.FargoSoulBlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CosmicCrucibleBlock extends BaseEntityBlock {

    public static final DirectionProperty Facing =  BlockStateProperties.HORIZONTAL_FACING;
    public static final MapCodec<CosmicCrucibleBlock> CODEC = simpleCodec(CosmicCrucibleBlock::new);

    public CosmicCrucibleBlock(Properties properties) {
        super(properties.noOcclusion());
        this.defaultBlockState().setValue(Facing, Direction.NORTH);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(Facing);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        BlockState blockState = super.getStateForPlacement(context);
        if (blockState != null && blockState.hasProperty(Facing)) {
            return blockState.setValue(Facing, context.getHorizontalDirection().getCounterClockWise());
        }
        return super.getStateForPlacement(context);
    }

    @Override
    protected @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @NotNull BlockState playerWillDestroy(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof CosmicCrucibleBlockEntity blockEntity) {
            ItemStack stack = new ItemStack(this);
            stack.set(DataComponents.BLOCK_ENTITY_DATA, CustomData.of(blockEntity.saveWithFullMetadata(level.registryAccess())));
            ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack);
            itemEntity.setDefaultPickUpDelay();
            level.addFreshEntity(itemEntity);
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof CosmicCrucibleBlockEntity blockEntity) {
            IItemHandler itemHandler = blockEntity.getItemHandler();
            if (!stack.isEmpty()) {
                if (!level.isClientSide()) {
                    ItemStack toInsert = stack.copy();
                    ItemStack remainder = ItemHandlerHelper.insertItemStacked(itemHandler, toInsert, false);
                    stack.setCount(remainder.getCount());
                    blockEntity.setChanged();
                    level.sendBlockUpdated(pos, state, state, 3);
                }
                return ItemInteractionResult.sidedSuccess(level.isClientSide());
            } else {
                for (int i = itemHandler.getSlots() - 1; i >= 0; i--) {
                    ItemStack stackInSlot = itemHandler.getStackInSlot(i);
                    if (!stackInSlot.isEmpty()) {
                        if (!level.isClientSide()) {
                            int extractAmount = Math.min(stackInSlot.getCount(), 64);
                            ItemStack extracted = itemHandler.extractItem(i, extractAmount, false);
                            ItemHandlerHelper.giveItemToPlayer(player, extracted);
                            blockEntity.setChanged();
                            level.sendBlockUpdated(pos, state, state, 3);
                        }
                        return ItemInteractionResult.sidedSuccess(level.isClientSide());
                    }
                }
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, FargoSoulBlockEntityRegister.CosmicCrucible.get(), (lvl, blockPos, blockState, cosmicCrucibleBlock) -> cosmicCrucibleBlock.tick());
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new CosmicCrucibleBlockEntity(blockPos, blockState);
    }
}

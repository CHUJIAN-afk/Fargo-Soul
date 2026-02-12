package First.fargo_soul.common.blcokEntity;

import First.fargo_soul.common.recipe.SoulRecipe;
import First.fargo_soul.common.recipe.SoulRecipeInput;
import First.fargo_soul.config.ServerSoulConfig;
import First.fargo_soul.register.BlockEntityRegister;
import First.fargo_soul.register.RecipeTypeRegister;
import First.fargo_soul.utils.ParticleUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class CosmicCrucibleBlockEntity extends BlockEntity {

    private final CosmicCrucibleItemHandler inventory;

    public CosmicCrucibleBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegister.CosmicCrucible.get(), pos, state);
        this.inventory = new CosmicCrucibleItemHandler(this);
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                BlockEntityRegister.CosmicCrucible.get(),
                (be, context) -> be.getItemHandler()
        );
    }

    public CosmicCrucibleItemHandler getItemHandler() {
        return inventory;
    }

    public void tick() {
        animate();
        collectItems();
        recipeFor();
        updateData();
    }

    private void animate() {
        Random random = SoulUtils.random;
        if (level != null && level.isClientSide() && random.nextFloat() < 0.2) {
            BlockPos pos = getBlockPos();
            Vec3 targetCenter = pos.above().getCenter();
            double targetX = targetCenter.x();
            double targetY = targetCenter.y();
            double targetZ = targetCenter.z();
            for (int i = 0; i < random.nextInt(1, 5); i++) {
                Vec3 center = pos.getCenter();
                double startX = center.x() + random.nextDouble(-0.5, 0.5);
                double startY = center.y() + random.nextDouble() * 2;
                double startZ = center.z() + random.nextDouble(-0.5, 0.5);
                double vx = (startX - targetX) * random.nextDouble(0.5, 10);
                double vy = (startY - targetY) * random.nextDouble(0.5, 10);
                double vz = (startZ - targetZ) * random.nextDouble(0.5, 10);
                level.addParticle(ParticleTypes.PORTAL, startX, startY, startZ, vx, vy, vz);
            }
        }
    }

    private void collectItems() {
        if (level == null) return;
        Vec3 center = getBlockPos().above().getCenter().add(0, -0.25, 0);
        List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, new AABB(getBlockPos()).inflate(10));
        for (ItemEntity entity : items) {
            Vec3 delta = center.subtract(entity.position());
            double distSqr = delta.lengthSqr();
            if (distSqr < 0.05) {
                ItemStack remainder = ItemHandlerHelper.insertItemStacked(this.inventory, entity.getItem(), false);
                if (remainder.isEmpty()) {
                    entity.discard();
                } else {
                    entity.setItem(remainder);
                }
            } else {
                double strength = 0.01 + (0.05 / Math.max(0.1, distSqr));
                entity.addDeltaMovement(delta.normalize().scale(Math.min(strength, 0.1)));
                if (entity.getPersistentData().getLong("time") != level.getGameTime()) {
                    entity.getPersistentData().putLong("time", level.getGameTime());
                    entity.addDeltaMovement(new Vec3(0, 0.04, 0));
                }
                if (entity.getDeltaMovement().length() > 0.3) {
                    entity.setDeltaMovement(entity.getDeltaMovement().normalize().scale(0.3));
                }
                entity.hasImpulse = true;
                entity.hurtMarked = true;
            }
        }
    }

    private void recipeFor() {
        CosmicCrucibleItemHandler itemHandler = this.getItemHandler();
        if (level != null && !level.isClientSide() && itemHandler.isChange()) {
            SoulRecipeInput soulRecipeInput = new SoulRecipeInput(itemHandler.getStackList());
            Optional<RecipeHolder<SoulRecipe>> recipeFor = level.getRecipeManager().getRecipeFor(RecipeTypeRegister.Integration.get(), soulRecipeInput, level);
            if (recipeFor.isPresent()) {
                SoulRecipe soulRecipe = recipeFor.get().value();
                ItemStack targetStack = soulRecipe.output().copy();
                recipeConsume(soulRecipe);
                BlockPos blockPos = getBlockPos();
                ItemStack remainder = ItemHandlerHelper.insertItemStacked(this.inventory, targetStack, false);
                if (!remainder.isEmpty()) {
                    Containers.dropItemStack(level, blockPos.getX(), blockPos.getY() + 2, blockPos.getZ(), remainder);
                }
                LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                bolt.setVisualOnly(true);
                bolt.moveTo(blockPos.getCenter());
                SoulUtils.addEntity(level, bolt);
                ParticleUtils.spawnParticleSphere(
                        (ServerLevel) level,
                        blockPos.above().getCenter(),
                        ParticleTypes.EXPLOSION,
                        0.1f,
                        20,
                        0.5f
                );
            }
        }
    }

    private void recipeConsume(SoulRecipe recipe) {
        CosmicCrucibleItemHandler itemHandler = this.getItemHandler();
        for (ItemStack target : recipe.inputs()) {
            int remainingToConsume = target.getCount();
            for (int i = 0; i < itemHandler.getSlots(); i++) {
                ItemStack slotStack = itemHandler.getStackInSlot(i);
                if (ItemStack.isSameItem(slotStack, target)) {
                    while (remainingToConsume > 0) {
                        ItemStack extracted = itemHandler.extractItem(i, remainingToConsume, false);
                        remainingToConsume -= extracted.getCount();
                        if (extracted.isEmpty()) {
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }

    private void updateData() {
        if (this.inventory.isChange() && level != null && !level.isClientSide()) {
            this.inventory.setChange(false);
            BlockState blockState = getBlockState();
            BlockPos blockPos = getBlockPos();
            level.sendBlockUpdated(blockPos, blockState, blockState, 3);
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        return this.saveCustomOnly(registries);
    }

    @Override
    public void onDataPacket(@NotNull Connection net, @NotNull ClientboundBlockEntityDataPacket pkt, HolderLookup.@NotNull Provider registries) {
        super.onDataPacket(net, pkt, registries);
        this.loadAdditional(pkt.getTag(), registries);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("Inventory", inventory.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("Inventory")) {
            inventory.deserializeNBT(registries, tag.getCompound("Inventory"));
        }
    }

    public static class CosmicCrucibleItemHandler extends ItemStackHandler {

        private final CosmicCrucibleBlockEntity self;
        private boolean change;

        public CosmicCrucibleItemHandler(CosmicCrucibleBlockEntity self) {
            super(ServerSoulConfig.CosmicCrucibleSize.get());
            this.self = self;
            this.change = false;
        }

        public List<ItemStack> getStackList() {
            List<ItemStack> list = new ArrayList<>();
            for (int i = 0; i < getSlots(); i++) {
                list.add(getStackInSlot(i));
            }
            return list;
        }

        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return Integer.MAX_VALUE;
        }

        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            self.setChanged();
            Level level = self.getLevel();
            if (level != null && !level.isClientSide) {
                setChange(true);
            }
        }

        public boolean isChange() {
            return change;
        }

        public void setChange(boolean change) {
            this.change = change;
        }

    }

}

package first.fargo_soul.common.soulInfo;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;

public abstract class SoulInfo implements INBTSerializable<CompoundTag> {

    private boolean remove = false;

    public abstract SoulInfoType<? extends SoulInfo> getType();

    public void tick(LivingEntity living) {

    }

    public void onRemove(LivingEntity living) {

    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public boolean isRemove() {
        return remove;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        return new CompoundTag();
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag tag) {
    }
}

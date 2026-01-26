package First.fargo_soul.common.attachment;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@EventBusSubscriber(modid = FargoSoul.MODID)
public class SoulContainerData implements INBTSerializable<CompoundTag>, AttachmentSyncHandler<SoulContainerData> {

    private final SoulContainer soulContainer;
    private boolean isChange;

    public SoulContainerData() {
        this.soulContainer = new SoulContainer();
        this.isChange = true;
        soulContainer.addListener(container -> isChange = true);
    }

    public boolean isChange() {
        return isChange;
    }

    public void setChange(boolean change) {
        isChange = change;
    }

    @SubscribeEvent
    public static void tick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide()) {
            SoulContainerData data = player.getData(AttachmentRegister.SoulContainerData);
            if (data.isChange) {
                player.syncData(AttachmentRegister.SoulContainerData);
                CurioUtils.updateSoulList(player);
                data.isChange = false;
            }
        }
    }

    public SoulContainer getSoulContainer() {
        return soulContainer;
    }

    @Override
    public void write(@NotNull RegistryFriendlyByteBuf buf, @NotNull SoulContainerData data, boolean b) {
        CompoundTag tag = data.serializeNBT(buf.registryAccess());
        buf.writeNbt(tag);
    }

    @Override
    public @Nullable SoulContainerData read(@NotNull IAttachmentHolder holder, @NotNull RegistryFriendlyByteBuf buf, @Nullable SoulContainerData data) {
        if (data == null) {
            data = new SoulContainerData();
        }
        CompoundTag tag = buf.readNbt();
        if (tag != null) {
            data.deserializeNBT(buf.registryAccess(), tag);
        }
        data.isChange = true;
        return data;
    }

    @Override
    public @NotNull CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.put("Items", soulContainer.createTag(provider));
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag tag) {
        ListTag items = tag.getList("Items", Tag.TAG_COMPOUND);
        soulContainer.fromTag(items, provider);
        isChange = true;
    }

    public static class SoulContainer extends SimpleContainer {

        public SoulContainer() {
            super(7);
        }

        @Override
        public void fromTag(ListTag tag, HolderLookup.@NotNull Provider levelRegistry) {
            this.clearContent();
            for (int i = 0; i < tag.size(); ++i) {
                CompoundTag slotTag = tag.getCompound(i);
                int slot = slotTag.getInt("Slot");
                ItemStack.parse(levelRegistry, slotTag.getCompound("Item")).ifPresent(stack -> this.setItem(slot, stack));
            }
        }

        @Override
        public @NotNull ListTag createTag(HolderLookup.@NotNull Provider levelRegistry) {
            ListTag listtag = new ListTag();
            for (int i = 0; i < this.getContainerSize(); ++i) {
                ItemStack itemstack = this.getItem(i);
                if (!itemstack.isEmpty()) {
                    CompoundTag slotTag = new CompoundTag();
                    slotTag.putInt("Slot", i);
                    slotTag.put("Item", itemstack.save(levelRegistry));
                    listtag.add(slotTag);
                }
            }
            return listtag;
        }

    }

}
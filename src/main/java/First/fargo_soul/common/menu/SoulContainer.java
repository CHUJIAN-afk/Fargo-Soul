package First.fargo_soul.common.menu;

import First.fargo_soul.client.slot.SoulSlot;
import First.fargo_soul.common.attachment.SoulContainerData;
import First.fargo_soul.mixin.minecraft.InventoryMenuAccessor;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.register.MenuRegister;
import com.mojang.datafixers.util.Pair;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;

public class SoulContainer extends AbstractContainerMenu {

    private final Inventory inventory;
    private final SoulContainerData.SoulContainer soulContainer;

    public SoulContainer(int containerId, Inventory inventory, RegistryFriendlyByteBuf buf) {
        this(containerId, inventory, (Player) null);
    }

    public SoulContainer(int containerId, Inventory inventory, Player player) {
        super(MenuRegister.SoulContainer.get(), containerId);
        this.inventory = inventory;
        this.soulContainer = inventory.player.getData(AttachmentRegister.SoulContainerData).getSoulContainer();
        init();
    }

    public void init() {
        Player player = inventory.player;
        int offsetX = 0;
        int offsetY = -8;
        int[][] soulSlotPositions = {
                {115, 26},
                {133, 26},
                {106, 42},
                {124, 42},
                {142, 42},
                {115, 58},
                {133, 58}
        };
        for (int i = 0; i < soulSlotPositions.length; i++) {
            int x = soulSlotPositions[i][0] + offsetX;
            int y = soulSlotPositions[i][1] + offsetY;
            addSlot(new SoulSlot(soulContainer, i, x, y, player));
        }
        for (int k = 0; k < InventoryMenuAccessor.getSlotIds().length; k++) {
            EquipmentSlot equipmentslot = InventoryMenuAccessor.getSlotIds()[k];
            ResourceLocation resourcelocation = InventoryMenuAccessor.TEXTURE_EMPTY_SLOTS().get(equipmentslot);
            this.addSlot(createArmorSlot(inventory, player, equipmentslot, 39 - k, 8, 8 + k * 18, resourcelocation));
        }
        //添加玩家库存槽位
        int startY = 84;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                int slotIndex = 9 + row * 9 + col;
                addSlot(new Slot(inventory, slotIndex, 8 + col * 18, startY + row * 18));
            }
        }
        //添加玩家热键槽位
        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(inventory, col, 8 + col * 18, startY + 58));
        }
        this.addSlot(new Slot(inventory, 40, 77, 62) {
            public void setByPlayer(@NotNull ItemStack newItemStack, @NotNull ItemStack oldItemStack) {
                player.onEquipItem(EquipmentSlot.OFFHAND, newItemStack, oldItemStack);
                super.setByPlayer(newItemStack, oldItemStack);
            }

            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
            }
        });
    }

    public Slot createArmorSlot(Container container, LivingEntity owner, EquipmentSlot slot, int slotIndex, int x, int y, @Nullable ResourceLocation emptyIcon) {
        try {
            Class<?> armorSlotClass = Class.forName("net.minecraft.world.inventory.ArmorSlot");
            Constructor<?> constructor = armorSlotClass.getDeclaredConstructor(Container.class, LivingEntity.class, EquipmentSlot.class, int.class, int.class, int.class, ResourceLocation.class);
            constructor.setAccessible(true);
            return (Slot) constructor.newInstance(container, owner, slot, slotIndex, x, y, emptyIcon);
        } catch (Exception e) {
            return new Slot(container, slotIndex, x, y);
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemStack = slot.getItem();
            itemstack = itemStack.copy();
            if (index < 7) {
                if (!this.moveItemStackTo(itemStack, 7, 43, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!this.moveItemStackTo(itemStack, 0, 7, false)) {
                    if (index < 34) {
                        if (!this.moveItemStackTo(itemStack, 34, 43, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else {
                        if (!this.moveItemStackTo(itemStack, 7, 34, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                }
            }
            if (itemStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (itemStack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, itemStack);
        }
        return itemstack;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }

}

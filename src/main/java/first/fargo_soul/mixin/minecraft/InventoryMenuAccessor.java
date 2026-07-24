package first.fargo_soul.mixin.minecraft;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.InventoryMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(InventoryMenu.class)
public interface InventoryMenuAccessor {

    @Accessor("SLOT_IDS")
    static EquipmentSlot[] getSlotIds() {
        throw new AssertionError();
    }

    @Accessor("TEXTURE_EMPTY_SLOTS")
    static Map<EquipmentSlot, ResourceLocation> TEXTURE_EMPTY_SLOTS() {
        throw new AssertionError();
    }

}

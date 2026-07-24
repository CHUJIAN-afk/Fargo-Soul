package first.fargo_soul.common.attachment;

import first.fargo_soul.api.item.ISoulItem;
import first.fargo_soul.register.AttachmentRegister;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ISoulItemCache {

    private final List<ISoulItem> list = new ArrayList<>();

    public static List<ISoulItem> getList(LivingEntity living) {
        ISoulItemCache data = living.getData(AttachmentRegister.I_SOUL_ITEM_CACHE);
        return Collections.unmodifiableList(data.list);
    }

    public static void update(LivingEntity living) {
        ISoulItemCache data = living.getData(AttachmentRegister.I_SOUL_ITEM_CACHE);
        data.list.clear();
        ICuriosItemHandler handler = CuriosApi.getCuriosInventory(living)
                .orElse(null);
        if (handler != null) {
            IItemHandlerModifiable curios = handler.getEquippedCurios();
            int slots = curios.getSlots();
            for (int i = 0; i < slots; i++) {
                ItemStack itemStack = curios.getStackInSlot(i);
                if (itemStack.getItem() instanceof ISoulItem iSoulItem) {
                    data.list.add(iSoulItem);
                }
            }
        }
    }
}

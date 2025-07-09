package First.fargo_soul.Utils;

import First.fargo_soul.Curios.CurioItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.*;

public class CurioUtils {

    public static boolean findCurio(LivingEntity livingEntity, ItemStack itemStack) {
        return CuriosApi.getCuriosInventory(livingEntity).map(iCuriosItemHandler -> iCuriosItemHandler.isEquipped(itemStack.getItem())).orElse(false);
    }

    public static boolean isEquipped(LivingEntity livingEntity, Item item) {
        List<CurioItem> OringinCurioList = new ArrayList<>();
        Optional<ICuriosItemHandler> curiosItemHandler = CuriosApi.getCuriosInventory(livingEntity);
        if (curiosItemHandler.isPresent()) {
            IItemHandlerModifiable iItemHandlerModifiable = curiosItemHandler.get().getEquippedCurios();
            int size = iItemHandlerModifiable.getSlots();
            for (int i = 0; i < size; i++) {
                if (iItemHandlerModifiable.getStackInSlot(i).getItem() instanceof CurioItem curioItem) {
                    OringinCurioList.add(curioItem);
                }
            }
        }
        List<CurioItem> CurioList = getAllCurioItems(OringinCurioList).stream().distinct().toList();
        for (CurioItem curioItem : CurioList) {
            if (curioItem.equals(item)) {
                return true;
            }
        }
        return false;
    }

    public static List<CurioItem> getAllCurioItems(List<CurioItem> originList) {
        List<CurioItem> result = new ArrayList<>();
        for (CurioItem item : originList) {
            result.add(item);
            List<CurioItem> curioItems = item.getCurioItemList();
            if (!curioItems.isEmpty()) {
                result.addAll(getAllCurioItems(item.getCurioItemList()));
            }
        }
        return result;
    }

}

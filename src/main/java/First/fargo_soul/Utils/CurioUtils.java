package First.fargo_soul.Utils;

import First.fargo_soul.Curios.SoulItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.*;

public class CurioUtils {

    public static boolean findCurio(LivingEntity livingEntity, ItemStack itemStack) {
        return CuriosApi.getCuriosInventory(livingEntity).map(iCuriosItemHandler -> iCuriosItemHandler.isEquipped(itemStack.getItem())).orElse(false);
    }

    public static boolean isEquipped(LivingEntity livingEntity, Item item) {
        List<SoulItem> OringinCurioList = getSoulInventory(livingEntity);
        List<SoulItem> CurioList = getAllCurioItems(OringinCurioList).stream().distinct().toList();
        for (SoulItem soulItem : CurioList) {
            if (soulItem.equals(item)) {
                return true;
            }
        }
        return false;
    }

    private static @NotNull List<SoulItem> getSoulInventory(LivingEntity livingEntity) {
        List<SoulItem> OringinCurioList = new ArrayList<>();
        Optional<ICuriosItemHandler> curiosItemHandler = CuriosApi.getCuriosInventory(livingEntity);
        if (curiosItemHandler.isPresent()) {
            IItemHandlerModifiable iItemHandlerModifiable = curiosItemHandler.get().getEquippedCurios();
            int size = iItemHandlerModifiable.getSlots();
            for (int i = 0; i < size; i++) {
                if (iItemHandlerModifiable.getStackInSlot(i).getItem() instanceof SoulItem soulItem) {
                    OringinCurioList.add(soulItem);
                }
            }
        }
        return OringinCurioList;
    }

    public static List<SoulItem> getAllCurioItems(List<SoulItem> originList) {
        List<SoulItem> result = new ArrayList<>();
        for (SoulItem item : originList) {
            result.add(item);
            List<SoulItem> soulItems = item.getCurioItemList();
            if (!soulItems.isEmpty()) {
                result.addAll(getAllCurioItems(item.getCurioItemList()));
            }
        }
        return result;
    }

}

package First.fargo_soul.Utils;

import First.fargo_soul.Attachment.Attachment.Data;
import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SoulUtils {

	public static <T extends SoulItem> SoulItem getSoulItemFromSoulData(LivingEntity livingEntity, Class<T> type) {
		AttachmentType<Data> dataAttachmentType = AttachmentRegister.Data.get();
		Data data = livingEntity.getData(dataAttachmentType);
		List<SoulItem> soulItemList = getAllCurioItems(getSoulInventory(livingEntity));
		Set<Class<? extends SoulItem>> set = soulItemList.stream().map(SoulItem::getClass).collect(Collectors.toSet());
		data.getSoulItemList().removeIf(soulItem -> !set.contains(soulItem.getClass()));
		soulItemList.forEach(data::addSoulItem);
		return data.getSoulItem(type);
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

	private static List<SoulItem> getAllCurioItems(List<SoulItem> originList) {
		List<SoulItem> result = new ArrayList<>();
		for (SoulItem item : originList) {
			result.add(item);
			List<SoulItem> soulItems = item.getSoulItemList();
			if (!soulItems.isEmpty()) {
				result.addAll(getAllCurioItems(soulItems));
			}
		}
		return result;
	}
}

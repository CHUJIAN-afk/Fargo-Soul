package First.fargo_soul.Utils;

import First.fargo_soul.Attachment.Attachment.SoulData;
import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.*;

public class CurioUtils {

    public static boolean isEquipped(LivingEntity livingEntity, Item item) {
        if (item instanceof SoulItem soulItem) {
            if (livingEntity.level().isClientSide()) {
                List<SoulItem> OringinCurioList = CurioUtils.getSoulInventory(livingEntity);
                List<SoulItem> CurioList = CurioUtils.getAllCurioItems(OringinCurioList).stream().distinct().toList();
                return CurioList.contains(item);
            }
            SoulData soulData = livingEntity.getData(AttachmentRegister.SoulData);
            List<SoulItem> soulItemList = soulData.getSoulItemList();
            if (soulItemList == null) {
                updateSoulList(livingEntity);
                soulItemList = soulData.getSoulItemList();
            }
            return soulItemList.contains(soulItem);
        }
        return false;
    }

    public static @NotNull List<SoulItem> getSoulInventory(LivingEntity livingEntity) {
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
            List<SoulItem> soulItems = item.getSoulItemList();
            if (!soulItems.isEmpty()) {
                result.addAll(getAllCurioItems(soulItems));
            }
        }
        return result;
    }

    public static void updateSoulList(LivingEntity livingEntity) {
        List<SoulItem> OringinCurioList = CurioUtils.getSoulInventory(livingEntity);
        List<SoulItem> CurioList = CurioUtils.getAllCurioItems(OringinCurioList).stream().distinct().toList();
        livingEntity.getData(AttachmentRegister.SoulData).setSoulItemList(CurioList);
    }

    public static List<Component> getAttributeList(SoulItem soulItem) {
        return getComponent(soulItem, "attribute");
    }

    public static List<Component> getTooltipList(SoulItem soulItem) {
        return getComponent(soulItem, "tooltip");
    }

    private static List<Component> getComponent(SoulItem soulItem, String string) {
        List<Component> componentList = new ArrayList<>();
        Map<String, String> languageData = Language.getInstance().getLanguageData();
        List<String> keyList = new ArrayList<>();


        languageData.keySet().forEach(key -> {
            if (key.contains(Fargo_soul.MODID + "." + BuiltInRegistries.ITEM.getKey(soulItem).getPath() + "." + string)) {
                keyList.add(key);
            }
        });
        keyList.sort(Comparator.comparingInt(CurioUtils::extractLastNumber));
        for (String key : keyList) {
            componentList.add(Component.translatable(key).withStyle(string.equals("attribute") ? ChatFormatting.BLUE : ChatFormatting.DARK_GRAY));
        }
        return componentList;
    }

    private static int extractLastNumber(String s) {
        int number = 0;
        int power = 1;
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                number += (c - '0') * power;
                power *= 10;
            } else {
                break;
            }
        }
        return number;
    }
}

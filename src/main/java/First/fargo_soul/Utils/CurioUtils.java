package First.fargo_soul.Utils;

import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.*;

public class CurioUtils {

    public static boolean isEquipped(LivingEntity livingEntity, Item item) {
        if (item instanceof SoulItem) {
            List<SoulItem> OringinCurioList = CurioUtils.getSoulInventory(livingEntity);
            List<SoulItem> CurioList = CurioUtils.getAllCurioItems(OringinCurioList).stream().distinct().toList();
            return CurioList.contains(item);
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

    private static final Map<String, List<Component>> ComponentMap = new HashMap<>();
    private static final Map<String, ModRarity> RarityMap = new HashMap<>();

    public static List<Component> getComponent(SoulItem soulItem, String string) {
        String key = soulItem.getDescriptionId() + string;
        if (!ComponentMap.containsKey(key)) {
            List<Component> componentList = new ArrayList<>();
            Map<String, String> languageData = Language.getInstance().getLanguageData();
            List<String> keyList = new ArrayList<>();
            languageData.keySet().forEach(key1 -> {
                if (key1.contains(Fargo_soul.MODID + "." + BuiltInRegistries.ITEM.getKey(soulItem).getPath() + "." + string)) {
                    keyList.add(key1);
                }
            });
            keyList.sort(Comparator.comparingInt(CurioUtils::extractLastNumber));
            for (String key1 : keyList) {
                MutableComponent attribute = Component.translatable(key1);
                if (!string.equals("tooltip") && soulItem.components().get(ConfluenceMagicLib.MOD_RARITY.get()) instanceof ModRarity modRarity) {
                    RarityMap.put(key, modRarity);
                }
                componentList.add(attribute);
            }
            ComponentMap.put(key, componentList);
        }
        List<Component> componentList = new ArrayList<>();
        for (Component component : ComponentMap.get(key)) {
            MutableComponent copy = component.copy();
            if (string.equals("tooltip")) {
                copy.withStyle(ChatFormatting.DARK_GRAY);
            } else {
                copy.withColor(RarityMap.get(key).color());
            }
            componentList.add(copy);
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

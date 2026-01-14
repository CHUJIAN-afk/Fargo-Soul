package First.fargo_soul.utils;

import First.fargo_soul.attachment.SoulAbilityEnabledData;
import First.fargo_soul.attachment.SoulListData;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.register.AttachmentRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.*;
import java.util.stream.Collectors;

public class CurioUtils {

    @SafeVarargs
    public static boolean isEquipped(LivingEntity livingEntity, Class<? extends SoulItem>... types) {
        SoulListData soulListData = livingEntity.getData(AttachmentRegister.SoulListData);
        SoulAbilityEnabledData enabledData = livingEntity.getData(AttachmentRegister.AbilityEnabledData);
        for (SoulItem item : soulListData.getSoulItemList()) {
            if (List.of(types).contains(item.getClass()) && enabledData.isEnabled(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取实体身上所有的魂石，包括未启用的
     *
     * @return 实体身上所有的魂石，包括未启用的
     */
    public static List<SoulItem> getEntitySoulItem(LivingEntity livingEntity) {
        return getSoulFromList(getSoulFromSlots(livingEntity));
    }

    public static void updateSoulList(LivingEntity livingEntity) {
        SoulListData soulListData = livingEntity.getData(AttachmentRegister.SoulListData);
        List<SoulItem> list = getSoulFromList(getSoulFromSlots(livingEntity));
        soulListData.setSoulItemList(list);
        if (!livingEntity.level().isClientSide()) {
            livingEntity.syncData(AttachmentRegister.SoulListData);
        }
    }

    public static List<SoulItem> getSoulFromSoul(SoulItem soulItem) {
        List<SoulItem> soulFromList = getSoulFromList(soulItem.getSoulItemList());
        soulFromList.addFirst(soulItem);
        return soulFromList;
    }

    public static List<SoulItem> getSoulFromList(List<SoulItem> originList) {
        List<SoulItem> result = new ArrayList<>();
        for (SoulItem item : originList) {
            if (!result.contains(item)) {
                result.add(item);
            }
            List<SoulItem> soulItems = item.getSoulItemList();
            if (!soulItems.isEmpty()) {
                result.addAll(getSoulFromList(soulItems));
            }
        }
        return result;
    }

    public static List<SoulItem> getSoulFromSlots(LivingEntity livingEntity) {
        List<SoulItem> OringinCurioList = new ArrayList<>();
        Optional<ICuriosItemHandler> optional = CuriosApi.getCuriosInventory(livingEntity);
        optional.ifPresent(iCuriosItemHandler -> {
            IItemHandlerModifiable curios = iCuriosItemHandler.getEquippedCurios();
            for (int i = 0; i < curios.getSlots(); i++) {
                ItemStack stack = curios.getStackInSlot(i);
                if (stack.getItem() instanceof SoulItem soulItem) {
                    OringinCurioList.add(soulItem);
                }
            }
        });
        return OringinCurioList;
    }

    public static int extractLastNumber(String s) {
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

    private static final Map<SoulItem, List<Component>> attributeList = new HashMap<>();
    private static final Map<SoulItem, List<Component>> toolTipList = new HashMap<>();

    public static List<Component> getSoulItemAttributesComponent(SoulItem soulItem) {
        List<Component> components = attributeList.computeIfAbsent(soulItem, k -> {
            Map<String, String> languageData = Language.getInstance().getLanguageData();
            ModRarity modRarity = soulItem.components().getOrDefault(ConfluenceMagicLib.MOD_RARITY.get(), ModRarity.GRAY);
            List<MutableComponent> mutableComponents = languageData.keySet().stream()
                    .filter(key -> key.contains("." + BuiltInRegistries.ITEM.getKey(soulItem).getPath() + "." + "attribute"))
                    .sorted(Comparator.comparingInt(CurioUtils::extractLastNumber))
                    .map(Component::translatable)
                    .map(component -> component.withColor(modRarity.color()))
                    .toList();
            return new ArrayList<>(mutableComponents);
        });
        ModRarity rarity = soulItem.getModRarity();
        if (rarity == ModRarity.MASTER || rarity == ModRarity.EXPERT || rarity == ModRarity.QUEST) {
            return components.stream()
                    .map(Component::copy)
                    .map(component -> component.withColor(rarity.color()))
                    .collect(Collectors.toList());
        }
        return components;
    }

    public static List<Component> getSoulItemTooltipComponent(SoulItem soulItem) {
        return toolTipList.computeIfAbsent(soulItem, k -> {
            Map<String, String> languageData = Language.getInstance().getLanguageData();
            List<MutableComponent> mutableComponents = languageData.keySet().stream()
                    .filter(key -> key.contains("." + BuiltInRegistries.ITEM.getKey(soulItem).getPath() + "." + "tooltip"))
                    .sorted(Comparator.comparingInt(CurioUtils::extractLastNumber))
                    .map(Component::translatable)
                    .map(component -> component.withStyle(ChatFormatting.DARK_GRAY))
                    .toList();
            return new ArrayList<>(mutableComponents);
        });
    }
    
}
package First.fargo_soul.utils;

import First.fargo_soul.common.attachment.SoulAbilityEnabledData;
import First.fargo_soul.common.attachment.SoulListData;
import First.fargo_soul.common.dataComponents.SoulRarity;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.register.AttachmentRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

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
        for (SoulItem soulItem : SoulUtils.AttributeSoulList) {
            Map<Holder<Attribute>, AttributeModifier> modifiers = soulItem.getAttributeModifiers();
            for (Map.Entry<Holder<Attribute>, AttributeModifier> entry : modifiers.entrySet()) {
                AttributeUtils.condition(
                        livingEntity,
                        entry.getKey(),
                        entry.getValue().id(),
                        entry.getValue().amount(),
                        entry.getValue().operation(),
                        CurioUtils.isEquipped(livingEntity, soulItem.getClass())
                );
            }
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
        NonNullList<ItemStack> items = livingEntity.getData(AttachmentRegister.SoulContainerData).getSoulContainer().getItems();
        List<SoulItem> result = new ArrayList<>();
        for (ItemStack itemStack : items) {
            if (itemStack.getItem() instanceof SoulItem soulItem) {
                result.add(soulItem);
            }
        }
        return result;
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
            SoulRarity modRarity = soulItem.getSoulRarity();
            List<MutableComponent> mutableComponents = languageData.keySet().stream()
                    .filter(key -> key.contains("." + BuiltInRegistries.ITEM.getKey(soulItem).getPath() + "." + "attribute"))
                    .sorted(Comparator.comparingInt(CurioUtils::extractLastNumber))
                    .map(Component::translatable)
                    .map(component -> component.withColor(modRarity.getColor()))
                    .toList();
            return new ArrayList<>(mutableComponents);
        });
        SoulRarity rarity = soulItem.getSoulRarity();
        if (rarity == SoulRarity.MASTER || rarity == SoulRarity.EXPERT || rarity == SoulRarity.QUEST) {
            return components.stream()
                    .map(Component::copy)
                    .map(component -> component.withColor(rarity.getColor()))
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
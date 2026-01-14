package First.fargo_soul.item.base;

import First.fargo_soul.utils.CurioUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.Collections;
import java.util.List;

public abstract class SoulItem extends Item implements ICurioItem, ISoulItem {

    public SoulItem(Properties properties) {
        super(properties.stacksTo(1).durability(0).rarity(Rarity.EPIC));
    }

    public ModRarity getModRarity() {
        return this.components().getOrDefault(ConfluenceMagicLib.MOD_RARITY.get(), ModRarity.COMMON);
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        Component component = super.getName(stack);
        ModRarity modRarity = stack.get(ConfluenceMagicLib.MOD_RARITY);
        modRarity = modRarity != null ? modRarity : ModRarity.COMMON;
        return component.copy().withColor(modRarity.color());
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack itemStack) {
        LivingEntity livingEntity = slotContext.entity();
        SoulItem soulItem = (SoulItem) itemStack.getItem();
        List<SoulItem> soulFromList = CurioUtils.getSoulFromList(CurioUtils.getSoulFromSlots(livingEntity));
        List<SoulItem> targetList = CurioUtils.getSoulFromSoul(soulItem);
        return Collections.disjoint(soulFromList, targetList);
    }

    @Override
    public boolean canUnequip(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack itemStack) {
        return canEquip(slotContext, itemStack) && !slotContext.entity().getMainHandItem().is(Items.DEBUG_STICK);
    }

}
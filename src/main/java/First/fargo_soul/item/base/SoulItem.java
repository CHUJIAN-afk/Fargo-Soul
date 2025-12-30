package First.fargo_soul.item.base;

import First.fargo_soul.utils.CurioUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class SoulItem extends Item implements ICurioItem {

    public SoulItem(Properties properties) {
        super(properties.stacksTo(1).durability(0).rarity(Rarity.EPIC));
    }

    public List<SoulItem> getSoulItemList() {
        return List.of();
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        Component component = super.getName(stack);
        if (stack.get(ConfluenceMagicLib.MOD_RARITY) instanceof ModRarity modRarity) {
            return component.copy().withColor(modRarity.color());
        }
        return component;
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack itemStack) {
        LivingEntity livingEntity = slotContext.entity();
        SoulItem soulItem = (SoulItem) itemStack.getItem();
        List<SoulItem> soulFromList = CurioUtils.getSoulFromSlots(livingEntity);
        return !soulFromList.contains(soulItem);
    }

    @Override
    public boolean canUnequip(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

}









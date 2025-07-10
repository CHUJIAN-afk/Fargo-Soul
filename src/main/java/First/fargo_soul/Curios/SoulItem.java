package First.fargo_soul.Curios;

import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.ArrayList;
import java.util.List;

public class SoulItem extends Item implements ICurioItem {
    public SoulItem(Properties properties) {
        super(new Properties().stacksTo(1).durability(0));
    }

    public static final List<SoulItem> SOUL_ITEM_LIST = new ArrayList<>();
    public List<SoulItem> getCurioItemList() {
        return SOUL_ITEM_LIST;
    }

    public List<Component> AttributeList = new ArrayList<>();

    public List<Component> getAttributeList() {
        return this.AttributeList;
    }

    public List<Component> TooltipList = new ArrayList<>();

    public List<Component> getTooltipList() {
        return this.TooltipList;
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack itemStack) {
        LivingEntity livingEntity = slotContext.entity();
        return !CurioUtils.isEquipped(livingEntity, itemStack.getItem());
    }

    @Override
    public boolean canUnequip(SlotContext slotContext, ItemStack stack) {
        return true;
    }







}

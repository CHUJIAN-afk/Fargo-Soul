package First.fargo_soul.Item.Soul.BaseSoul;

import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.ArrayList;
import java.util.List;

public class SoulItem extends Item implements ICurioItem {
    public SoulItem(Properties properties) {
        super(properties.stacksTo(1).durability(0));
    }

    public static final List<SoulItem> SOUL_ITEM_LIST = new ArrayList<>();

    public List<SoulItem> getCurioItemList() {
        return SOUL_ITEM_LIST;
    }

    public final List<Component> AttributeList = new ArrayList<>();

    public List<Component> getAttributeList() {
        return this.AttributeList;
    }

    public final List<Component> TooltipList = new ArrayList<>();

    public List<Component> getTooltipList() {
        return this.TooltipList;
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack itemStack) {
        LivingEntity livingEntity = slotContext.entity();
        if (itemStack.getItem() instanceof SoulItem soulItem) {
            List<SoulItem> soulItemList = CurioUtils.getAllCurioItems(soulItem.getCurioItemList());
            for (SoulItem soulItem1 : soulItemList) {
                if (CurioUtils.isEquipped(livingEntity, soulItem1)) {
                    return false;
                }
            }
        }
        return !CurioUtils.isEquipped(livingEntity, itemStack.getItem());
    }

    @Override
    public boolean canUnequip(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static void invulnerableTimeHandler(LivingIncomingDamageEvent event) {
        if (!(event.getEntity() instanceof Player) && event.getEntity() instanceof LivingEntity livingEntity && event.getSource().getWeaponItem() == null) {
            if (!event.getSource().is(DamageTypes.IN_WALL) && !event.getSource().is(DamageTypes.CRAMMING)) {
                livingEntity.invulnerableTime = 0;
            }
        }
    }

}







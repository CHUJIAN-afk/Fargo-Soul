package First.fargo_soul.Item.Soul.BaseSoul;

import First.fargo_soul.Client.Tooltip.SoulTooltipComponent;
import First.fargo_soul.Utils.CurioUtils;
import com.mojang.datafixers.util.Either;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class SoulItem extends Item implements ICurioItem, BaseSoul {

    public SoulItem(Properties properties) {
        super(properties.stacksTo(1).durability(0));
    }

    @OnlyIn(Dist.CLIENT)
    public static void RenderTooltipEvent(RenderTooltipEvent.GatherComponents event) {
        if (event.getItemStack().getItem() instanceof SoulItem soulItem) {
            List<Either<FormattedText, TooltipComponent>> tooltipElements = event.getTooltipElements();
            tooltipElements.add(3, Either.right(new SoulTooltipComponent((soulItem.getSoulItemList().size() + 1) * 16, 16, soulItem)));
        }
    }

    public static void ItemTooltipEvent(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() instanceof SoulItem soulItem) {
            List<Component> toolTip = event.getToolTip();
            TooltipFlag flags = event.getFlags();
            toolTip.add(Component.literal("按住 [").withStyle(ChatFormatting.DARK_GRAY)
                    .append(Component.literal("Shift").withStyle(flags.hasShiftDown() ? ChatFormatting.WHITE : ChatFormatting.GRAY)
                            .append(Component.literal("] 可查看概要").withStyle(ChatFormatting.DARK_GRAY))
                    )
            );
            if (flags.hasShiftDown()) {
                toolTip.addAll(soulItem.getAttributeList());
                List<SoulItem> soulItemList = CurioUtils.getAllCurioItems(soulItem.getSoulItemList());
                for (SoulItem item : soulItemList) {
                    toolTip.add(Component.empty());
                    toolTip.addAll(item.getAttributeList());
                }
            } else {
                toolTip.addAll(soulItem.getTooltipList());
            }
        }
    }

    public static void invulnerableTimeHandler(LivingIncomingDamageEvent event) {
        if (!(event.getEntity() instanceof Player) && event.getEntity() instanceof LivingEntity livingEntity && event.getSource().getWeaponItem() == null) {
            if (!event.getSource().is(DamageTypes.IN_WALL) && !event.getSource().is(DamageTypes.CRAMMING)) {
                livingEntity.invulnerableTime = 0;
            }
        }
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack itemStack) {
        LivingEntity livingEntity = slotContext.entity();
        if (itemStack.getItem() instanceof SoulItem soulItem) {
            List<SoulItem> soulItemList = CurioUtils.getAllCurioItems(soulItem.getSoulItemList());
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

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of();
    }

    @Override
    public List<Component> getAttributeList() {
        return List.of();
    }

    @Override
    public List<Component> getTooltipList() {
        return List.of();
    }
}







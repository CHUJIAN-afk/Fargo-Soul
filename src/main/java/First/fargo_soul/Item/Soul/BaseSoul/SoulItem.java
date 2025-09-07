package First.fargo_soul.Item.Soul.BaseSoul;

import First.fargo_soul.Client.Tooltip.SoulTooltipComponent;
import First.fargo_soul.Utils.CurioUtils;
import com.mojang.datafixers.util.Either;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
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
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.event.CurioChangeEvent;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class SoulItem extends Item implements ICurioItem {

    public SoulItem(Properties properties) {
        super(properties.stacksTo(1).durability(0));
    }

    @OnlyIn(Dist.CLIENT)
    public static void RenderTooltipHandler(RenderTooltipEvent.GatherComponents event) {
        if (event.getItemStack().getItem() instanceof SoulItem soulItem) {
            List<Either<FormattedText, TooltipComponent>> tooltipElements = event.getTooltipElements();
            int size = tooltipElements.size();
            tooltipElements.add(Math.min(size, 3), Either.right(new SoulTooltipComponent((soulItem.getSoulItemList().size() + 1) * 16, 16, soulItem)));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void ItemTooltipHandler(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() instanceof SoulItem soulItem) {
            List<Component> toolTip = event.getToolTip();
            TooltipFlag flags = event.getFlags();
            MutableComponent mutableComponent = Component.translatable("key.shift.tooltip.1").withStyle(ChatFormatting.DARK_GRAY);
            mutableComponent.append(Component.literal("Shift").withStyle(flags.hasShiftDown() ? ChatFormatting.WHITE : ChatFormatting.GRAY));
            mutableComponent.append(Component.translatable("key.shift.tooltip.2").withStyle(ChatFormatting.DARK_GRAY));
            toolTip.add(mutableComponent);
            if (flags.hasShiftDown()) {
                List<SoulItem> soulItemList = CurioUtils.getAllCurioItems(soulItem.getSoulItemList());
                List<Component> attributeList = CurioUtils.getAttributeList(soulItem);
                if (!attributeList.isEmpty()) {
                    toolTip.addAll(attributeList);
                }
                for (SoulItem item : soulItemList) {
                    toolTip.add(Component.empty());
                    toolTip.addAll(CurioUtils.getAttributeList(item));
                }
            } else {
                List<Component> tooltipList = CurioUtils.getTooltipList(soulItem);
                if (soulItem.components().get(ConfluenceMagicLib.MOD_RARITY.get()) instanceof ModRarity modRarity && modRarity.equals(ModRarity.MASTER)) {
                    for (Component component : tooltipList) {
                        toolTip.add(component.copy().withColor(modRarity.color()));
                    }
                } else {
                    toolTip.addAll(tooltipList);
                }
            }
        }
    }

    public static void CurioChangeHandler(CurioChangeEvent event) {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            CurioUtils.updateSoulList(livingEntity);
        }
    }

    public static void invulnerableTimeHandler(LivingIncomingDamageEvent event) {
        if (!(event.getEntity() instanceof Player) && event.getEntity() instanceof LivingEntity livingEntity && event.getSource().getWeaponItem() == null) {
            if (!event.getSource().is(DamageTypes.IN_WALL) && !event.getSource().is(DamageTypes.CRAMMING)) {
                livingEntity.invulnerableTime = 0;
            }
        }
    }

    public List<SoulItem> getSoulItemList() {
        return List.of();
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack itemStack) {
        LivingEntity livingEntity = slotContext.entity();
        if (itemStack.getItem() instanceof SoulItem soulItem && livingEntity instanceof Player player) {
            List<SoulItem> soulItemList = CurioUtils.getAllCurioItems(soulItem.getSoulItemList());
            for (SoulItem soulItem1 : soulItemList) {
                if (CurioUtils.isEquipped(player, soulItem1)) {
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

}







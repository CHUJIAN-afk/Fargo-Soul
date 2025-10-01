package First.fargo_soul.Item.Soul.BaseSoul;

import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Client.Tooltip.SoulTooltipComponent;
import First.fargo_soul.Event.AddItemTagEvent;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.SoulUtils;
import com.mojang.datafixers.util.Either;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.event.CurioChangeEvent;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.ArrayList;
import java.util.List;

public class SoulItem extends Item implements ICurioItem {

    public SoulItem(Properties properties) {
        super(properties.stacksTo(1).durability(0));
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

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void AddItemTagEvent(AddItemTagEvent event) {
            ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath("curios", "soul");
            SoulItem item = SoulsRegister.TerraSoul.get();
            List<SoulItem> soulItemList = SoulUtils.getAllCurioItems(item.getSoulItemList());
            soulItemList.add(item);
            List<ResourceLocation> resourceLocationList = new ArrayList<>();
            soulItemList.forEach(soulItem -> resourceLocationList.add(BuiltInRegistries.ITEM.getKey(soulItem)));
            event.getMap().put(resourceLocation, resourceLocationList);
        }


        @SubscribeEvent
        public static void LivingIncomingDamageEvent(LivingIncomingDamageEvent event) {
            if (event.getSource().getDirectEntity() instanceof Entity entity && entity.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(SoulItem.class).enabled && !event.getSource().isDirect()) {
                event.getEntity().invulnerableTime = 0;
            }
        }

        @SubscribeEvent
        public static void CurioChangeEvent(CurioChangeEvent event) {
            SoulUtils.updateSoulList(event.getEntity());
        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void RenderTooltipHandler(RenderTooltipEvent.GatherComponents event) {
            if (event.getItemStack().getItem() instanceof SoulItem soulItem) {
                List<Either<FormattedText, TooltipComponent>> tooltipElements = event.getTooltipElements();
                int size = tooltipElements.size();
                tooltipElements.add(Math.min(size, 3), Either.right(new SoulTooltipComponent((soulItem.getSoulItemList().size() + 1) * 24, 24, soulItem)));
            }
        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
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
                    List<Component> attributeList = CurioUtils.getComponent(soulItem, "attribute");
                    if (!attributeList.isEmpty()) {
                        toolTip.addAll(attributeList);
                    }
                    for (SoulItem item : soulItemList) {
                        toolTip.add(Component.empty());
                        toolTip.addAll(CurioUtils.getComponent(item, "attribute"));
                    }
                } else {
                    toolTip.addAll(CurioUtils.getComponent(soulItem, "tooltip"));
                }
            }
        }

    }

}







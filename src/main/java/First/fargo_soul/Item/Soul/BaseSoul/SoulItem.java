package First.fargo_soul.Item.Soul.BaseSoul;

import First.fargo_soul.Client.Tooltip.SoulTooltipComponent;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.SoulUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Either;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;
import org.joml.Quaternionf;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.event.CurioChangeEvent;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.Random;

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
        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void RenderTooltipHandler(RenderTooltipEvent.GatherComponents event) {
            if (event.getItemStack().getItem() instanceof SoulItem soulItem) {
                List<Either<FormattedText, TooltipComponent>> tooltipElements = event.getTooltipElements();
                int size = tooltipElements.size();
                tooltipElements.add(Math.min(size, 3), Either.right(new SoulTooltipComponent((soulItem.getSoulItemList().size() + 1) * 16, 16, soulItem)));
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

        @SubscribeEvent
        public static void invulnerable(LivingIncomingDamageEvent event) {
            if (event.getSource().getEntity() instanceof Projectile projectile && projectile.getOwner() instanceof Player) {
                if (event.getEntity() instanceof LivingEntity target) {
                    target.invulnerableTime = 0;
                }
            }
        }

        @SubscribeEvent
        public static void CurioChangeHandler(CurioChangeEvent event) {
            if (event.getEntity() instanceof LivingEntity livingEntity) {
                CurioUtils.updateSoulList(livingEntity);
            }
        }

    }

}







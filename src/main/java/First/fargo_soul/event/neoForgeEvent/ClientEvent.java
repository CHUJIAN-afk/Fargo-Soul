package First.fargo_soul.event.neoForgeEvent;


import First.fargo_soul.FargoSoul;
import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.attachment.SoulAbilityEnabledData;
import First.fargo_soul.attachment.SoulListData;
import First.fargo_soul.client.renderer.blockEntityRender.CosmicCrucibleBlockEntityRenderer;
import First.fargo_soul.client.screen.SoulScreen;
import First.fargo_soul.client.tooltip.SoulTooltipComponent;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.CosmicPower;
import First.fargo_soul.item.terraSoul.cosmicPower.WizardSoul;
import First.fargo_soul.item.terraSoul.deathPower.CrystalAssassinSoul;
import First.fargo_soul.item.terraSoul.deathPower.PenetratingNinjaSoul;
import First.fargo_soul.item.terraSoul.lifePower.BeeSoul;
import First.fargo_soul.item.terraSoul.lifePower.BeetleSoul;
import First.fargo_soul.item.terraSoul.naturePower.GreenSoul;
import First.fargo_soul.item.terraSoul.willPower.RedRidingSoul;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.register.BlockEntityRegister;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.register.KeyRegister;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.KeyUtils;
import First.fargo_soul.utils.RenderUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Either;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@EventBusSubscriber(modid = FargoSoul.MODID, value = Dist.CLIENT)
public class ClientEvent {
/*
    @SubscribeEvent
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.HOTBAR, FargoSoul.rl("soul_overlay"), SoulGuiLayer::render);
    }
*/

    @SubscribeEvent
    public static void openScreen(InputEvent.Key event) {
        if (event.getKey() == KeyRegister.SoulListKey.getKey().getValue()) {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.player instanceof LocalPlayer player) {
                List<SoulItem> soulFromSlots = CurioUtils.getSoulFromSlots(player);
                if (!soulFromSlots.isEmpty()) {
                    minecraft.setScreen(new SoulScreen(soulFromSlots));
                } else {
                    player.displayClientMessage(Component.translatable("fargo_soul.screen.is_empty").withStyle(ChatFormatting.GOLD),true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void cosmicCrucibleTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.is(ItemRegister.CosmicCrucibleBlockItem.get())) {
            List<Component> toolTip = event.getToolTip();
            toolTip.add(Component.translatable("tooltip.fargo_soul.cosmic_crucible").withStyle(ChatFormatting.GRAY));

            CustomData customData = stack.get(DataComponents.BLOCK_ENTITY_DATA);
            if (customData != null && event.getEntity() != null) {
                ListTag items = customData.copyTag().getCompound("Inventory").getList("Items", 10);
                int size = items.size();
                int max = Math.min(size, 9);
                for (int i = 0; i < max; i++) {
                    Optional<ItemStack> s = ItemStack.parse(event.getEntity().registryAccess(), items.getCompound(i));
                    if (s.isPresent() && !s.get().isEmpty()) {
                        toolTip.add(Component.literal(" ◈ ").append(s.get().getHoverName()).append(" x" + s.get().getCount()).withStyle(ChatFormatting.DARK_AQUA));
                    }
                    if (i == max - 1 && size > 9) {
                        toolTip.add(Component.literal(" ◈ 以及另外 " + (size - 9) + " 种物品").withStyle(ChatFormatting.DARK_PURPLE));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(
                BlockEntityRegister.CosmicCrucible.get(),
                CosmicCrucibleBlockEntityRenderer::new
        );
    }

    @SubscribeEvent
    public static void render(RenderLivingEvent.Post<?, ?> event) {
        LivingEntity livingEntity = event.getEntity();
        SoulListData soulListData = livingEntity.getData(AttachmentRegister.SoulListData);
        SoulAbilityEnabledData enabledData = livingEntity.getData(AttachmentRegister.AbilityEnabledData);
        List<SoulItem> soulItemList = soulListData.getSoulItemList().stream()
                .filter(enabledData::isEnabled)
                .distinct()
                .toList();
        if (!soulItemList.isEmpty()) {
            PoseStack poseStack = event.getPoseStack();
            MultiBufferSource multiBufferSource = event.getMultiBufferSource();
            int packedLight = event.getPackedLight();
            float partialTick = event.getPartialTick();

            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            Level level = livingEntity.level();
            RandomSource random = level.getRandom();
            float scale = (float) livingEntity.getBoundingBox().getSize();
            int renderLight = CurioUtils.isEquipped(livingEntity, WizardSoul.class) ? LightTexture.FULL_BRIGHT : packedLight;
            int id = livingEntity.getId();
            float ageInTicks = RenderUtils.getAgeInTicks(livingEntity, partialTick, CurioUtils.isEquipped(livingEntity, CosmicPower.class) ? 1.3f : 1);
            poseStack.pushPose();
            poseStack.translate(0, livingEntity.getBbHeight() * 0.5f, 0);
            poseStack.mulPose(Axis.YP.rotationDegrees(ageInTicks));
            poseStack.mulPose(Axis.XP.rotationDegrees(ageInTicks));
            poseStack.mulPose(Axis.ZP.rotationDegrees(ageInTicks));
            float ringRadius = 1.2f * scale * (float) (Math.cos(ageInTicks * 0.1f) * 0.2f + 1f);
            for (SoulItem soulItem : soulItemList) {
                random.setSeed(soulItem.hashCode() + id);
                int i = soulItemList.indexOf(soulItem);
                float angle = (float) i / soulItemList.size() * Mth.TWO_PI + ageInTicks * 0.05f;
                float x = (float) Math.cos(angle) * ringRadius;
                float z = (float) Math.sin(angle) * ringRadius;
                poseStack.pushPose();
                poseStack.translate(x, 0, z);

                poseStack.mulPose(Axis.YP.rotationDegrees(ageInTicks * random.nextFloat()));
                poseStack.mulPose(Axis.XP.rotationDegrees(ageInTicks * random.nextFloat()));
                poseStack.mulPose(Axis.ZP.rotationDegrees(ageInTicks * random.nextFloat()));
                float itemScale = 0.35f * scale * (float) (Math.cos(ageInTicks * 0.1f) * 0.2f + 1f);
                poseStack.scale(itemScale, itemScale, itemScale);
                itemRenderer.renderStatic(
                        soulItem.getDefaultInstance(),
                        ItemDisplayContext.FIXED,
                        renderLight,
                        OverlayTexture.NO_OVERLAY,
                        poseStack,
                        multiBufferSource,
                        level,
                        soulItem.hashCode()
                );
                poseStack.popPose();
            }
            poseStack.popPose();
        }
    }

    @SubscribeEvent
    public static void tooltip(ItemTooltipEvent event) {
        ItemStack itemStack = event.getItemStack();
        if (itemStack.getItem() instanceof SoulItem soulItem) {
            boolean shiftDown = event.getFlags().hasShiftDown();
            List<Component> toolTip = event.getToolTip();
            toolTip.add(Component.translatable("key.shift.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
                    .append(Component.literal("Shift").withStyle(shiftDown ? ChatFormatting.WHITE : ChatFormatting.GRAY))
                    .append(Component.translatable("key.shift.tooltip.2").withStyle(ChatFormatting.DARK_GRAY)));
            if (shiftDown) {
                List<SoulItem> soulFromSoul = CurioUtils.getSoulFromSoul(soulItem);
                List<Component> keyList = new ArrayList<>();
                for (SoulItem item : soulFromSoul) {
                    if (!soulFromSoul.getFirst().equals(item)) {
                        keyList.add(Component.empty());
                    }
                    keyList.addAll(CurioUtils.getSoulItemAttributesComponent(item));
                }
                toolTip.addAll(keyList);
            } else {
                toolTip.addAll(CurioUtils.getSoulItemTooltipComponent(soulItem));
            }
        }
    }

    @SubscribeEvent
    public static void RegisterClientTooltipComponentFactoriesEvent(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(SoulTooltipComponent.class, soulTooltipComponent -> soulTooltipComponent);
    }

    @SubscribeEvent
    public static void RenderTooltipHandler(RenderTooltipEvent.GatherComponents event) {
        if (event.getItemStack().getItem() instanceof SoulItem soulItem) {
            List<Either<FormattedText, TooltipComponent>> tooltipElements = event.getTooltipElements();
            int size = tooltipElements.size();
            tooltipElements.add(Math.min(size, 1), Either.right(new SoulTooltipComponent((soulItem.getSoulItemList().size() + 1) * 16, 16, 1.5f, soulItem)));
        }
    }

    @SubscribeEvent
    public static void Sprint(MovementInputUpdateEvent event) {
        if (event.getEntity() instanceof LocalPlayer player) {
            SoulAbilityData soulAbilityData = SoulAbilityData.getSoulAbilityData(player);
            SoulAbilityData.SoulInfo soulInfo = soulAbilityData.getSoulInfo("Sprint", true);
            soulInfo.setMaxCooldown(40);
            if (soulInfo.isReady() && KeyUtils.isDoubleTappingForward(event.getInput()) && CurioUtils.isEquipped(player, getSprintList())) {
                soulInfo.setCooldown(soulInfo.getMaxCooldown());
                double factor = 1.5;
                if (CurioUtils.isEquipped(player, RedRidingSoul.class)) {
                    SoulAbilityData.SoulInfo info = soulAbilityData.getSoulInfo(RedRidingSoul.class);
                    if (info.getStacks() == info.getMaxStacks()) {
                        factor *= 1.5f;
                    }
                }
                player.addDeltaMovement(player.getLookAngle().scale(factor));
                PacketDistributor.sendToServer(new PenetratingNinjaSoul.Packet(CurioUtils.isEquipped(player, PenetratingNinjaSoul.class)));
            }
        }
    }

    @SubscribeEvent
    public static void Fly(MovementInputUpdateEvent event) {
        if (event.getEntity() instanceof LocalPlayer player) {
            SoulAbilityData.SoulInfo soulInfo = player.getData(AttachmentRegister.SoulAbilityData).getSoulInfo("Fly", true);
            soulInfo.setMaxStacks(getFlyTime(player));
            if (soulInfo.getStacks() > 0 && event.getInput().jumping && CurioUtils.isEquipped(player, getFlyList())) {
                soulInfo.shrinkStacks();
                Vec3 deltaMovement = player.getDeltaMovement();
                Vec3 newDeltaMovement = new Vec3(
                        deltaMovement.x(),
                        Math.min(deltaMovement.y() + 0.25, 0.5),
                        deltaMovement.z()
                );
                player.setDeltaMovement(newDeltaMovement);
            } else if (player.onGround()) {
                soulInfo.setStacks(soulInfo.getMaxStacks());
            }
        }
    }

    public static int getFlyTime(Player player) {
        int time = 60;
        if (CurioUtils.isEquipped(player, GreenSoul.class)) {
            time += 40;
        }
        return time;
    }

    @SuppressWarnings("unchecked")
    public static Class<? extends SoulItem>[] getSprintList() {
        return new Class[]{
                CrystalAssassinSoul.class,
                PenetratingNinjaSoul.class,
                GreenSoul.class
        };
    }

    @SuppressWarnings("unchecked")
    public static Class<? extends SoulItem>[] getFlyList() {
        return new Class[]{
                BeeSoul.class,
                BeetleSoul.class
        };
    }

}

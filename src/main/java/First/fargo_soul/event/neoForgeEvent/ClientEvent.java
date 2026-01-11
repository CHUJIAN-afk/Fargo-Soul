package First.fargo_soul.event.neoForgeEvent;


import First.fargo_soul.FargoSoul;
import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.attachment.SoulAbilityEnabledData;
import First.fargo_soul.attachment.SoulListData;
import First.fargo_soul.client.gui.SoulGuiLayer;
import First.fargo_soul.client.renderer.blockEntityRender.CosmicCrucibleBlockEntityRenderer;
import First.fargo_soul.client.renderer.entityRenderer.BoneRenderer;
import First.fargo_soul.client.renderer.entityRenderer.NeedleRenderer;
import First.fargo_soul.client.screen.SoulScreen;
import First.fargo_soul.client.tooltip.CosmicCrucibleItemTooltipComponent;
import First.fargo_soul.client.tooltip.SoulTooltipComponent;
import First.fargo_soul.config.ClientConfig;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.CosmicPower;
import First.fargo_soul.item.terraSoul.cosmicPower.WizardSoul;
import First.fargo_soul.item.terraSoul.deathPower.PenetratingNinjaSoul;
import First.fargo_soul.item.terraSoul.naturePower.LavaSoul;
import First.fargo_soul.item.terraSoul.terraPower.ObsidianSoul;
import First.fargo_soul.item.terraSoul.willPower.RedRidingSoul;
import First.fargo_soul.register.*;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.KeyUtils;
import First.fargo_soul.utils.RenderUtils;
import First.fargo_soul.utils.SoulUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Either;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = FargoSoul.MODID, value = Dist.CLIENT)
public class ClientEvent {

    @SubscribeEvent
    public static void renderFog(ViewportEvent.RenderFog event) {
        Camera camera = event.getCamera();
        Minecraft minecraft = Minecraft.getInstance();
        BlockPos blockPos = camera.getBlockPosition();
        if (minecraft.level instanceof Level level && minecraft.player instanceof LocalPlayer player) {
            if (CurioUtils.isEquipped(player, ObsidianSoul.class)) {
                FluidState fluidState = level.getFluidState(blockPos);
                if (camera.getPosition().y < blockPos.getY() + fluidState.getHeight(level, blockPos)) {
                    Fluid fluid = fluidState.getType();
                    Entity entity = camera.getEntity();
                    if (!entity.isSpectator() && fluid.equals(Fluids.FLOWING_LAVA)) {
                        event.setNearPlaneDistance(-4.0f);
                        event.setFarPlaneDistance(20.0f);
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    private static void onBlockOverlay(RenderBlockScreenEffectEvent event) {
        if (event.getOverlayType().equals(RenderBlockScreenEffectEvent.OverlayType.FIRE) && CurioUtils.isEquipped(event.getPlayer(), LavaSoul.class)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void soulRender(RenderLivingEvent.Post<?, ?> event) {
        SoulUtils.RegisterSoulList.forEach(soulItem -> soulItem.render(event));
    }

    @SubscribeEvent
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.HOTBAR, FargoSoul.rl("soul_overlay"), SoulGuiLayer::render);
    }

    @SubscribeEvent
    public static void soulMovementInputUpdate(MovementInputUpdateEvent event) {
        if (event.getEntity() instanceof LocalPlayer player) {
            SoulUtils.RegisterSoulList.forEach(soulItem -> soulItem.movementInput(player, event.getInput()));
        }
    }

    @SubscribeEvent
    public static void soulKeyPressed(InputEvent.Key event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player instanceof LocalPlayer player) {
            SoulUtils.RegisterSoulList.forEach(soulItem -> soulItem.keyPressed(player, event.getKey()));
        }
    }

    @SubscribeEvent
    public static void RegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegister.NeedleEntity.get(), NeedleRenderer::new);
        event.registerEntityRenderer(EntityRegister.BoneEntity.get(), BoneRenderer::new);
    }

    @SubscribeEvent
    public static void openScreen(InputEvent.Key event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player instanceof LocalPlayer player && event.getKey() == KeyRegister.SoulListKey.getKey().getValue()) {
            List<SoulItem> soulFromSlots = CurioUtils.getSoulFromSlots(player);
            if (!soulFromSlots.isEmpty()) {
                minecraft.setScreen(new SoulScreen(soulFromSlots));
            } else {
                player.displayClientMessage(Component.translatable("fargo_soul.screen.is_empty").withStyle(ChatFormatting.GOLD), true);
            }
        }
    }

    @SubscribeEvent
    public static void cosmicCrucibleTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.is(ItemRegister.CosmicCrucibleBlockItem.get())) {
            List<Component> toolTip = event.getToolTip();
            toolTip.add(Component.translatable("tooltip.fargo_soul.cosmic_crucible").withStyle(ChatFormatting.GRAY));
        }
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockEntityRegister.CosmicCrucible.get(), CosmicCrucibleBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void render(RenderLivingEvent.Post<?, ?> event) {
        if (!ClientConfig.CreatureSoulRendering.get()) {
            return;
        }
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
        event.register(SoulTooltipComponent.class, tooltipComponent -> tooltipComponent);
        event.register(CosmicCrucibleItemTooltipComponent.class, tooltipComponent -> tooltipComponent);
    }

    @SubscribeEvent
    public static void renderSoulItemTooltipHandler(RenderTooltipEvent.GatherComponents event) {
        if (event.getItemStack().getItem() instanceof SoulItem soulItem && ClientConfig.EmbedAChildSoulInTheItemTooltip.get()) {
            List<Either<FormattedText, TooltipComponent>> tooltipElements = event.getTooltipElements();
            int size = tooltipElements.size();
            tooltipElements.add(Math.min(size, 1), Either.right(new SoulTooltipComponent((soulItem.getSoulItemList().size() + 1) * 16, 16, 1f, soulItem)));
        }
    }

    @SubscribeEvent
    public static void renderCosmicCrucibleItemTooltipHandler(RenderTooltipEvent.GatherComponents event) {
        if (event.getItemStack().is(ItemRegister.CosmicCrucibleBlockItem)) {
            ItemStack stack = event.getItemStack();
            CustomData customData = stack.get(DataComponents.BLOCK_ENTITY_DATA);
            ClientLevel level = Minecraft.getInstance().level;
            if (customData != null && level != null) {
                ListTag items = customData.copyTag().getCompound("Inventory").getList("Items", 10);
                List<ItemStack> itemStackList = new ArrayList<>();
                for (int i = 0; i < items.size(); i++) {
                    CompoundTag itemTag = items.getCompound(i);
                    ItemStack itemStack = ItemStack.parse(level.registryAccess(), itemTag).orElse(ItemStack.EMPTY);
                    if (!itemStack.isEmpty()) {
                        itemStackList.add(itemStack);
                    }
                }
                if (!itemStackList.isEmpty()) {
                    List<Either<FormattedText, TooltipComponent>> tooltipElements = event.getTooltipElements();
                    tooltipElements.add(tooltipElements.size(), Either.right(new CosmicCrucibleItemTooltipComponent(itemStackList)));
                }
            }
        }
    }

    @SubscribeEvent
    public static void Sprint(MovementInputUpdateEvent event) {
        if (event.getEntity() instanceof LocalPlayer player) {
            SoulAbilityData soulAbilityData = SoulAbilityData.getSoulAbilityData(player);
            SoulAbilityData.SoulInfo soulInfo = soulAbilityData.getSoulInfo("Sprint", true);
            soulInfo.setMaxCooldown(40);
            if (soulInfo.isReady() && KeyUtils.isDoubleTappingForward(event.getInput()) && CurioUtils.isEquipped(player, SoulUtils.getSprintList())) {
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
            soulInfo.setMaxStacks(SoulUtils.getFlyTime(player));
            if (soulInfo.getStacks() > 0 && event.getInput().jumping && CurioUtils.isEquipped(player, SoulUtils.getFlyList())) {
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

}

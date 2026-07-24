package first.fargo_soul.common.event.neoForgeEvent;


import first.fargo_soul.FargoSoul;
import first.fargo_soul.client.button.OpenSoulContainerButton;
import first.fargo_soul.client.gui.SoulGuiRenderManager;
import first.fargo_soul.client.renderer.blockEntityRender.CosmicCrucibleBlockEntityRenderer;
import first.fargo_soul.client.renderer.entityRenderer.BoneRenderer;
import first.fargo_soul.client.renderer.entityRenderer.NeedleRenderer;
import first.fargo_soul.client.screen.SoulContainerScreen;
import first.fargo_soul.client.tooltip.CosmicCrucibleItemTooltipComponent;
import first.fargo_soul.common.dataComponents.SoulRarity;
import first.fargo_soul.common.event.modEvent.PlayerFlyEvent;
import first.fargo_soul.common.event.modEvent.SprintEvent;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.terraSoul.CosmicPower;
import first.fargo_soul.common.item.terraSoul.cosmicPower.WizardSoul;
import first.fargo_soul.common.item.terraSoul.naturePower.LavaSoul;
import first.fargo_soul.common.item.terraSoul.terraPower.ObsidianSoul;
import first.fargo_soul.config.ClientConfig;
import first.fargo_soul.mixin.minecraft.ScreenAccessor;
import first.fargo_soul.network.KeyHandlePacket;
import first.fargo_soul.network.OpenSoulContainerPacket;
import first.fargo_soul.network.SprintPacket;
import first.fargo_soul.register.*;
import first.fargo_soul.utils.CurioUtils;
import first.fargo_soul.utils.KeyUtils;
import first.fargo_soul.utils.RenderUtils;
import first.fargo_soul.utils.SoulUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Either;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
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
import net.minecraft.world.entity.player.Player;
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
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = FargoSoul.MODID, value = Dist.CLIENT)
public class ClientEvent {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void colorChange(ClientTickEvent.Pre event) {
        int expert = SoulRarity.ExpertColor;
        if (expert == -1) expert = 0xFF0000;
        int expertColor = expert;
        int er = (expertColor >> 16) & 0xFF;
        int eg = (expertColor >> 8) & 0xFF;
        int eb = expertColor & 0xFF;
        int ediscoStyle = (expertColor >> 24) & 0xFF;
        switch (ediscoStyle) {
            case 0: if (eg < 255) eg = Math.min(eg + 7, 255); if (eg == 255) { er = 248; ediscoStyle = 1; } break;
            case 1: if (er > 0) er = Math.max(er - 7, 0); if (er == 0) { eb = 7; ediscoStyle = 2; } break;
            case 2: if (eb < 255) eb = Math.min(eb + 7, 255); if (eb == 255) { eg = 248; ediscoStyle = 3; } break;
            case 3: if (eg > 0) eg = Math.max(eg - 7, 0); if (eg == 0) { er = 7; ediscoStyle = 4; } break;
            case 4: if (er < 255) er = Math.min(er + 7, 255); if (er == 255) { eb = 248; ediscoStyle = 5; } break;
            case 5: if (eb > 0) eb = Math.max(eb - 7, 0); if (eb == 0) ediscoStyle = 0; break;
        }
        SoulRarity.ExpertColor = ((ediscoStyle << 24) | (er << 16) | (eg << 8) | eb);

        int master = SoulRarity.MasterColor;
        if (master == -2) master = 0xFF0000;
        int masterColor = master;
        int mr = (masterColor >> 16) & 0xFF;
        int mg = (masterColor >> 8) & 0xFF;
        int mb = masterColor & 0xFF;
        int mdiscoStyle = (masterColor >> 24) & 0xFF;
        int speed = 14;
        switch (mdiscoStyle) {
            case 0: if (mg < 255) mg = Math.min(mg + speed, 255); if (mg == 255) { mr = 241; mdiscoStyle = 1; } break;
            case 1: if (mr > 0) mr = Math.max(mr - speed, 0); if (mr == 0) { mb = 14; mdiscoStyle = 2; } break;
            case 2: if (mb < 255) mb = Math.min(mb + speed, 255); if (mb == 255) { mg = 241; mdiscoStyle = 3; } break;
            case 3: if (mg > 0) mg = Math.max(mg - speed, 0); if (mg == 0) { mr = 14; mdiscoStyle = 4; } break;
            case 4: if (mr < 255) mr = Math.min(mr + speed, 255); if (mr == 255) { mb = 241; mdiscoStyle = 5; } break;
            case 5: if (mb > 0) mb = Math.max(mb - speed, 0); if (mb == 0) mdiscoStyle = 0; break;
        }
        SoulRarity.MasterColor = ((mdiscoStyle << 24) | (mr << 16) | (mg << 8) | mb);
    }

    @SubscribeEvent
    public static void onLevelLoad(LevelEvent.Load event) {
        if (event.getLevel().isClientSide()) {
            SoulGuiRenderManager.SoulInfoManager.clear();
        }
    }

    @SubscribeEvent
    public static void openScreen(ScreenEvent.Init.Post event) {
        if (event.getScreen() instanceof AbstractContainerScreen<?> screen) {
            int guiLeft = screen.getGuiLeft();
            int guiTop = screen.getGuiTop();
            if (screen instanceof InventoryScreen || screen instanceof SoulContainerScreen) {
                int x = guiLeft + 27;
                int y = guiTop + 68;
                List<Renderable> renderables = screen.renderables;
                OpenSoulContainerButton button = renderables.stream()
                        .filter(renderable -> renderable instanceof OpenSoulContainerButton)
                        .map(renderable -> (OpenSoulContainerButton) (renderable))
                        .findFirst()
                        .orElse(null);
                if (button == null) {
                    OpenSoulContainerButton containerButton = new OpenSoulContainerButton(x, y);
                    containerButton.setTooltip(Tooltip.create(Component.translatable("curios.identifier.soul")));
                    ((ScreenAccessor) screen).invokeAddRenderableWidget(containerButton);
                } else {
                    button.setPosition(x, y);
                }
            }
            if (screen instanceof CreativeModeInventoryScreen) {
                int x = guiLeft + 75;
                int y = guiTop + 41;
                OpenSoulContainerButton containerButton = new OpenSoulContainerButton(x, y);
                containerButton.setX(x);
                containerButton.setY(y);
                containerButton.setWidth(6);
                containerButton.setHeight(6);
                ((ScreenAccessor) screen).invokeAddRenderableWidget(containerButton);
            }
        }
    }

    @SubscribeEvent
    public static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(MenuRegister.SoulContainer.get(), SoulContainerScreen::new);
    }

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
        event.registerAbove(VanillaGuiLayers.HOTBAR, FargoSoul.rl("soul_info_overlay"), SoulGuiRenderManager::infoRender);
        event.registerAbove(VanillaGuiLayers.HOTBAR, FargoSoul.rl("soul_render_overlay"), SoulGuiRenderManager::guiRender);
    }

    @SubscribeEvent
    public static void RegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegister.NeedleEntity.get(), NeedleRenderer::new);
        event.registerEntityRenderer(EntityRegister.BoneEntity.get(), BoneRenderer::new);
        //event.registerEntityRenderer(EntityRegister.Mutant.get(), MutantRenderer::new);
    }

    @SubscribeEvent
    public static void openScreen(InputEvent.Key event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.screen == null && minecraft.player instanceof LocalPlayer && event.getKey() == KeyRegister.SoulMenuKey.getKey().getValue()) {
            NetworkPacketRegister.playToServer(new OpenSoulContainerPacket());
        }
    }

    @SubscribeEvent
    public static void cosmicCrucibleTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.is(FargoSoulItemRegister.CosmicCrucibleBlockItem.get())) {
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
                toolTip.addAll(CurioUtils.getSoulItemAttributesComponent(soulItem));
                List<SoulItem> soulItems = CurioUtils.getSoulFromList(soulItem.getSoulItemList(soulItem));
                if (!soulItems.isEmpty()) {
                    if (ModList.get().isLoaded("modernui")) {
                        for (SoulItem item : soulItems) {
                            toolTip.add(Component.empty());
                            toolTip.addAll(CurioUtils.getSoulItemAttributesComponent(item));
                        }
                    } else if (event.getContext().level() instanceof Level level) {
                        RandomSource random = level.getRandom();
                        random.setSeed(level.getGameTime() / (long) Math.max(2, 40f / soulItems.size()));
                        toolTip.add(Component.empty());
                        toolTip.addAll(CurioUtils.getSoulItemAttributesComponent(soulItems.get(random.nextInt(soulItems.size()))));
                    }
                }
            } else {
                toolTip.addAll(CurioUtils.getSoulItemTooltipComponent(soulItem).stream().map(component -> component.copy().withColor(SoulRarity.GRAY.color())).toList());
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
        if (event.getItemStack().getItem() instanceof SoulItem soulItem) {
            List<Either<FormattedText, TooltipComponent>> tooltipElements = event.getTooltipElements();
            if (ClientConfig.EmbedAChildSoulInTheItemTooltip.get()) {
                tooltipElements.add(1, Either.right(new SoulTooltipComponent(soulItem)));
            }
        }
    }

    @SubscribeEvent
    public static void renderCosmicCrucibleItemTooltipHandler(RenderTooltipEvent.GatherComponents event) {
        if (event.getItemStack().is(FargoSoulItemRegister.CosmicCrucibleBlockItem)) {
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
    public static void soulSprint(MovementInputUpdateEvent event) {
        if (event.getEntity() instanceof LocalPlayer player) {
            SoulAbilityData.SoulInfo soulInfo = player.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(FargoSoul.rl("Sprint"), true);
            soulInfo.setMaxCooldown(40);
            Vec3 vec3 = player.getLookAngle().scale(1.5);
            SprintEvent.Client sprintEvent = new SprintEvent.Client(player, vec3);
            NeoForge.EVENT_BUS.post(sprintEvent);
            for (SoulItem soulItem : SoulUtils.RegisterSoulList) {
                soulItem.sprintClient(sprintEvent);
            }
            if (soulInfo.isReady() && sprintEvent.isSprinting() && KeyUtils.isDoubleTappingForward(event.getInput())) {
                soulInfo.setCooldown(soulInfo.getMaxCooldown());
                player.addDeltaMovement(sprintEvent.getVec3());
                PacketDistributor.sendToServer(new SprintPacket());
            }
        }
    }

    @SubscribeEvent
    public static void fly(MovementInputUpdateEvent event) {
        if (event.getEntity() instanceof LocalPlayer player) {
            SoulAbilityData.SoulInfo soulInfo = player.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(FargoSoul.rl("Fly"), true);
            PlayerFlyEvent flyEvent = new PlayerFlyEvent(player);
            NeoForge.EVENT_BUS.post(flyEvent);
            for (SoulItem soulItem : SoulUtils.RegisterSoulList) {
                soulItem.fly(flyEvent);
            }
            soulInfo.setMaxStacks(flyEvent.getMaxFlyTime());
            if (event.getInput().jumping && flyEvent.isAllowingFly()) {
                player.fallDistance = 0;
                double addedX = 0;
                double addedY = 0;
                double addedZ = 0;
                Vec3 deltaMovement = player.getDeltaMovement();
                if (soulInfo.getStacks() > 0) {
                    soulInfo.shrinkStacks();
                    if (deltaMovement.y() < 0.5) {
                        addedY = Math.min(0.25, 0.5 - deltaMovement.y());
                    }
                } else {
                    if (deltaMovement.y() < -0.05) {
                        addedY = Math.min(0.05, -0.05 - deltaMovement.y());
                    }
                }
                if (!player.onGround() && event.getInput().up) {
                    Vec3 lookAngle = player.getLookAngle().normalize();
                    addedX = lookAngle.x() * 0.1;
                    addedZ = lookAngle.z() * 0.1;
                }
                player.addDeltaMovement(new Vec3(addedX, addedY, addedZ));
            }
            if (player.onGround()) {
                soulInfo.setStacks(soulInfo.getMaxStacks());
            }
        }
    }

}

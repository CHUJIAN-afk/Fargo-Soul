package First.fargo_soul.Item.Soul.BaseSoul;

import First.fargo_soul.Attachment.Attachment.SoulAbilityData;
import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Client.Tooltip.SoulTooltipComponent;
import First.fargo_soul.DataComponent.DataComponents.SoulComponent;
import First.fargo_soul.DataComponent.DataComponentsRegister;
import First.fargo_soul.Event.AddItemTagEvent;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Item.Soul.TerraSoul.CosmicPower.CosmicPower;
import First.fargo_soul.Item.Soul.TerraSoul.CosmicPower.SoulStone.WizardSoul;
import First.fargo_soul.Item.Soul.TerraSoul.DeathPower.SoulStone.CrystalAssassinSoul;
import First.fargo_soul.Item.Soul.TerraSoul.DeathPower.SoulStone.PenetratingNinjaSoul;
import First.fargo_soul.Item.Soul.TerraSoul.LifePower.SoulStone.BeeSoul;
import First.fargo_soul.Item.Soul.TerraSoul.LifePower.SoulStone.BeetleSoul;
import First.fargo_soul.Item.Soul.TerraSoul.NaturePower.SoulStone.GreenSoul;
import First.fargo_soul.Item.Soul.TerraSoul.WillPower.Soulstone.RedRidingSoul;
import First.fargo_soul.Item.Soul.TerraSoul.WillPower.WillPower;
import First.fargo_soul.Recipe.RecipeParser;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.KeyUtils;
import First.fargo_soul.Utils.SoulUtils;
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
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.network.PacketDistributor;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.event.CurioChangeEvent;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.*;

public class SoulItem extends Item implements ICurioItem {

    public SoulItem(Properties properties) {
        super(properties.stacksTo(1).durability(0));
    }

    public List<SoulItem> getSoulItemList() {
        return List.of();
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        if (stack.get(ConfluenceMagicLib.MOD_RARITY) instanceof ModRarity modRarity) {
            return Component.translatable(stack.getDescriptionId()).withColor(modRarity.color());
        }
        return super.getName(stack);
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

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getSource().getDirectEntity() instanceof Entity entity && !entity.level().isClientSide()) {
                SoulAbilityData.SoulInfo soulInfo1 = entity.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(SoulAbilityData.SoulAttack);
                SoulAbilityData.SoulInfo soulInfo2 = entity.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(SoulItem.class);
                if (soulInfo1.isEnabled() || soulInfo2.isEnabled()) {
                    event.getEntity().invulnerableTime = 0;
                }
            }
        }

        private static List<Class<? extends SoulItem>> getSprintList() {
            return Arrays.asList(
                    CrystalAssassinSoul.class,
                    PenetratingNinjaSoul.class,
                    GreenSoul.class
            );
        }

        @SubscribeEvent
        public static void MonsterSprint(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof Mob mob && mob.getTarget() instanceof LivingEntity target && !target.level().isClientSide()) {
                SoulAbilityData.SoulInfo soulInfo = mob.getData(AttachmentRegister.SoulAbilityData).getSoulInfo("Sprint");
                soulInfo.setCooldown(100);
                if (SoulUtils.isEquippedAny(mob, getSprintList()) && soulInfo.isReady()) {
                    soulInfo.setCooldown(soulInfo.getMaxCooldown());
                    mob.getLookControl().setLookAt(target);
                    double factor = 1.5;
                    if (SoulUtils.isEquipped(mob, RedRidingSoul.class)) {
                        SoulAbilityData.SoulInfo info = mob.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(RedRidingSoul.class);
                        soulInfo.setMaxCooldown(SoulUtils.isEquipped(mob, WillPower.class) ? 15 : 10);
                        if (info.getStacks() == info.getMaxStacks()) {
                            factor *= 1.5f;
                        }
                    }
                    Vec3 viewVector = mob.getLookAngle().scale(factor);
                    mob.addDeltaMovement(viewVector);
                }
            }
        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void Sprint(MovementInputUpdateEvent event) {
            if (event.getEntity() instanceof LocalPlayer player) {
                SoulAbilityData.SoulInfo soulInfo = player.getData(AttachmentRegister.SoulAbilityData).getClientSoulInfo("Sprint");
                soulInfo.setMaxCooldown(40);
                if (soulInfo.isReady() && KeyUtils.isDoubleTappingForward(event.getInput()) && SoulUtils.isEquippedAny(player, getSprintList())) {
                    soulInfo.setCooldown(soulInfo.getMaxCooldown());
                    double factor = 1.5;
                    if (SoulUtils.isEquipped(player, RedRidingSoul.class)) {
                        SoulAbilityData.SoulInfo info = player.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(RedRidingSoul.class);
                        soulInfo.setMaxStacks(SoulUtils.isEquipped(player, WillPower.class) ? 15 : 10);
                        if (info.getStacks() == info.getMaxStacks()) {
                            factor *= 1.5f;
                        }
                    }
                    Vec3 viewVector = player.getLookAngle().scale(factor);
                    player.addDeltaMovement(viewVector);
                    PacketDistributor.sendToServer(new PenetratingNinjaSoul.Packet(SoulUtils.isEquipped(player, PenetratingNinjaSoul.class)));
                }
            }
        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void Fly(MovementInputUpdateEvent event) {
            if (event.getEntity() instanceof LocalPlayer player) {
                List<Class<? extends SoulItem>> typeList = Arrays.asList(
                        BeeSoul.class,
                        BeetleSoul.class
                );
                SoulAbilityData.SoulInfo soulInfo = player.getData(AttachmentRegister.SoulAbilityData).getClientSoulInfo("Fly");
                soulInfo.setMaxStacks(getFlyTime(player));
                if (soulInfo.getStacks() > 0 && event.getInput().jumping && SoulUtils.isEquippedAny(player, typeList)) {
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

        private static int getFlyTime(Player player) {
            int time = 60;
            if (SoulUtils.isEquipped(player, GreenSoul.class)) {
                time += 40;
            }
            return time;
        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void render(RenderLivingEvent.Post<?, ?> event) {
            LivingEntity attacker = event.getEntity();
            List<SoulItem> soulItemList = SoulUtils.getSoulItemList(attacker);
            if (!soulItemList.isEmpty()) {
                float partialTick = event.getPartialTick();
                float ageInTicks = SoulUtils.getAgeInTicks(attacker, partialTick, 5);
                MultiBufferSource multiBufferSource = event.getMultiBufferSource();
                PoseStack poseStack = event.getPoseStack();
                int light = event.getPackedLight();
                Minecraft minecraft = Minecraft.getInstance();
                ItemRenderer itemRenderer = minecraft.getItemRenderer();
                double size = attacker.getBoundingBox().getSize();
                float scale = (float) size;

                poseStack.pushPose();
                poseStack.mulPose(Axis.YP.rotationDegrees(-Mth.lerp(partialTick, attacker.yHeadRotO, attacker.yHeadRot)));

                List<SoulItem> centerSouls = new ArrayList<>();
                List<SoulItem> innerCircleSouls = new ArrayList<>();
                List<SoulItem> outerCircleSouls = new ArrayList<>();

                for (SoulItem soulItem : soulItemList) {
                    if (soulItem == SoulsRegister.TerraSoul.get()) {
                        centerSouls.add(soulItem);
                    } else if (!soulItem.getSoulItemList().isEmpty()) {
                        innerCircleSouls.add(soulItem);
                    } else {
                        outerCircleSouls.add(soulItem);
                    }
                }

                for (SoulItem soulItem : centerSouls) {
                    renderCenterSoul(soulItem, attacker, poseStack, scale, itemRenderer, multiBufferSource, light);
                }

                if (!innerCircleSouls.isEmpty()) {
                    renderCircleSouls(innerCircleSouls, attacker, poseStack, scale, itemRenderer, multiBufferSource, light, ageInTicks, 0.3f, 1);
                }

                if (!outerCircleSouls.isEmpty()) {
                    int half = (outerCircleSouls.size() + 1) / 2;
                    List<SoulItem> secondCircle = outerCircleSouls.subList(0, half);
                    List<SoulItem> thirdCircle = outerCircleSouls.subList(half, outerCircleSouls.size());
                    if (!secondCircle.isEmpty()) {
                        renderCircleSouls(secondCircle, attacker, poseStack, scale, itemRenderer, multiBufferSource, light, ageInTicks, 0.6f, 2);
                    }
                    if (!thirdCircle.isEmpty()) {
                        renderCircleSouls(thirdCircle, attacker, poseStack, scale, itemRenderer, multiBufferSource, light, ageInTicks, 0.8f, 3);
                    }
                }
                poseStack.popPose();
            }
        }

        @OnlyIn(Dist.CLIENT)
        private static void renderCenterSoul(SoulItem soulItem, LivingEntity attacker, PoseStack poseStack, float scale,
                                             ItemRenderer itemRenderer, MultiBufferSource multiBufferSource, int light) {
            poseStack.pushPose();
            AABB boundingBox = attacker.getBoundingBox();
            poseStack.translate(0, boundingBox.getYsize(), -boundingBox.getZsize() * 0.7f);
            poseStack.scale(0.5f * scale, 0.5f * scale, 0.5f * scale);
            renderSoulItem(soulItem, attacker, poseStack, itemRenderer, multiBufferSource, light);
            poseStack.popPose();
        }

        @OnlyIn(Dist.CLIENT)
        private static void renderCircleSouls(List<SoulItem> soulItems, LivingEntity attacker, PoseStack poseStack, float scale,
                                              ItemRenderer itemRenderer, MultiBufferSource multiBufferSource, int light,
                                              float ageInTicks, float baseRadius, int circleLevel) {
            float speed = SoulUtils.isEquipped(attacker, CosmicPower.class) ? 0.006f : 0.003f;
            for (int i = 0; i < soulItems.size(); i++) {
                SoulItem soulItem = soulItems.get(i);
                poseStack.pushPose();
                float angle = circleLevel * ageInTicks * speed + ((float) Math.PI * 2 / soulItems.size() * i);
                float radius = baseRadius * scale;
                float x = (float) Math.sin(angle) * radius;
                float y = (float) Math.cos(angle) * radius;
                AABB boundingBox = attacker.getBoundingBox();
                poseStack.translate(x, y + boundingBox.getYsize(), -boundingBox.getZsize() * 0.7f);
                poseStack.mulPose(Axis.XP.rotationDegrees(angle * 360 / (float) Math.PI));
                poseStack.scale(0.3f * scale, 0.3f * scale, 0.3f * scale);
                renderSoulItem(soulItem, attacker, poseStack, itemRenderer, multiBufferSource, light);
                poseStack.popPose();
            }
        }

        @OnlyIn(Dist.CLIENT)
        private static void renderSoulItem(SoulItem soulItem, LivingEntity attacker, PoseStack poseStack, ItemRenderer itemRenderer, MultiBufferSource multiBufferSource, int light) {
            int renderLight = SoulUtils.isEquipped(attacker, WizardSoul.class) ? LightTexture.FULL_BRIGHT : light;
            itemRenderer.renderStatic(
                    soulItem.getDefaultInstance(),
                    ItemDisplayContext.FIXED,
                    renderLight,
                    OverlayTexture.NO_OVERLAY,
                    poseStack,
                    multiBufferSource,
                    attacker.level(),
                    attacker.getId()
            );
        }

        @SubscribeEvent
        public static void EnemyAttribute(EntityTickEvent.Post event) {
            Entity entity = event.getEntity();
            if (entity instanceof Enemy && entity instanceof Mob mob && !mob.level().isClientSide()) {
                SoulAbilityData.SoulInfo soulInfo = mob.getData(AttachmentRegister.SoulAbilityData).getSoulInfo("soul");
                AttributeUtils.ConditionAttributeModifier(
                        mob,
                        Attributes.MAX_HEALTH,
                        ResourceLocation.fromNamespaceAndPath(Fargo_soul.MODID, "soul_add"),
                        soulInfo.getStacks() * 5,
                        AttributeModifier.Operation.ADD_VALUE,
                        soulInfo.isEnabled()
                );
                AttributeUtils.ConditionAttributeModifier(
                        mob,
                        Attributes.MAX_HEALTH,
                        ResourceLocation.fromNamespaceAndPath(Fargo_soul.MODID, "soul_total"),
                        soulInfo.getStacks(),
                        AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
                        soulInfo.isEnabled()
                );
            }
        }

        private static final List<SoulItem> soulItemList = new ArrayList<>();

        @SubscribeEvent
        public static void EnemyJoin(EntityJoinLevelEvent event) {
            Entity entity = event.getEntity();
            double chance = 0.05;
            if (entity instanceof Enemy && entity instanceof Mob mob && !mob.level().isClientSide()) {
                SoulAbilityData.SoulInfo soulInfo = mob.getData(AttachmentRegister.SoulAbilityData).getSoulInfo("first");
                if (!soulInfo.isEnabled()) {
                    soulInfo.setEnabled(true);
                    Optional<ICuriosItemHandler> curiosInventory = CuriosApi.getCuriosInventory(mob);
                    if (curiosInventory.isPresent()) {
                        if (soulItemList.isEmpty()) {
                            BuiltInRegistries.ITEM.stream().forEach(item -> {
                                if (item instanceof SoulItem soulItem && !soulItemList.contains(soulItem)) {
                                    soulItemList.add(soulItem);
                                }
                            });
                        } else {
                            IItemHandlerModifiable curios = curiosInventory.get().getEquippedCurios();
                            SoulAbilityData.SoulInfo info = mob.getData(AttachmentRegister.SoulAbilityData).getSoulInfo("soul");
                            List<SoulItem> list = new ArrayList<>();
                            for (int i = 0; i < 4; i++) {
                                SoulItem soulItem = soulItemList.get(SoulUtils.random.nextInt(soulItemList.size()));
                                if (mob.getRandom().nextDouble() < chance) {
                                    curios.setStackInSlot(i, soulItem.getDefaultInstance());
                                    list.add(soulItem);
                                }
                            }
                            List<SoulItem> allCurioItems = SoulUtils.getAllCurioItems(list);
                            if (!allCurioItems.isEmpty()) {
                                info.setEnabled(true);
                                info.setStacks(allCurioItems.size());
                            }
                        }
                    }
                }
            }
        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void Death(LivingDeathEvent event) {
            Entity entity = event.getEntity();
            if (entity instanceof Enemy && entity instanceof Mob mob && !mob.level().isClientSide()) {
                SoulAbilityData.SoulInfo info = mob.getData(AttachmentRegister.SoulAbilityData).getSoulInfo("soul");
                if (info.isEnabled()) {
                    Optional<ICuriosItemHandler> curiosInventory = CuriosApi.getCuriosInventory(mob);
                    curiosInventory.ifPresent(iCuriosItemHandler -> {
                        IItemHandlerModifiable equippedCurios = iCuriosItemHandler.getEquippedCurios();
                        for (int i = 0; i < 4; i++) {
                            if (!equippedCurios.getStackInSlot(i).isEmpty()) {
                                Item item = equippedCurios.getStackInSlot(i).getItem();
                                ItemStack instance;
                                if (event.getSource().getEntity() instanceof Player) {
                                    instance = SoulsRegister.SoulCoreItem.get().getDefaultInstance();
                                    if (RecipeParser.getRecipeFromItem(item) instanceof SoulComponent soulComponent) {
                                        instance.set(DataComponentsRegister.SoulData.get(), soulComponent);
                                    }
                                } else {
                                    instance = ItemStack.EMPTY;
                                }
                                equippedCurios.setStackInSlot(i, instance);
                            }
                        }
                    });
                }
            }
        }

        @SubscribeEvent
        public static void AddItemTagEvent(AddItemTagEvent event) {
            ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath("curios", "soul");
            SoulItem item = SoulsRegister.TerraSoul.get();
            List<SoulItem> soulItemList = SoulUtils.getAllCurioItems(item.getSoulItemList());
            soulItemList.add(item);
            List<ResourceLocation> resourceLocationList = new ArrayList<>();
            soulItemList.forEach(soulItem -> resourceLocationList.add(BuiltInRegistries.ITEM.getKey(soulItem)));
            event.add(resourceLocation, resourceLocationList);
        }

        @SubscribeEvent
        public static void CurioChangeEvent(CurioChangeEvent event) {
            SoulUtils.updateSoulList(event.getEntity());
        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void RegisterClientTooltipComponentFactoriesEvent(RegisterClientTooltipComponentFactoriesEvent event) {
            event.register(SoulTooltipComponent.class, soulTooltipComponent -> soulTooltipComponent);
        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void RenderTooltipHandler(RenderTooltipEvent.GatherComponents event) {
            if (event.getItemStack().getItem() instanceof SoulItem soulItem) {
                List<Either<FormattedText, TooltipComponent>> tooltipElements = event.getTooltipElements();
                int size = tooltipElements.size();
                tooltipElements.add(Math.min(size, 1), Either.right(new SoulTooltipComponent((soulItem.getSoulItemList().size() + 1) * 16, 16, 1.5f, soulItem)));
            }
        }

        private static final Map<Integer, List<Component>> attributeList = new HashMap<>();
        private static final Map<Integer, List<Component>> tooltipList = new HashMap<>();

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void Tooltip(ItemTooltipEvent event) {
            if (event.getItemStack().getItem() instanceof SoulItem soulItem) {
                List<Component> toolTip = event.getToolTip();
                boolean shiftDown = event.getFlags().hasShiftDown();
                toolTip.add(Component.translatable("key.shift.tooltip.1").withStyle(ChatFormatting.DARK_GRAY).append(Component.literal("Shift").withStyle(shiftDown ? ChatFormatting.WHITE : ChatFormatting.GRAY)).append(Component.translatable("key.shift.tooltip.2").withStyle(ChatFormatting.DARK_GRAY)));
                if (shiftDown) {
                    toolTip.addAll(addAttributeList(soulItem));
                } else {
                    toolTip.addAll(tooltipList.computeIfAbsent(soulItem.hashCode(), k -> CurioUtils.getComponent(soulItem, "tooltip")));
                }
            }
        }

        public static List<Component> addAttributeList(SoulItem soulItem) {
            return attributeList.computeIfAbsent(soulItem.hashCode(), k -> {
                List<SoulItem> soulItemList = CurioUtils.getAllCurioItems(soulItem.getSoulItemList());
                List<Component> list = new ArrayList<>(CurioUtils.getComponent(soulItem, "attribute"));
                soulItemList.forEach(item -> {
                    list.add(Component.empty());
                    list.addAll(CurioUtils.getComponent(item, "attribute"));
                });
                return list;
            });
        }

    }

}







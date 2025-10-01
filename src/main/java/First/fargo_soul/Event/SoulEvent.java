package First.fargo_soul.Event;


import First.fargo_soul.Attachment.Attachment.SoulAbilityData;
import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
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
import First.fargo_soul.Utils.KeyUtils;
import First.fargo_soul.Utils.SoulUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EventBusSubscriber(modid = Fargo_soul.MODID, bus = EventBusSubscriber.Bus.GAME)
public class SoulEvent {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void render(RenderLivingEvent.Post<?, ?> event) {
        LivingEntity attacker = event.getEntity();
        List<SoulItem> soulItemList = SoulUtils.getSoulItemList(attacker);
        if (soulItemList.isEmpty()) return;

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

        // 分层处理
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
        // 渲染中心层（泰拉之魂）
        for (SoulItem soulItem : centerSouls) {
            renderCenterSoul(soulItem, attacker, poseStack, scale, itemRenderer, multiBufferSource, light);
        }
        // 渲染内圈（有内部列表的物品）
        if (!innerCircleSouls.isEmpty()) {
            renderCircleSouls(innerCircleSouls, attacker, poseStack, scale, itemRenderer, multiBufferSource, light, ageInTicks, partialTick, 0.3f, 1);
        }
        // 渲染外圈（其余物品，分布在第二圈和第三圈）
        if (!outerCircleSouls.isEmpty()) {
            int half = (outerCircleSouls.size() + 1) / 2;
            List<SoulItem> secondCircle = outerCircleSouls.subList(0, half);
            List<SoulItem> thirdCircle = outerCircleSouls.subList(half, outerCircleSouls.size());
            if (!secondCircle.isEmpty()) {
                renderCircleSouls(secondCircle, attacker, poseStack, scale, itemRenderer, multiBufferSource, light, ageInTicks, partialTick, 0.6f, 2);
            }
            if (!thirdCircle.isEmpty()) {
                renderCircleSouls(thirdCircle, attacker, poseStack, scale, itemRenderer, multiBufferSource, light, ageInTicks, partialTick, 0.8f, 3);
            }
        }

        poseStack.popPose();
    }

    private static void renderCenterSoul(SoulItem soulItem, LivingEntity attacker, PoseStack poseStack, float scale,
                                         ItemRenderer itemRenderer, MultiBufferSource multiBufferSource, int light) {
        poseStack.pushPose();
        AABB boundingBox = attacker.getBoundingBox();
        poseStack.translate(0, boundingBox.getYsize(), -boundingBox.getZsize() * 0.7f);
        poseStack.scale(0.5f * scale, 0.5f * scale, 0.5f * scale);
        renderSoulItem(soulItem, attacker, poseStack, itemRenderer, multiBufferSource, light);
        poseStack.popPose();
    }

    private static void renderCircleSouls(List<SoulItem> soulItems, LivingEntity attacker, PoseStack poseStack, float scale,
                                          ItemRenderer itemRenderer, MultiBufferSource multiBufferSource, int light,
                                          float ageInTicks, float partialTick, float baseRadius, int circleLevel) {
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

    private static final List<Class<? extends SoulItem>> SprintList = Arrays.asList(
            CrystalAssassinSoul.class,
            PenetratingNinjaSoul.class,
            GreenSoul.class
    );

    @SubscribeEvent
    public static void MonsterSprint(EntityTickEvent.Post event) {
        if (event.getEntity() instanceof Monster monster && monster.getTarget() instanceof LivingEntity target) {
            if (SoulUtils.isEquippedAny(monster, SprintList) && monster.tickCount % 100 == 0) {
                monster.getLookControl().setLookAt(target);
                monster.addDeltaMovement(monster.getLookAngle());
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void Sprint(MovementInputUpdateEvent event) {
        if (event.getEntity() instanceof LocalPlayer player) {
            SoulAbilityData.SoulInfo soulInfo = player.getData(AttachmentRegister.SoulAbilityData).getClientSoulInfo("Sprint");
            soulInfo.maxCooldown = 40;
            if (soulInfo.cooldown == 0 && KeyUtils.isDoubleTappingForward(event.getInput()) && SoulUtils.isEquippedAny(player, SprintList)) {
                soulInfo.cooldown = soulInfo.maxCooldown;
                double factor = 1.5;
                if (SoulUtils.isEquipped(player, RedRidingSoul.class)) {
                    SoulAbilityData.SoulInfo info = player.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(RedRidingSoul.class);
                    soulInfo.maxStacks = SoulUtils.isEquipped(player, WillPower.class) ? 15 : 10;
                    if (info.stacks == info.maxStacks) {
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
            soulInfo.maxStacks = SoulUtils.isEquipped(player, GreenSoul.class) ? 100 : 60;
            if (soulInfo.stacks > 0 && event.getInput().jumping && SoulUtils.isEquippedAny(player, typeList)) {
                soulInfo.stacks--;
                Vec3 deltaMovement = player.getDeltaMovement();
                Vec3 newDeltaMovement = new Vec3(
                        deltaMovement.x(),
                        Math.min(deltaMovement.y() + 0.25, 0.5),
                        deltaMovement.z()
                );
                player.setDeltaMovement(newDeltaMovement);
            } else if (player.onGround()) {
                soulInfo.stacks = soulInfo.maxStacks;
            }
        }
    }

}

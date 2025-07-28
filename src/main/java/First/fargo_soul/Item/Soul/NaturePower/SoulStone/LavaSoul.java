package First.fargo_soul.Item.Soul.NaturePower.SoulStone;

import First.fargo_soul.Item.Soul.SoulItem;
import First.fargo_soul.Item.Soul.Souls;
import First.fargo_soul.Utils.CurioUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


public class LavaSoul extends SoulItem {
    public static final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    public LavaSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.translatable("item.fargo_soul.lava_soul.attribute.1").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.lava_soul.attribute.2").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.lava_soul.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
    );

    @Override
    public List<Component> getAttributeList() {
        return this.AttributeList;
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, Item.TooltipContext context, ItemStack stack) {
        tooltips.addAll(AttributeList);
        tooltips.addAll(TooltipList);
        return tooltips;
    }

    public static void LavaSoulTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.LavaSoul.get()) && player.tickCount % 20 == 0) {
            List<LivingEntity> livingEntityList = player.serverLevel().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(5));
            livingEntityList.removeIf(livingEntity -> CurioUtils.isEquipped(livingEntity, Souls.LavaSoul.get()));
            for (LivingEntity livingEntity : livingEntityList) {
                livingEntity.setRemainingFireTicks(Math.min(livingEntity.getRemainingFireTicks() + 40, 80));
            }
        }
    }

    public static void LavaSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            List<Player> playerList = livingEntity.level().getEntitiesOfClass(Player.class, livingEntity.getBoundingBox().inflate(5), player -> CurioUtils.isEquipped(player, Souls.LavaSoul.get()));
            if (!playerList.isEmpty()) {
                event.setAmount(event.getAmount() * (1 + (0.2f * playerList.size())));
            }
        }
    }

    public static void LavaSoulRenderHandler(SlotContext slotContext, PoseStack poseStack, MultiBufferSource buffer, float ageInTicks) {
        if (slotContext.entity() instanceof LocalPlayer player && CurioUtils.isEquipped(player, Souls.LavaSoul.get())) {
            ItemStack magmaBlock = Items.MAGMA_BLOCK.getDefaultInstance();
            Minecraft minecraft = Minecraft.getInstance();
            ItemRenderer itemRenderer = minecraft.getItemRenderer();
            float radius = 5.0f;
            int blockCount = 100;
            for (int i = 0; i < blockCount; i++) {
                poseStack.pushPose();
                float angle = ageInTicks * 0.05f + (float) (Math.PI * 2 / blockCount * i);
                float x = (float) (Math.cos(angle) * radius);
                float z = (float) (Math.sin(angle) * radius);
                poseStack.translate(x, 0, z);
                poseStack.mulPose(Axis.YP.rotationDegrees(angle * 180 / (float) Math.PI));
                poseStack.scale(0.1f, 0.1f, 0.1f);
                itemRenderer.renderStatic(
                        magmaBlock,
                        ItemDisplayContext.FIXED,
                        LightTexture.FULL_BRIGHT,
                        OverlayTexture.NO_OVERLAY,
                        poseStack,
                        buffer,
                        player.level(),
                        0
                );
                poseStack.popPose();
            }
        }
    }

}

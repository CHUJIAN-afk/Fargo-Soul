package First.fargo_soul.Item.Soul.TerraSoul.NaturePower.SoulStone;

import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.TerraSoul.NaturePower.NaturePower;
import First.fargo_soul.Utils.SoulUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;


public class LavaSoul extends SoulItem {

    public LavaSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.ORANGE));
    }

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void LavaSoulTickHandler(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                if (SoulUtils.isEquipped(attacker, LavaSoul.class) && attacker.tickCount % 5 == 0 && SoulUtils.getSoulTarget(attacker, 10) instanceof LivingEntity soulTarget) {
                    soulTarget.setRemainingFireTicks(Math.min(soulTarget.getRemainingFireTicks() + 10, 80));
                    if (SoulUtils.isEquipped(attacker, NaturePower.class) && attacker.getRandom().nextDouble() < 0.05) {
                        Level level = attacker.level();
                        Vec3 center = soulTarget.getBoundingBox().getCenter();
                        level.explode(
                                attacker,
                                center.x(),
                                center.y(),
                                center.z(),
                                1,
                                false,
                                Level.ExplosionInteraction.NONE
                        );
                    }
                }
            }
        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void render(RenderLivingEvent.Post<?, ?> event) {
            LivingEntity attacker = event.getEntity();
            if (SoulUtils.isEquipped(attacker, LavaSoul.class)) {
                MultiBufferSource buffer = event.getMultiBufferSource();
                PoseStack poseStack = event.getPoseStack();
                ItemStack magmaBlock = Items.MAGMA_BLOCK.getDefaultInstance();
                Minecraft minecraft = Minecraft.getInstance();
                ItemRenderer itemRenderer = minecraft.getItemRenderer();
                float ageInTicks = SoulUtils.getAgeInTicks(attacker, event.getPartialTick(), 1f);
                float radius = 5.0f;
                int blockCount = 400;
                for (int i = 0; i < blockCount; i++) {
                    poseStack.pushPose();
                    float angle = ageInTicks * 0.05f + (float) (Math.PI * 2 / blockCount * i);
                    float x = (float) (Math.cos(angle) * radius);
                    float z = (float) (Math.sin(angle) * radius);
                    poseStack.translate(x, attacker.getBoundingBox().getYsize() * 0.5, z);
                    poseStack.mulPose(Axis.YP.rotationDegrees(angle * 180 / (float) Math.PI));
                    poseStack.scale(0.1f, 0.1f, 0.1f);
                    itemRenderer.renderStatic(
                            magmaBlock,
                            ItemDisplayContext.FIXED,
                            LightTexture.FULL_BRIGHT,
                            OverlayTexture.NO_OVERLAY,
                            poseStack,
                            buffer,
                            attacker.level(),
                            0
                    );
                    poseStack.popPose();
                }
            }
        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        private static void onBlockOverlay(RenderBlockScreenEffectEvent event) {
            if (event.getOverlayType().equals(RenderBlockScreenEffectEvent.OverlayType.FIRE) && SoulUtils.isEquipped(event.getPlayer(), LavaSoul.class)) {
                event.setCanceled(true);
            }
        }

    }



}

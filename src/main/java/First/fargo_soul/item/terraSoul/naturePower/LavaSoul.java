package First.fargo_soul.item.terraSoul.naturePower;

import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.NaturePower;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.RenderUtils;
import First.fargo_soul.utils.SoulUtils;
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
import net.neoforged.neoforge.client.event.RenderLivingEvent;


public class LavaSoul extends SoulItem {

    public LavaSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void render(RenderLivingEvent.Post<?, ?> event) {
        LivingEntity attacker = event.getEntity();
        if (CurioUtils.isEquipped(attacker, LavaSoul.class)) {
            MultiBufferSource buffer = event.getMultiBufferSource();
            PoseStack poseStack = event.getPoseStack();
            ItemStack magmaBlock = Items.MAGMA_BLOCK.getDefaultInstance();
            Minecraft minecraft = Minecraft.getInstance();
            ItemRenderer itemRenderer = minecraft.getItemRenderer();
            float ageInTicks = RenderUtils.getAgeInTicks(attacker, event.getPartialTick(), 1f);
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

    @Override
    public void tick(LivingEntity ticker) {
        if (!ticker.level().isClientSide()) {
            if (CurioUtils.isEquipped(ticker, LavaSoul.class) && ticker.tickCount % 5 == 0 && SoulUtils.getSoulTarget(ticker, 10) instanceof LivingEntity soulTarget) {
                soulTarget.setRemainingFireTicks(Math.min(soulTarget.getRemainingFireTicks() + 10, 80));
                if (CurioUtils.isEquipped(ticker, NaturePower.class) && ticker.getRandom().nextDouble() < 0.05) {
                    Level level = ticker.level();
                    Vec3 center = soulTarget.getBoundingBox().getCenter();
                    level.explode(
                            ticker,
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

}

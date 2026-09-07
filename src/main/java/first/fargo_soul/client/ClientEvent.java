package first.fargo_soul.client;


import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.attachment.SoulInfoData;
import first.fargo_soul.common.attachment.SoulItemData;
import first.fargo_soul.common.item.base.ValueModifier;
import first.fargo_soul.common.soulInfo.FlySoulInfo;
import first.fargo_soul.common.soulInfo.SprintSoulInfo;
import first.fargo_soul.network.KeyHandlePacket;
import first.fargo_soul.network.SprintPacket;
import first.fargo_soul.register.FargoSoulAttachmentRegister;
import first.fargo_soul.register.FargoSoulSoulInfoRegister;
import first.fargo_soul.utils.KeyUtils;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = FargoSoul.MODID, value = Dist.CLIENT)
public class ClientEvent {

    @SubscribeEvent
    public static void onLevelLoad(InputEvent.Key event) {
        int key = event.getKey();
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player != null) {
            SoulItemData.forEach(player, soulItem -> {
                ResourceLocation keyed = soulItem.keyPressed(player, key);
                if (keyed != null) {
                    PacketDistributor.sendToServer(new KeyHandlePacket(keyed));
                }
            });
        }
    }

    @SubscribeEvent
    public static void movementInput(MovementInputUpdateEvent event) {
        Player player = event.getEntity();
        Input input = event.getInput();
        SoulItemData.forEach(player, soulItem -> soulItem.movement(player, input));
        SoulItemData soulItemData = player.getData(FargoSoulAttachmentRegister.SOUL_ITEM_DATA);

        if (KeyUtils.isDoubleTappingForward(event.getInput()) && soulItemData.isCanSprint()) {
            SprintSoulInfo info = SoulInfoData.getSoulInfo(player, FargoSoulSoulInfoRegister.SPRINT_SOUL_INFO);
            if (info == null) {
                info = new SprintSoulInfo();
                SoulInfoData.putSoulInfo(player, info);

                float scale = 1f;
                List<ValueModifier> modifiers = new ArrayList<>();
                SoulItemData.forEach(player, soulItem -> soulItem.sprintClient(player, modifiers));
                scale = ValueModifier.getModifierAfter(scale, modifiers);
                Vec3 lookAngle = player.getLookAngle();
                player.addDeltaMovement(new Vec3(lookAngle.x, lookAngle.y * 0.25f, lookAngle.z).scale(scale));
                PacketDistributor.sendToServer(new SprintPacket());
            }
        }

        if (soulItemData.getMaxFlyTime() > 0) {
            player.fallDistance = 0;
            if (event.getInput().jumping) {
                FlySoulInfo info = SoulInfoData.getSoulInfo(player, FargoSoulSoulInfoRegister.FLY_SOUL_INFO);
                if (info == null) {
                    info = new FlySoulInfo();
                    SoulInfoData.putSoulInfo(player, info);
                }
                Vec3 deltaMovement = player.getDeltaMovement();
                double addedX = 0;
                double addedY = 0;
                double addedZ = 0;
                if (info.flyTime < soulItemData.getMaxFlyTime()) {
                    info.flyTime++;
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
        }
    }

    @SubscribeEvent
    public static void renderFog(ViewportEvent.RenderFog event) {
        Camera camera = event.getCamera();
        Minecraft minecraft = Minecraft.getInstance();
        BlockPos blockPos = camera.getBlockPosition();
        Entity entity = camera.getEntity();
        if (!entity.isSpectator() && minecraft.level instanceof Level level && minecraft.player instanceof LocalPlayer player) {
            SoulItemData soulItemData = player.getData(FargoSoulAttachmentRegister.SOUL_ITEM_DATA);
            FluidState fluidState = level.getFluidState(blockPos);
            Fluid fluid = fluidState.getType();
            if (!soulItemData.isRenderLavaFog() && fluid.isSame(Fluids.LAVA) && (camera.getPosition().y < blockPos.getY() + fluidState.getHeight(level, blockPos))) {
                event.setNearPlaneDistance(-4.0f);
                event.setFarPlaneDistance(20.0f);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    private static void onBlockOverlay(RenderBlockScreenEffectEvent event) {
        if(event.getOverlayType()==RenderBlockScreenEffectEvent.OverlayType.FIRE) {
            SoulItemData soulItemData = event.getPlayer().getData(FargoSoulAttachmentRegister.SOUL_ITEM_DATA);
            if (!soulItemData.isRenderLavaFog()) {
                event.setCanceled(true);
            }
        }
    }
}

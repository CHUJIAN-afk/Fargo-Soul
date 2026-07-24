package first.fargo_soul.api.item;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.attachment.ISoulItemCache;
import first.fargo_soul.network.KeyHandlePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

@EventBusSubscriber(modid = FargoSoul.MODID, value = Dist.CLIENT)
public class ISoulItemClientEventHandler {

    @SubscribeEvent
    public static void soulMovementInputUpdate(MovementInputUpdateEvent event) {
        Player entity = event.getEntity();
        List<ISoulItem> list = ISoulItemCache.getList(entity);
        for (ISoulItem iSoulItem : list) {
            iSoulItem.movementInput(event);
        }
    }

    @SubscribeEvent
    public static void soulKeyPressed(InputEvent.Key event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.getConnection() != null && minecraft.player instanceof Player player) {
            List<ISoulItem> list = ISoulItemCache.getList(player);
            for (ISoulItem iSoulItem : list) {
                ResourceLocation key = iSoulItem.keyPressed(player, event.getKey());
                if (key != null) {
                    PacketDistributor.sendToServer(new KeyHandlePacket(key));
                }
            }
        }
    }
}

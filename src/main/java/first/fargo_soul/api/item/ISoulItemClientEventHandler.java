package first.fargo_soul.api.item;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.attachment.ISoulItemCache;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;

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
}

package First.fargo_soul.Compact.Create.Event;


import First.fargo_soul.Compact.Create.CreatePower.SoulStone.BurnerSoul;
import First.fargo_soul.Compact.Create.CreatePower.SoulStone.DeepDivingSoul;
import First.fargo_soul.Compact.Create.CreatePower.SoulStone.GogglesSoul;
import First.fargo_soul.Fargo_soul;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class CreateEvent {

    @EventBusSubscriber(modid = Fargo_soul.MODID, bus = EventBusSubscriber.Bus.GAME)
    public static class CreateGameEvent {

        @SubscribeEvent
        public static void RightClickBlockEvent(PlayerInteractEvent.RightClickBlock event) {
            BurnerSoul.BurnerSoulRightClickBlockHandler(event);
        }

        @SubscribeEvent
        public static void RightClickBlockEvent(PlayerTickEvent.Post event) {
            GogglesSoul.GogglesSoulTickHandler(event);
        }

    }


    @EventBusSubscriber(modid = Fargo_soul.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
    public static class CreateClientTEvent {

        @SubscribeEvent
        public static void RenderFog(ViewportEvent.RenderFog event) {
            DeepDivingSoul.DeepDivingSoulRenderFogHandler(event);
        }

    }


}

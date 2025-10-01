package First.fargo_soul.Client;


import First.fargo_soul.Client.Renderer.BoneRenderer;
import First.fargo_soul.Client.Renderer.NeedleRenderer;
import First.fargo_soul.Client.Tooltip.SoulTooltipComponent;
import First.fargo_soul.Fargo_soul;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;

import static First.fargo_soul.Entity.EntityRegister.Bone;
import static First.fargo_soul.Entity.EntityRegister.Needle;

public class ClientEvent {

    @EventBusSubscriber(modid = Fargo_soul.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvent {
        @SubscribeEvent
        public static void FMLClientSetupEvent(FMLClientSetupEvent event) {
            EntityRenderers.register(Needle.get(), NeedleRenderer::new);
            EntityRenderers.register(Bone.get(), BoneRenderer::new);
        }

        @SubscribeEvent
        public static void RegisterClientTooltipComponentFactoriesEvent(RegisterClientTooltipComponentFactoriesEvent event) {
            event.register(SoulTooltipComponent.class, soulTooltipComponent -> soulTooltipComponent);
        }

    }

}

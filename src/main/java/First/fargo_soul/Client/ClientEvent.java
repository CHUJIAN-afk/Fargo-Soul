package First.fargo_soul.Client;


import First.fargo_soul.Client.Renderer.*;
import First.fargo_soul.Client.Tooltip.SoulTooltipComponent;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Item.Soul.TerraSoul.CosmicPower.SoulStone.MeteorSoul;
import First.fargo_soul.Item.Soul.TerraSoul.CosmicPower.SoulStone.StardustSoul;
import First.fargo_soul.Item.Soul.TerraSoul.CosmicPower.SoulStone.VortexSoul;
import First.fargo_soul.Item.Soul.TerraSoul.DeathPower.SoulStone.CrystalAssassinSoul;
import First.fargo_soul.Item.Soul.TerraSoul.DeathPower.SoulStone.PenetratingNinjaSoulStone.MonkSoul;
import First.fargo_soul.Item.Soul.TerraSoul.LifePower.SoulStone.BeeSoul;
import First.fargo_soul.Item.Soul.TerraSoul.NaturePower.SoulStone.GreenSoul;
import First.fargo_soul.Item.Soul.TerraSoul.WillPower.Soulstone.GoldSoul;
import First.fargo_soul.Item.Soul.UniverseSoul.SharpshooterSoul.Soul.ScoutScope;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import static First.fargo_soul.Entity.EntityRegister.*;

public class ClientEvent {

    @EventBusSubscriber(modid = Fargo_soul.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvent {
        @SubscribeEvent
        public static void FMLClientSetupEvent(FMLClientSetupEvent event) {
            //魂石总渲染
            SoulsRegister.SoulItems.getRegistry().get().forEach(item -> CuriosRendererRegistry.register(item, PlayerRenderer::new));
            //弹射物渲染
            EntityRenderers.register(IceSpike.get(), IceSpikeRenderer::new);
            EntityRenderers.register(Needle.get(), NeedleRenderer::new);
            EntityRenderers.register(Ghost.get(), GhostRenderer::new);
            EntityRenderers.register(Bone.get(), BoneRenderer::new);
            EntityRenderers.register(Spear.get(), SpearRenderer::new);
            EntityRenderers.register(Banner.get(), BannerRenderer::new);
            EntityRenderers.register(NebulaEmpoweredFlame.get(), NebulaEmpoweredFlameRenderer::new);
        }

        @SubscribeEvent
        public static void RegisterClientTooltipComponentFactoriesEvent(RegisterClientTooltipComponentFactoriesEvent event) {
            event.register(SoulTooltipComponent.class, soulTooltipComponent -> soulTooltipComponent);
        }




    }


}

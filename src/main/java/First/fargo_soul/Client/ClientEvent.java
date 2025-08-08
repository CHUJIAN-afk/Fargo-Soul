package First.fargo_soul.Client;


import First.fargo_soul.Client.Renderer.*;
import First.fargo_soul.Client.Tooltip.SoulTooltipComponent;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Item.Soul.TerraSoul.CosmicPower.SoulStone.MeteorSoul;
import First.fargo_soul.Item.Soul.TerraSoul.DeathPower.SoulStone.CrystalAssassinSoul;
import First.fargo_soul.Item.Soul.TerraSoul.DeathPower.SoulStone.PenetratingNinjaSoulStone.MonkSoul;
import First.fargo_soul.Item.Soul.TerraSoul.LifePower.SoulStone.BeeSoul;
import First.fargo_soul.Item.Soul.TerraSoul.NaturePower.SoulStone.GreenSoul;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import static First.fargo_soul.Entity.EntityRegister.*;

public class ClientEvent {

    @EventBusSubscriber(modid = Fargo_soul.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
    public static class ClientGameEvent {

        @SubscribeEvent
        public static void MovementInputEvent(MovementInputUpdateEvent event) {
            //自然之力
            GreenSoul.GreenSoulMovementInputHandler(event);
            BeeSoul.BeeSoulMovementInputHandler(event);
            //死亡之力
            CrystalAssassinSoul.CrystalAssassinSoulMovementInputHandler(event);
            MonkSoul.MonkSoulMovementInputHandler(event);
            //宇宙之力
            MeteorSoul.MeteorSoulMovementInputHandler(event);
        }

        @SubscribeEvent
        public static void RenderTooltipEvent(RenderTooltipEvent.GatherComponents event) {
            //渲染物品
            SoulItem.RenderTooltipEvent(event);
        }
        @SubscribeEvent
        public static void ItemTooltipEvent(ItemTooltipEvent event){
            //渲染文本
            SoulItem.ItemTooltipEvent(event);
        }

    }


    @EventBusSubscriber(modid = Fargo_soul.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvent {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
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
        public static void registerTooltipComponentFactories(RegisterClientTooltipComponentFactoriesEvent event) {
            event.register(SoulTooltipComponent.class, soulTooltipComponent -> soulTooltipComponent);
        }
    }


}

package First.fargo_soul.Event;


import First.fargo_soul.Curios.Soul.EarthPower.SoulStone.*;
import First.fargo_soul.Curios.Soul.EarthPower.SoulStone.CobaltSoulStone.AncientCobaltSoul;
import First.fargo_soul.Curios.Soul.ForestPower.SoulStone.*;
import First.fargo_soul.Curios.Soul.TerraPower.SoulStone.*;
import First.fargo_soul.Curios.Soul.TerraPower.SoulStone.ObsidianSoulStone.AshWoodSoul;
import First.fargo_soul.Fargo_soul;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;


@EventBusSubscriber(modid = Fargo_soul.MODID, bus = EventBusSubscriber.Bus.GAME)
public class SoulEvent {

    @SubscribeEvent
    public static void DamageEvent(LivingIncomingDamageEvent event) {
        event.getEntity().invulnerableTime = 0;
        //森林之力
        EbonyWoodSoul.EbonyWoodDamageHandler(event);
        EbonyWoodSoul.EbonyWoodDamageHandler2(event);
        PalmWoodSoul.PalmWoodDamageHandler(event);
        PearlWoodSoul.PearlWoodDamageHandler(event);
        RoseWoodSoul.RosewoodSoulDamageHandler(event);
        ShadowWoodSoul.ShadowWoodSoulDamageHandler(event);
        //泰拉之力
        AshWoodSoul.AshWoodSoulDamageHandler(event);
        CopperSoul.CopperSoulDamageHandler(event);
        IronSoul.IronSoulDamageHandler2(event);
        LeadSoul.LeadSoulDamageHandler(event);
        LeadSoul.LeadSoulDamageHandler2(event);
        ObsidianSoul.ObsidianSoulDamageHandler(event);
        SilverSoul.SilverSoulDamageHandler(event);
        SilverSoul.SilverSoulDamageHandler2(event);
        TinSoul.TinSoulLivingDamageHandler(event);
        TinSoul.TinSoulLivingDamageHandler2(event);
        TungstenSoul.TungstenSoulDamageHandler(event);
        //大地之力
        AncientCobaltSoul.AncientCobaltSoulDamageHnadler(event);
        PalladiumSoul.PalladiumSoulDamageHandler(event);
        MithrilSoul.MithrilSoulDamageHandler(event);
        OrichalcumSoul.OrichalcumSoulDamageHandler(event);
        AdamantiteSoul.AdamantiteSoulDamageHandler(event);
        TitaniumSoul.TitaniumSoulDamageHandler2(event);
    }

    @SubscribeEvent
    public static void PlayerTickEvent(PlayerTickEvent.Post event) {
        //森林之力
        EbonyWoodSoul.EbonyWoodTickHandler(event);
        PineWoodSoul.PineWoodSoulTickHandler(event);
        ShadowWoodSoul.ShadowWoodSoulTickHandler(event);
        //泰拉之力
        AshWoodSoul.AshWoodSoulTickHandler(event);
        IronSoul.IronSoulTickHandler(event);
        SilverSoul.SilverSoulTickHandler(event);
        TungstenSoul.TungstenSoulTickHandler(event);
        //大地之力
        AncientCobaltSoul.AncientCobaltSoulTickHandler(event);
        AdamantiteSoul.AdamantiteSoulTickHandler(event);
        TitaniumSoul.TitaniumSoulTickHandler(event);
    }

    @SubscribeEvent
    public static void ItemEntityPickupEvent(ItemEntityPickupEvent.Post event){
        //泰拉之力
        IronSoul.IronSoulPickupHandler(event);
    }

    @SubscribeEvent
    public static void LivingJumpEvent(LivingEvent.LivingJumpEvent event){
        //大地之力
        AncientCobaltSoul.AncientCobaltSoulJumpHandler(event);
        CobaltSoul.CobaltSoulJumpHandler(event);
    }

    @SubscribeEvent
    public static void LivingHealEvent(LivingHealEvent event){
        //大地之力
        PalladiumSoul.PalladiumSoulHealHandler(event);
    }



}

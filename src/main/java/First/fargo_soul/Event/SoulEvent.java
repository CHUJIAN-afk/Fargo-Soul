package First.fargo_soul.Event;


import First.fargo_soul.Attribute.AttributeRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.EarthPower.SoulStone.*;
import First.fargo_soul.Item.Soul.EarthPower.SoulStone.CobaltSoulStone.AncientCobaltSoul;
import First.fargo_soul.Item.Soul.ForestPower.SoulStone.*;
import First.fargo_soul.Item.Soul.LifePower.SoulStone.*;
import First.fargo_soul.Item.Soul.LifePower.SoulStone.TurtleSoulStone.CactusSoul;
import First.fargo_soul.Item.Soul.NaturePower.SoulStone.*;
import First.fargo_soul.Item.Soul.SoulItem;
import First.fargo_soul.Item.Soul.TerraPower.SoulStone.*;
import First.fargo_soul.Item.Soul.TerraPower.SoulStone.ObsidianSoulStone.AshWoodSoul;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = Fargo_soul.MODID, bus = EventBusSubscriber.Bus.GAME)
public class SoulEvent {

    @SubscribeEvent
    public static void DamageEvent(LivingIncomingDamageEvent event) {
        //去除非玩家实体的，无受击武器时的无敌帧
        SoulItem.invulnerableTimeHandler(event);
        //关于暴击率等属性处理
        AttributeRegister.CriticalHandler(event);
        AttributeRegister.DamageHandler(event);
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
        //自然之力
        CrimsonSoul.CrimsonSoulDamageHandler2(event);
        MushroomSoul.MushroomSoulDamageHandler(event);
        RainCloudSoul.RainCloudSoulDamageHandler2(event);
        //生命之力
        BeeSoul.BeeSoulDamageHandler2(event);
        BeetleSoul.BeetleSoulDamageHandler(event);
        BeetleSoul.BeetleSoulDamageHandler2(event);
        SpiderSoul.SpiderSoulDamageHandler(event);
        CactusSoul.CactusSoulDamageHandler2(event);
        TurtleSoul.TurtleSoulDamageHandler2(event);
    }

    @SubscribeEvent
    public static void LivingDeathEvent(LivingDeathEvent event){
        //生命之力
        CactusSoul.CactusSoulDeathHandler(event);
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
        //自然之力
        FrostSoul.FrostSoulTickHandler(event);
        GreenSoul.GreenSoulTickHandler(event);
        GreenSoul.GreenSoulTickHandler2(event);
        LavaSoul.LavaSoulTickHandler(event);
        RainCloudSoul.RainCloudSoulTickHnadler(event);
        //生命之力
        BeeSoul.BeeSoulTickHandler1(event);
        BeeSoul.BeeSoulTickHandler2(event);
        BeeSoul.BeeSoulTickHandler3(event);
        PumpkinSoul.PumpkinSoulTickHandler1(event);
        PumpkinSoul.PumpkinSoulTickHandler2(event);
        SpiderSoul.SpiderSoulTickHnadler(event);
        TurtleSoul.TurtleSoulTickHnadler(event);
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

    @SubscribeEvent
    public static void MovementInputEvent(MovementInputUpdateEvent event){
        //自然之力
        GreenSoul.GreenSoulMovementInputHandler(event);
        BeeSoul.BeeSoulMovementInputHandler(event);
    }

    @SubscribeEvent
    public static void UseItemFinishEvent(LivingEntityUseItemEvent.Finish event){
        //自然之力
        MushroomSoul.MushroomSoulUseItemFinishHandler(event);
    }
    @SubscribeEvent
    public static void ChangeTargetEvent(LivingChangeTargetEvent event){
        //生命之力
        BeeSoul.BeeSoulChangeTargetHandler(event);
    }

}

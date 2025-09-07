package First.fargo_soul.Event;


import First.fargo_soul.Attribute.AttributeRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Item.Soul.TerraSoul.CosmicPower.SoulStone.BlazeSoul;
import First.fargo_soul.Item.Soul.TerraSoul.CosmicPower.SoulStone.MeteorSoul;
import First.fargo_soul.Item.Soul.TerraSoul.CosmicPower.SoulStone.NebulaSoul;
import First.fargo_soul.Item.Soul.TerraSoul.DeathPower.SoulStone.*;
import First.fargo_soul.Item.Soul.TerraSoul.DeathPower.SoulStone.PenetratingNinjaSoulStone.MonkSoul;
import First.fargo_soul.Item.Soul.TerraSoul.EarthPower.SoulStone.*;
import First.fargo_soul.Item.Soul.TerraSoul.EarthPower.SoulStone.CobaltSoulStone.AncientCobaltSoul;
import First.fargo_soul.Item.Soul.TerraSoul.ForestPower.SoulStone.*;
import First.fargo_soul.Item.Soul.TerraSoul.LifePower.SoulStone.*;
import First.fargo_soul.Item.Soul.TerraSoul.LifePower.SoulStone.TurtleSoulStone.CactusSoul;
import First.fargo_soul.Item.Soul.TerraSoul.NaturePower.SoulStone.*;
import First.fargo_soul.Item.Soul.TerraSoul.SpiritPower.SoulStone.ForbiddenSoul;
import First.fargo_soul.Item.Soul.TerraSoul.SpiritPower.SoulStone.GhostSoul;
import First.fargo_soul.Item.Soul.TerraSoul.SpiritPower.SoulStone.HolySoul;
import First.fargo_soul.Item.Soul.TerraSoul.SpiritPower.SoulStone.TekeSoul;
import First.fargo_soul.Item.Soul.TerraSoul.TerraPower.SoulStone.*;
import First.fargo_soul.Item.Soul.TerraSoul.TerraPower.SoulStone.ObsidianSoulStone.AshWoodSoul;
import First.fargo_soul.Item.Soul.TerraSoul.WillPower.Soulstone.*;
import First.fargo_soul.Item.Soul.UniverseSoul.BerserkerSoul.BerserkerSoul;
import First.fargo_soul.Item.Soul.UniverseSoul.BerserkerSoul.Soul.*;
import First.fargo_soul.Item.Soul.UniverseSoul.SharpshooterSoul.SharpshooterSoul;
import First.fargo_soul.Item.Soul.UniverseSoul.SharpshooterSoul.Soul.MarksmanEssence;
import First.fargo_soul.Item.Soul.UniverseSoul.SharpshooterSoul.Soul.MeltRocketBag;
import First.fargo_soul.Item.Soul.UniverseSoul.SharpshooterSoul.Soul.ScoutScope;
import First.fargo_soul.Item.Soul.UniverseSoul.SharpshooterSoul.Soul.StalkerQuiver;
import First.fargo_soul.Item.Soul.UniverseSoul.UniverseSoul;
import First.fargo_soul.Utils.CustomUtils;
import net.minecraft.world.level.storage.loot.LootPool;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

@EventBusSubscriber(modid = Fargo_soul.MODID, bus = EventBusSubscriber.Bus.GAME)
public class SoulEvent {

    @SubscribeEvent
    public static void CurioChangeEvent(CurioChangeEvent event) {
        //魂石数据缓存更新
        SoulItem.CurioChangeHandler(event);
    }


    @SubscribeEvent
    public static void EntityJoinLevelEvent(EntityJoinLevelEvent event){
        //属性处理
        AttributeRegister.RangedSpeedHandler(event);
    }

    @SubscribeEvent
    public static void LivingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        //无敌帧
        SoulItem.invulnerableTimeHandler(event);
        //属性处理
        AttributeRegister.CriticalHandler(event);
        AttributeRegister.DamageAndRangedDamageHandler(event);
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
        LavaSoul.LavaSoulDamageHandler(event);
        MushroomSoul.MushroomSoulDamageHandler(event);
        RainCloudSoul.RainCloudSoulDamageHandler2(event);
        //生命之力
        BeeSoul.BeeSoulDamageHandler2(event);
        BeetleSoul.BeetleSoulDamageHandler(event);
        BeetleSoul.BeetleSoulDamageHandler2(event);
        SpiderSoul.SpiderSoulDamageHandler(event);
        CactusSoul.CactusSoulDamageHandler2(event);
        TurtleSoul.TurtleSoulDamageHandler2(event);
        //心灵之力
        GhostSoul.GhostSoulDamageHnadler(event);
        //死亡之力
        AncientShadowSoul.AncientShadowSoulDamageHandler(event);
        CrystalAssassinSoul.CrystalAssassinSoulDamageHandler(event);
        DarkArtistSoul.DarkArtistSoulDamageHandler(event);
        GloomySoul.GloomySoulDamageHandler(event);
        NecromancerSoul.NecromancerSoulDamageHandler(event);
        //意志之力
        GladiatorSoul.GladiatorSoulDamageHandler(event);
        GladiatorSoul.GladiatorSoulDamageHandler2(event);
        GoldSoul.GoldSoulDamageHandler(event);
        RedRidingSoul.RedRidingSoulDamageHandler(event);
        ValhallaKnightSoul.ValhallaKnightSoulDamageHandler2(event);
        //宇宙之力
        BlazeSoul.BlazeSoulDamageHandler(event);
        BlazeSoul.BlazeSoulDamageHandler2(event);
        MeteorSoul.MeteorSoulDamageHandler(event);
        NebulaSoul.NebulaSoulDamageHandler(event);
        NebulaSoul.NebulaSoulDamageHandler2(event);
        //狂战士之魂
        FireGloves.FireGlovesDamageHandler(event);
    }

    @SubscribeEvent
    public static void LivingShieldBlockEvent(LivingShieldBlockEvent event){
        //泰拉之力
        SilverSoul.SilverSoulDamageHandler2(event);
    }


    @SubscribeEvent
    public static void LivingDamageEvent(LivingDamageEvent.Post event) {
        //生命之力
        CactusSoul.CactusSoulDamageHandler2(event);
        //狂战士之魂
        StingerNecklace.StingerNecklaceDamageHandler2(event);
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
        //心灵之力
        ForbiddenSoul.ForbiddenSoulTickHandler(event);
        GhostSoul.GhostSoulTickHnadler(event);
        TekeSoul.TekeSoulTickHandler(event);
        //死亡之力
        NinjaSoul.NinjaSoulTickHandler(event);
        MonkSoul.MonkSoulMovementTickHandler(event);
        //意志之力
        GladiatorSoul.GladiatorSoulTickHandler(event);
        ValhallaKnightSoul.ValhallaKnightSoulTickHandler(event);
        //宇宙之力
        NebulaSoul.NebulaSoulTickHandler(event);
        MeteorSoul.MeteorSoulTickHandler(event);
        //狂战士之魂
        BarbarianEssence.BarbarianEssenceTickHandler(event);
        BerserkerGloves.BerserkerGlovesTickHandler(event);
        CelestialShell.CelestialShellTickHandler(event);
        FireGloves.FireGlovesTickHandler(event);
        BerserkerSoul.BerserkerSoulTickHandler(event);
        //神枪手之魂
        MarksmanEssence.MarksmanEssenceTickHandler(event);
        MeltRocketBag.MeltRocketBagTickHandler(event);
        ScoutScope.ScoutScopeTickHandler(event);
        SharpshooterSoul.SharpshooterSoulTickHandler(event);
        StalkerQuiver.StalkerQuiverTickHandler(event);
        //寰宇之魂
        UniverseSoul.UniverseSoulTickHandler(event);
    }

    @SubscribeEvent
    public static void LivingDeathEvent(LivingDeathEvent event) {
        //生命之力
        CactusSoul.CactusSoulDeathHandler(event);
        //心灵之力
        GhostSoul.GhostSoulDeathHnadler(event);
        //死亡之力
        NecromancerSoul.NecromancerSoulDeathHandler(event);
        //意志之力
        GoldSoul.GoldSoulDeathHandler(event);
    }

    @SubscribeEvent
    public static void ItemEntityPickupEvent(ItemEntityPickupEvent.Post event){
        //泰拉之力
        IronSoul.IronSoulPickupHandler(event);
        //死亡之力
        NecromancerSoul.NecromancerSoulPickupHandler(event);
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
        //心灵之力
        HolySoul.HolySoulHealHandler(event);
        //意志之力
        ValhallaKnightSoul.ValhallaKnightSoulHealHandler(event);
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
        //死亡之力
        AncientShadowSoul.AdamantiteSoulChangeTargetHandler(event);
        NinjaSoul.NinjaSoulChangeTargetHandler(event);
        //神枪手之魂
        ScoutScope.ScoutScopeTargetChangeHandler(event);
        StalkerQuiver.StalkerQuiverTargetChangeHandler(event);
    }

    @SubscribeEvent
    public static void EntityInteractEvent(PlayerInteractEvent.EntityInteract event){
        //心灵之力
        TekeSoul.TekeSoulEntityInteractHandler(event);
    }

    @SubscribeEvent
    public static void LivingDropsEvent(LivingDropsEvent event) {
        //意志之力
        PlatinumSoul.PlatinumSoulDropsEvent(event);
    }

    @SubscribeEvent
    public static void MobEffectExpired(MobEffectEvent.Expired event) {
        //宇宙之力
        BlazeSoul.BlazeSoulMobEffectExpiredHandler(event);
        CrimsonSoul.CrimsonSoulMobEffectExpiredHandler(event);
    }

    @SubscribeEvent
    public static void LivingBreatheEvent(LivingBreatheEvent event) {
        CelestialShell.CelestialShellBreathHandler(event);
    }

    @SubscribeEvent
    public static void LootTableLoadEvent(LootTableLoadEvent event) {


        //添加战利品表
        CustomUtils.AddLootTable(event, SoulsRegister.SoulItems.getRegistry().get(), LootPool.lootPool().name(Fargo_soul.MODID));




    }

    @SubscribeEvent
    public static void ProjectileImpactEvent(ProjectileImpactEvent event){
        //神枪手之魂
        MeltRocketBag.MeltRocketBagProjectileImpactHandler(event);
    }




}

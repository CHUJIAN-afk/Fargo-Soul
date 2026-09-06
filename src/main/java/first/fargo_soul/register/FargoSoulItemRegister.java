package first.fargo_soul.register;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.dataComponents.SoulRarity;
import first.fargo_soul.common.item.TerraSoul;
import first.fargo_soul.common.item.terraSoul.*;
import first.fargo_soul.common.item.terraSoul.cosmicPower.*;
import first.fargo_soul.common.item.terraSoul.deathPower.*;
import first.fargo_soul.common.item.terraSoul.earthPower.*;
import first.fargo_soul.common.item.terraSoul.forestPower.*;
import first.fargo_soul.common.item.terraSoul.lifePower.*;
import first.fargo_soul.common.item.terraSoul.naturePower.*;
import first.fargo_soul.common.item.terraSoul.spiritPower.*;
import first.fargo_soul.common.item.terraSoul.terraPower.*;
import first.fargo_soul.common.item.terraSoul.willPower.*;
import first.lyra.register.LyraItemRegisterBuilder;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

@SuppressWarnings("SpellCheckingInspection")
public class FargoSoulItemRegister {

    private static final DeferredRegister.Items Register = DeferredRegister.createItems(FargoSoul.MODID);

    public static final DeferredItem<BlockItem> CosmicCrucibleBlockItem = LyraItemRegisterBuilder.build(Register, "cosmic_crucible", () -> new BlockItem(FargoSoulBlockRegister.CosmicCrucible.get(), new Item.Properties().stacksTo(1)))
            .blockLanguage("Cosmic Crucible", "宇宙坩埚")
            .itemTag(FargoSoulItemTagsRegister.SectionBasic)
            .itemModel((location, provider) -> provider.simpleBlockItem(location))
            .build();

    public static final DeferredItem<Item> Soul = LyraItemRegisterBuilder.build(Register, "soul")
            .itemLanguage("Incorporeal Soul", "无形之魂")
            .itemTag(FargoSoulItemTagsRegister.SectionBasic)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .build();

    public static final DeferredItem<TerraSoul> TerraSoulItem = register("terra_soul", TerraSoul::new)
            .itemLanguage("Terra Soul", "泰拉之魂")
            .itemLanguageTooltip(1, " ", "获得一个可以发射各种弹幕的光环")
            .itemLanguageTooltip(2, " ", "拾取物品时获得额外暴击率，村民可提供更多折扣")
            .itemLanguageTooltip(3, " ", "暴击和格挡会发射强大的闪电，减少受到的减益伤害，武器变大")
            .itemLanguageTooltip(4, " ", "攻击时增加暴击率，增加物品吸取范围")
            .itemLanguageTooltip(5, " ", "不攻击时获得防御增益，攻击时获得攻击增益")
            .itemLanguageTooltip(6, " ", "持有者拥有吸取生命的光环，增加受到的伤害，向附近的敌人发射叶绿水晶")
            .itemLanguageTooltip(7, " ", "蘑菇治疗得到强化并提供增益，持有者的攻击造成多种减益")
            .itemLanguageTooltip(8, " ", "持有者反射接触伤害，持有者的随从可以暴击")
            .itemLanguageTooltip(9, " ", "根据持有者移速增加射弹各种属性")
            .itemLanguageTooltip(10, " ", "持有者可召唤禁戒风暴，治疗会恢复更多，生命值降低至0时复活并净化减益")
            .itemLanguageTooltip(11, " ", "持有者每4秒获得一次无敌的暗影冲刺，根据移速增加射弹各种属性，屠戮敌人可能会掉落骨块")
            .itemLanguageTooltip(12, " ", "允许吸取灵魂，增加持有者的治疗和坐骑属性，连续攻击会获得额外伤害")
            .itemLanguageTooltip(13, " ", "持有者击败的敌人可能会掉落额外的战利品")
            .itemLanguageTooltip(14, " ", "持有者攻击偶尔会向敌人发射月亮并爆炸产生月亮强化焰")
            .itemLanguageTooltip(15, " ", "增加持有者的移动速度 and 加速度并降低持有者的动量，星尘静滞冷却时间降低至60秒")
            .itemLanguageTooltip(-1, " ", "“泰拉之主，天地共证”")
            .itemTag(FargoSoulItemTagsRegister.SectionTerraSoul)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    // ============================================================
    // 宇宙之力 · 魔石分段
    // ============================================================
    public static final DeferredItem<CosmicPower> CosmicPowerItem =
            register("cosmic_power", CosmicPower::new, SoulRarity.Purple.color())
                    .itemLanguage("Cosmic Power", "宇宙之力")
                    .itemLanguageTooltip(1, " ", "每秒从世界中汲取1点宇宙能量，最多积累100点宇宙能量")
                    .itemLanguageTooltip(2, " ", "根据积累比例，造成的伤害最多提升80%，受到的伤害最多减少20%")
                    .itemLanguageTooltip(3, " ", "受到伤害时，如果伤害超过你最大生命值的50%，则尝试消耗50宇宙能量使伤害减少50%")
                    .itemLanguageTooltip(4, " ", "死亡时释放超新星爆炸，对周围敌人造成你最大生命值360%的魔法伤害")
                    .itemLanguageTooltip(-1, " ", "“自宇宙大爆炸以来就一直存在”")
                    .itemTag(FargoSoulItemTagsRegister.SectionPower)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<BlazeSoul> BlazeSoulItem =
            register("blaze_soul", BlazeSoul::new, 0xFF691F)
                    .itemLanguage("Blaze Enchantment", "耀斑魔石")
                    .itemLanguageTooltip(1, " ", "攻击时积累相当于15%伤害值的日耀能量，最多积累1000点日耀能量，每秒消耗1点日耀能量恢复你1%最大生命值")
                    .itemLanguageTooltip(2, " ", "日耀能量积累比例会产生日曜护盾，使你受到的伤害最多减少40%")
                    .itemLanguageTooltip(3, " ", "日耀能量超过300点时，赋予你日耀冲刺，穿过敌人时造成日耀喷发，对敌人造成你12%最大生命值的火焰伤害")
                    .itemLanguageTooltip(4, " ", "日耀能量超过600点时，冲刺初速度提升100%，日耀喷发的伤害提升120%")
                    .itemLanguageTooltip(5, " ", "日耀能量超过900点时，附近的敌人每秒会受到你4%最大生命值的火焰伤害并被点燃")
                    .itemLanguageTooltip(6, " ", "日耀能量大于900时，按下“日耀”键会释放日耀之力，每秒额外消耗20点日耀能量，日耀能量耗尽时会失去日耀能量")
                    .itemLanguageTooltip(7, " ", "日耀之力期间，造成伤害时消耗50日耀能量使伤害提升375%")
                    .itemLanguageTooltip(8, " ", "日耀之力期间，日曜护盾减伤效果翻倍，并使受到的伤害降低，降低值相当于你15%最大生命值")
                    .itemLanguageTooltip(9, " ", "日耀之力期间，攻击不会积累日耀能量")
                    .itemLanguageTooltip(-1, " ", "“烫手魔石”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<MeteorSoul> METEOR_SOUL =
            register("meteor_soul", MeteorSoul::new, 0x654A52)
                    .itemLanguage("Meteor Enchantment", "流星魔石")
                    .itemLanguageTooltip(1, " ", "按下潜行键大幅增加下落速度")
                    .itemLanguageTooltip(2, " ", "根据当前移动速度，造成的伤害最高提升至125%")
                    .itemLanguageTooltip(3, " ", "攻击时有10%概率召唤流星攻击敌人")
                    .itemLanguageTooltip(4, " ", "免疫摔落伤害")
                    .itemLanguageTooltip(-1, " ", "《Drop a draco on 'em》")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<NebulaSoul> NebulaSoulItem =
            register("nebula_soul", NebulaSoul::new, 0xFF7AE7)
                    .itemLanguage("Nebula Enchantment", "星云魔石")
                    .itemLanguageTooltip(1, " ", "造成伤害的伤害提升，提升值相当于你4%最大生命值，并且会积累相当于15%伤害值的星云能量")
                    .itemLanguageTooltip(2, " ", "每秒消耗当前10%星云能量为你恢复生命值")
                    .itemLanguageTooltip(-1, " ", "“创生之柱照耀着你”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<StardustSoul> StardustSoulItem =
            register("stardust_soul", StardustSoul::new, 0x00ADF1)
                    .itemLanguage("Stardust Enchantment", "星尘魔石")
                    .itemLanguageTooltip(1, " ", "按下“冻结”键后会冻结时间，持续6秒，该效果有120秒冷却时间")
                    .itemLanguageTooltip(2, " ", "时间冻结期间，造成的伤害提升至200%")
                    .itemLanguageTooltip(-1, " ", "“你成为了替身使者”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<VortexSoul> VortexSoulItem =
            register("vortex_soul", VortexSoul::new, 0x00F4A8)
                    .itemLanguage("Vortex Enchantment", "星旋魔石")
                    .itemLanguageTooltip(1, " ", "按下“传送”键传送，最大传送距离为512格，大于此距离无法传送，传送冷却时间为20秒")
                    .itemLanguageTooltip(2, " ", "在传送位置召唤一个漩涡持续吸引并伤害周围敌人，持续5秒")
                    .itemLanguageTooltip(-1, " ", "“撕裂现实”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<WizardSoul> WizardSoulItem =
            register("wizard_soul", WizardSoul::new, SoulRarity.Blue.color())
                    .itemLanguage("Wizard Enchantment", "巫师魔石")
                    .itemLanguageTooltip(1, " ", "造成的魔法伤害提升提升60%")
                    .itemLanguageTooltip(-1, " ", "“我们爱施放魔法!”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    // ============================================================
    // 死亡之力 · 魔石分段
    // ============================================================
    public static final DeferredItem<DeathPower> DeathPowerItem =
            register("death_power", DeathPower::new, SoulRarity.Purple.color())
                    .itemLanguage("Death Power", "死亡之力")
                    .itemLanguageTooltip(1, " ", "赋予暗影冲刺，冲刺初速度提升50%")
                    .itemLanguageTooltip(2, " ", "暗影冲刺冲过敌人施加10秒死亡标记效果，死亡标记减少20%最大生命值，减少40%护甲，减少10%移动速度")
                    .itemLanguageTooltip(3, " ", "对被死亡标记影响的敌人，造成的伤害提升200%")
                    .itemLanguageTooltip(4, " ", "对低于最大生命值12%的敌人，造成的伤害提升，提升值至相当于25%敌人已损生命值")
                    .itemLanguageTooltip(5, " ", "免疫死亡标记")
                    .itemLanguageTooltip(-1, " ", "“黑暗，更黑暗，还是更黑暗”")
                    .itemTag(FargoSoulItemTagsRegister.SectionPower)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<AncientShadowSoul> AncientShadowSoulItem =
            register("ancient_shadow_soul", AncientShadowSoul::new, 0x5E55DC)
                    .itemLanguage("Ancient Shadow Enchantment", "远古暗影魔石")
                    .itemLanguageTooltip(1, " ", "攻击时，有20%概率为目标施加5秒黑暗效果")
                    .itemLanguageTooltip(2, " ", "召唤三颗魔法暗影球围绕你旋转，提供照明效果")
                    .itemLanguageTooltip(3, " ", "免疫黑暗")
                    .itemLanguageTooltip(-1, " ", "“十分古老，却非常实用”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<NinjaSoul> NinjaSoulItem =
            register("ninja_soul", NinjaSoul::new, 0x48494D)
                    .itemLanguage("Ninja Enchantment", "忍者魔石")
                    .itemLanguageTooltip(1, " ", "不攻击时，每秒积累20点伏击能量，最多积累600点伏击能量")
                    .itemLanguageTooltip(2, " ", "每点伏击能量使伤害提升1%，攻击后清空伏击能量")
                    .itemLanguageTooltip(-1, " ", "“等待正确的时机......”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<CrystalAssassinSoul> CrystalAssassinSoulItem =
            register("crystal_assassin_soul", CrystalAssassinSoul::new, 0xCF249D)
                    .itemLanguage("Crystal Assassin Enchantment", "水晶刺客魔石")
                    .itemLanguageTooltip(1, " ", "赋予水晶冲刺")
                    .itemLanguageTooltip(2, " ", "冲刺后，赋予你1秒先发至人效果，使下次攻击造成的伤害提升60%并施加水晶碎甲效果，降低目标20点防御力")
                    .itemLanguageTooltip(-1, " ", "“登顶”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<DarkArtistSoul> DarkArtistSoulItem =
            register("dark_artist_soul", DarkArtistSoul::new, 0x9C59B2)
                    .itemLanguage("Dark Artist Enchantment", "暗黑艺术家魔石")
                    .itemLanguageTooltip(1, " ", "对被暗影焰影响的敌人，造成的伤害提升40%，并吸收相当于20%伤害值的暗黑能量，最多吸收400点暗黑能量")
                    .itemLanguageTooltip(2, " ", "暗黑能量达到最大值时，消耗全部暗黑能量，使你获得20秒暗影之赐效果，最大生命值提升40%，移动速度提升15%，攻击力提升40%")
                    .itemLanguageTooltip(3, " ", "在暗影之赐效果下，有20%概率闪避负面效果，闪避后恢复你12%最大生命值")
                    .itemLanguageTooltip(-1, " ", "“阴影蕴含之物远超其表象”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<GloomySoul> GloomySoulItem =
            register("gloomy_soul", GloomySoul::new, 0x644D75)
                    .itemLanguage("Gloomy Enchantment", "阴森魔石")
                    .itemLanguageTooltip(1, " ", "攻击施加10秒暗影焰效果")
                    .itemLanguageTooltip(2, " ", "对被暗影焰影响的敌人，攻击有5%概率施加2秒死亡标记效果")
                    .itemLanguageTooltip(-1, " ", "“自1902年以来融化的灵魂”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<NecromancerSoul> NecromancerSoulItem =
            register("necromancer_soul", NecromancerSoul::new, 0x565642)
                    .itemLanguage("Necromancer Enchantment", "死灵魔石")
                    .itemLanguageTooltip(1, " ", "击杀亡灵生物掠夺1点死灵能量，死灵能量最大值为100点")
                    .itemLanguageTooltip(2, " ", "死亡时，如果死灵能量达到最大值，则消耗全部死灵能量使你复活，并恢复20%最大生命值")
                    .itemLanguageTooltip(3, " ", "击杀骷髅或凋零骷髅额外掉落其头颅，并额外掠夺2点死灵能量")
                    .itemLanguageTooltip(-1, "\"Welcome to the Bone Zone\"", "“欢迎来到骸骨领域”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<PenetratingNinjaSoul> PenetratingNinjaSoulItem =
            register("penetrating_ninja_soul", PenetratingNinjaSoul::new, 0x945B0A)
                    .itemLanguage("Penetrating Ninja Enchantment", "渗透忍者魔石")
                    .itemLanguageTooltip(1, " ", "赋予渗透冲刺，冲刺初速度提升25%")
                    .itemLanguageTooltip(2, " ", "按下“渗透”键，可以使下一次冲刺后的1秒内免疫一切伤害并可以穿过墙壁，该效果有30秒冷却时间")
                    .itemLanguageTooltip(-1, " ", "“藏匿于墙中的村庄”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    // ============================================================
    // 泰拉之力 · 魔石分段
    // ============================================================
    public static final DeferredItem<TerraPower> TerraPowerItem =
            register("terra_power", TerraPower::new, SoulRarity.Purple.getColor())
                    .itemLanguage("Terra Power", "泰拉之力")
                    .itemLanguageTooltip(1, " ", "攻击时有20%概率释放3~6颗雷电球，雷电球会电击附近的敌人并施加铅中毒，该效果有1秒冷却时间")
                    .itemLanguageTooltip(2, " ", "在场的雷电球越多，释放雷电球的概率越高，冷却越短，概率最多提升40%，冷却时间最多降低60%")
                    .itemLanguageTooltip(3, " ", "造成伤害时有5%概率使伤害提升100%，并获得5秒泰拉共鸣效果，使释放雷电球的概率提升20%，雷电球造成的伤害提升80%")
                    .itemLanguageTooltip(4, " ", "受到的伤害减少，减少值相当于[4+你2%最大生命值]")
                    .itemLanguageTooltip(-1, " ", "“大地赐予它力量”")
                    .itemTag(FargoSoulItemTagsRegister.SectionPower)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<CopperSoul> CopperSoulItem =
            register("copper_soul", CopperSoul::new, 0xD56617)
                    .itemLanguage("Copper Enchantment", "铜魔石")
                    .itemLanguageTooltip(1, " ", "攻击时有10%概率释放雷电球，雷电球会电击附近的敌人，该效果有5秒冷却时间")
                    .itemLanguageTooltip(2, " ", "攻击雨中或水中的目标时释放闪电的概率增加20%")
                    .itemLanguageTooltip(-1, " ", "“它的音乐还是电音”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<TinSoul> TinSoulItem =
            register("tin_soul", TinSoul::new, 0xA28B4E)
                    .itemLanguage("Tin Enchantment", "锡魔石")
                    .itemLanguageTooltip(1, " ", "暴击伤害提升20%")
                    .itemLanguageTooltip(2, " ", "每次暴击时都会增加10%额外暴击伤害，额外暴击伤害的最大值为80%")
                    .itemLanguageTooltip(3, " ", "受到伤害会使额外暴击伤害减半")
                    .itemLanguageTooltip(-1, "\"Crits are back\"", "“暴击回归”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<IronSoul> IronSoulItem =
            register("iron_soul", IronSoul::new, 0x988E83)
                    .itemLanguage("Iron Enchantment", "铁魔石")
                    .itemLanguageTooltip(1, " ", "吸引周围的物品")
                    .itemLanguageTooltip(2, " ", "拾取物品后的5秒内受到的伤害减少20%")
                    .itemLanguageTooltip(-1, " ", "“趁热打铁”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<LeadSoul> LeadSoulItem =
            register("lead_soul", LeadSoul::new, 0x46485D)
                    .itemLanguage("Lead Enchantment", "铅魔石")
                    .itemLanguageTooltip(1, " ", "攻击施加5秒铅中毒")
                    .itemLanguageTooltip(2, " ", "被铅中毒影响的敌人会向周围的敌人传染铅中毒")
                    .itemLanguageTooltip(3, " ", "免疫铅中毒")
                    .itemLanguageTooltip(-1, " ", "“不建议食用”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<SilverSoul> SilverSoulItem =
            register("silver_soul", SilverSoul::new, 0xB4B4CD)
                    .itemLanguage("Silver Enchantment", "银魔石")
                    .itemLanguageTooltip(1, " ", "举盾后0.2~0.4秒抵挡攻击会反弹敌人200%伤害，并赋予你1秒惊人一刻，伤害提升150%，该效果有3秒冷却时间")
                    .itemLanguageTooltip(2, " ", "反弹伤害后的0.4秒内无敌")
                    .itemLanguageTooltip(-1, " ", "“反射”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<TungstenSoul> TungstenSoulItem =
            register("tungsten_soul", TungstenSoul::new, 0xB0D2B2)
                    .itemLanguage("Tungsten Enchantment", "钨魔石")
                    .itemLanguageTooltip(1, " ", "攻击距离提升50%")
                    .itemLanguageTooltip(-1, " ", "“大就是好”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<ObsidianSoul> ObsidianSoulItem =
            register("obsidian_soul", ObsidianSoul::new, 0x453C75)
                    .itemLanguage("Obsidian Enchantment", "黑曜石魔石")
                    .itemLanguageTooltip(1, " ", "在火焰或熔岩中时获得更好的视野")
                    .itemLanguageTooltip(2, " ", "受到的伤害减少1点")
                    .itemLanguageTooltip(3, " ", "免疫火焰伤害")
                    .itemLanguageTooltip(4, " ", "免疫熔岩伤害")
                    .itemLanguageTooltip(-1, " ", "“大地在呼唤”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    // ============================================================
    // 森林之力 · 魔石分段
    // ============================================================
    public static final DeferredItem<ForestPower> ForestPowerItem =
            register("forest_power", ForestPower::new, SoulRarity.Purple.getColor())
                    .itemLanguage("Forest Power", "森林之力")
                    .itemLanguageTooltip(1, " ", "生成一个半径12格的光环，每秒向光环的敌人施加8秒凋零和流血效果")
                    .itemLanguageTooltip(2, " ", "攻击受凋零影响的敌人时，造成的伤害提升8点")
                    .itemLanguageTooltip(3, " ", "攻击受流血影响的敌人时，使敌人喷出3~6个追踪鲜血球，每个鲜血球恢复你3点生命值。受流血影响的敌人恢复的生命值减少50%")
                    .itemLanguageTooltip(4, " ", "在你的头顶产生雪球攻击光环内的敌人，雪球命中时施加寒冷效果，使敌人移动速度减少30%，该效果有2秒冷却时间")
                    .itemLanguageTooltip(5, " ", "拾取物品使你在8秒内造成的暴击伤害提升40%")
                    .itemLanguageTooltip(6, " ", "交易价格减少50%")
                    .itemLanguageTooltip(-1, "\"Very hard\"", "“很硬”")
                    .itemTag(FargoSoulItemTagsRegister.SectionPower)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<WoodSoul> WoodSoulItem =
            register("wood_soul", WoodSoul::new, 0x986B48)
                    .itemLanguage("Wood Enchantment", "木魔石")
                    .itemLanguageTooltip(1, " ", "交易价格降低20%")
                    .itemLanguageTooltip(2, " ", "每次击败掠夺者，价格额外降低0.25%~0.5%，最多额外降低30%")
                    .itemLanguageTooltip(-1, " ", "“被店主们讨厌的诡计”")
                    .itemLanguageTooltip(-2, " ", "“卑微的开始……”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<PineWoodSoul> PineWoodSoulItem =
            register("pine_wood_soul", PineWoodSoul::new, 0x8B7464)
                    .itemLanguage("Pine Wood Enchantment", "针叶木魔石")
                    .itemLanguageTooltip(1, " ", "产生雪球攻击附近内的敌人，雪球命中时施加寒冷效果，使敌人移动速度减少30%，该效果有5秒冷却时间")
                    .itemLanguageTooltip(-1, " ", "“又冷又酷”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<RoseWoodSoul> RoseWoodSoulItem =
            register("rose_wood_soul", RoseWoodSoul::new, 0xB56C64)
                    .itemLanguage("Rose Wood Enchantment", "红木魔石")
                    .itemLanguageTooltip(1, " ", "赋予丛林冲刺，冲刺初速度提升25%")
                    .itemLanguageTooltip(2, " ", "在冲刺期间受到的伤害减少20%")
                    .itemLanguageTooltip(-1, " ", "“保证钩到你”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<EbonyWoodSoul> EbonyWoodSoulItem =
            register("ebony_wood_soul", EbonyWoodSoul::new, 0x645A8D)
                    .itemLanguage("Ebony Wood Enchantment", "乌木魔石")
                    .itemLanguageTooltip(1, " ", "产生一个半径6格的腐化光环，每秒从光环内的每个敌人吸取腐化值，最多为250点")
                    .itemLanguageTooltip(2, " ", "根据腐化值比例，造成的伤害最多提升5，受到的伤害最多减少5%")
                    .itemLanguageTooltip(-1, " ", "“潜力未完全开发”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<ShadowWoodSoul> ShadowWoodSoulItem =
            register("shadow_wood_soul", ShadowWoodSoul::new, 0x586876)
                    .itemLanguage("Shadow Wood Enchantment", "阴影木魔石")
                    .itemLanguageTooltip(1, " ", "产生一个半径6格的鲜血光环，每秒对鲜血光环内敌人施加6秒血如泉涌效果，使恢复的生命值减少70%")
                    .itemLanguageTooltip(2, " ", "攻击受血如泉涌影响的敌人时喷出2~3个伤害性血液，追踪周围其他敌人，该效果有0.5秒冷却时间")
                    .itemLanguageTooltip(-1, " ", "“出奇的干净”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<PalmWoodSoul> PalmWoodSoulItem =
            register("palm_wood_soul", PalmWoodSoul::new, 0xB78D56)
                    .itemLanguage("Palm Wood Enchantment", "棕榈木魔石")
                    .itemLanguageTooltip(1, " ", "按下“棕榈爆炸”键，对附近敌人施加20秒涂油效果，使敌人受到的火焰伤害提升200%")
                    .itemLanguageTooltip(2, " ", "攻击被涂油影响的敌人时，伤害提升20%并点燃敌人")
                    .itemLanguageTooltip(-1, " ", "“出奇的宁静”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<PearlWoodSoul> PearlWoodSoulItem =
            register("pearl_wood_soul", PearlWoodSoul::new, 0xAD9A5F)
                    .itemLanguage("Pearl Wood Enchantment", "珍珠木魔石")
                    .itemLanguageTooltip(1, " ", "暴击伤害提升20%")
                    .itemLanguageTooltip(2, " ", "拾取物品时，召唤一颗星星追踪周围一个敌人，命中时爆炸，并为你提供10秒星之光辉效果，该效果有1秒冷却时间")
                    .itemLanguageTooltip(3, " ", "处于星之光辉影响下，暴击伤害提升40%")
                    .itemLanguageTooltip(-1, " ", "“太少了，太晚了……”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    // ============================================================
    // 大地之力 · 魔石分段
    // ============================================================
    public static final DeferredItem<EarthPower> EarthPowerItem = register("earth_power", EarthPower::new)
            .itemLanguage("Earth Power", "大地之力")
            .itemLanguageTooltip(1, " ", "不攻击3秒后，每秒积聚20点大地能量，最多积聚1000点大地能量，攻击时消耗10点大地能量")
            .itemLanguageTooltip(2, " ", "根据大地能量积累比例，最多提升100%造成伤害，100%攻击速度，5每秒生命恢复，100%护甲")
            .itemLanguageTooltip(-1, "\"Gaia's blessing shines upon you\"", "“盖亚的祝福照耀着你”")
            .itemTag(FargoSoulItemTagsRegister.SectionPower)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<CobaltSoul> CobaltSoulItem = register("cobalt_soul", CobaltSoul::new)
            .itemLanguage("Cobalt Enchantment", "钴蓝魔石")
            .itemLanguageTooltip(1, " ", "受到伤害时，对附近的敌人施加2.5秒涂油效果，使敌人受到的火焰伤害提升200%，该效果有3秒冷却时间")
            .itemLanguageTooltip(2, " ", "免疫浸油")
            .itemLanguageTooltip(-1, " ", "“真不敢相信这竟然不是钯金”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<PalladiumSoul> PalladiumSoulItem = register("palladium_soul", PalladiumSoul::new)
            .itemLanguage("Palladium Enchantment", "钯金魔石")
            .itemLanguageTooltip(1, " ", "攻击有20%概率使敌人爆出3~5个追踪鲜血球，每个鲜血球恢复你3点生命值，该效果有2秒冷却时间")
            .itemLanguageTooltip(2, " ", "恢复生命值时积累25%生命能量，最多积累200点")
            .itemLanguageTooltip(3, " ", "生命能量达到最大值时，消耗所有能量，为你恢复100%最大生命值，并使你获得10秒生命流涌效果")
            .itemLanguageTooltip(4, " ", "在生命流涌效果下，超过最大生命值的恢复会产生伤害性血液，伤害值等同溢出的恢复值，追踪周围其他敌人")
            .itemLanguageTooltip(-1, " ", "“你感到你的伤口在慢慢愈合”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<MithrilSoul> MithrilSoulItem = register("mithril_soul", MithrilSoul::new)
            .itemLanguage("Mithril Enchantment", "秘银魔石")
            .itemLanguageTooltip(1, " ", "攻击速度提升30%")
            .itemLanguageTooltip(2, " ", "攻击使攻击速度额外提升5%，最多额外提升70%，5秒不攻击使额外攻击速度清空")
            .itemLanguageTooltip(-1, " ", "“你感觉武器的知识渗透进你的脑海中”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<OrichalcumSoul> OrichalcumSoulItem = register("orichalcum_soul", OrichalcumSoul::new)
            .itemLanguage("Orichalcum Enchantment", "山铜魔石")
            .itemLanguageTooltip(1, " ", "攻击时时召唤3~6枚追踪花瓣，花瓣造成5%魔法伤害并施加5秒山铜中毒效果，该效果有0.25秒冷却时间")
            .itemLanguageTooltip(3, " ", "免疫山铜中毒")
            .itemLanguageTooltip(-1, "\"Nature blesses you\"", "“自然祝福着你”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<AdamantiteSoul> AdamantiteSoulItem = register("adamantite_soul", AdamantiteSoul::new)
            .itemLanguage("Adamantite Enchantment", "精金魔石")
            .itemLanguageTooltip(1, " ", "移动速度提升15%")
            .itemLanguageTooltip(2, " ", "攻击造成5秒盔甲破损效果，减少40%护甲值")
            .itemLanguageTooltip(3, " ", "攻击护甲低于20的敌人时，造成的伤害提升40%，暴击伤害提升80%")
            .itemLanguageTooltip(-1, "\"Chaos\"", "“混乱”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<TitaniumSoul> TitaniumSoulItem = register("titanium_soul", TitaniumSoul::new)
            .itemLanguage("Titanium Enchantment", "钛金魔石")
            .itemLanguageTooltip(1, " ", "减少15%受到伤害")
            .itemLanguageTooltip(2, " ", "受到伤害会积累20%守护能量，最多积累600点守护能量，每秒流失5点")
            .itemLanguageTooltip(3, " ", "根据守护能量的比例，减少你受到的伤害，最多减少35%")
            .itemLanguageTooltip(4, " ", "守护能量超过300点时，提升25%最大生命值，提升50%护甲值")
            .itemLanguageTooltip(5, " ", "低于50%最大生命值时，守护能量的减伤效果翻倍")
            .itemLanguageTooltip(-1, " ", "“有了绝对防御后，谁还需要躲避呢？”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    // ============================================================
    // 自然之力 · 魔石分段
    // ============================================================
    public static final DeferredItem<NaturePower> NaturePowerItem = register("nature_power", NaturePower::new)
            .itemLanguage("Nature Power", "自然之力")
            .itemLanguageTooltip(1, " ", "召唤5个围绕持有者旋转的叶绿水晶，叶绿水晶会向敌怪发射激光")
            .itemLanguageTooltip(2, " ", "叶绿水晶造成的伤害提升300%")
            .itemLanguageTooltip(3, " ", "受到的伤害降低15点")
            .itemLanguageTooltip(-1, "\"Travel through every secret corner of the wilderness\"", "“走遍荒野的每一个秘密角落”")
            .itemTag(FargoSoulItemTagsRegister.SectionPower)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<CrimsonSoul> CrimsonSoulItem = register("crimson_soul", CrimsonSoul::new)
            .itemLanguage("Crimson Enchantment", "猩红魔石")
            .itemLanguageTooltip(1, " ", "攻击使敌人爆出2~4个追踪鲜血球，为你恢复3点生命值，每个敌人有0.25秒冷却时间")
            .itemLanguageTooltip(2, " ", "每秒恢复1%最大生命值，周围4格没有敌人时，每秒额外恢复2最大生命值")
            .itemLanguageTooltip(3, " ", "击杀敌人使你恢复敌人20%最大生命值的生命值")
            .itemLanguageTooltip(-1, " ", "“你从敌人的鲜血中重生”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<LavaSoul> LavaSoulItem = register("lava_soul", LavaSoul::new)
            .itemLanguage("Lava Enchantment", "熔岩魔石")
            .itemLanguageTooltip(1, " ", "点燃附近的目标")
            .itemLanguageTooltip(2, " ", "对燃烧的敌人，造成的伤害提升至120%")
            .itemLanguageTooltip(3, " ", "免疫熔岩伤害")
            .itemLanguageTooltip(-1, "\"They will feel the wrath of Hell\"", "“他们将感受到地狱的愤怒”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<RainCloudSoul> RainCloudSoulItem =
            register("rain_cloud_soul", RainCloudSoul::new)
                    .itemLanguage("Rain Cloud Enchantment", "雨云魔石")
                    .itemLanguageTooltip(1, " ", "受到伤害时有概率召唤雷电劈向目标，该效果有2秒冷却时间")
                    .itemLanguageTooltip(2, " ", "免疫雷电伤害")
                    .itemLanguageTooltip(-1, "\"Come again another day\"", "“改日再来”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<FrostSoul> FrostSoulItem = register("frost_soul", FrostSoul::new)
            .itemLanguage("Frost Enchantment", "冰霜魔石")
            .itemLanguageTooltip(1, " ", "攻击时召唤2枚雪球攻击敌人，该效果有1秒冷却时间")
            .itemLanguageTooltip(4, " ", "在寒冷群系时，雪球的冷却时间减少60%")
            .itemLanguageTooltip(-1, " ", "“让我们给这个世界披上一层厚厚的冰衣”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<GreenSoul> GreenSoulItem = register("green_soul", GreenSoul::new)
            .itemLanguage("Green Enchantment", "叶绿魔石")
            .itemLanguageTooltip(1, " ", "赋予叶绿冲刺。冲刺会对敌人造成10秒中毒效果")
            .itemLanguageTooltip(2, " ", "赋予飞行")
            .itemLanguageTooltip(3, " ", "产生一个跟随持有者的叶绿水晶攻击周围敌人，叶绿水晶会向敌怪发射激光")
            .itemLanguageTooltip(-1, " ", "“丛林的精华在你周围凝结成晶体”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<MushroomSoul> MushroomSoulItem = register("mushroom_soul", MushroomSoul::new)
            .itemLanguage("Mushroom Enchantment", "蘑菇魔石")
            .itemLanguageTooltip(1, " ", "食用蘑菇煲额外恢复50点生命值")
            .itemLanguageTooltip(2, " ", "击杀敌人后掉落2~4格蘑菇")
            .itemLanguageTooltip(-1, " ", "“是用真的蘑菇做的！”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    // ============================================================
    // 生命之力 · 魔石分段
    // ============================================================
    public static final DeferredItem<LifePower> LifePowerItem = register("life_power", LifePower::new)
            .itemLanguage("Life Power", "生命之力")
            .itemLanguageTooltip(1, " ", "赋予飞行")
            .itemLanguageTooltip(2, " ", "飞行时间提升150%")
            .itemLanguageTooltip(3, " ", "饮用蜂蜜使你获得10秒仙馔密酒效果")
            .itemLanguageTooltip(4, " ", "处于仙馔密酒效果下时，造成的伤害提升30%伤害，提升5每秒生命恢复，召唤伤害提升200%")
            .itemLanguageTooltip(5, " ", "反弹500%的接触伤害")
            .itemLanguageTooltip(-1, " ", "“罕有生灵敢违背你的意愿”")
            .itemTag(FargoSoulItemTagsRegister.SectionPower)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<BeeSoul> BeeSoulItem = register("bee_soul", BeeSoul::new)
            .itemLanguage("Bee Enchantment", "蜜蜂魔石")
            .itemLanguageTooltip(1, " ", "赋予飞行")
            .itemLanguageTooltip(2, " ", "飞行时间提升40%")
            .itemLanguageTooltip(3, " ", "接触花朵时提升2每秒生命恢复")
            .itemLanguageTooltip(-1, " ", "“根据目前所知的所有航空原理，蜜蜂应该根本不可能会飞”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<BeetleSoul> BeetleSoulItem = register("beetle_soul", BeetleSoul::new)
            .itemLanguage("Beetle Enchantment", "甲虫魔石")
            .itemLanguageTooltip(1, " ", "赋予飞行")
            .itemLanguageTooltip(2, " ", "每7秒获得1层甲虫耐力，最多叠加3层，每层甲虫耐力减少15%受到伤害，")
            .itemLanguageTooltip(3, " ", "受到伤害时减少一层甲虫耐力，每2秒最多减少一层甲虫耐力")
            .itemLanguageTooltip(4, " ", "攻击时获得1层甲虫力量，最多叠加3层，每层甲虫力量提升30%造成伤害，每秒失去1层甲虫力量")
            .itemLanguageTooltip(-1, " ", "“你的血管里流淌着看不见的粪便生命”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<PumpkinSoul> PumpkinSoulItem = register("pumpkin_soul", PumpkinSoul::new)
            .itemLanguage("Pumpkin Enchantment", "南瓜魔石")
            .itemLanguageTooltip(1, " ", "携带南瓜头时减少15%受到伤害")
            .itemLanguageTooltip(2, " ", "踩在成熟的南瓜上时会使南瓜产生爆炸并恢复你5%最大生命值")
            .itemLanguageTooltip(-1, " ", "“你对南瓜的突发渴望永远不会得到满足”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<SpiderSoul> SpiderSoulItem = register("spider_soul", SpiderSoul::new)
            .itemLanguage("Spider Enchantment", "蜘蛛魔石")
            .itemLanguageTooltip(1, " ", "提升15%召唤伤害，24点召唤穿透")
            .itemLanguageTooltip(-1, " ", "“蜘蛛恐惧者？作为惩罚，让他被蜘蛛干掉吧！”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<TurtleSoul> TurtleSoulItem =
            register("turtle_soul", TurtleSoul::new)
                    .itemLanguage("Turtle Enchantment", "乌龟魔石")
                    .itemLanguageTooltip(1, " ", "反弹60%伤害")
                    .itemLanguageTooltip(2, " ", "生命值低于50%时，反弹的伤害提升至80%伤害")
                    .itemLanguageTooltip(3, " ", "生命值低于25%时获得龟壳，龟壳存在时，受到的伤害减少，减少值相当于你4%最大生命值")
                    .itemLanguageTooltip(6, " ", "免疫仙人掌伤害")
                    .itemLanguageTooltip(-1, " ", "“你突然有一种想躲进壳里的冲动”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    // ============================================================
    // 心灵之力 · 魔石分段
    // ============================================================
    public static final DeferredItem<SpiritPower> SpiritPowerItem =
            register("spirit_power", SpiritPower::new)
                    .itemLanguage("Spirit Power", "心灵之力")
                    .itemLanguageTooltip(1, " ", "受到的治疗效果提升70%")
                    .itemLanguageTooltip(2, " ", "飞行时间提升100%")
                    .itemLanguageTooltip(3, " ", "额外召唤8柄胜利与誓约之刃，胜利与誓约之刃造成的伤害提升200%")
                    .itemLanguageTooltip(-1, "\"Drifting away like a transcended immortal\"", "“飘飘乎如遗世独立，羽化而登仙”")
                    .itemTag(FargoSoulItemTagsRegister.SectionPower)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<ForbiddenSoul> ForbiddenSoulItem =
            register("forbidden_soul", ForbiddenSoul::new)
                    .itemLanguage("Forbidden Enchantment", "禁戒魔石")
                    .itemLanguageTooltip(1, " ", "按下“风暴”键召唤禁戒风暴，吸引附近的目标，持续5秒，该效果有30秒冷却时间")
                    .itemLanguageTooltip(-1, " ", "“走路像个埃及人”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<HolySoul> HolySoulItem =
            register("holy_soul", HolySoul::new)
                    .itemLanguage("Holy Enchantment", "神圣魔石")
                    .itemLanguageTooltip(1, " ", "提升80%受到治疗")
                    .itemLanguageTooltip(2, " ", "受到治疗时会产生冲击波，击退附近的目标，该效果有1秒冷却时间")
                    .itemLanguageTooltip(-1, " ", "“尽管放马过来”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<AncientHolySoul> AncientHolySoulItem = register("ancient_holy_soul", AncientHolySoul::new)
            .itemLanguage("Ancient Holy Enchantment", "远古神圣魔石")
            .itemLanguageTooltip(1, " ", "召唤胜利与誓约之刃为你而战")
            .itemLanguageTooltip(-1, " ", "“你有足够的力量驾驭我吗？”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<TekeSoul> TekeSoulItem = register("teke_soul", TekeSoul::new)
            .itemLanguage("Teke Enchantment", "提基魔石")
            .itemLanguageTooltip(1, " ", "提升35%召唤伤害")
            .itemLanguageTooltip(2, " ", "造成召唤伤害时，施加12秒灵雾迷障效果，减少20%移动速度，10%护甲值")
            .itemLanguageTooltip(-1, " ", "“Aku Aku!”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<GhostSoul> GhostSoulItem = register("ghost_soul", GhostSoul::new)
            .itemLanguage("Ghost Enchantment", "幽魂魔石")
            .itemLanguageTooltip(1, " ", "攻击时夺取1点灵魂能量，最多夺取100点灵魂能量，每点灵魂能量提升2%最大生命值")
            .itemLanguageTooltip(2, " ", "灵魂能量达到最大值时，死亡会消耗所有灵魂能量使你复活，恢复你25%最大生命值并清除减益效果，该效果有600秒冷却时间")
            .itemLanguageTooltip(3, " ", "灵魂能量溢出时，每秒消耗所有溢出灵魂能量，召唤一枚追踪灵魂球，灵魂球伤害为溢出灵魂能量*4%最大生命值")
            .itemLanguageTooltip(-1, " ", "“他们的生命力将毁灭他们自己”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    // ============================================================
    // 意志之力 · 魔石分段
    // ============================================================
    public static final DeferredItem<WillPower> WillPowerItem = register("will_power", WillPower::new)
            .itemLanguage("Will Power", "意志之力")
            .itemLanguageTooltip(6, " ", "受到的治疗效果提升25%")
            .itemLanguageTooltip(7, " ", "击杀敌人有40%概率提升1600%战利品数量")
            .itemLanguageTooltip(8, " ", "攻击被迈达斯影响的敌人时，造成的伤害提升80%")
            .itemLanguageTooltip(-1, "\"Indomitable determination\"", "“坚不可摧的决心”")
            .itemTag(FargoSoulItemTagsRegister.SectionPower)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<GoldSoul> GoldSoulItem =
            register("gold_soul", GoldSoul::new)
                    .itemLanguage("Gold Enchantment", "金魔石")
                    .itemLanguageTooltip(1, " ", "攻击造成10秒迈达斯减益")
                    .itemLanguageTooltip(2, " ", "击杀被迈达斯影响的目标时额外会掉落1~4个金锭")
                    .itemLanguageTooltip(3, " ", "按下“金身”键，使你在6秒内无敌，该效果有120秒冷却")
                    .itemLanguageTooltip(5, " ", "无敌时间结束时，尝试消耗物品栏中的金锭延长无敌时间")
                    .itemLanguageTooltip(6, " ", "免疫迈达斯")
                    .itemLanguageTooltip(-1, " ", "“有钱能使鬼推磨”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<PlatinumSoul> PlatinumSoulItem = register("platinum_soul", PlatinumSoul::new)
            .itemLanguage("Platinum Enchantment", "铂金魔石")
            .itemLanguageTooltip(1, " ", "击杀敌人有20%概率提升500%战利品数量")
            .itemLanguageTooltip(-1, " ", "“无价之宝”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<GladiatorSoul> GladiatorSoulItem =
            register("gladiator_soul", GladiatorSoul::new)
                    .itemLanguage("Gladiator Enchantment", "角斗士魔石")
                    .itemLanguageTooltip(1, " ", "周围8格的目标数少于3个时，造成的伤害提升30%，否则减少20%受到伤害")
                    .itemLanguageTooltip(2, " ", "周围8格只有一个目标时，造成的伤害提升50%，受到的伤害提升50%")
                    .itemLanguageTooltip(-1, " ", "“你不觉得刺激吗？”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<RedRidingSoul> RedRidingSoulItem =
            register("red_riding_soul", RedRidingSoul::new)
                    .itemLanguage("Red Riding Enchantment", "红色骑术魔石")
                    .itemLanguageTooltip(1, " ", "对有护甲的目标，造成的伤害提升40%")
                    .itemLanguageTooltip(2, " ", "攻击时叠加1层游击效果，最多叠加10层，每层游击提升1%移动速度")
                    .itemLanguageTooltip(3, " ", "游击层数达到10层时，冲刺初速度提升50%")
                    .itemLanguageTooltip(5, " ", "受到伤害时清空游击层数")
                    .itemLanguageTooltip(-1, " ", "“小红帽，大坏蛋！”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<ValhallaKnightSoul> ValhallaKnightSoulItem =
            register("valhalla_knight_soul", ValhallaKnightSoul::new)
                    .itemLanguage("Valhalla Knight Enchantment", "英灵殿骑士魔石")
                    .itemLanguageTooltip(1, " ", "骑乘坐骑时，造成的伤害提升40%，提升50%护甲值，提升2每秒生命恢复")
                    .itemLanguageTooltip(2, " ", "受到的治疗效果提升15%")
                    .itemLanguageTooltip(-1, " ", "“瓦尔哈拉的呼唤”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    private static <T extends Item> LyraItemRegisterBuilder<T> register(String name, Function<Item.Properties, T> function) {
        return register(name, function, null);
    }

    private static <T extends Item> LyraItemRegisterBuilder<T> register(String name, Function<Item.Properties, T> function, Integer color) {
        return LyraItemRegisterBuilder.build(Register, name, () -> {
            Item.Properties properties = new Item.Properties().stacksTo(1).fireResistant();
            if (color != null) {
                properties = properties.component(FargoSoulDataComponentsRegister.SOUL_RARITY, new SoulRarity(color));
            }
            return function.apply(properties);
        });
    }

    public static void register(IEventBus eventBus) {
        Register.register(eventBus);
    }
}

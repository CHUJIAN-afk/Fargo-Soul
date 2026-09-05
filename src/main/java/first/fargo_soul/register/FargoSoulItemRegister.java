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
                    .itemLanguageTooltip(2, " ", "周围被黑暗效果影响的敌人偶尔会向其他敌人发射暗影球，造成4点伤害和暗影焰效果")
                    .itemLanguageTooltip(3, " ", "召唤三颗魔法暗影球围绕你旋转，提供照明效果")
                    .itemLanguageTooltip(3, " ", "免疫黑暗")
                    .itemLanguageTooltip(-1, " ", "“十分古老，却非常实用”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<NinjaSoul> NinjaSoulItem =
            register("ninja_soul", NinjaSoul::new, 0x48494D)
                    .itemLanguage("Ninja Enchantment", "忍者魔石")
                    .itemLanguageTooltip(1, " ", "移动速度越低，造成的伤害越高，最多将造成的伤害提升至140%")
                    .itemLanguageTooltip(2, " ", "静止时，造成的伤害提升至180%")
                    .itemLanguageTooltip(-1, " ", "“等待正确的时机......”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<CrystalAssassinSoul> CrystalAssassinSoulItem =
            register("crystal_assassin_soul", CrystalAssassinSoul::new, 0xCF249D)
                    .itemLanguage("Crystal Assassin Enchantment", "水晶刺客魔石")
                    .itemLanguageTooltip(1, " ", "赋予冲刺能力")
                    .itemLanguageTooltip(2, " ", "冲刺后，赋予你1秒先发至人效果，使下次攻击造成的伤害提升至300%，并施加水晶碎甲效果，降低目标10点防御力")
                    .itemLanguageTooltip(-1, " ", "“登顶”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<DarkArtistSoul> DarkArtistSoulItem =
            register("dark_artist_soul", DarkArtistSoul::new, 0x9C59B2)
                    .itemLanguage("Dark Artist Enchantment", "暗黑艺术家魔石")
                    .itemLanguageTooltip(1, " ", "攻击时召唤爆炸烈焰球攻击敌人，该效果有0.5秒冷却时间")
                    .itemLanguageTooltip(-1, " ", "“阴影蕴含之物远超其表象”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<GloomySoul> GloomySoulItem =
            register("gloomy_soul", GloomySoul::new, 0x644D75)
                    .itemLanguage("Gloomy Enchantment", "阴森魔石")
                    .itemLanguageTooltip(1, " ", "使你的攻击附加暗影焰")
                    .itemLanguageTooltip(-1, " ", "“自1902年以来融化的灵魂”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<NecromancerSoul> NecromancerSoulItem =
            register("necromancer_soul", NecromancerSoul::new, 0x565642)
                    .itemLanguage("Necromancer Enchantment", "死灵魔石")
                    .itemLanguageTooltip(1, " ", "攻击敌人时有几率爆出一堆骨头，对附近敌人造成伤害")
                    .itemLanguageTooltip(2, " ", "击杀骷髅或凋零骷髅额外掉落其头颅")
                    .itemLanguageTooltip(-1, "\"Welcome to the Bone Zone\"", "“欢迎来到骸骨领域”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    public static final DeferredItem<PenetratingNinjaSoul> PenetratingNinjaSoulItem =
            register("penetrating_ninja_soul", PenetratingNinjaSoul::new, 0x945B0A)
                    .itemLanguage("Penetrating Ninja Enchantment", "渗透忍者魔石")
                    .itemLanguageTooltip(1, " ", "赋予渗透冲刺能力")
                    .itemLanguageTooltip(2, " ", "渗透冲刺时能够撞击敌人，造成10%持有者最大生命值的凋零伤害与大量击退")
                    .itemLanguageTooltip(3, " ", "冲刺后的0.5秒内免疫一切伤害并可以穿过墙壁")
                    .itemLanguageTooltip(-1, " ", "“藏匿于墙中的村庄”")
                    .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
                    .itemModel(LyraItemRegisterBuilder::basicModel)
                    .itemTag(FargoSoulItemTagsRegister.Curio)
                    .build();

    // ============================================================
    // 森林之力 · 魔石分段
    // ============================================================
    public static final DeferredItem<WoodSoul> WoodSoulItem = register("wood_soul", WoodSoul::new)
            .itemLanguage("Wood Enchantment", "木魔石")
            .itemLanguageTooltip(1, "", "交易价格降低")
            .itemLanguageTooltip(-1, "", "“被店主们讨厌的诡计”")
            .itemLanguageTooltip(-2, "", "“卑微的开始……”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<PineWoodSoul> PineWoodSoulItem = register("pine_wood_soul", PineWoodSoul::new)
            .itemLanguage("Pine Wood Enchantment", "针叶木魔石")
            .itemLanguageTooltip(1, "Generates snowballs above your head to attack random nearby enemies", "持有者的头顶会产生雪球攻击周围随机目标")
            .itemLanguageTooltip(-1, "\"Cold and cool\"", "“又冷又酷”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<RoseWoodSoul> RoseWoodSoulItem = register("rose_wood_soul", RoseWoodSoul::new)
            .itemLanguage("Rose Wood Enchantment", "红木魔石")
            .itemLanguageTooltip(1, "Attacking airborne targets pulls them slightly towards you", "持有者攻击空中目标时，会将目标向持有者方向牵引一小段距离")
            .itemLanguageTooltip(2, "Immunity to traction", "免疫牵引")
            .itemLanguageTooltip(-1, "\"Guaranteed to hook you\"", "“保证钩到你”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<EbonyWoodSoul> EbonyWoodSoulItem = register("ebony_wood_soul", EbonyWoodSoul::new)
            .itemLanguage("Ebony Wood Enchantment", "乌木魔石")
            .itemLanguageTooltip(1, "Nearby targets are gradually Corrupted, up to 100 points", "在持有者周围的目标会被逐渐腐化，腐化值上限为100点")
            .itemLanguageTooltip(2, "Each point reduces target damage by 0.05% and increases damage taken by 0.1%", "每点腐化值降低目标对持有者的伤害0.05%，提升持有者对目标的伤害0.1%")
            .itemLanguageTooltip(3, "Immunity to Corruption", "免疫腐化")
            .itemLanguageTooltip(-1, "\"Potential not fully tapped\"", "“潜力未完全开发”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<ShadowWoodSoul> ShadowWoodSoulItem = register("shadow_wood_soul", ShadowWoodSoul::new)
            .itemLanguage("Shadow Wood Enchantment", "阴影木魔石")
            .itemLanguageTooltip(1, "Apply a Siphon Mark to a random nearby target every 5s", "每5秒为持有者周围随机一个目标附加汲取标记")
            .itemLanguageTooltip(2, "When a marked target attacks you, siphon 2 HP and remove mark", "带有汲取标记的目标攻击持有者时，持有者将汲取目标2点生命值并移除标记")
            .itemLanguageTooltip(-1, "\"Surprisingly clean\"", "“出奇的干净”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<PalmWoodSoul> PalmWoodSoulItem = register("palm_wood_soul", PalmWoodSoul::new)
            .itemLanguage("Palm Wood Enchantment", "棕榈木魔石")
            .itemLanguageTooltip(1, "Attacks ignite targets or extend burn duration up to 30s", "持有者攻击时，点燃目标或延长目标的燃烧时间，至多延长至30秒")
            .itemLanguageTooltip(2, "Fire damage taken by nearby enemies increased to 150%", "持有者周围的目标受到的火焰伤害提升至150%")
            .itemLanguageTooltip(-1, "\"Surprisingly peaceful\"", "“出奇的宁静”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<PearlWoodSoul> PearlWoodSoulItem = register("pearl_wood_soul", PearlWoodSoul::new)
            .itemLanguage("Pearl Wood Enchantment", "珍珠木魔石")
            .itemLanguageTooltip(1, "Crit damage multiplier increased to 130%", "持有者攻击暴击时，暴击伤害系数提升至130%")
            .itemLanguageTooltip(2, "Non-crits have a 25% chance to be re-rolled as a crit", "持有者攻击没有暴击时，额外进行25%概率的暴击判定")
            .itemLanguageTooltip(-1, "\"Too little, too late...\"", "“太少了，太晚了……”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    // ============================================================
    // 泰拉之力 · 魔石分段
    // ============================================================
    public static final DeferredItem<CopperSoul> CopperSoulItem = register("copper_soul", CopperSoul::new)
            .itemLanguage("Copper Enchantment", "铜魔石")
            .itemLanguageTooltip(1, "Chance to summon lightning on attack; 2s cooldown", "持有者攻击时有概率召唤闪电攻击目标，该效果有2秒冷却时间")
            .itemLanguageTooltip(2, "Lightning pulls nearby enemies into the strike", "如果目标周围有其他目标，会被吸引到闪电位置一同被攻击")
            .itemLanguageTooltip(3, "Increased chance against targets in rain or water", "命中雨中或水中的目标时释放闪电的概率增加")
            .itemLanguageTooltip(-1, "\"Its music is electric\"", "“它的音乐还是电音”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<TinSoul> TinSoulItem = register("tin_soul", TinSoul::new)
            .itemLanguage("Tin Enchantment", "锡魔石")
            .itemLanguageTooltip(1, "Min crit chance to 10%, min crit damage to 200%", "将持有者的暴击率下限设为10%，暴击伤害下限设为200%")
            .itemLanguageTooltip(2, "Each crit adds +10% extra crit chance, max 60%", "每次持有者暴击时都会增加10%额外暴击率，额外暴击率的最大值为60%")
            .itemLanguageTooltip(3, "Extra crit chance halved when damaged", "持有者受伤会使额外暴击率减半")
            .itemLanguageTooltip(-1, "\"Crits are back\"", "“暴击回归”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<IronSoul> IronSoulItem = register("iron_soul", IronSoul::new)
            .itemLanguage("Iron Enchantment", "铁魔石")
            .itemLanguageTooltip(1, "Attracts nearby items", "持有者会吸引周围的物品")
            .itemLanguageTooltip(2, "Damage taken reduced by 20% for 5s after picking up an item", "拾取物品后的5秒内受到的伤害减少20%")
            .itemLanguageTooltip(-1, "\"Strike while the iron is hot\"", "“趁热打铁”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<LeadSoul> LeadSoulItem = register("lead_soul", LeadSoul::new)
            .itemLanguage("Lead Enchantment", "铅魔石")
            .itemLanguageTooltip(1, "Attacks have a chance to inflict Lead Poisoning", "持有者攻击有几率造成铅中毒")
            .itemLanguageTooltip(2, "Immunity to Lead Poisoning", "免疫铅中毒")
            .itemLanguageTooltip(-1, "\"Not recommended for consumption\"", "“不建议食用”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<SilverSoul> SilverSoulItem = register("silver_soul", SilverSoul::new)
            .itemLanguage("Silver Enchantment", "银魔石")
            .itemLanguageTooltip(1, "+10 defense while blocking", "持有者举盾状态会增加10点防御力")
            .itemLanguageTooltip(2, "Blocking within 0.2s reflects 200% damage", "持有者举盾后0.2秒抵挡攻击会反弹目标200%伤害")
            .itemLanguageTooltip(3, "Damage increased to 150% for 1s after reflecting", "持有者反弹后1秒内造成的伤害提高至150%")
            .itemLanguageTooltip(-1, "\"Reflection\"", "“反射”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<TungstenSoul> TungstenSoulItem = register("tungsten_soul", TungstenSoul::new)
            .itemLanguage("Tungsten Enchantment", "钨魔石")
            .itemLanguageTooltip(1, "+50% attack reach", "持有者增加50%攻击距离")
            .itemLanguageTooltip(2, "+100% size of held items", "持有者手持物品大小提升100%")
            .itemLanguageTooltip(-1, "\"Bigger is better\"", "“大就是好”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<ObsidianSoul> ObsidianSoulItem = register("obsidian_soul", ObsidianSoul::new)
            .itemLanguage("Obsidian Enchantment", "黑曜石魔石")
            .itemLanguageTooltip(1, "Generates fireballs to attack nearby enemies", "持有者周围会产生火球攻击附近敌人")
            .itemLanguageTooltip(2, "Better vision in lava", "持有者在熔岩中时获得更好的视野")
            .itemLanguageTooltip(3, "Sets minimum armor to 8", "将持有者的护甲值下限提升至8点")
            .itemLanguageTooltip(4, "Immunity to fire and lava damage", "免疫火焰与熔岩伤害")
            .itemLanguageTooltip(-1, "\"The Earth is calling\"", "“大地在呼唤”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    // ============================================================
    // 大地之力 · 魔石分段
    // ============================================================
    public static final DeferredItem<CobaltSoul> CobaltSoulItem = register("cobalt_soul", CobaltSoul::new)
            .itemLanguage("Cobalt Enchantment", "钴蓝魔石")
            .itemLanguageTooltip(1, "Inflicts Oiled on nearby targets", "对持有者附近的目标施加浸油减益")
            .itemLanguageTooltip(2, "Oiled targets take greatly increased fire damage", "被浸油影响的目标受到的火焰伤害大幅增加")
            .itemLanguageTooltip(3, "Generate a small explosion and apply Oiled when damaged; 3s cooldown", "受到伤害时，在持有者位置产生小爆炸，并对附近的目标施加浸油减益，该效果有3秒冷却时间")
            .itemLanguageTooltip(4, "Immunity to Oiled", "免疫浸油")
            .itemLanguageTooltip(-1, "\"Can't believe it's not Palladium\"", "“真不敢相信这竟然不是钯金”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<PalladiumSoul> PalladiumSoulItem = register("palladium_soul", PalladiumSoul::new)
            .itemLanguage("Palladium Enchantment", "钯金魔石")
            .itemLanguageTooltip(1, "Attacks have a chance to grant 2s of Regeneration", "持有者攻击时有概率给予2秒生命恢复增益")
            .itemLanguageTooltip(2, "Stacks refresh duration and increase Regen level, up to level 3", "每次获取增益会刷新生命恢复的持续时间，并提升生命恢复增益的等级，上限3级")
            .itemLanguageTooltip(3, "Gain Absorption for every 10 HP recovered", "持有者每恢复10生命值额外获取伤害吸收增益")
            .itemLanguageTooltip(-1, "\"You feel your wounds slowly healing\"", "“你感到你的伤口在慢慢愈合”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<MithrilSoul> MithrilSoulItem = register("mithril_soul", MithrilSoul::new)
            .itemLanguage("Mithril Enchantment", "秘银魔石")
            .itemLanguageTooltip(1, "Attacking after 5s of idle time grants +50% attack speed", "持有者在5秒不攻击后开始攻击会获得攻击速度增益，提升50%攻击速度")
            .itemLanguageTooltip(2, "Buff lost 3s after attacking", "持有者攻击目标后3秒失去增益")
            .itemLanguageTooltip(-1, "\"Weapon knowledge seeps into your mind\"", "“你感觉武器的知识渗透进你的脑海中”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<OrichalcumSoul> OrichalcumSoulItem = register("orichalcum_soul", OrichalcumSoul::new)
            .itemLanguage("Orichalcum Enchantment", "山铜魔石")
            .itemLanguageTooltip(1, "Chance to summon petals on hit dealing [1 + 5% damage] magic damage and 5s Orichalcum Poisoning", "持有者攻击时有概率召唤花瓣落到目标的身上，造成额外[1+5%造成伤害]的魔法伤害并施加5秒山铜中毒减益")
            .itemLanguageTooltip(2, "Orichalcum Poisoning deals continuous damage and increases poison damage taken to 350%", "山铜中毒会对目标持续造成魔法伤害，并将目标受到的所有毒性伤害提升至350%")
            .itemLanguageTooltip(3, "Immunity to Orichalcum Poisoning", "免疫山铜中毒")
            .itemLanguageTooltip(-1, "\"Nature blesses you\"", "“自然祝福着你”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<AdamantiteSoul> AdamantiteSoulItem = register("adamantite_soul", AdamantiteSoul::new)
            .itemLanguage("Adamantite Enchantment", "精金魔石")
            .itemLanguageTooltip(1, "Each attack grants 5% attack speed, max 8 stacks; reset after 5s", "持有者每次攻击会提高5%攻击速度，最高叠加8层，5秒不攻击清空")
            .itemLanguageTooltip(2, "At max stacks, attacks have a chance to cause targets to lose aggro", "攻击速度增益达到最大值时，持有者的攻击有概率使目标丢失索敌")
            .itemLanguageTooltip(-1, "\"Chaos\"", "“混乱”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<TitaniumSoul> TitaniumSoulItem = register("titanium_soul", TitaniumSoul::new)
            .itemLanguage("Titanium Enchantment", "钛金魔石")
            .itemLanguageTooltip(1, "Generate a Titanium Shield after 20s of no damage, blocking next hit", "持有者每20秒不受伤害会生成一个钛金护盾，免疫下次受到伤害")
            .itemLanguageTooltip(2, "If shield is down, gain up to 25% move speed based on missing health", "钛金护盾未生成时，根据持有者已损生命值比例提升移动速度，最高增加25%移动速度")
            .itemLanguageTooltip(-1, "\"Who needs to dodge with absolute defense?\"", "“有了绝对防御后，谁还需要躲避呢？”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    // ============================================================
    // 自然之力 · 魔石分段
    // ============================================================
    public static final DeferredItem<CrimsonSoul> CrimsonSoulItem = register("crimson_soul", CrimsonSoul::new)
            .itemLanguage("Crimson Enchantment", "猩红魔石")
            .itemLanguageTooltip(1, "Siphon 10% of health lost by nearby targets", "周围的目标每次受伤时，持有者都会汲取目标失去生命值的10%")
            .itemLanguageTooltip(2, "Siphon scales up to 40% based on missing health", "持有者已损生命值比例越高，汲取的生命值越多，至多汲取目标失去生命值的40%")
            .itemLanguageTooltip(-1, "\"Reborn from the blood of enemies\"", "“你从敌人的鲜血中重生”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<LavaSoul> LavaSoulItem = register("lava_soul", LavaSoul::new)
            .itemLanguage("Lava Enchantment", "熔岩魔石")
            .itemLanguageTooltip(1, "Ignites nearby targets", "引燃持有者附近的目标")
            .itemLanguageTooltip(2, "Immunity to Lava", "免疫熔岩")
            .itemLanguageTooltip(-1, "\"They will feel the wrath of Hell\"", "“他们将感受到地狱的愤怒”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<RainCloudSoul> RainCloudSoulItem = register("rain_cloud_soul", RainCloudSoul::new)
            .itemLanguage("Rain Cloud Enchantment", "雨云魔石")
            .itemLanguageTooltip(1, "Chance to strike targets with lightning when damaged", "受到伤害时有概率召唤雷电劈向目标")
            .itemLanguageTooltip(2, "Immunity to lightning damage", "免疫雷击伤害")
            .itemLanguageTooltip(-1, "\"Come again another day\"", "“改日再来”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<FrostSoul> FrostSoulItem = register("frost_soul", FrostSoul::new)
            .itemLanguage("Frost Enchantment", "冰霜魔石")
            .itemLanguageTooltip(1, "Generates 1 snowball per second, up to 10", "每秒在持有者周围生成1个雪球，最多可同时存在10个")
            .itemLanguageTooltip(2, "Fires a snowball at nearby targets every 0.25s", "每0.25秒发射1个雪球攻击附近的目标")
            .itemLanguageTooltip(3, "Greatly increases snowball damage", "大幅提升持有者雪球的伤害")
            .itemLanguageTooltip(4, "In cold biomes, generation and fire rate increased by 60%", "在寒冷群系时，雪球的生成和发射间隔降低60%")
            .itemLanguageTooltip(-1, "\"Let's coat the world in a thick layer of ice\"", "“让我们给这个世界披上一层厚厚的冰衣”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<GreenSoul> GreenSoulItem = register("green_soul", GreenSoul::new)
            .itemLanguage("Green Enchantment", "叶绿魔石")
            .itemLanguageTooltip(1, "Grants dash ability", "使持有者获得冲刺能力")
            .itemLanguageTooltip(2, "Extends flight time", "延长持有者的飞行时间")
            .itemLanguageTooltip(3, "Chlorophyte Crystal attacks nearby random targets", "产生一个跟随持有者的叶绿水晶攻击附近随机目标")
            .itemLanguageTooltip(4, "Crystal hits inflict Glowing", "叶绿水晶时对攻击的目标会施加发光效果")
            .itemLanguageTooltip(-1, "\"The essence of the jungle condenses into crystals\"", "“丛林的精华在你周围凝结成晶体”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<MushroomSoul> MushroomSoulItem = register("mushroom_soul", MushroomSoul::new)
            .itemLanguage("Mushroom Enchantment", "蘑菇魔石")
            .itemLanguageTooltip(1, "Mushroom Stew heals an additional [4 + 10% missing HP]", "持有者食用蘑菇煲时会额外恢复[4+10%持有者已损生命值]的生命值")
            .itemLanguageTooltip(2, "Enemies killed drop mushrooms", "目标被持有者击杀后，会掉落蘑菇")
            .itemLanguageTooltip(-1, "\"Made with real mushrooms!\"", "“是用真的蘑菇做的！”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    // ============================================================
    // 生命之力 · 魔石分段
    // ============================================================
    public static final DeferredItem<BeeSoul> BeeSoulItem = register("bee_soul", BeeSoul::new)
            .itemLanguage("Bee Enchantment", "蜜蜂魔石")
            .itemLanguageTooltip(1, "Allows short flight", "允许持有者短时间飞行")
            .itemLanguageTooltip(2, "Nearby bees recover HP and won't attack you", "使持有者附近的蜜蜂逐渐恢复生命值，蜜蜂不会攻击持有者")
            .itemLanguageTooltip(3, "Slowly recover HP when touching flowers", "持有者接触花朵时缓慢恢复生命值")
            .itemLanguageTooltip(4, "Nearby bees assist in attacking your target", "持有者攻击目标时，周围的蜜蜂会协同攻击目标")
            .itemLanguageTooltip(5, "Drinking honey heals 5% max HP and summons a bee", "持有者饮用蜂蜜时，额外恢复5%持有者最大生命值的生命值并召唤一只蜜蜂")
            .itemLanguageTooltip(6, "You cannot damage bees", "持有者不会对蜜蜂造成伤害")
            .itemLanguageTooltip(7, "Immunity to fall damage", "免疫摔落伤害")
            .itemLanguageTooltip(-1, "\"According to all known laws of aviation, there is no way a bee should be able to fly\"", "“根据目前所知的所有航空原理，蜜蜂应该根本不可能会飞”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<BeetleSoul> BeetleSoulItem = register("beetle_soul", BeetleSoul::new)
            .itemLanguage("Beetle Enchantment", "甲虫魔石")
            .itemLanguageTooltip(1, "Allows short flight", "允许持有者短时间飞行")
            .itemLanguageTooltip(2, "Gain Beetle Endurance every 7s, max 2 stacks", "持有者每7秒获得1层甲虫耐力，最多叠加2层")
            .itemLanguageTooltip(3, "Each stack gives +15% DR; lost when damaged", "每层甲虫耐力增加持有者15%伤害减免，每次受到伤害时失去1层甲虫耐力")
            .itemLanguageTooltip(4, "Gain Beetle Might on hit, max 4 stacks", "持有者攻击目标时获得1层甲虫力量，最多叠加4层")
            .itemLanguageTooltip(5, "Each stack gives +3 Armor Pen; lose 1 stack per second", "每层甲虫力量增加持有者3点护甲穿透，每秒失去1层甲虫力量")
            .itemLanguageTooltip(6, "Arthropods will not attack you", "节肢动物们不会主动攻击持有者")
            .itemLanguageTooltip(-1, "\"Invisible fecal life flows through your veins\"", "“你的血管里流淌着看不见的粪便生命”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<PumpkinSoul> PumpkinSoulItem = register("pumpkin_soul", PumpkinSoul::new)
            .itemLanguage("Pumpkin Enchantment", "南瓜魔石")
            .itemLanguageTooltip(1, "Plant pumpkin seeds while moving if on suitable soil", "如果脚下可以种植南瓜，持有者在移动时将会种下南瓜种子")
            .itemLanguageTooltip(2, "Stepping on ripe pumpkins causes them to explode", "踩在成熟的南瓜上时会使南瓜产生爆炸")
            .itemLanguageTooltip(3, "Speeds up pumpkin growth nearby", "加速持有者周围南瓜的生长速度")
            .itemLanguageTooltip(-1, "\"Your sudden craving for pumpkins will never be satisfied\"", "“你对南瓜的突发渴望永远不会得到满足”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<SpiderSoul> SpiderSoulItem = register("spider_soul", SpiderSoul::new)
            .itemLanguage("Spider Enchantment", "蜘蛛魔石")
            .itemLanguageTooltip(1, "Minions gain +20% crit rate and 150% crit damage", "使持有者的仆从获得20%暴击率和150%暴击伤害")
            .itemLanguageTooltip(2, "Minion crit damage bonus capped at 100", "持有者仆从暴击增加的伤害上限为100点")
            .itemLanguageTooltip(-1, "\"Arachnophobe? Kill him with spiders!\"", "“蜘蛛恐惧者？作为惩罚，让他被蜘蛛干掉吧！”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<TurtleSoul> TurtleSoulItem = register("turtle_soul", TurtleSoul::new)
            .itemLanguage("Turtle Enchantment", "乌龟魔石")
            .itemLanguageTooltip(1, "Reflects 60% contact damage; scales down with distance to 10%", "持有者被目标攻击时反弹60%接触伤害，距离越远反弹的伤害越低，至多衰减至接触伤害of the 10%")
            .itemLanguageTooltip(2, "At <50% HP: reflects 80% damage regardless of distance with knockback", "持有者生命值低于50%时，反弹的伤害提升至80%接触伤害，反弹伤害不再随距离衰减，反弹伤害时附加击退")
            .itemLanguageTooltip(3, "At <25% HP: gain Shell granting 4% max HP damage block", "持有者生命值低于25%时，持有者会获得龟壳，龟壳存在时，持有者获得相当于4%最大生命值的伤害格挡")
            .itemLanguageTooltip(4, "Shell vanishes >25% HP or breaks on lethal hit; 120s cooldown", "龟壳会在持有者高于25%生命值时消失，或在受到大于持有者当前生命值的伤害时破碎，并完全抵挡此伤害，龟壳破碎后120秒不会再生成龟壳")
            .itemLanguageTooltip(5, "Damaged: chance to release cacti; kills burst into more cacti", "受到攻击时，有概率释放出一堆仙人掌，被仙人掌扎死的敌人会爆裂出更多仙人掌")
            .itemLanguageTooltip(6, "Immunity to cactus damage", "免疫仙人掌伤害")
            .itemLanguageTooltip(-1, "\"You have a sudden urge to hide in a shell\"", "“你突然有一种想躲进壳里的冲动”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    // ============================================================
    // 心灵之力 · 魔石分段
    // ============================================================
    public static final DeferredItem<ForbiddenSoul> ForbiddenSoulItem = register("forbidden_soul", ForbiddenSoul::new)
            .itemLanguage("Forbidden Enchantment", "禁戒魔石")
            .itemLanguageTooltip(1, "Press 'Storm' to summon a sandstorm at cursor for 5s; 30s cooldown", "按下“风暴”键在持有者准星方块召唤风暴，每秒吸引风暴附近的目标，持续5秒，该效果有30秒冷却时间")
            .itemLanguageTooltip(2, "Immunity to storm attraction", "免疫风暴吸引")
            .itemLanguageTooltip(-1, "\"Walk like an Egyptian\"", "“走路像个埃及人”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<HolySoul> HolySoulItem = register("holy_soul", HolySoul::new)
            .itemLanguage("Holy Enchantment", "神圣魔石")
            .itemLanguageTooltip(1, "Healing received increased to 140%", "持有者受到的治疗提升至140%")
            .itemLanguageTooltip(2, "Healing creates a shockwave knocking back nearby targets", "持有者受到治疗时会产生冲击波，击退附近的目标")
            .itemLanguageTooltip(-1, "\"Bring it on\"", "《尽管放马过来》")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<AncientHolySoul> AncientHolySoulItem = register("ancient_holy_soul", AncientHolySoul::new)
            .itemLanguage("Ancient Holy Enchantment", "远古神圣魔石")
            .itemLanguageTooltip(1, "Min melee attack strength to 35%", "持有者近战攻击时的最低攻击强度提升至35%")
            .itemLanguageTooltip(-1, "\"Do you have the power to wield me?\"", "“你有足够的力量驾驭我吗？”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<TekeSoul> TekeSoulItem = register("teke_soul", TekeSoul::new)
            .itemLanguage("Teke Enchantment", "提基魔石")
            .itemLanguageTooltip(1, "Minions revive at 25% HP on death; 300s cooldown", "持有者仆从死亡时复活，恢复25%持有者最大生命值的生命值并清除减益效果，该效果有300秒冷却时间")
            .itemLanguageTooltip(-1, "\"Aku Aku!\"", "“Aku Aku!”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<GhostSoul> GhostSoulItem = register("ghost_soul", GhostSoul::new)
            .itemLanguage("Ghost Enchantment", "幽魂魔石")
            .itemLanguageTooltip(1, "Revive at 25% HP and cleanse debuffs on death; 600s cooldown", "持有者死亡时复活，恢复25%持有者最大生命值的生命值并清除减益效果，该效果有600秒冷却时间")
            .itemLanguageTooltip(2, "On revive, deal 100% max HP magic damage to nearby enemies", "持有者复活时对周围所有目标造成100%持有者最大生命值的魔法伤害")
            .itemLanguageTooltip(3, "Attacks steal Soul Energy from targets", "持有者每次攻击时都会从目标夺取灵魂能量")
            .itemLanguageTooltip(4, "Each Soul Energy layer increases max HP by 1%", "每层额外的灵魂能量提升持有者1%最大生命值")
            .itemLanguageTooltip(5, "Each layer lost reduces max HP by 1%", "每失去一层灵魂能量降低1%最大生命值")
            .itemLanguageTooltip(6, "Max 100 energy, min -20 energy", "最多失去20层灵魂能量，最多获取100层灵魂能量")
            .itemLanguageTooltip(-1, "\"Their life force will destroy themselves\"", "“他们的生命力将毁灭他们自己”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    // ============================================================
    // 意志之力 · 魔石分段
    // ============================================================
    public static final DeferredItem<GoldSoul> GoldSoulItem = register("gold_soul", GoldSoul::new)
            .itemLanguage("Gold Enchantment", "金魔石")
            .itemLanguageTooltip(1, "Attacks inflict Midas", "持有者攻击会造成迈达斯减益")
            .itemLanguageTooltip(2, "Midas-afflicted enemies drop gold ingots on death", "受迈达斯影响的目标在死亡时会掉落金锭")
            .itemLanguageTooltip(3, "Press 'Gold Stasis' to surround yourself with golden rings for 5s; 120s CD", "按下“金身”键会将持有者包裹在两道金环中，金环持续5秒，该效果有120秒冷却")
            .itemLanguageTooltip(4, "While in Gold Stasis, 40% damage taken is transferred as magic damage to nearby enemies", "被金环包裹时，持有者40%受到伤害将会以魔法伤害的形式转移至周围其他目标")
            .itemLanguageTooltip(5, "Each gold ingot in inventory adds 0.1% transfer damage, up to 95%", "持有者背包中每有一个金锭会提升0.1%转移伤害，转移伤害上限为95%受到伤害")
            .itemLanguageTooltip(6, "Immunity to Midas", "免疫迈达斯")
            .itemLanguageTooltip(-1, "\"Money makes the world go round\"", "“有钱能使鬼推磨”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<PlatinumSoul> PlatinumSoulItem = register("platinum_soul", PlatinumSoul::new)
            .itemLanguage("Platinum Enchantment", "铂金魔石")
            .itemLanguageTooltip(1, "20% chance for enemies to drop x5 loot", "目标死亡时有20%的几率获得5倍的战利品")
            .itemLanguageTooltip(-1, "\"Priceless treasure\"", "“无价之宝”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<GladiatorSoul> GladiatorSoulItem = register("gladiator_soul", GladiatorSoul::new)
            .itemLanguage("Gladiator Enchantment", "角斗士魔石")
            .itemLanguageTooltip(1, "Summon arrow rain every 24 attacks", "持有者每24次攻击召唤一次箭雨攻击目标")
            .itemLanguageTooltip(2, "Arrow rain damage to 180% if <3 nearby targets", "持有者周围的目标数少于3个时，箭雨的基础伤害提升至180%")
            .itemLanguageTooltip(3, "If >=3 targets: -10% damage taken, +110% damage dealt", "持有者周围的目标数大于或等于3个时，持有者受到的伤害减少10%，造成的伤害提升至110%")
            .itemLanguageTooltip(-1, "\"Are you not entertained?\"", "“你不觉得刺激吗？”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<RedRidingSoul> RedRidingSoulItem = register("red_riding_soul", RedRidingSoul::new)
            .itemLanguage("Red Riding Enchantment", "红色骑术魔石")
            .itemLanguageTooltip(1, "Damage increased to 120% against armored targets", "持有者对有护甲的目标造成伤害提升至120%")
            .itemLanguageTooltip(2, "Each attack grants 1 Guerrilla stack, max 10", "持有者每次攻击时叠加1层游击，上限10层")
            .itemLanguageTooltip(3, "Each stack adds +1 Armor Pen and +1% move speed", "每层游击提升持有者1点护甲穿透和1%移动速度")
            .itemLanguageTooltip(4, "Dash strength increased at max stacks", "游击层数达到上限时，提升持有者冲刺强度")
            .itemLanguageTooltip(5, "Stacks cleared when taking damage", "持有者受到伤害时清空游击层数")
            .itemLanguageTooltip(-1, "\"Big Bad Red Riding Hood!\"", "“小红帽，大坏蛋！”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<ValhallaKnightSoul> ValhallaKnightSoulItem = register("valhalla_knight_soul", ValhallaKnightSoul::new)
            .itemLanguage("Valhalla Knight Enchantment", "英灵殿骑士魔石")
            .itemLanguageTooltip(1, "Increased mount speed and jump height", "提升持有者骑乘坐骑的速度和跳跃高度")
            .itemLanguageTooltip(2, "Mount and rider gain 15 defense", "骑乘坐骑时，持有者与坐骑获得15点防御")
            .itemLanguageTooltip(3, "Mount gains immunity to fall damage", "使坐骑免疫坠落伤害")
            .itemLanguageTooltip(4, "Healing received increased to 115%", "持有者受到的治疗提升至115%")
            .itemLanguageTooltip(-1, "\"The call of Valhalla\"", "“瓦尔哈拉的呼唤”")
            .itemTag(FargoSoulItemTagsRegister.SectionEnchantment)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    // ============================================================
    // 力级 · 力分段
    // ============================================================

    public static final DeferredItem<EarthPower> EarthPowerItem = register("earth_power", EarthPower::new)
            .itemLanguage("Earth Power", "大地之力")
            .itemLanguageTooltip(1, "Enhanced Adamantite - +15% move speed at max attack speed stacks", "强化-精金-攻击速度增益达到最大值时，移动速度额外提升15%")
            .itemLanguageTooltip(2, "Enhanced Cobalt - Reduced explosion CD, increased radius and damage, longer Oiled duration", "强化-钴蓝-爆炸冷却减少，爆炸范围提升，爆炸伤害提升，延长爆炸所施加浸油减益的时间")
            .itemLanguageTooltip(3, "Enhanced Mithril - Attack speed buff duration increased to 6s", "强化-秘银-攻击速度增益的持续时间提升至6秒")
            .itemLanguageTooltip(4, "Enhanced Orichalcum - Increased petal chance, Orichalcum Poisoning level +1", "强化-山铜-召唤花瓣的概率提升，施加山铜中毒的等级+1")
            .itemLanguageTooltip(5, "Enhanced Palladium - Increased life regen chance, max life regen level +1", "强化-钯金-获取生命恢复增益的概率提升，生命恢复增益的等级上限+1")
            .itemLanguageTooltip(6, "Enhanced Titanium - Titanium Shield knocks back nearby enemies when blocking", "强化-钛金-钛金护盾抵挡伤害时，击退周围的目标")
            .itemLanguageTooltip(7, "Build Earth Energy after 2s of not attacking; consumed on attack", "不攻击2秒后会积聚大地能量，攻击会消耗能量")
            .itemLanguageTooltip(8, "At max energy: +100% Attack Speed, +5 HP/s Regen, +25% DR", "攻击速度，生命回复，伤害减免会随能量积攒而增加，至多增加100%攻击速度，每秒5点生命恢复，25%伤害减免")
            .itemLanguageTooltip(9, "Attacks deal bonus damage based on energy, up to 40%", "充能后攻击会造成攻击伤害一定比例的额外伤害，随能量积攒而增加比例，至多40%")
            .itemLanguageTooltip(-1, "\"Gaia's blessing shines upon you\"", "“盖亚的祝福照耀着你”")
            .itemTag(FargoSoulItemTagsRegister.SectionPower)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<ForestPower> ForestPowerItem = register("forest_power", ForestPower::new)
            .itemLanguage("Forest Power", "森林之力")
            .itemLanguageTooltip(1, "Enhanced Ebonwood - Corruption limit increased to 200", "强化-乌木-腐化目标的上限提升至200点")
            .itemLanguageTooltip(2, "Enhanced Palm Wood - Attacks on burning targets may cause explosions", "强化-棕榈木-持有者攻击燃烧的目标时，有概率产生小型爆炸")
            .itemLanguageTooltip(3, "Enhanced Pearlwood - Additional critical strike check count +1", "强化-珍珠木-额外进行的暴击判定次数+1")
            .itemLanguageTooltip(4, "Enhanced Boreal Wood - Increases snowball generation speed", "强化-针叶木-提升产生雪球的速度")
            .itemLanguageTooltip(5, "Enhanced Rich Mahogany - Increases traction strength", "强化-红木-提升牵引力度")
            .itemLanguageTooltip(6, "Enhanced Shadewood - Life siphoned increased to 3 points", "强化-阴影木-汲取的生命值提升至3点")
            .itemLanguageTooltip(7, "Enhanced Wood - Further increases discounts", "强化-木-进一步提升折扣")
            .itemLanguageTooltip(8, "Grants a Wither Aura", "使持有者拥有凋零光环")
            .itemLanguageTooltip(9, "Hitting enemies in the aura fires snowballs; 0.25s cooldown", "击中光环内的敌人时会发射雪球，该效果有0.25秒冷却时间")
            .itemLanguageTooltip(10, "Attacking Withered enemies deals 15 bonus damage", "持有者攻击被凋零效果影响的敌人时，造成的伤害额外提升15点")
            .itemLanguageTooltip(11, "Gain 30% DR and 200% Thorns while in the air; 8s cooldown", "在空中时，获得30%伤害减免和200%荆棘效果，该效果有8秒冷却时间")
            .itemLanguageTooltip(12, "Gain 20% DR against enemies you have previously killed", "持有者受到曾击杀过的目标伤害时，获得20%伤害减免")
            .itemLanguageTooltip(-1, "\"Very hard\"", "“很硬”")
            .itemTag(FargoSoulItemTagsRegister.SectionPower)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<LifePower> LifePowerItem = register("life_power", LifePower::new)
            .itemLanguage("Life Power", "生命之力")
            .itemLanguageTooltip(1, "Enhanced Turtle - Increased cactus burst chance, count, and damage", "强化-乌龟-爆出仙人掌的概率提升，数量提升，伤害提升")
            .itemLanguageTooltip(2, "Enhanced Bee - Bees and flower-contact healing speed increased", "强化-蜜蜂-周围蜜蜂的恢复速度加快，持有者接触花朵时恢复生命值的速度加快")
            .itemLanguageTooltip(3, "Enhanced Beetle - Max Beetle Endurance +1, Max Beetle Might +2", "强化-甲虫-甲虫耐力层数上限+1，甲虫力量层数上限+2")
            .itemLanguageTooltip(4, "Enhanced Pumpkin - Explosion damage increased, enemies hit drop pumpkin seeds", "强化-南瓜-南瓜的爆炸伤害提升，被爆炸伤害的目标会爆出南瓜子")
            .itemLanguageTooltip(5, "Enhanced Spider - Minion crit to 40% and 200% damage, no cap on minion crit bonus", "强化-蜘蛛-仆从暴击率提升至40%，仆从暴击伤害提升至200%，仆从暴击增伤不再有上限")
            .itemLanguageTooltip(6, "Provides powerful wing flight", "提供强大的翅膀飞行能力")
            .itemLanguageTooltip(7, "Heal when touching flowers, spawning bees and granting 10s Ambrosia", "触碰花朵时会回复血量，同时产生大量蜜蜂，获得10秒仙馔密酒增益")
            .itemLanguageTooltip(8, "Ambrosia: +30% Damage, +20% DR, and +5 HP/s Regen", "仙馔密酒增益增加30%伤害，增加20%伤害减免和5每秒生命恢复")
            .itemLanguageTooltip(9, "Ambrosia increases minion crit damage to 400%", "仙馔密酒增益会使仆从的暴击伤害提升至400%")
            .itemLanguageTooltip(10, "Reflects 500% contact damage", "反弹500%的接触伤害")
            .itemLanguageTooltip(-1, "\"Few creatures dare defy your will\"", "“罕有生灵敢违背你的意愿”")
            .itemTag(FargoSoulItemTagsRegister.SectionPower)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<NaturePower> NaturePowerItem = register("nature_power", NaturePower::new)
            .itemLanguage("Nature Power", "自然之力")
            .itemLanguageTooltip(1, "Enhanced Crimson - Siphon range increased", "强化-猩红-汲取范围提升")
            .itemLanguageTooltip(2, "Enhanced Frost - Max snowballs increased to 20", "强化-冰霜-雪球的存在上限提升至20个")
            .itemLanguageTooltip(3, "Enhanced Chlorophyte - Attack interval reduced, inflicts Poison", "强化-叶绿-攻击间隔降低，并且会对目标施加中毒")
            .itemLanguageTooltip(4, "Enhanced Lava - Burning targets nearby may explode", "强化-熔岩-持有者周围燃烧的目标有概率产生爆炸")
            .itemLanguageTooltip(5, "Enhanced Mushroom - Stews grant Mush-Power for 10s", "强化-蘑菇-持有者食用蘑菇煲后获得蘑菇力量增益持续10秒")
            .itemLanguageTooltip(6, "Enhanced Rain - Lightning chance increased during rain", "强化-雨云-在雨天时，召唤雷电的概率提升")
            .itemLanguageTooltip(7, "Nature Aura: deals damage and siphons HP every 0.5s; damage x4 if not hit for 4s", "持有者拥有一个自然光环，每过0.5秒对光环内的敌人造成伤害并吸取生命值，基础伤害为2，每4秒不受击可增加一倍伤害，最高4倍")
            .itemLanguageTooltip(8, "Summons 5 Chlorophyte Crystals firing 20 damage lasers", "召唤5个围绕持有者旋转的叶绿水晶，水晶会向敌怪发射激光，激光基础伤害为20")
            .itemLanguageTooltip(9, "Crystal hits spawn 5 mushrooms dealing 20% of laser damage", "叶绿水晶击中后会产生5个蘑菇，蘑菇伤害为激光造成伤害的20%")
            .itemLanguageTooltip(10, "Mushrooms restore +50 HP; Mush-Power increases aura size", "持有者食用蘑菇可额外恢复50点生命值，拥有蘑菇力量时增加光环大小")
            .itemLanguageTooltip(11, "Attacks slow targets", "持有者攻击会使向目标施加缓慢")
            .itemLanguageTooltip(12, "Ranged damage taken reduced by 15", "远程射弹对持有者造成的伤害降低15点")
            .itemLanguageTooltip(-1, "\"Travel through every secret corner of the wilderness\"", "“走遍荒野的每一个秘密角落”")
            .itemTag(FargoSoulItemTagsRegister.SectionPower)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<SpiritPower> SpiritPowerItem = register("spirit_power", SpiritPower::new)
            .itemLanguage("Spirit Power", "心灵之力")
            .itemLanguageTooltip(1, "Enhanced Ancient Hallowed - Min melee attack strength to 50%", "强化-远古神圣-持有者近战攻击时的最低攻击强度提升至50%")
            .itemLanguageTooltip(2, "Enhanced Forbidden - Storm duration 8s, increased range", "强化-禁戒-风暴的持续时间提升至8秒，影响范围提升")
            .itemLanguageTooltip(3, "Enhanced Spectre - Soul Energy cap increased to 200", "强化-幽魂-持有者获取灵魂能量的上限提升至200层")
            .itemLanguageTooltip(4, "Enhanced Hallowed - Shockwave radius increased", "强化-神圣-冲击波范围提升")
            .itemLanguageTooltip(5, "Enhanced Tiki - Minion revive CD reduced to 180s", "强化-提基-仆从复活冷却降低至180秒")
            .itemLanguageTooltip(6, "Healing received increased to 170%", "持有者受到的治疗量提升至170%")
            .itemLanguageTooltip(7, "Gain 5 Soul Energy per second; excess energy heals you", "每秒获得5层灵魂能量，溢出的灵魂能量会改为治疗持有者")
            .itemLanguageTooltip(8, "Greatly extends flight time", "大幅延长持有者的飞行时间")
            .itemLanguageTooltip(-1, "\"Drifting away like a transcended immortal\"", "“飘飘乎如遗世独立，羽化而登仙”")
            .itemTag(FargoSoulItemTagsRegister.SectionPower)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<TerraPower> TerraPowerItem = register("terra_power", TerraPower::new)
            .itemLanguage("Terra Power", "泰拉之力")
            .itemLanguageTooltip(1, "Enhanced Copper - Summons two lightning bolts with 200% damage", "强化-铜-召唤两道闪电，并且伤害提升至200%")
            .itemLanguageTooltip(2, "Enhanced Iron - Increased attraction strength and range", "强化-铁-吸引强度提升，吸引范围提升")
            .itemLanguageTooltip(3, "Enhanced Lead - Lead Poisoning level +1", "强化-铅-铅中毒减益等级+1")
            .itemLanguageTooltip(4, "Enhanced Obsidian - Reduced fireball interval, increased speed", "强化-黑曜石-产生火球的间隔降低，火球的飞行速度提升")
            .itemLanguageTooltip(5, "Enhanced Silver - Parrying window +0.1s", "强化-银-额外增加0.1秒格挡判定时间")
            .itemLanguageTooltip(6, "Enhanced Tin - Min crit 20%, max extra crit 100%", "强化-锡-暴击率下限提升至20%，额外暴击率上限提升至100%")
            .itemLanguageTooltip(7, "Enhanced Tungsten - Additional +50% item size", "强化-钨-额外提升50%物品大小")
            .itemLanguageTooltip(8, "Crits may fire 40 damage lightning causing Lead Poisoning; 5s cooldown", "持有者暴击有概率发射一道闪电，闪电基础伤害为40，造成爆炸并附加铅中毒，此效果有5秒冷却")
            .itemLanguageTooltip(9, "Lightning increases crit by 10% (max 40%); max crit reduces bolt CD by 40%", "每发射一道闪电，持有者暴击率提升10%，最高提升40%暴击率，暴击率满时减少40%闪电发射冷却")
            .itemLanguageTooltip(10, "Taking damage resets crit bonus", "受到伤害时重置暴击率")
            .itemLanguageTooltip(-1, "\"The Earth grants it strength\"", "“大地赐予它力量”")
            .itemTag(FargoSoulItemTagsRegister.SectionPower)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    public static final DeferredItem<WillPower> WillPowerItem = register("will_power", WillPower::new)
            .itemLanguage("Will Power", "意志之力")
            .itemLanguageTooltip(1, "Enhanced Gladiator - Arrow rain interval reduced to 12 attacks", "强化-角斗士-召唤箭雨的间隔降低至12次攻击")
            .itemLanguageTooltip(2, "Enhanced Gold - -15% damage from Midas-afflicted enemies, Gold Ring duration to 7s", "强化-金-持有者受被迈达斯影响的敌人攻击时受到的伤害降低15%，金环持续时间提升至7秒")
            .itemLanguageTooltip(3, "Enhanced Platinum - Bonus loot multiplier to x8", "强化-铂金-产生额外战利品的倍数提升至8倍")
            .itemLanguageTooltip(4, "Enhanced Red Riding - Guerrilla stack limit to 15", "强化-红色骑术-游击层数上限提升至15层")
            .itemLanguageTooltip(5, "Enhanced Valhalla Knight - Mounts can knock away targets on collision", "强化-英灵殿骑士-骑乘坐骑时，坐骑能够撞飞目标")
            .itemLanguageTooltip(6, "Healing received increased to 125%", "持有者受到的治疗效果提升至125%")
            .itemLanguageTooltip(7, "Bonus loot multiplier to x16 at max Guerrilla stacks", "游击层数达到上限时，击败敌人产生的战利品倍数提升至16倍")
            .itemLanguageTooltip(8, "Damage builds up to 200% on continuous hits; drops rapidly idle", "连续攻击时造成的伤害逐渐提升至200%，不攻击时迅速降低")
            .itemLanguageTooltip(-1, "\"Indomitable determination\"", "“坚不可摧的决心”")
            .itemTag(FargoSoulItemTagsRegister.SectionPower)
            .itemModel(LyraItemRegisterBuilder::basicModel)
            .itemTag(FargoSoulItemTagsRegister.Curio)
            .build();

    // ============================================================
    // 泰拉之魂 · 魂分段
    // ============================================================
    public static final DeferredItem<TerraSoul> TerraSoulItem = register("terra_soul", TerraSoul::new)
            .itemLanguage("Terra Soul", "泰拉之魂")
            .itemLanguageTooltip(1, "Gains an aura that fires various barrages", "获得一个可以发射各种弹幕的光环")
            .itemLanguageTooltip(2, "Gain extra crit rate when picking up items; villagers provide more discounts", "拾取物品时获得额外暴击率，村民可提供更多折扣")
            .itemLanguageTooltip(3, "Crits and parries fire powerful lightning, reduces debuff damage, weapons become larger", "暴击和格挡会发射强大的闪电，减少受到的减益伤害，武器变大")
            .itemLanguageTooltip(4, "Increases crit rate when attacking, increases item pickup range", "攻击时增加暴击率，增加物品吸取范围")
            .itemLanguageTooltip(5, "Gain defense buff when not attacking, gain attack buff when attacking", "不攻击时获得防御增益，攻击时获得攻击增益")
            .itemLanguageTooltip(6, "The wielder has a lifesteal aura, increases damage taken, fires Chlorophyte Crystals at nearby enemies", "持有者拥有吸取生命的光环，增加受到的伤害，向附近的敌人发射叶绿水晶")
            .itemLanguageTooltip(7, "Mushroom healing is enhanced and provides buffs, attacks inflict multiple debuffs", "蘑菇治疗得到强化并提供增益，持有者的攻击造成多种减益")
            .itemLanguageTooltip(8, "The wielder reflects contact damage, minions can crit", "持有者反射接触伤害，持有者的随从可以暴击")
            .itemLanguageTooltip(9, "Increases projectile attributes based on movement speed", "根据持有者移速增加射弹各种属性")
            .itemLanguageTooltip(10, "Can summon Forbidden Storms, healing is increased, revives and cleanses debuffs when HP drops to 0", "持有者可召唤禁戒风暴，治疗会恢复更多，生命值降低至0时复活并净化减益")
            .itemLanguageTooltip(11, "Gain an invincible Shadow Dash every 4s, increases projectile attributes based on speed, killing enemies may drop bone chunks", "持有者每4秒获得一次无敌的暗影冲刺，根据移速增加射弹各种属性，屠戮敌人可能会掉落骨块")
            .itemLanguageTooltip(12, "Allows soul siphoning, increases healing and mount attributes, consecutive attacks gain extra damage", "允许吸取灵魂，增加持有者的治疗和坐骑属性，连续攻击会获得额外伤害")
            .itemLanguageTooltip(13, "Defeated enemies may drop extra loot", "持有者击败的敌人可能会掉落额外的战利品")
            .itemLanguageTooltip(14, "Attacks occasionally fire moons that explode into Lunar Flare", "持有者攻击偶尔会向敌人发射月亮并爆炸产生月亮强化焰")
            .itemLanguageTooltip(15, "Increases movement speed and acceleration, reduces momentum, Stardust Stasis cooldown reduced to 60s", "增加持有者的移动速度 and 加速度并降低持有者的动量，星尘静滞冷却时间降低至60秒")
            .itemLanguageTooltip(-1, "\"The Master of Terra, witnessed by Heaven and Earth\"", "“泰拉之主，天地共证”")
            .itemTag(FargoSoulItemTagsRegister.SectionTerraSoul)
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

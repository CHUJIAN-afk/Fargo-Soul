package first.fargo_soul.register;

import first.fargo_soul.common.item.terraSoul.CosmicPower;
import first.fargo_soul.common.item.terraSoul.cosmicPower.*;
import net.neoforged.neoforge.registries.DeferredItem;

public class FargoSoulSoulItemRegister {

    public static final DeferredItem<BlazeSoul> BLAZE_SOUL = FargoSoulItemRegisterBuilder.build("blaze_soul", BlazeSoul::new)
            .itemLanguage("Blaze Enchantment", "耀斑魔石")
            .itemLanguageTooltip(1, " ", "每5秒获得一层耀斑护盾，最多叠加3层")
            .itemLanguageTooltip(2, " ", "每层耀斑护盾减少15%受到伤害，受击时消耗一层并对附近所有敌人造成攻击力400%的火焰伤害")
            .itemLanguageTooltip(3, " ", "烫手魔石")
            .itemModel(FargoSoulItemRegisterBuilder::basicModel)
            .build();

    public static final DeferredItem<MeteorSoul> METEOR_SOUL = FargoSoulItemRegisterBuilder.build("blaze_soul", MeteorSoul::new)
            .itemLanguage("Meteor Enchantment", "流星魔石")
            .itemLanguageTooltip(1, " ", "按下潜行键大幅增加下落速度")
            .itemLanguageTooltip(3, " ", "《Drop a draco on 'em》")
            .itemModel(FargoSoulItemRegisterBuilder::basicModel)
            .build();

    public static final DeferredItem<NebulaSoul> NEBULA_SOUL = FargoSoulItemRegisterBuilder.build("blaze_soul", NebulaSoul::new)
            .itemLanguage("Nebula Enchantment", "星云魔石")
            .itemLanguageTooltip(1, " ", "召唤星云射击，对敌人造成攻击力200%的魔法伤害，该效果有3秒冷却时间")
            .itemLanguageTooltip(3, " ", "创生之柱照耀着你")
            .itemModel(FargoSoulItemRegisterBuilder::basicModel)
            .build();

    public static final DeferredItem<StardustSoul> STARDUST_SOUL = FargoSoulItemRegisterBuilder.build("blaze_soul", StardustSoul::new)
            .itemLanguage("Stardust Enchantment", "星尘魔石")
            .itemLanguageTooltip(1, " ", "按下冻结键后会冻结时间持续6秒，该效果有120秒冷却时间")
            .itemLanguageTooltip(2, " ", "你成为了替身使者")
            .itemModel(FargoSoulItemRegisterBuilder::basicModel)
            .build();

    public static final DeferredItem<VortexSoul> VORTEX_SOUL = FargoSoulItemRegisterBuilder.build("blaze_soul", VortexSoul::new)
            .itemLanguage("Vortex Enchantment", "星旋魔石")
            .itemLanguageTooltip(1, " ", "按下传送键传送至目视的位置，该效果有120秒冷却时间")
            .itemLanguageTooltip(2, " ", "最大传送距离为512格，大于此距离无法传送")
            .itemLanguageTooltip(3, " ", "撕裂现实")
            .itemModel(FargoSoulItemRegisterBuilder::basicModel)
            .build();

    public static final DeferredItem<WizardSoul> WIZARD_SOUL = FargoSoulItemRegisterBuilder.build("blaze_soul", WizardSoul::new)
            .itemLanguage("Wizard Enchantment", "巫师魔石")
            .itemLanguageTooltip(1, " ", "增加25%魔法伤害")
            .itemLanguageTooltip(2, " ", "减少15%~30%受到的魔法伤害")
            .itemLanguageTooltip(3, " ", "我们爱施放魔法")
            .itemModel(FargoSoulItemRegisterBuilder::basicModel)
            .build();

    public static final DeferredItem<CosmicPower> COSMIC_POWER = FargoSoulItemRegisterBuilder.build("cosmic_power", CosmicPower::new)
            .itemLanguage("Cosmic Power", "宇宙之力")
            .itemLanguageTooltip(1, " ", "按下潜行键大幅增加下落速度")
            .itemLanguageTooltip(2, " ", "减少20%受到的伤害，受击时对附近敌人造成攻击力200%的火焰伤害，该效果有0.25秒冷却时间")
            .itemLanguageTooltip(3, " ", "攻击时有15%概率释放星云射击，对敌人造成攻击力150%的魔法伤害，该效果有0.25秒冷却时间")
            .itemLanguageTooltip(4, " ", "攻击时可能产生以下强化效果")
            .itemLanguageTooltip(5, " ", "耀斑强化：增加25%伤害")
            .itemLanguageTooltip(6, " ", "星尘强化：增加目标已损生命值5%的伤害")
            .itemLanguageTooltip(7, " ", "星云强化：恢复攻击力15%的生命值")
            .itemLanguageTooltip(8, " ", "星旋强化：对目标附近的其他敌人造成25%伤害")
            .itemLanguageTooltip(9, " ", "自宇宙大爆炸以来就一直存在")
            .itemModel(FargoSoulItemRegisterBuilder::basicModel)
            .build();

    public static void register(){

    }
}

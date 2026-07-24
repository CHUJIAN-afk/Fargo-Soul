package first.fargo_soul.register;

import first.fargo_soul.common.item.terraSoul.cosmicPower.BlazeSoul;
import net.neoforged.neoforge.registries.DeferredItem;

public class FargoSoulSoulItemRegister {

    public static final DeferredItem<BlazeSoul> CosmicCrucibleBlockItem = FargoSoulItemRegisterBuilder.build("blaze_soul", BlazeSoul::new)
            .itemLanguage("Blaze Soul", "耀斑魔石")
            .itemLanguageTooltip(1, " ", "每5秒获得一层耀斑护盾，最多叠加3层")
            .itemLanguageTooltip(2, " ", "每层耀斑护盾减少15%受到伤害，受击时消耗一层并对附近所有敌人造成相当于攻击力400%的火焰伤害")
            .itemLanguageTooltip(4, " ", "“烫手魔石”")
            .itemModel(FargoSoulItemRegisterBuilder::basicModel)
            .build();
}

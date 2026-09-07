package first.fargo_soul.dadageneeator.provider;

import first.fargo_soul.register.FargoSoulMobEffectRegister;
import first.lyra.dataGenerator.provider.LyraLanguageProvider;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.effect.MobEffect;

public class FargoSoulLangProvider extends LyraLanguageProvider {

    public FargoSoulLangProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void init() {
        entry("itemGroup.fargo_soul", "Fargo Soul", "Fargo 魂石");
        entry("fargo_soul.key.tooltip", "Hold [", "按住 [ %s ] 可查看概要");
        effect(FargoSoulMobEffectRegister.DeathMark, "Death Mark", "死亡标记");
        effect(FargoSoulMobEffectRegister.CrystalArmorBreak, "Crystal Armor Break", "水晶碎甲");
        effect(FargoSoulMobEffectRegister.PreemptiveStrike, "Preemptive Strike", "先发至人");
        effect(FargoSoulMobEffectRegister.ShadowFire, "Shadowflame", "暗影焰");
        effect(FargoSoulMobEffectRegister.ShadowGift, "Shadow Gift", "暗影之赐");
        effect(FargoSoulMobEffectRegister.LeadPoisoning, "Lead Poisoning", "铅中毒");
        effect(FargoSoulMobEffectRegister.AmazingMoment, "Amazing Moment", "惊人一刻");
        effect(FargoSoulMobEffectRegister.TerraResonance, "Terra Resonance", "泰拉共鸣");
        effect(FargoSoulMobEffectRegister.Bleeding, "Bleeding", "流血");
        effect(FargoSoulMobEffectRegister.Hemorrhage, "Hemorrhage", "血如泉涌");
        effect(FargoSoulMobEffectRegister.Oiled, "Oiled", "浸油");
        effect(FargoSoulMobEffectRegister.Chill, "Chill", "寒冷");
        effect(FargoSoulMobEffectRegister.Starlight, "Starlight", "星之光辉");
        effect(FargoSoulMobEffectRegister.ArmorBreak, "Armor Break", "盔甲破损");
        effect(FargoSoulMobEffectRegister.TekeMist, "Teke Mist", "灵雾迷障");
        effect(FargoSoulMobEffectRegister.OrichalcumPoisoning, "Orichalcum Poisoning", "山铜中毒");
        effect(FargoSoulMobEffectRegister.Midas, "Midas", "迈达斯");
        effect(FargoSoulMobEffectRegister.Ambrosia, "Ambrosia", "仙馔密酒");
    }

    public void effect(Holder<MobEffect> mobEffectHolder, String en, String zh) {
        entry(mobEffectHolder.value().getDescriptionId(), en, zh);
    }
}

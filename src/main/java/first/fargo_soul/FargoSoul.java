package first.fargo_soul;

import first.fargo_soul.register.*;
import first.lyra.register.LyraItemRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(FargoSoul.MODID)
public class FargoSoul {

    public static final String MODID = "fargo_soul";
    public static final LyraItemRegistries REGISTRIES = LyraItemRegistries.create(MODID).languageInit(registries -> {
        registries.language("itemGroup.fargo_soul", "Fargo Soul", "Fargo 魂石");
        registries.language("fargo_soul.key.tooltip", "Hold [", "按住 [ %s ] 可查看概要");
        registries.effectLanguage(FargoSoulMobEffectRegister.DeathMark, "Death Mark", "死亡标记");
        registries.effectLanguage(FargoSoulMobEffectRegister.CrystalArmorBreak, "Crystal Armor Break", "水晶碎甲");
        registries.effectLanguage(FargoSoulMobEffectRegister.PreemptiveStrike, "Preemptive Strike", "先发至人");
        registries.effectLanguage(FargoSoulMobEffectRegister.ShadowFire, "Shadowflame", "暗影焰");
        registries.effectLanguage(FargoSoulMobEffectRegister.ShadowGift, "Shadow Gift", "暗影之赐");
        registries.effectLanguage(FargoSoulMobEffectRegister.LeadPoisoning, "Lead Poisoning", "铅中毒");
        registries.effectLanguage(FargoSoulMobEffectRegister.AmazingMoment, "Amazing Moment", "惊人一刻");
        registries.effectLanguage(FargoSoulMobEffectRegister.TerraResonance, "Terra Resonance", "泰拉共鸣");
        registries.effectLanguage(FargoSoulMobEffectRegister.Bleeding, "Bleeding", "流血");
        registries.effectLanguage(FargoSoulMobEffectRegister.Hemorrhage, "Hemorrhage", "血如泉涌");
        registries.effectLanguage(FargoSoulMobEffectRegister.Oiled, "Oiled", "浸油");
        registries.effectLanguage(FargoSoulMobEffectRegister.Chill, "Chill", "寒冷");
        registries.effectLanguage(FargoSoulMobEffectRegister.Starlight, "Starlight", "星之光辉");
        registries.effectLanguage(FargoSoulMobEffectRegister.ArmorBreak, "Armor Break", "盔甲破损");
        registries.effectLanguage(FargoSoulMobEffectRegister.TekeMist, "Teke Mist", "灵雾迷障");
        registries.effectLanguage(FargoSoulMobEffectRegister.OrichalcumPoisoning, "Orichalcum Poisoning", "山铜中毒");
        registries.effectLanguage(FargoSoulMobEffectRegister.Midas, "Midas", "迈达斯");
        registries.effectLanguage(FargoSoulMobEffectRegister.Ambrosia, "Ambrosia", "仙馔密酒");
    });

    public FargoSoul(IEventBus eventBus) {
        REGISTRIES.register(eventBus, var -> FargoSoulItemRegister.register());
        FargoSoulCreativeModeTabRegister.register(eventBus);
        FargoSoulMobEffectRegister.register(eventBus);
        FargoSoulAttributeRegister.register(eventBus);
        FargoSoulAttachmentRegister.register(eventBus);
        SummonerAttachmentEntityRegister.register(eventBus);
        FargoSoulSoulInfoRegister.register(eventBus);
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path.toLowerCase());
    }
}

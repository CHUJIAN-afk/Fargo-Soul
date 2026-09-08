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
        registries.language(FargoSoulMobEffectRegister.DeathMark.value().getDescriptionId(), "Death Mark", "死亡标记");
        registries.language(FargoSoulMobEffectRegister.CrystalArmorBreak.value().getDescriptionId(), "Crystal Armor Break", "水晶碎甲");
        registries.language(FargoSoulMobEffectRegister.PreemptiveStrike.value().getDescriptionId(), "Preemptive Strike", "先发至人");
        registries.language(FargoSoulMobEffectRegister.ShadowFire.value().getDescriptionId(), "Shadowflame", "暗影焰");
        registries.language(FargoSoulMobEffectRegister.ShadowGift.value().getDescriptionId(), "Shadow Gift", "暗影之赐");
        registries.language(FargoSoulMobEffectRegister.LeadPoisoning.value().getDescriptionId(), "Lead Poisoning", "铅中毒");
        registries.language(FargoSoulMobEffectRegister.AmazingMoment.value().getDescriptionId(), "Amazing Moment", "惊人一刻");
        registries.language(FargoSoulMobEffectRegister.TerraResonance.value().getDescriptionId(), "Terra Resonance", "泰拉共鸣");
        registries.language(FargoSoulMobEffectRegister.Bleeding.value().getDescriptionId(), "Bleeding", "流血");
        registries.language(FargoSoulMobEffectRegister.Hemorrhage.value().getDescriptionId(), "Hemorrhage", "血如泉涌");
        registries.language(FargoSoulMobEffectRegister.Oiled.value().getDescriptionId(), "Oiled", "浸油");
        registries.language(FargoSoulMobEffectRegister.Chill.value().getDescriptionId(), "Chill", "寒冷");
        registries.language(FargoSoulMobEffectRegister.Starlight.value().getDescriptionId(), "Starlight", "星之光辉");
        registries.language(FargoSoulMobEffectRegister.ArmorBreak.value().getDescriptionId(), "Armor Break", "盔甲破损");
        registries.language(FargoSoulMobEffectRegister.TekeMist.value().getDescriptionId(), "Teke Mist", "灵雾迷障");
        registries.language(FargoSoulMobEffectRegister.OrichalcumPoisoning.value().getDescriptionId(), "Orichalcum Poisoning", "山铜中毒");
        registries.language(FargoSoulMobEffectRegister.Midas.value().getDescriptionId(), "Midas", "迈达斯");
        registries.language(FargoSoulMobEffectRegister.Ambrosia.value().getDescriptionId(), "Ambrosia", "仙馔密酒");
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

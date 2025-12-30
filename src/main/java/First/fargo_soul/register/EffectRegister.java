package First.fargo_soul.register;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.effect.beneficial.MidasEffect;
import First.fargo_soul.effect.harmful.*;
import First.fargo_soul.effect.neutral.*;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EffectRegister {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, FargoSoul.MODID);


    public static final Holder<MobEffect> LeadPoisoning;
    public static final Holder<MobEffect> AmazingMoment;
    public static final Holder<MobEffect> Oil;
    public static final Holder<MobEffect> OrichalcumPoisoning;
    public static final Holder<MobEffect> ScarletHeals;
    public static final Holder<MobEffect> Freezing;
    public static final Holder<MobEffect> Frostbite;
    public static final Holder<MobEffect> FungalEmpowerment;
    public static final Holder<MobEffect> Honey;
    public static final Holder<MobEffect> BeetleMight;
    public static final Holder<MobEffect> BeetleEndurance;
    public static final Holder<MobEffect> ShellDefense;
    public static final Holder<MobEffect> Ghost;
    public static final Holder<MobEffect> PreemptiveStrike;
    public static final Holder<MobEffect> ShadowFire;
    public static final Holder<MobEffect> Midas;
    public static final Holder<MobEffect> SunburstEruption;
    public static final Holder<MobEffect> Flare;
    public static final Holder<MobEffect> VitalityBoostedBlaze;
    public static final Holder<MobEffect> WrathFire;

    static {
        LeadPoisoning = EFFECTS.register("lead_poisoning", LeadPoisoningEffect::new);
        AmazingMoment = EFFECTS.register("amazing_moment", AmazingMomentEffect::new);
        Oil = EFFECTS.register("oil", OilEffect::new);
        OrichalcumPoisoning = EFFECTS.register("orichalcum_poisoning", OrichalcumPoisoningEffect::new);
        ScarletHeals = EFFECTS.register("scarlet_heals", ScarletHealsEffect::new);
        Freezing = EFFECTS.register("freezing", FreezingEffect::new);
        Frostbite = EFFECTS.register("frostbite", FrostbiteEffect::new);
        FungalEmpowerment = EFFECTS.register("fungal_empowerment", FungalEmpowermentEffect::new);
        Honey = EFFECTS.register("honey", HoneyEffect::new);
        BeetleMight = EFFECTS.register("beetle_might", BeetleMightEffect::new);
        BeetleEndurance = EFFECTS.register("beetle_endurance", BeetleEnduranceEffect::new);
        ShellDefense = EFFECTS.register("shell_defense", ShellDefenseEffect::new);
        Ghost = EFFECTS.register("ghost", GhostEffect::new);
        PreemptiveStrike = EFFECTS.register("preemptive_strike", PreemptiveStrikeEffect::new);
        ShadowFire = EFFECTS.register("shadow_fire", ShadowFireEffect::new);
        Midas = EFFECTS.register("midas", MidasEffect::new);
        SunburstEruption = EFFECTS.register("sunburst_eruption", SunburstEruptionEffect::new);
        Flare = EFFECTS.register("flare", FlareEffect::new);
        VitalityBoostedBlaze = EFFECTS.register("vitality_boosted_blaze", VitalityBoostedBlaze::new);
        WrathFire = EFFECTS.register("wrath_fire", WrathFire::new);
    }
    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }

}

package First.fargo_soul.Effect;

import First.fargo_soul.Effect.Beneficial.MidasEffect;
import First.fargo_soul.Effect.Harmful.*;
import First.fargo_soul.Effect.Neutral.*;
import First.fargo_soul.Fargo_soul;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EffectRegister {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, Fargo_soul.MODID);

    public static final Holder<MobEffect> LeadPoisoning = EFFECTS.register("lead_poisoning", LeadPoisoningEffect::new);
    public static final Holder<MobEffect> AmazingMoment = EFFECTS.register("amazing_moment", AmazingMomentEffect::new);
    public static final Holder<MobEffect> Oil = EFFECTS.register("oil", OilEffect::new);
    public static final Holder<MobEffect> OrichalcumPoisoning = EFFECTS.register("orichalcum_poisoning", OrichalcumPoisoningEffect::new);
    public static final Holder<MobEffect> ScarletHeals = EFFECTS.register("scarlet_heals", ScarletHealsEffect::new);
    public static final Holder<MobEffect> Freezing = EFFECTS.register("freezing", FreezingEffect::new);
    public static final Holder<MobEffect> Frostbite = EFFECTS.register("frostbite", FrostbiteEffect::new);
    public static final Holder<MobEffect> FungalEmpowerment = EFFECTS.register("fungal_empowerment", FungalEmpowermentEffect::new);
    public static final Holder<MobEffect> Honey = EFFECTS.register("honey", HoneyEffect::new);
    public static final Holder<MobEffect> BeetleMight = EFFECTS.register("beetle_might", BeetleMightEffect::new);
    public static final Holder<MobEffect> BeetleEndurance = EFFECTS.register("beetle_endurance", BeetleEnduranceEffect::new);
    public static final Holder<MobEffect> ShellDefense = EFFECTS.register("shell_defense", ShellDefenseEffect::new);
    public static final Holder<MobEffect> Ghost = EFFECTS.register("ghost", GhostEffect::new);
    public static final Holder<MobEffect> PreemptiveStrike = EFFECTS.register("preemptive_strike", PreemptiveStrikeEffect::new);
    public static final Holder<MobEffect> ShadowFire = EFFECTS.register("shadow_fire", ShadowFireEffect::new);
    public static final Holder<MobEffect> Midas = EFFECTS.register("midas", MidasEffect::new);
    public static final Holder<MobEffect> SunburstEruption = EFFECTS.register("sunburst_eruption", SunburstEruptionEffect::new);
    public static final Holder<MobEffect> Flare = EFFECTS.register("flare", FlareEffect::new);
    public static final Holder<MobEffect> VitalityBoostedBlaze = EFFECTS.register("vitality_boosted_blaze", VitalityBoostedBlaze::new);
    public static final Holder<MobEffect> WrathFire = EFFECTS.register("wrath_fire", WrathFire::new);


}

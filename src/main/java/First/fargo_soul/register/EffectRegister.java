package First.fargo_soul.register;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.effect.harmful.*;
import First.fargo_soul.effect.neutral.*;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EffectRegister {

    public static final DeferredRegister<MobEffect> Register = DeferredRegister.create(Registries.MOB_EFFECT, FargoSoul.MODID);

    public static final Holder<MobEffect> LeadPoisoning = register("lead_poisoning", LeadPoisoningEffect::new);
    public static final Holder<MobEffect> AmazingMoment = register("amazing_moment", AmazingMomentEffect::new);
    public static final Holder<MobEffect> Oil = register("oil", OilEffect::new);
    public static final Holder<MobEffect> OrichalcumPoisoning = register("orichalcum_poisoning", OrichalcumPoisoningEffect::new);
    public static final Holder<MobEffect> ScarletHeals = register("scarlet_heals", ScarletHealsEffect::new);
    public static final Holder<MobEffect> Freezing = register("freezing", FreezingEffect::new);
    public static final Holder<MobEffect> Frostbite = register("frostbite", FrostbiteEffect::new);
    public static final Holder<MobEffect> FungalEmpowerment = register("fungal_empowerment", FungalEmpowermentEffect::new);
    public static final Holder<MobEffect> Honey = register("honey", HoneyEffect::new);
    public static final Holder<MobEffect> BeetleMight = register("beetle_might", BeetleMightEffect::new);
    public static final Holder<MobEffect> BeetleEndurance = register("beetle_endurance", BeetleEnduranceEffect::new);
    public static final Holder<MobEffect> ShellDefense = register("shell_defense", ShellDefenseEffect::new);
    public static final Holder<MobEffect> Ghost = register("ghost", GhostEffect::new);
    public static final Holder<MobEffect> PreemptiveStrike = register("preemptive_strike", PreemptiveStrikeEffect::new);
    public static final Holder<MobEffect> ShadowFire = register("shadow_fire", ShadowFireEffect::new);
    public static final Holder<MobEffect> Midas = register("midas", MidasEffect::new);
    public static final Holder<MobEffect> SunburstEruption = register("sunburst_eruption", SunburstEruptionEffect::new);
    public static final Holder<MobEffect> Flare = register("flare", FlareEffect::new);
    public static final Holder<MobEffect> VitalityBoostedBlaze = register("vitality_boosted_blaze", VitalityBoostedBlaze::new);
    public static final Holder<MobEffect> WrathFire = register("wrath_fire", WrathFire::new);

    private static Holder<MobEffect> register(String name, Supplier<? extends MobEffect> sup) {
        return Register.register(name, sup);
    }

    public static void register(IEventBus eventBus) {
        Register.register(eventBus);
    }

}

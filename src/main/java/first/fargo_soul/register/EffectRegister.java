package first.fargo_soul.register;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.effect.harmful.*;
import first.fargo_soul.common.effect.neutral.*;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EffectRegister {

    public static final DeferredRegister<MobEffect> Register = DeferredRegister.create(Registries.MOB_EFFECT, FargoSoul.MODID);

    public static final Holder<MobEffect> LeadPoisoning = register("lead_poisoning", LeadPoisoningEffect::new);
    public static final Holder<MobEffect> Oil = register("oil", OilEffect::new);
    public static final Holder<MobEffect> OrichalcumPoisoning = register("orichalcum_poisoning", OrichalcumPoisoningEffect::new);
    public static final Holder<MobEffect> FungalEmpowerment = register("fungal_empowerment", FungalEmpowermentEffect::new);
    public static final Holder<MobEffect> ShadowFire = register("shadow_fire", ShadowFireEffect::new);
    public static final Holder<MobEffect> Midas = register("midas", MidasEffect::new);

    private static Holder<MobEffect> register(String name, Supplier<? extends MobEffect> sup) {
        return Register.register(name, sup);
    }

    public static void register(IEventBus eventBus) {
        Register.register(eventBus);
    }

}

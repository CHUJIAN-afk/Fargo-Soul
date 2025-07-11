package First.fargo_soul.Effect;

import First.fargo_soul.Fargo_soul;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredRegister;
import First.fargo_soul.Effect.HarmfulEffect.*;

public class EffectRegister {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, Fargo_soul.MODID);

    public static final Holder<MobEffect> LeadPoisoning = EFFECTS.register("lead_poisoning_effect", LeadPoisoningEffect::new);
    public static final Holder<MobEffect> AmazingMoment = EFFECTS.register("amazing_moment_effect", AmazingMomentEffect::new);

}

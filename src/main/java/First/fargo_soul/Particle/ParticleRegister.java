package First.fargo_soul.Particle;

import First.fargo_soul.Fargo_soul;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ParticleRegister {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPE = DeferredRegister.create(Registries.PARTICLE_TYPE, Fargo_soul.MODID);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> RAINBOW = PARTICLE_TYPE.register("rainbow", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PLAYER_RAINBOW = PARTICLE_TYPE.register("player_rainbow", () -> new SimpleParticleType(false));

}

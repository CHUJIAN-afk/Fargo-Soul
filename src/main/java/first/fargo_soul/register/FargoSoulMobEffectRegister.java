package first.fargo_soul.register;

import first.fargo_soul.FargoSoul;
import first.lyra.register.SimpleMobEffectBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FargoSoulMobEffectRegister {

    public static final DeferredRegister<MobEffect> Register = DeferredRegister.create(Registries.MOB_EFFECT, FargoSoul.MODID);

    public static final Holder<MobEffect> DeathMark =
            Register.register("death_mark", location -> new SimpleMobEffectBuilder(MobEffectCategory.HARMFUL, 0xB51F1F)
                    .addAttributeModifier(Attributes.MAX_HEALTH, location, -0.2f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                    .addAttributeModifier(Attributes.ARMOR, location, -0.4f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, location, 0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                    .build()
            );

    public static void register(IEventBus eventBus) {
        Register.register(eventBus);
    }

}

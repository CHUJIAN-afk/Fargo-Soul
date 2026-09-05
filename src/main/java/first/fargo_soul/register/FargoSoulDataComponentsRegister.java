package first.fargo_soul.register;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.dataComponents.SoulRarity;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FargoSoulDataComponentsRegister {

    private static final DeferredRegister.DataComponents Register = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, FargoSoul.MODID);

    public static final Supplier<DataComponentType<SoulRarity>> SOUL_RARITY = Register.registerComponentType("soul_color", builder -> builder.persistent(SoulRarity.CODEC).networkSynchronized(SoulRarity.STREAM_CODEC));

    public static void register(IEventBus eventBus) {
        Register.register(eventBus);
    }
}

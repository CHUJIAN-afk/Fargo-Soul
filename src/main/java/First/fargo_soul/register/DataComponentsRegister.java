package First.fargo_soul.register;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.common.dataComponents.SoulRarity;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class DataComponentsRegister {

    private static final DeferredRegister.DataComponents Register = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, FargoSoul.MODID);

    public static final Supplier<DataComponentType<SoulRarity>> SoulRarity = Register.registerComponentType("soul_rarity", builder -> builder.persistent(First.fargo_soul.common.dataComponents.SoulRarity.CODEC).networkSynchronized(First.fargo_soul.common.dataComponents.SoulRarity.STREAM_CODEC));

    public static void register(IEventBus eventBus) {
        Register.register(eventBus);
    }

}

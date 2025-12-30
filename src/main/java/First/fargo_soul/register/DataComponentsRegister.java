package First.fargo_soul.register;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.dataComponent.SoulComponent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class DataComponentsRegister {

	private static final DeferredRegister.DataComponents DataComponents;
	public static final Supplier<DataComponentType<SoulComponent>> SoulData;

	static {
		DataComponents = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, FargoSoul.MODID);
		SoulData = DataComponents.registerComponentType(FargoSoul.MODID, builder -> builder.persistent(SoulComponent.SoulCodec).networkSynchronized(SoulComponent.SoulStreamCodec)
		);
	}

	public static void register(IEventBus eventBus) {
		DataComponents.register(eventBus);
	}

}

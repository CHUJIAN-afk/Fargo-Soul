package First.fargo_soul.DataComponent;

import First.fargo_soul.DataComponent.DataComponents.SoulComponent;
import First.fargo_soul.Fargo_soul;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class DataComponentsRegister {

	private static final DeferredRegister.DataComponents DataComponents;
	public static final Supplier<DataComponentType<SoulComponent>> SoulData;

	static {
		DataComponents = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Fargo_soul.MODID);
		SoulData = DataComponents.registerComponentType(Fargo_soul.MODID, builder -> builder.persistent(SoulComponent.SoulCodec).networkSynchronized(SoulComponent.SoulStreamCodec)
		);
	}

	public static void register(IEventBus eventBus) {
		DataComponents.register(eventBus);
	}

}

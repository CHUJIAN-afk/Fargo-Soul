package first.fargo_soul.register;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.api.MarklibRegisters;
import first.fargo_soul.common.attachment.soulInfoData.soulInfo.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class SoulInfoRegister {

    private static final DeferredRegister<SoulInfoType<?>> REGISTER = DeferredRegister.create(MarklibRegisters.MARK_TYPE, FargoSoul.MODID);

    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<CoolDownSoulInfo>> COOLDOWN = register("cooldown", CoolDownSoulInfo::new);
    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<DurationSoulInfo>> DURATION = register("duration", DurationSoulInfo::new);
    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<EnabledSoulInfo>> ENABLED = register("enabled", EnabledSoulInfo::new);
    public static final DeferredHolder<SoulInfoType<?>, SoulInfoType<StackSoulInfo>> STACK = register("stack", StackSoulInfo::new);

    private static <T extends AbstractSoulInfo> DeferredHolder<SoulInfoType<?>, SoulInfoType<T>> register(String name, Supplier<T> factory) {
        return REGISTER.register(name, () -> new SoulInfoType<>(factory));
    }

    public static void register(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }
}

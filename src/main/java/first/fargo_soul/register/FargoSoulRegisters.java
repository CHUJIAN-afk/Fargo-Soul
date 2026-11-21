package first.fargo_soul.register;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.soulInfo.SoulInfoType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

@EventBusSubscriber(modid = FargoSoul.MODID)
public class FargoSoulRegisters {

    public static final ResourceKey<Registry<SoulInfoType<?>>> SOUL_INFO_TYPE_KEY = ResourceKey.createRegistryKey(FargoSoul.rl("soul_info_type"));

    public static final Registry<SoulInfoType<?>> SOUL_INFO_TYPE = new RegistryBuilder<>(SOUL_INFO_TYPE_KEY).sync(true).create();

    @SubscribeEvent
    public static void register(NewRegistryEvent event) {
        event.register(SOUL_INFO_TYPE);
    }
}

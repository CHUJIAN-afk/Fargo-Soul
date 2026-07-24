package first.fargo_soul.api;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.attachment.soulInfoData.soulInfo.SoulInfoType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;


public class MarklibRegisters {

    public static final ResourceKey<Registry<SoulInfoType<?>>> MARK_TYPE_KEY = ResourceKey.createRegistryKey(FargoSoul.rl("soul_info_type"));

    public static final Registry<SoulInfoType<?>> MARK_TYPE = new RegistryBuilder<>(MARK_TYPE_KEY).sync(true).create();

    public static void register(NewRegistryEvent event) {
        event.register(MARK_TYPE);
    }
}

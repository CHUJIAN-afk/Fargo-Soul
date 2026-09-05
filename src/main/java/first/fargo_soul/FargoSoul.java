package first.fargo_soul;

import first.fargo_soul.register.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(FargoSoul.MODID)
public class FargoSoul {

    public static final String MODID = "fargo_soul";

    public FargoSoul(IEventBus eventBus) {
        FargoSoulDataComponentsRegister.register(eventBus);
        FargoSoulItemRegister.register(eventBus);
        FargoSoulBlockRegister.register(eventBus);
        FargoSoulBlockEntityRegister.register(eventBus);
        FargoSoulCreativeModeTabRegister.register(eventBus);
        FargoSoulMobEffectRegister.register(eventBus);
        FargoSoulAttributeRegister.register(eventBus);
        FargoSoulAttachmentRegister.register(eventBus);
        FargoSoulRecipeSerializerRegister.register(eventBus);
        FargoSoulRecipeTypeRegister.register(eventBus);
        SummonerAttachmentEntityRegister.register(eventBus);
        FargoSoulSoulInfoRegister.register(eventBus);
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path.toLowerCase());
    }
}

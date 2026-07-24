package first.fargo_soul;

import first.fargo_soul.config.ClientConfig;
import first.fargo_soul.config.ServerSoulConfig;
import first.fargo_soul.register.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(FargoSoul.MODID)
public class FargoSoul {

    public static final String MODID = "fargo_soul";
    public static final Logger logger = LoggerFactory.getLogger(MODID);

    public FargoSoul(IEventBus eventBus, ModContainer modContainer) {
        FargoSoulSoulItemRegister.register();
        FargoSoulItemRegisterBuilder.register(eventBus);

        DataComponentsRegister.register(eventBus);
        FargoSoulItemRegister.register(eventBus);
        BlockRegister.register(eventBus);
        BlockEntityRegister.register(eventBus);
        CreativeModeTabRegister.register(eventBus);
        EntityRegister.register(eventBus);
        EffectRegister.register(eventBus);
        AttributeRegister.register(eventBus);
        AttachmentRegister.register(eventBus);
        RecipeSerializerRegister.register(eventBus);
        RecipeTypeRegister.register(eventBus);
        MenuRegister.register(eventBus);
        modContainer.registerConfig(ModConfig.Type.SERVER, ServerSoulConfig.Spec);
        if (FMLLoader.getDist().isClient()) {
            modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.Spec);
            modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        }
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path.toLowerCase());
    }

}

package First.fargo_soul;

import First.fargo_soul.config.ClientConfig;
import First.fargo_soul.config.ServerSoulConfig;
import First.fargo_soul.register.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(FargoSoul.MODID)
public class FargoSoul {

    public static final String MODID = "fargo_soul";
    public static final Logger logger = LoggerFactory.getLogger(MODID);

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public FargoSoul(IEventBus eventBus, ModContainer modContainer) {
        ItemRegister.register(eventBus);
        BlockRegister.register(eventBus);
        BlockEntityRegister.register(eventBus);
        CreativeModeTabRegister.register(eventBus);
        EntityRegister.register(eventBus);
        EffectRegister.register(eventBus);
        AttributeRegister.register(eventBus);
        AttachmentRegister.register(eventBus);
        RecipeTypeRegister.register(eventBus);
        RecipeSerializerRegister.register(eventBus);
        modContainer.registerConfig(ModConfig.Type.SERVER, ServerSoulConfig.Spec);
        modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.Spec);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        }
    }
    
}

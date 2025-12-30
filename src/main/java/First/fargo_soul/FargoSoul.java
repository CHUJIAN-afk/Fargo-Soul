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
        ItemRegister.register(eventBus);//物品注册
        BlockRegister.register(eventBus);//方块注册
        BlockEntityRegister.register(eventBus);//方块实体注册
        CreativeModeTabRegister.register(eventBus);//创造模式物品栏
        EntityRegister.register(eventBus);//实体注册
        EffectRegister.register(eventBus);//药水效果注册
        AttributeRegister.register(eventBus);//属性注册
        AttachmentRegister.register(eventBus);//数据组件注册
        DataComponentsRegister.register(eventBus);//物品组件注册
        RecipeTypeRegister.register(eventBus);//配方类型注册
        RecipeSerializerRegister.register(eventBus);//配方序列化注册
        modContainer.registerConfig(ModConfig.Type.SERVER, ServerSoulConfig.Spec);
        modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.Spec);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        }
    }

}

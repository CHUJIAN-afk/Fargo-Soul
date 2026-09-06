package first.fargo_soul.register;

import first.fargo_soul.FargoSoul;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = FargoSoul.MODID, value = Dist.CLIENT)
public class FargoSoulKeyRegister {

    private static final List<KeyMapping> keyList = new ArrayList<>();

    public static final KeyMapping BlazeSoulKey = register("日耀");
    public static final KeyMapping GoldSoulKey = register("金身");
    public static final KeyMapping StardustSoulKey = register("冻结");
    public static final KeyMapping VortexSoulKey = register("传送");
    public static final KeyMapping ForbiddenKey = register("风暴");
    public static final KeyMapping PenetratingNinjaKey = register("渗透");
    public static final KeyMapping PalmWoodKey = register("棕榈爆炸");
    public static final KeyMapping SoulMenuKey = register("打开魂石空间");

    private static KeyMapping register(String name) {
        KeyMapping keyMapping = new KeyMapping(name, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, InputConstants.UNKNOWN.getValue(), Component.translatable("itemGroup.fargo_soul").getString());
        keyList.add(keyMapping);
        return keyMapping;
    }

    @SubscribeEvent
    public static void registerKeyMappingsEvent(RegisterKeyMappingsEvent event) {
        for (KeyMapping key : keyList) {
            event.register(key);
        }
    }

}

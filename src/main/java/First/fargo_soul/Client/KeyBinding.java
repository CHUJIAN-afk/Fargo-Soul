package First.fargo_soul.Client;

import First.fargo_soul.Fargo_soul;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;

@OnlyIn(Dist.CLIENT)
public class KeyBinding {

    //按键映射
    public static final KeyMapping GoldSoulKey;
    public static final KeyMapping StardustSoulKey;
    public static final KeyMapping VortexSoulKey;
    public static final KeyMapping ScoutingKey;
    public static final KeyMapping ForbiddenKey;
    public static final KeyMapping SoulListKey;

    static {
        GoldSoulKey = KeyRegister("金身");
        StardustSoulKey = KeyRegister("冻结");
        VortexSoulKey = KeyRegister("传送");
        ScoutingKey = KeyRegister("侦查");
        ForbiddenKey = KeyRegister("风暴");
        SoulListKey = KeyRegister("打开能力清单");
    }

    private static KeyMapping KeyRegister(String name) {
        return new KeyMapping(
                name,
                KeyConflictContext.IN_GAME,
                InputConstants.Type.KEYSYM,
                InputConstants.UNKNOWN.getValue(),
                Component.translatable("itemGroup.fargo_soul").getString()
        );
    }

    //注册按键
    @EventBusSubscriber(modid = Fargo_soul.MODID, value = Dist.CLIENT)
    public static class ClientBusEvents {
        @SubscribeEvent
        public static void registerKeyMappingsEvent(RegisterKeyMappingsEvent event) {
            event.register(GoldSoulKey);
            event.register(StardustSoulKey);
            event.register(VortexSoulKey);
            event.register(ScoutingKey);
            event.register(ForbiddenKey);
            event.register(SoulListKey);
        }
    }

}

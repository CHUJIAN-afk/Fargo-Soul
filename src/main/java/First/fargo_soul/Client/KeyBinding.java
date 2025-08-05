package First.fargo_soul.Client;

import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.TerraSoul.CosmicPower.SoulStone.StardustSoul;
import First.fargo_soul.Item.Soul.TerraSoul.CosmicPower.SoulStone.VortexSoul;
import First.fargo_soul.Item.Soul.TerraSoul.WillPower.Soulstone.GoldSoul;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class KeyBinding {
    //按键映射
    public static final KeyMapping GoldSoulKey = new net.minecraft.client.KeyMapping(
            "金身",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_F,
            "Fargo Soul"
    );

    public static final KeyMapping StardustSoulKey = new net.minecraft.client.KeyMapping(
            "冻结",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_I,
            "Fargo Soul"
    );

    public static final KeyMapping VortexSoulKey = new net.minecraft.client.KeyMapping(
            "传送",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_I,
            "Fargo Soul"
    );

    //注册按键
    @EventBusSubscriber(modid = Fargo_soul.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    public static class ClientBusEvents {
        @SubscribeEvent
        public static void registerKeyMappingsEvent(RegisterKeyMappingsEvent registerKeyMappingsEvent) {
            registerKeyMappingsEvent.register(GoldSoulKey);
            registerKeyMappingsEvent.register(StardustSoulKey);
            registerKeyMappingsEvent.register(VortexSoulKey);
        }
    }

    //按键处理
    @EventBusSubscriber(modid = Fargo_soul.MODID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void KeyInputEvent(InputEvent.Key event) {
            GoldSoul.GoldSoulInputHandler(event);
            StardustSoul.StardustSoulInputHandler(event);
            VortexSoul.VortexSoulInputHandler(event);
        }
    }






}

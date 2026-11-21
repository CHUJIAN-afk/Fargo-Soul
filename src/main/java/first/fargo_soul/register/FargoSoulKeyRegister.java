package first.fargo_soul.register;

import first.fargo_soul.FargoSoul;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 按键注册。
 * <p>
 * 名称一律使用翻译键（{@code key.fargo_soul.<name>}）与翻译键分类
 * （{@link #CATEGORY}），显示文本由语言文件提供，不再硬编码。
 * </p>
 */
@EventBusSubscriber(modid = FargoSoul.MODID, value = Dist.CLIENT)
public class FargoSoulKeyRegister {

    /** 按键分类翻译键 */
    public static final String CATEGORY = "key.categories.fargo_soul";

    private static final List<KeyMapping> keyList = new ArrayList<>();

    public static final KeyMapping BlazeSoulKey = register("blaze_soul");
    public static final KeyMapping GoldSoulKey = register("gold_soul");
    public static final KeyMapping StardustSoulKey = register("stardust_soul");
    public static final KeyMapping VortexSoulKey = register("vortex_soul");
    public static final KeyMapping ForbiddenKey = register("forbidden");
    public static final KeyMapping PenetratingNinjaKey = register("penetrating_ninja");
    public static final KeyMapping PalmWoodKey = register("palm_wood");
    public static final KeyMapping SoulMenuKey = register("soul_menu");

    private static KeyMapping register(String name) {
        KeyMapping keyMapping = new KeyMapping("key." + FargoSoul.MODID + "." + name, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, InputConstants.UNKNOWN.getValue(), CATEGORY);
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

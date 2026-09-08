package first.fargo_soul.register;

import first.fargo_soul.FargoSoul;
import first.lyra.client.creativeTab.AnimBanner;
import first.lyra.common.creativeTab.CreativeTabDispatcher;
import first.lyra.common.creativeTab.Section;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FargoSoulCreativeModeTabRegister {

    private static final DeferredRegister<CreativeModeTab> Register = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FargoSoul.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> FargoSoulTab = Register.register(FargoSoul.MODID, () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.fargo_soul")).icon(() -> FargoSoulItemRegister.TerraSoulItem.get().getDefaultInstance()).build());

    public static final Section SOUL = new Section(1, FargoSoul.rl("textures/item/banner/default_banner.png"), new AnimBanner(18, 1, 1), FargoSoulItemTagsRegister.SectionSoul);
    public static final Section POWER = new Section(2, FargoSoul.rl("textures/item/banner/default_banner.png"), new AnimBanner(18, 1, 1), FargoSoulItemTagsRegister.SectionPower);
    public static final Section ENCHANTMENT = new Section(3, FargoSoul.rl("textures/item/banner/default_banner.png"), new AnimBanner(18, 1, 1), FargoSoulItemTagsRegister.SectionEnchantment);

    public static void register(IEventBus eventBus) {
        Register.register(eventBus);
        eventBus.addListener((FMLCommonSetupEvent event) -> CreativeTabDispatcher.register(FargoSoulTab, SOUL, POWER, ENCHANTMENT));
    }
}

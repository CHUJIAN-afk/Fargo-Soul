package first.fargo_soul.register;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import first.fargo_soul.FargoSoul;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.List;

public class FargoSoulCreativeModeTabRegister {

    private static final Registrate REGISTER = FargoSoul.REGISTRATE;

    private static final RegistryEntry<CreativeModeTab, CreativeModeTab> Tab = REGISTER.simple("tab", Registries.CREATIVE_MODE_TAB, () ->
            CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.fargo_soul"))
                    .icon(() -> FargoSoulItemRegister.TerraSoulItem.get().getDefaultInstance())
                    .displayItems((params, output) -> {
                        for (TagKey<Item> tag : List.of(FargoSoulItemTagsRegister.SectionSoul, FargoSoulItemTagsRegister.SectionPower, FargoSoulItemTagsRegister.SectionEnchantment)) {
                            BuiltInRegistries.ITEM.stream().filter(item -> item.getDefaultInstance().is(tag)).forEach(output::accept);
                        }
                    })
                    .build());

    public static void init() {
    }
}

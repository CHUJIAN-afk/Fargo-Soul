package first.fargo_soul.mixin.minecraft;

import net.neoforged.neoforge.common.data.LanguageProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(LanguageProvider.class)
public interface LanguageProviderAccessor {
    @Accessor
    Map<String, String> getData();

    @Accessor
    String getModid();

    @Accessor
    String getLocale();
}

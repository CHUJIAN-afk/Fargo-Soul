package first.fargo_soul.compat.jei;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.register.FargoSoulItemRegister;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class FargoSoulJeiPlugin implements IModPlugin {

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return FargoSoul.rl("jei_plugin");
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        FargoSoulItemRegister.JEI_INFO.forEach((item, components) -> registration.addIngredientInfo(item, components.toArray(new Component[0])));
    }
}

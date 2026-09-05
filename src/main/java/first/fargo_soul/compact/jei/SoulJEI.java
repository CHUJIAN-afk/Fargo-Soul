package first.fargo_soul.compact.jei;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.recipe.SoulRecipe;
import first.fargo_soul.register.FargoSoulItemRegister;
import first.fargo_soul.register.FargoSoulRecipeTypeRegister;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@JeiPlugin
public class SoulJEI implements IModPlugin {

	@Override
	public @NotNull ResourceLocation getPluginUid() {
		return FargoSoul.rl("jei_plugin");
	}

	@Override
	public void registerCategories(@NotNull IRecipeCategoryRegistration registration) {
		registration.addRecipeCategories(new SoulCategory(registration.getJeiHelpers().getGuiHelper()));
	}

	@Override
	public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(FargoSoulItemRegister.CosmicCrucibleBlockItem.asItem()), SoulCategory.SoulRecipeType);
	}

	@Override
	public void registerRecipes(@NotNull IRecipeRegistration registration) {
		if (Minecraft.getInstance().level instanceof Level level) {
			List<SoulRecipe> soulRecipeList = level.getRecipeManager().getAllRecipesFor(FargoSoulRecipeTypeRegister.Integration.get()).stream().map(RecipeHolder::value).toList();
			registration.addRecipes(SoulCategory.SoulRecipeType, soulRecipeList);
		}
	}
}

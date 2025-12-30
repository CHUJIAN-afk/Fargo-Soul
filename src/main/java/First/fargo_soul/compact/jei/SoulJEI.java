package First.fargo_soul.compact.jei;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.recipe.SoulRecipe;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.register.RecipeTypeRegister;
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
		return ResourceLocation.fromNamespaceAndPath(FargoSoul.MODID, "jei_plugin");
	}

	@Override
	public void registerCategories(@NotNull IRecipeCategoryRegistration registration) {
		registration.addRecipeCategories(new SoulCategory(registration.getJeiHelpers().getGuiHelper()));
	}

	@Override
	public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(ItemRegister.CosmicCrucibleBlockItem.asItem()), SoulCategory.SoulRecipeType);
	}

	@Override
	public void registerRecipes(@NotNull IRecipeRegistration registration) {
		if (Minecraft.getInstance().level instanceof Level level) {
			List<SoulRecipe> soulRecipeList = level.getRecipeManager().getAllRecipesFor(RecipeTypeRegister.Integration.get()).stream().map(RecipeHolder::value).toList();
			registration.addRecipes(SoulCategory.SoulRecipeType, soulRecipeList);
		}
	}

}

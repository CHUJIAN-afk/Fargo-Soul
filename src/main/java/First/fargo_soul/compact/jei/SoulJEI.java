package First.fargo_soul.compact.jei;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.client.screen.SoulContainerScreen;
import First.fargo_soul.common.recipe.SoulRecipe;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.register.RecipeTypeRegister;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Rect2i;
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
		registration.addRecipeCatalyst(new ItemStack(ItemRegister.CosmicCrucibleBlockItem.asItem()), SoulCategory.SoulRecipeType);
	}

	@Override
	public void registerRecipes(@NotNull IRecipeRegistration registration) {
		if (Minecraft.getInstance().level instanceof Level level) {
			List<SoulRecipe> soulRecipeList = level.getRecipeManager().getAllRecipesFor(RecipeTypeRegister.Integration.get()).stream().map(RecipeHolder::value).toList();
			registration.addRecipes(SoulCategory.SoulRecipeType, soulRecipeList);
		}
	}

	@Override
	public void registerGuiHandlers(@NotNull IGuiHandlerRegistration registration) {
		registration.addGuiContainerHandler(SoulContainerScreen.class, new IGuiContainerHandler<>() {

			@Override
			public @NotNull List<Rect2i> getGuiExtraAreas(@NotNull SoulContainerScreen screen) {
				return List.of(new Rect2i(screen.getXPos() - 4, screen.getYPos() - 4, screen.getWidth() + 8, screen.getHeight() + 8));
				/*
				Collection<SoulContainerScreen.ButtonInfo> values = containerScreen.getButtonInfoMap().values();
				if (!values.isEmpty()) {
					int xPos = values.stream().mapToInt(SoulContainerScreen.ButtonInfo::x).min().getAsInt();
					int yPos = values.stream().mapToInt(SoulContainerScreen.ButtonInfo::y).min().getAsInt();
					int maxX = values.stream().mapToInt(b -> b.x() + b.width()).max().getAsInt();
					int maxY = values.stream().mapToInt(b -> b.y() + b.height()).max().getAsInt();
					return List.of(new Rect2i(xPos, yPos, maxX - xPos, maxY - yPos));
				}
				return IGuiContainerHandler.super.getGuiExtraAreas(containerScreen);
				*/
			}

		});
	}

}

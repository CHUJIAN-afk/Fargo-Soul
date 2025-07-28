package First.fargo_soul.Dadageneeator;

import First.fargo_soul.Item.Soul.SoulItem;
import First.fargo_soul.Item.Soul.Souls;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        addShapelessRecipe(recipeOutput, Souls.EarthPower);
        addShapelessRecipe(recipeOutput, Souls.ForestPower);
        addShapelessRecipe(recipeOutput, Souls.LifePower);
        addShapelessRecipe(recipeOutput, Souls.NaturePower);
        addShapelessRecipe(recipeOutput, Souls.TerraPower);
        addShapelessRecipe(recipeOutput, Souls.SpiritPower);
        addShapelessRecipe(recipeOutput, Souls.DeathPower);
        addShapelessRecipe(recipeOutput, Souls.WillPower);
        addShapelessRecipe(recipeOutput, Souls.CosmicPower);
        addShapelessRecipe(recipeOutput, Souls.TerraSoul);
    }

    protected void addShapelessRecipe(RecipeOutput recipeOutput, DeferredItem<SoulItem> output) {
        ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output);
        List<SoulItem> soulItems = output.get().getCurioItemList();
        for (SoulItem input : soulItems) {
            builder.requires(input);
        }
        builder.unlockedBy("has_" + soulItems.getFirst(), has(soulItems.getFirst()))
                .save(recipeOutput, output.getId());
    }

}

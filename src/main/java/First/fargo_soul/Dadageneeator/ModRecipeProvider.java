package First.fargo_soul.Dadageneeator;

import First.create.Create.CreateCompact;
import First.create.Create.CreateSoulsRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
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

        addShapelessRecipe(recipeOutput, SoulsRegister.EarthPower);
        addShapelessRecipe(recipeOutput, SoulsRegister.ForestPower);
        addShapelessRecipe(recipeOutput, SoulsRegister.LifePower);
        addShapelessRecipe(recipeOutput, SoulsRegister.NaturePower);
        addShapelessRecipe(recipeOutput, SoulsRegister.TerraPower);
        addShapelessRecipe(recipeOutput, SoulsRegister.SpiritPower);
        addShapelessRecipe(recipeOutput, SoulsRegister.DeathPower);
        addShapelessRecipe(recipeOutput, SoulsRegister.WillPower);
        addShapelessRecipe(recipeOutput, SoulsRegister.CosmicPower);
        addShapelessRecipe(recipeOutput, SoulsRegister.TerraSoul);

        addShapelessRecipe(recipeOutput, SoulsRegister.BerserkerSoul);
        addShapelessRecipe(recipeOutput, SoulsRegister.SharpshooterSoul);
        addShapelessRecipe(recipeOutput, SoulsRegister.UniverseSoul);


        //机械动力联动
        addShapelessRecipe(recipeOutput.withConditions(new ModLoadedCondition(CreateCompact.MODID)), CreateSoulsRegister.Create_Power);
    }

    protected void addShapelessRecipe(RecipeOutput recipeOutput, DeferredItem<SoulItem> output) {
        ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output);
        List<SoulItem> soulItems = output.get().getSoulItemList();
        soulItems.forEach(builder::requires);
        builder.unlockedBy("has_" + soulItems.getFirst(), has(soulItems.getFirst())).save(recipeOutput, output.getId());
    }

}

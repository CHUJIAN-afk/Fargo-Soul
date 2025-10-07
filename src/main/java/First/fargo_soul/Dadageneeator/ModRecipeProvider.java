package First.fargo_soul.Dadageneeator;

import First.fargo_soul.Event.DataGeneratorRecipeEvent;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        DataGeneratorRecipeEvent dataGeneratorModelsEvent = new DataGeneratorRecipeEvent();
        NeoForge.EVENT_BUS.post(dataGeneratorModelsEvent);
        List<SoulItem> soulItemList = dataGeneratorModelsEvent.getSoulItemList();
        for (SoulItem soulItem : soulItemList) {
            addShapelessRecipe(recipeOutput, soulItem);
        }
    }

    private void addShapelessRecipe(RecipeOutput recipeOutput, SoulItem soulItem) {
        ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, soulItem);
        List<SoulItem> soulItems = soulItem.getSoulItemList();
        soulItems.forEach(builder::requires);
        builder.unlockedBy("has_" + soulItems.getFirst(), has(soulItems.getFirst())).save(recipeOutput, BuiltInRegistries.ITEM.getKey(soulItem));
    }

}

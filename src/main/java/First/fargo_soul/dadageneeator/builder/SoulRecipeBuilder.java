package First.fargo_soul.dadageneeator.builder;

import First.fargo_soul.common.recipe.SoulRecipe;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SoulRecipeBuilder implements RecipeBuilder {

    private final List<ItemStack> ingredients;
    private final ItemStack result;
    private RecipeOutput output = null;

    public SoulRecipeBuilder(List<ItemStack> ingredients, ItemStack result) {
        this.ingredients = ingredients;
        this.result = result;
    }

    public SoulRecipeBuilder requires(Item item, int count) {
        this.ingredients.add(item.getDefaultInstance().copyWithCount(count));
        return this;
    }

    public SoulRecipeBuilder requires(ItemStack itemStack, int count) {
        this.ingredients.add(itemStack.copyWithCount(count));
        return this;
    }

    public void setOutput(RecipeOutput output) {
        this.output = output;
    }

    public void build() {
        if (output != null) {
            save(output, BuiltInRegistries.ITEM.getKey(result.getItem()));
        }
    }

    public static SoulRecipeBuilder soulRecipe(ItemStack result) {
        return new SoulRecipeBuilder(new ArrayList<>(), result);
    }

    public void addIngredient(Item item, int count) {
        this.ingredients.add(new ItemStack(item, count));
    }

    @Override
    public @NotNull SoulRecipeBuilder unlockedBy(@NotNull String name, @NotNull Criterion<?> criterion) {
        return this;
    }

    @Override
    public @NotNull SoulRecipeBuilder group(@Nullable String group) {
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return result.getItem();
    }

    @Override
    public void save(RecipeOutput output, @NotNull ResourceLocation id) {
        SoulRecipe recipe = new SoulRecipe(this.ingredients, this.result);
        output.accept(id, recipe, null);
    }

}

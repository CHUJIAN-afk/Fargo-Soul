package First.fargo_soul.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record SoulRecipeInput(List<ItemStack> itemStackList) implements RecipeInput {

    @Override
    public @NotNull ItemStack getItem(int i) {
        return itemStackList.get(i);
    }

    @Override
    public int size() {
        return itemStackList.size();
    }

}

package First.fargo_soul.common.recipe;

import First.fargo_soul.register.RecipeSerializerRegister;
import First.fargo_soul.register.RecipeTypeRegister;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record SoulRecipe(List<ItemStack> inputs, ItemStack output) implements Recipe<SoulRecipeInput> {

    @Override
    public boolean matches(@NotNull SoulRecipeInput soulRecipeInput, @NotNull Level level) {
        List<ItemStack> targetList = this.inputs;
        List<ItemStack> inputList = soulRecipeInput.itemStackList();
        if (inputList.size() < targetList.size()) {
            return false;
        }
        for (ItemStack target : targetList) {
            boolean foundMatch = false;
            for (ItemStack input : inputList) {
                if (ItemStack.isSameItem(target, input)) {
                    if (input.getCount() >= target.getCount()) {
                        foundMatch = true;
                        break;
                    } else {
                        return false;
                    }
                }
            }
            if (!foundMatch) {
                return false;
            }
        }
        return true;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SoulRecipeInput soulRecipeInput, HolderLookup.@NotNull Provider provider) {
        return this.output;
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider provider) {
        return this.output.copy();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeSerializerRegister.Integration.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return RecipeTypeRegister.Integration.get();
    }

    public static class Serializer implements RecipeSerializer<SoulRecipe> {

        private static final MapCodec<SoulRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                ItemStack.CODEC.listOf().fieldOf("ingredients").forGetter(SoulRecipe::inputs),
                ItemStack.CODEC.fieldOf("result").forGetter(SoulRecipe::output)
        ).apply(inst, SoulRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, SoulRecipe> STREAM_CODEC = StreamCodec.composite(
                ItemStack.STREAM_CODEC.apply(ByteBufCodecs.list()),
                SoulRecipe::inputs,
                ItemStack.STREAM_CODEC,
                SoulRecipe::output,
                SoulRecipe::new
        );

        @Override
        public @NotNull MapCodec<SoulRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, SoulRecipe> streamCodec() {
            return STREAM_CODEC;
        }

    }

}

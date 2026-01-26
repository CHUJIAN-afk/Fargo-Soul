package First.fargo_soul.compact.jei;


import First.fargo_soul.FargoSoul;
import First.fargo_soul.common.recipe.SoulRecipe;
import First.fargo_soul.register.ItemRegister;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SoulCategory implements IRecipeCategory<SoulRecipe> {

    public static final RecipeType<SoulRecipe> SoulRecipeType = new RecipeType<>(FargoSoul.rl("integration"), SoulRecipe.class);

    private final IGuiHelper helper;
    private final IDrawable background;
    private final IDrawable icon;
    private final Component localizedName;

    public SoulCategory(IGuiHelper helper) {
        this.helper = helper;
        this.background = helper.createBlankDrawable(144, 144);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ItemRegister.CosmicCrucibleBlockItem.asItem()));
        this.localizedName = Component.translatable("jei.fargo_soul.category.soul");
    }

    @Override
    public @NotNull RecipeType<SoulRecipe> getRecipeType() {
        return SoulRecipeType;
    }

    @Override
    public @NotNull Component getTitle() {
        return localizedName;
    }

    @Override
    public void draw(@NotNull SoulRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics);
        int centerX = getWidth() / 2 - 10;
        int centerY = getHeight() / 2 - 10;
        helper.getSlotDrawable().draw(guiGraphics, centerX, centerY);
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, SoulRecipe recipe, @NotNull IFocusGroup focuses) {
        int materialCount = recipe.inputs().size();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        builder.addOutputSlot(centerX - 9, centerY - 9).addItemStack(recipe.output());
        double radius = Math.min(centerX, centerY) * 0.75;
        for (int i = 0; i < materialCount; i++) {
            double angle = 2 * Math.PI * i / materialCount - Math.PI / 2;
            int x = (int) (centerX + radius * Math.cos(angle)) - 9;
            int y = (int) (centerY + radius * Math.sin(angle)) - 9;
            builder.addSlot(RecipeIngredientRole.INPUT, x, y).addItemStack(recipe.inputs().get(i));
        }
    }

    @Override
    public int getWidth() {
        return background.getWidth();
    }

    @Override
    public int getHeight() {
        return background.getHeight();
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

}
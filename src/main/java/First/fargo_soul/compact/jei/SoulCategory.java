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
        this.background = helper.createBlankDrawable(100, 100);
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
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // 1. 产物槽位置 - 稍微再往下移，为长箭头的上下间距腾出空间
        int outputY = centerY + 30;
        helper.getSlotDrawable().draw(guiGraphics, centerX - 9, outputY - 9);

        // 2. 布局参数
        int materialCount = recipe.inputs().size();
        int slotSize = 18;
        int spacing = 2;
        int maxPerRow = 5;

        int rows = (materialCount + maxPerRow - 1) / maxPerRow;
        // 材料区域起始Y - 向上推移，给中间留出约 40 像素的间距
        int startY = outputY - 40 - (rows * slotSize + (rows - 1) * spacing);

        // 3. 渲染材料槽背景
        for (int i = 0; i < materialCount; i++) {
            int row = i / maxPerRow;
            int col = i % maxPerRow;
            int rowCount = Math.min(materialCount - row * maxPerRow, maxPerRow);

            int rowWidth = rowCount * slotSize + (rowCount - 1) * spacing;
            int rowStartX = (getWidth() - rowWidth) / 2;

            int x = rowStartX + col * (slotSize + spacing);
            int y = startY + row * (slotSize + spacing);

            helper.getSlotDrawable().draw(guiGraphics, x, y);
        }

        // 4. 渲染从上往下的箭头
        int materialBottomY = startY + rows * slotSize + (rows - 1) * spacing;

        // 箭头原始尺寸通常为 24(宽) x 17(高)
        // 旋转后：宽 17, 高 24
        int arrowWidth = 17;
        int arrowHeight = 24;

        // 计算箭头中心位置
        int arrowX = centerX;
        int arrowY = materialBottomY + (outputY - 9 - materialBottomY) / 2;

        // --- 执行旋转渲染 ---
        guiGraphics.pose().pushPose();
        // 平移到箭头中心点
        guiGraphics.pose().translate(arrowX, arrowY, 0);
        // 旋转 90 度 (顺时针)
        guiGraphics.pose().mulPose(com.mojang.math.Axis.ZP.rotationDegrees(90));
        // 抵消箭头纹理本身的偏移 (回到原点渲染，使其绕中心旋转)
        // 注意：draw 默认从 (0,0) 开始画 24x17，所以旋转中心偏移应为宽的一半和高的一半
        helper.getRecipeArrow().draw(guiGraphics, -12, -8);
        guiGraphics.pose().popPose();
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, SoulRecipe recipe, @NotNull IFocusGroup focuses) {
        int offset = 1; // 与 draw 保持一致
        int centerX = getWidth() / 2 + offset;
        int centerY = getHeight() / 2 + offset;

        // 1. 同步产物槽位置
        int outputY = centerY + 30;
        // addOutputSlot 的坐标是左上角坐标
        builder.addOutputSlot(centerX - 9, outputY - 9).addItemStack(recipe.output());

        int materialCount = recipe.inputs().size();
        int slotSize = 18;
        int spacing = 2;
        int maxPerRow = 5;

        int rows = (materialCount + maxPerRow - 1) / maxPerRow;
        int startY = outputY - 40 - (rows * slotSize + (rows - 1) * spacing);

        // 3. 布局材料输入槽
        for (int i = 0; i < materialCount; i++) {
            int row = i / maxPerRow;
            int col = i % maxPerRow;
            int rowCount = Math.min(materialCount - row * maxPerRow, maxPerRow);

            int rowWidth = rowCount * slotSize + (rowCount - 1) * spacing;
            int rowStartX = (getWidth() + (offset * 2) - rowWidth) / 2;

            int x = rowStartX + col * (slotSize + spacing);
            int y = startY + row * (slotSize + spacing);

            // builder 添加槽位，x 和 y 是槽位的左上角
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
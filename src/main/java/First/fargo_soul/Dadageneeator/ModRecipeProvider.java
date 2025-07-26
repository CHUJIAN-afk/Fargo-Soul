package First.fargo_soul.Dadageneeator;

import First.fargo_soul.Item.Soul.Souls;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;


public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.WoodSoul.get())
                .define('X', Items.OAK_LOG)
                .define('A', Items.VINE)
                .define('C', Items.GRASS_BLOCK)
                .define('S', Items.FERN)
                .define('P', Items.BAMBOO)
                .define('B', Items.PUMPKIN_SEEDS)
                .pattern("CAC")
                .pattern("SXS")
                .pattern("BPB")
                .unlockedBy("has_oak_log", has(Items.OAK_LOG))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.PineWoodSoul.get())
                .define('X', Items.SPRUCE_LOG)
                .define('A', Items.PINK_BANNER)
                .define('C', Items.SNOW_BLOCK)
                .define('S', Items.BROWN_MUSHROOM)
                .define('P', Items.STICK)
                .define('B', Items.SPRUCE_PLANKS)
                .pattern("CAC")
                .pattern("SXS")
                .pattern("BPB")
                .unlockedBy("has_spruce_log", has(Items.SPRUCE_LOG))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.RosewoodSoul.get())
                .define('X', Items.CRIMSON_STEM)
                .define('A', Items.POPPY)
                .define('C', Items.RED_TULIP)
                .define('S', Items.ROSE_BUSH)
                .define('P', Items.BEETROOT)
                .define('B', Items.FLOWER_POT)
                .pattern("CAC")
                .pattern("SXS")
                .pattern("BPB")
                .unlockedBy("has_crimson_stem", has(Items.CRIMSON_STEM))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.EbonyWoodSoul.get())
                .define('X', Items.DARK_OAK_LOG)
                .define('A', Items.OBSIDIAN)
                .define('C', Items.BLACKSTONE)
                .define('S', Items.WARPED_NYLIUM)
                .define('P', Items.COAL)
                .define('B', Items.GUNPOWDER)
                .pattern("CAC")
                .pattern("SXS")
                .pattern("BPB")
                .unlockedBy("has_dark_oak_log", has(Items.DARK_OAK_LOG))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.ShadowWoodSoul.get())
                .define('X', Items.WARPED_STEM)
                .define('A', Items.SHROOMLIGHT)
                .define('C', Items.WARPED_NYLIUM)
                .define('S', Items.NETHER_WART)
                .define('P', Items.SOUL_SOIL)
                .define('B', Items.ECHO_SHARD)
                .pattern("CAC")
                .pattern("SXS")
                .pattern("BPB")
                .unlockedBy("has_warped_stem", has(Items.WARPED_STEM))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.PalmWoodSoul.get())
                .define('X', Items.JUNGLE_LOG)
                .define('A', Items.COCOA_BEANS)
                .define('C', Items.SAND)
                .define('S', Items.VINE)
                .define('P', Items.STICK)
                .define('B', Items.FEATHER)
                .pattern("CAC")
                .pattern("SXS")
                .pattern("BPB")
                .unlockedBy("has_jungle_log", has(Items.JUNGLE_LOG))
                .save(recipeOutput);


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.PearlWoodSoul.get())
                .define('X', Items.BAMBOO)
                .define('A', Items.PRISMARINE_SHARD)
                .define('C', Items.NAUTILUS_SHELL)
                .define('S', Items.HEART_OF_THE_SEA)
                .define('P', Items.END_ROD)
                .define('B', Items.GLOWSTONE_DUST)
                .pattern("CAC")
                .pattern("SXS")
                .pattern("BPB")
                .unlockedBy("has_bamboo", has(Items.BAMBOO))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.ForestPower.get())
                .define('A', Souls.WoodSoul.get())
                .define('B', Souls.PineWoodSoul.get())
                .define('C', Souls.RosewoodSoul.get())
                .define('D', Souls.EbonyWoodSoul.get())
                .define('E', Souls.ShadowWoodSoul.get())
                .define('F', Souls.PalmWoodSoul.get())
                .define('G', Souls.PearlWoodSoul.get())
                .pattern("ABC")
                .pattern("DEF")
                .pattern("G  ")
                .unlockedBy("has_wood_soul", has(Souls.WoodSoul.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.CopperSoul.get())
                .define('X', Items.COPPER_INGOT)
                .define('A', Items.RAW_COPPER)
                .define('C', Items.CLAY_BALL)
                .define('S', Items.COPPER_BLOCK)
                .define('P', Items.BRICK)
                .define('B', Items.FLINT)
                .pattern("CAC")
                .pattern("SXS")
                .pattern("BPB")
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.TinSoul.get())
                .define('X', Items.IRON_INGOT) // 假设锡锭暂时没有，可以用铁锭代替，或者自定义物品
                .define('A', Items.RAW_IRON)   // 如果你有锡矿相关，可以适当替换
                .define('C', Items.STONE)
                .define('S', Items.COBBLESTONE)
                .define('P', Items.GRAY_DYE)
                .define('B', Items.TERRACOTTA)
                .pattern("CAC")
                .pattern("SXS")
                .pattern("BPB")
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.IronSoul.get())
                .define('X', Items.IRON_INGOT)
                .define('A', Items.IRON_NUGGET)
                .define('C', Items.FURNACE)
                .define('S', Items.IRON_ORE)
                .define('P', Items.COAL)
                .define('B', Items.REDSTONE)
                .pattern("CAC")
                .pattern("SXS")
                .pattern("BPB")
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.LeadSoul.get())
                .define('X', Items.IRON_INGOT) // 假设你没有铅锭，可用铁锭或自定义铅锭
                .define('A', Items.COAL_BLOCK)
                .define('C', Items.STONE_BRICKS)
                .define('S', Items.GRAVEL)
                .define('P', Items.CHARCOAL)
                .define('B', Items.LEAD) // 如果有自定义铅锭，请替换为 YourModItems.LEAD_INGOT.get()
                .pattern("CAC")
                .pattern("SXS")
                .pattern("BPB")
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.SilverSoul.get())
                .define('X', Items.IRON_INGOT) // 同样，原版没有，如果你使用自定义银锭，请替换
                .define('A', Items.IRON_INGOT)
                .define('C', Items.GLOWSTONE_DUST)
                .define('S', Items.LAPIS_LAZULI)
                .define('P', Items.PRISMARINE_SHARD)
                .define('B', Items.WHITE_WOOL)
                .pattern("CAC")
                .pattern("SXS")
                .pattern("BPB")
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.TungstenSoul.get())
                .define('X', Items.IRON_INGOT) // 若你自定义了钨锭，替换为你的钨锭
                .define('A', Items.REDSTONE_BLOCK)
                .define('C', Items.QUARTZ)
                .define('S', Items.IRON_NUGGET)
                .define('P', Items.DIAMOND)
                .define('B', Items.IRON_BLOCK)
                .pattern("CAC")
                .pattern("SXS")
                .pattern("BPB")
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.ObsidianSoul.get())
                .define('X', Items.OBSIDIAN)
                .define('A', Items.BASALT)
                .define('C', Items.BLACKSTONE)
                .define('S', Items.MAGMA_BLOCK)
                .define('P', Items.SOUL_SOIL)
                .define('B', Items.LAVA_BUCKET)
                .pattern("CAC")
                .pattern("SXS")
                .pattern("BPB")
                .unlockedBy("has_obsidian", has(Items.OBSIDIAN))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.TerraPower.get())
                .define('A', Souls.CopperSoul.get())
                .define('B', Souls.TinSoul.get())
                .define('C', Souls.IronSoul.get())
                .define('D', Souls.LeadSoul.get())
                .define('E', Souls.SilverSoul.get())
                .define('F', Souls.TungstenSoul.get())
                .define('G', Souls.ObsidianSoul.get())
                .pattern("ABC")
                .pattern("DEF")
                .pattern("G  ")
                .unlockedBy("has_copper_soul", has(Souls.CopperSoul.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.AncientCobaltSoul.get())
                .define('X', Items.COAL_BLOCK)
                .define('A', Items.IRON_BLOCK)
                .define('C', Items.NETHER_QUARTZ_ORE)
                .define('S', Items.BLACKSTONE)
                .define('P', Items.DEEPSLATE)
                .define('B', Items.FOX_SPAWN_EGG)
                .pattern("CAC")
                .pattern("SXS")
                .pattern("BPB")
                .unlockedBy("has_coal_block", has(Items.COAL_BLOCK))
                .save(recipeOutput);


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.CobaltSoul.get())
                .define('X', Items.IRON_INGOT)
                .define('A', Items.RAW_IRON)
                .define('C', Items.STONE)
                .define('S', Items.COBBLESTONE)
                .define('P', Items.GRAVEL)
                .define('B', Items.DIRT)
                .pattern("CAC")
                .pattern("SXS")
                .pattern("BPB")
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.PalladiumSoul.get())
                .define('X', Items.GOLD_INGOT)
                .define('A', Items.IRON_INGOT)
                .define('C', Items.QUARTZ)
                .define('S', Items.DIAMOND)
                .define('P', Items.EMERALD)
                .define('B', Items.LAPIS_LAZULI)
                .pattern("CAC")
                .pattern("SXS")
                .pattern("BPB")
                .unlockedBy("has_gold_ingot", has(Items.GOLD_INGOT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.MithrilSoul.get())
                .define('X', Items.IRON_INGOT) // 或自定义秘银锭
                .define('A', Items.IRON_INGOT)
                .define('C', Items.DIAMOND)
                .define('S', Items.NETHER_STAR) // 可选稀有材料
                .define('P', Items.PRISMARINE_SHARD)
                .define('B', Items.END_STONE)
                .pattern("CAC")
                .pattern("SXS")
                .pattern("BPB")
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.OrichalcumSoul.get())
                .define('X', Items.COPPER_INGOT)
                .define('A', Items.GOLD_INGOT)
                .define('C', Items.REDSTONE_BLOCK)
                .define('S', Items.QUARTZ_BLOCK)
                .define('P', Items.BRICK)
                .define('B', Items.LAPIS_BLOCK)
                .pattern("CAC")
                .pattern("SXS")
                .pattern("BPB")
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.AdamantiteSoul.get())
                .define('X', Items.DIAMOND)
                .define('A', Items.NETHERITE_INGOT)
                .define('C', Items.OBSIDIAN)
                .define('S', Items.ANCIENT_DEBRIS)
                .define('P', Items.ENDER_EYE)
                .define('B', Items.BASALT)
                .pattern("CAC")
                .pattern("SXS")
                .pattern("BPB")
                .unlockedBy("has_diamond", has(Items.DIAMOND))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.TitaniumSoul.get())
                .define('X', Items.IRON_INGOT)
                .define('A', Items.GOLD_INGOT)
                .define('C', Items.QUARTZ)
                .define('S', Items.BONE_BLOCK)
                .define('P', Items.PRISMARINE_SHARD) // 或用海晶碎片替代
                .define('B', Items.END_STONE_BRICKS)
                .pattern("CAC")
                .pattern("SXS")
                .pattern("BPB")
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.EarthPower.get())
                .define('A', Souls.AncientCobaltSoul.get())
                .define('B', Souls.CobaltSoul.get())
                .define('C', Souls.PalladiumSoul.get())
                .define('D', Souls.MithrilSoul.get())
                .define('E', Souls.OrichalcumSoul.get())
                .define('F', Souls.AdamantiteSoul.get())
                .define('G', Souls.TitaniumSoul.get())
                .pattern("ABC")
                .pattern("DEF")
                .pattern("G  ")
                .unlockedBy("has_ancient_cobalt_soul", has(Souls.AncientCobaltSoul.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.CrimsonSoul.get())
                .define('X', Items.CRIMSON_STEM)
                .define('A', Items.NETHER_WART)
                .define('C', Items.SOUL_SOIL)
                .define('S', Items.WARPED_NYLIUM)
                .define('P', Items.RED_MUSHROOM)
                .define('B', Items.TNT)
                .pattern("CAC")
                .pattern("SXS")
                .pattern("BPB")
                .unlockedBy("has_crimson_stem", has(Items.CRIMSON_STEM))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.LavaSoul.get())
                .define('X', Items.LAVA_BUCKET)
                .define('A', Items.MAGMA_BLOCK)
                .define('C', Items.STONE_BRICKS)
                .define('S', Items.FIRE_CHARGE)
                .define('P', Items.BLAZE_POWDER)
                .define('B', Items.OBSIDIAN)
                .pattern("CAC")
                .pattern("SXS")
                .pattern("BPB")
                .unlockedBy("has_lava_bucket", has(Items.LAVA_BUCKET))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.RainCloudSoul.get())
                .define('X', Items.WATER_BUCKET)
                .define('A', Items.WHITE_WOOL) // 可使用普通羊毛或青金石等替代
                .define('C', Items.PRISMARINE_SHARD)
                .define('S', Items.SNOWBALL)
                .define('P', Items.FEATHER)
                .define('B', Items.BLUE_WOOL) // 无云方块，可用 END_ROD 或 BLUE_WOOL 替代
                .pattern("CAC")
                .pattern("SXS")
                .pattern("BPB")
                .unlockedBy("has_water_bucket", has(Items.WATER_BUCKET))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.FrostSoul.get())
                .define('X', Items.SNOW_BLOCK)
                .define('A', Items.ICE)
                .define('C', Items.PACKED_ICE)
                .define('S', Items.SNOWBALL)
                .define('P', Items.BLUE_ICE)
                .define('B', Items.MELON_SLICE)
                .pattern("CAC")
                .pattern("SXS")
                .pattern("BPB")
                .unlockedBy("has_snow_block", has(Items.SNOW_BLOCK))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.MushroomSoul.get())
                .define('X', Items.RED_MUSHROOM)
                .define('A', Items.BROWN_MUSHROOM)
                .define('C', Items.MYCELIUM)
                .define('S', Items.PODZOL)
                .define('P', Items.BROWN_MUSHROOM_BLOCK)
                .define('B', Items.RED_MUSHROOM_BLOCK)
                .pattern("CAC")
                .pattern("SXS")
                .pattern("BPB")
                .unlockedBy("has_red_mushroom", has(Items.RED_MUSHROOM))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.NaturePower.get())
                .define('A', Souls.CrimsonSoul.get())
                .define('B', Souls.LavaSoul.get())
                .define('C', Souls.RainCloudSoul.get())
                .define('D', Souls.FrostSoul.get())
                .define('E', Souls.GreenSoul.get())
                .define('F', Souls.MushroomSoul.get())
                .pattern("ABC")
                .pattern("DE ")
                .pattern("F  ")
                .unlockedBy("has_crimson_soul", has(Souls.CrimsonSoul.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.BeeSoul.get())
                .define('X', Items.BEE_NEST)
                .define('A', Items.HONEYCOMB)
                .define('C', Items.HONEY_BLOCK)
                .define('S', Items.FLOWER_POT)
                .define('P', Items.POPPY)
                .define('B', Items.GRASS_BLOCK)
                .pattern("CAC")
                .pattern("SXS")
                .pattern("BPB")
                .unlockedBy("has_bee", has(Items.BEEHIVE)) // 若没有蜜蜂物品，可用蜂巢替代解锁
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.BeetleSoul.get())
                .define('X', Items.OAK_SAPLING) // 暂用树苗代替，如果有自定义甲虫物品请替换
                .define('A', Items.STICK)
                .define('C', Items.ACACIA_LEAVES)
                .define('S', Items.DIRT)
                .define('P', Items.SEAGRASS)
                .define('B', Items.BROWN_MUSHROOM)
                .pattern("CAC")
                .pattern("SXS")
                .pattern("BPB")
                .unlockedBy("has_stick", has(Items.STICK))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.PumpkinSoul.get())
                .define('X', Items.PUMPKIN)
                .define('A', Items.CARVED_PUMPKIN)
                .define('C', Items.JACK_O_LANTERN)
                .define('S', Items.MELON_SLICE)
                .define('P', Items.SUGAR_CANE)
                .define('B', Items.HAY_BLOCK)
                .pattern("CAC")
                .pattern("SXS")
                .pattern("BPB")
                .unlockedBy("has_pumpkin", has(Items.PUMPKIN))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.SpiderSoul.get())
                .define('X', Items.STRING)
                .define('A', Items.SPIDER_EYE)
                .define('C', Items.TRIPWIRE_HOOK) // 假定你有蜘蛛网方块，否则用 TRIPWIRE
                .define('S', Items.FEATHER)
                .define('P', Items.COBWEB)
                .define('B', Items.DARK_OAK_LEAVES)
                .pattern("CAC")
                .pattern("SXS")
                .pattern("BPB")
                .unlockedBy("has_string", has(Items.STRING))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.CactusSoul.get())
                .define('X', Items.CACTUS)
                .define('A', Items.GREEN_WOOL)
                .define('C', Items.SAND)
                .define('S', Items.PRISMARINE_SHARD)
                .define('P', Items.DEAD_BUSH)
                .define('B', Items.BROWN_MUSHROOM)
                .pattern("CAC")
                .pattern("SXS")
                .pattern("BPB")
                .unlockedBy("has_cactus", has(Items.CACTUS))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.TurtleSoul.get())
                .define('X', Items.TURTLE_SCUTE) // 海龟壳
                .define('A', Items.SEAGRASS)
                .define('C', Items.SAND)
                .define('S', Items.TURTLE_EGG)
                .define('P', Items.PRISMARINE_SHARD)
                .define('B', Items.WATER_BUCKET)
                .pattern("CAC")
                .pattern("SXS")
                .pattern("BPB")
                .unlockedBy("has_scute", has(Items.TURTLE_SCUTE))
                .save(recipeOutput);


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Souls.LifePower.get())
                .define('A', Souls.BeeSoul.get())
                .define('B', Souls.BeetleSoul.get())
                .define('C', Souls.PumpkinSoul.get())
                .define('D', Souls.SpiderSoul.get())
                .define('E', Souls.CactusSoul.get())
                .define('F', Souls.TurtleSoul.get())
                .pattern("ABC")
                .pattern("DEF")
                .pattern("F  ")
                .unlockedBy("has_bee_soul", has(Souls.BeeSoul.get()))
                .save(recipeOutput);

























        super.buildRecipes(recipeOutput);
    }

}

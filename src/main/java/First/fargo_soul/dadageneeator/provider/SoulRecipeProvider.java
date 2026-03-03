package First.fargo_soul.dadageneeator.provider;

import First.fargo_soul.dadageneeator.builder.SoulRecipeBuilder;
import First.fargo_soul.register.BlockRegister;
import First.fargo_soul.register.ItemRegister;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SoulRecipeProvider extends RecipeProvider {

    protected RecipeOutput recipeOutput = null;

    public SoulRecipeProvider(PackOutput recipeOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(recipeOutput, registries);
    }

    protected ItemStack item(Item item, int count) {
        return new ItemStack(item, count);
    }

    protected ItemStack item(Item item) {
        return new ItemStack(item, 1);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        this.recipeOutput = recipeOutput;

        addRecipe(ItemRegister.BlazeSoulItem.toStack())        // 耀斑魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 2)// 下界合金升级锻造模板
                .requires(Items.BLAZE_ROD, 64)                 // 烈焰棒
                .requires(Items.TNT, 32)                       // TNT
                .requires(Items.WITHER_SKELETON_SKULL, 3)      // 凋灵骷髅头颅
                .build();

        addRecipe(ItemRegister.StardustSoulItem.toStack())     // 星尘魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.ECHO_SHARD, 8)                 // 回响碎片
                .requires(Items.END_CRYSTAL, 4)                // 末地水晶
                .requires(Items.SHULKER_SHELL, 16)             // 潜影壳
                .requires(Items.DRAGON_BREATH, 16)             // 龙息
                .build();

        addRecipe(ItemRegister.NebulaSoulItem.toStack())       // 星云魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.ENCHANTED_GOLDEN_APPLE, 1)     // 附魔金苹果
                .requires(Items.GHAST_TEAR, 32)                // 恶魂之泪
                .requires(Items.AMETHYST_SHARD, 64)            // 紫水晶碎片
                .requires(Items.DRAGON_BREATH, 16)             // 龙息
                .build();

        addRecipe(ItemRegister.VortexSoulItem.toStack())       // 星旋魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.MUSIC_DISC_5, 1)               // 唱片 5
                .requires(Items.RECOVERY_COMPASS, 1)           // 回归罗盘
                .requires(Items.ECHO_SHARD, 8)                 // 回响碎片
                .requires(Items.LODESTONE, 4)                  // 磁石
                .requires(Items.CONDUIT, 1)                    // 潮汐核心
                .build();

        addRecipe(ItemRegister.MeteorSoulItem.toStack())       // 流星魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.FIRE_CHARGE, 64)               // 火球
                .requires(Items.POINTED_DRIPSTONE, 64)         // 滴水石锥
                .requires(Items.RAW_GOLD, 64)                  // 金原矿
                .requires(Items.ANVIL, 4)                      // 铁砧
                .build();

        addRecipe(ItemRegister.WizardSoulItem.toStack())       // 巫师魔台
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.ENCHANTING_TABLE, 1)           // 附魔台
                .requires(Items.EXPERIENCE_BOTTLE, 64)         // 附魔之瓶
                .requires(Items.LAPIS_BLOCK, 16)               // 青金石块
                .requires(Items.RESPAWN_ANCHOR, 4)             // 重生锚
                .build();

        addRecipe(ItemRegister.CosmicPowerItem.toStack())      // 宇宙之力
                .requires(ItemRegister.BlazeSoulItem.get(), 1) // 耀斑魔石
                .requires(ItemRegister.StardustSoulItem.get(), 1)// 星尘魔石
                .requires(ItemRegister.NebulaSoulItem.get(), 1)// 星云魔石
                .requires(ItemRegister.VortexSoulItem.get(), 1)// 星旋魔石
                .requires(ItemRegister.MeteorSoulItem.get(), 1)// 流星魔石
                .requires(ItemRegister.WizardSoulItem.get(), 1)// 巫师魔石
                .build();

        addRecipe(ItemRegister.AncientShadowSoulItem.toStack()) // 远古暗影魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.ECHO_SHARD, 8)                 // 回响碎片
                .requires(Items.SCULK_SHRIEKER, 4)             // 幽匿尖啸体
                .requires(Items.WITHER_SKELETON_SKULL, 3)      // 凋灵骷髅头颅
                .requires(Items.CRYING_OBSIDIAN, 32)           // 哭泣的黑曜石
                .build();

        addRecipe(ItemRegister.CrystalAssassinSoulItem.toStack())// 水晶刺客魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.AMETHYST_CLUSTER, 32)          // 簇生紫水晶
                .requires(Items.RABBIT_FOOT, 16)               // 兔子脚
                .requires(Items.BLUE_ICE, 32)                  // 蓝冰
                .requires(Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 海岸盔甲纹饰
                .build();

        addRecipe(ItemRegister.DarkArtistSoulItem.toStack())   // 暗黑艺术家魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.ENCHANTING_TABLE, 1)           // 附魔台
                .requires(Items.SPECTRAL_ARROW, 64)            // 光灵箭
                .requires(Items.BLAZE_ROD, 32)                 // 烈焰棒
                .requires(Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 潮汐盔甲纹饰
                .build();

        addRecipe(ItemRegister.GloomySoulItem.toStack())       // 阴森魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.WITHER_ROSE, 16)               // 凋灵玫瑰
                .requires(Items.GHAST_TEAR, 16)                // 恶魂之泪
                .requires(Items.SKELETON_SKULL, 8)             // 骷髅头颅
                .requires(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 荒野盔甲纹饰
                .build();

        addRecipe(ItemRegister.NecromancerSoulItem.toStack())  // 死灵魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.TOTEM_OF_UNDYING, 1)           // 不死图腾
                .requires(Items.RESPAWN_ANCHOR, 4)             // 重生锚
                .requires(Items.WITHER_SKELETON_SKULL, 3)      // 凋灵骷髅头颅
                .requires(Items.BONE_BLOCK, 64)                // 骨块
                .build();

        addRecipe(ItemRegister.NinjaSoulItem.toStack())        // 忍者魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 宁静盔甲纹饰
                .requires(Items.SCULK_SENSOR, 8)               // 幽匿感应器
                .requires(Items.IRON_SWORD, 1)                 // 铁剑
                .requires(Items.COBWEB, 16)                    // 蜘蛛网
                .build();

        addRecipe(ItemRegister.PenetratingNinjaSoulItem.toStack())// 渗透忍者魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.NETHERITE_INGOT, 2)            // 下界合金锭
                .requires(Items.RECOVERY_COMPASS, 1)           // 回归罗盘
                .requires(Items.CHORUS_FRUIT, 64)              // 紫颂果
                .requires(Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 守望者盔甲纹饰
                .build();

        addRecipe(ItemRegister.DeathPowerItem.toStack())       // 死亡之力
                .requires(ItemRegister.AncientShadowSoulItem.get(), 1)// 远古暗影魔石
                .requires(ItemRegister.CrystalAssassinSoulItem.get(), 1)// 水晶刺客魔石
                .requires(ItemRegister.DarkArtistSoulItem.get(), 1)// 暗黑艺术家魔石
                .requires(ItemRegister.GloomySoulItem.get(), 1)// 阴森魔石
                .requires(ItemRegister.NecromancerSoulItem.get(), 1)// 死灵魔石
                .requires(ItemRegister.NinjaSoulItem.get(), 1) // 忍者魔石
                .requires(ItemRegister.PenetratingNinjaSoulItem.get(), 1)// 渗透忍者魔石
                .build();

        addRecipe(ItemRegister.AdamantiteSoulItem.toStack())   // 精金魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.IRON_BLOCK, 16)                // 铁块
                .requires(Items.REDSTONE_BLOCK, 8)            // 红石块
                .requires(Items.LODESTONE, 2)                  // 磁石
                .requires(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 猪灵盔甲纹饰
                .build();

        addRecipe(ItemRegister.CobaltSoulItem.toStack())       // 钴蓝魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.LAPIS_BLOCK, 16)               // 青金石块
                .requires(Items.BLUE_ICE, 64)                  // 蓝冰
                .requires(Items.TNT, 32)                       // TNT
                .requires(Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 海岸盔甲纹饰
                .build();

        addRecipe(ItemRegister.MithrilSoulItem.toStack())      // 秘银魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.DIAMOND_BLOCK, 8)              // 钻石块
                .requires(Items.EXPERIENCE_BOTTLE, 64)         // 附魔之瓶
                .requires(Items.PRISMARINE_BRICKS, 64)         // 海晶石砖
                .requires(Items.GRINDSTONE, 8)                 // 磨石
                .requires(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 宁静盔甲纹饰
                .build();

        addRecipe(ItemRegister.OrichalcumSoulItem.toStack())   // 山铜魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.PINK_PETALS, 64)               // 樱花瓣
                .requires(Items.SPORE_BLOSSOM, 16)             // 孢子花
                .requires(Items.AMETHYST_CLUSTER, 32)          // 簇生紫水晶
                .requires(Items.FLOWERING_AZALEA_LEAVES, 64)   // 开花杜鹃树叶
                .requires(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 荒野盔甲纹饰
                .build();

        addRecipe(ItemRegister.PalladiumSoulItem.toStack())    // 钯金魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.GOLDEN_APPLE, 16)              // 金苹果
                .requires(Items.COPPER_BLOCK, 32)              // 铜块
                .requires(Items.GLISTERING_MELON_SLICE, 64)    // 闪烁的西瓜片
                .requires(Items.PEARLESCENT_FROGLIGHT, 16)     // 珠光蛙光灯
                .requires(Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 寻路者盔甲纹饰
                .build();

        addRecipe(ItemRegister.TitaniumSoulItem.toStack())     // 钛金魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 2)// 下界合金升级锻造模板
                .requires(Items.SHIELD, 8)                     // 盾牌
                .requires(Items.RESPAWN_ANCHOR, 4)             // 重生锚
                .requires(Items.GILDED_BLACKSTONE, 64)         // 镶金黑石
                .requires(Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 监守者盔甲纹饰
                .build();

        addRecipe(ItemRegister.EarthPowerItem.toStack())       // 大地之力
                .requires(ItemRegister.AdamantiteSoulItem.get(), 1)// 精金魔石
                .requires(ItemRegister.CobaltSoulItem.get(), 1)// 钴蓝魔石
                .requires(ItemRegister.MithrilSoulItem.get(), 1)// 秘银魔石
                .requires(ItemRegister.OrichalcumSoulItem.get(), 1)// 山铜魔石
                .requires(ItemRegister.PalladiumSoulItem.get(), 1)// 钯金魔石
                .requires(ItemRegister.TitaniumSoulItem.get(), 1)// 钛金魔石
                .build();

        addRecipe(ItemRegister.EbonyWoodSoulItem.toStack())    // 乌木魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.DARK_OAK_LOG, 64)              // 深色橡木原木
                .requires(Items.ECHO_SHARD, 4)                 // 回响碎片
                .requires(Items.WITHER_ROSE, 8)                // 凋灵玫瑰
                .requires(Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 宿主盔甲纹饰
                .build();

        addRecipe(ItemRegister.PalmWoodSoulItem.toStack())     // 棕榈木魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.JUNGLE_LOG, 64)                // 丛林原木
                .requires(Items.SAND, 64)                      // 沙子
                .requires(Items.FIRE_CHARGE, 32)               // 火球
                .requires(Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 潮汐盔甲纹饰
                .build();

        addRecipe(ItemRegister.PearlWoodSoulItem.toStack())    // 珍珠木魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.BIRCH_LOG, 64)                 // 白桦原木
                .requires(Items.END_ROD, 16)                   // 末地烛
                .requires(Items.PEARLESCENT_FROGLIGHT, 16)     // 珠光蛙光灯
                .requires(Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 塑造者盔甲纹饰
                .build();

        addRecipe(ItemRegister.PineWoodSoulItem.toStack())     // 针叶木魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.SPRUCE_LOG, 64)                // 云杉原木
                .requires(Items.BLUE_ICE, 32)                  // 蓝冰
                .requires(Items.POWDER_SNOW_BUCKET, 4)         // 细雪桶
                .requires(Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 寻路者盔甲纹饰
                .build();

        addRecipe(ItemRegister.RoseWoodSoulItem.toStack())     // 红木魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.MANGROVE_LOG, 64)              // 红树原木
                .requires(Items.FISHING_ROD, 4)                // 钓鱼竿
                .requires(Items.POINTED_DRIPSTONE, 32)         // 滴水石锥
                .requires(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 荒野盔甲纹饰
                .build();

        addRecipe(ItemRegister.ShadowWoodSoulItem.toStack())   // 阴影木魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.CHERRY_LOG, 64)                // 樱花原木
                .requires(Items.SOUL_LANTERN, 16)              // 灵魂灯笼
                .requires(Items.WITHER_SKELETON_SKULL, 3)      // 凋灵骷髅头颅
                .requires(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 宁静盔甲纹饰
                .build();

        addRecipe(ItemRegister.WoodSoulItem.toStack())         // 木魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.OAK_LOG, 64)                   // 橡木原木
                .requires(Items.EMERALD_BLOCK, 4)             // 绿宝石块
                .requires(Items.TOTEM_OF_UNDYING, 1)           // 不死图腾
                .requires(Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 哨兵盔甲纹饰
                .build();

        addRecipe(ItemRegister.ForestPowerItem.toStack())      // 森林之力
                .requires(ItemRegister.EbonyWoodSoulItem.get(), 1)// 乌木魔石
                .requires(ItemRegister.PalmWoodSoulItem.get(), 1)// 棕榈木魔石
                .requires(ItemRegister.PearlWoodSoulItem.get(), 1)// 珍珠木魔石
                .requires(ItemRegister.PineWoodSoulItem.get(), 1)// 针叶木魔石
                .requires(ItemRegister.RoseWoodSoulItem.get(), 1)// 红木魔石
                .requires(ItemRegister.ShadowWoodSoulItem.get(), 1)// 阴影木魔石
                .requires(ItemRegister.WoodSoulItem.get(), 1)  // 木魔石
                .build();

        addRecipe(ItemRegister.BeeSoulItem.toStack())          // 蜜蜂魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.HONEY_BLOCK, 32)               // 蜂蜜块
                .requires(Items.HONEYCOMB, 48)                 // 蜜脾
                .requires(Items.FLOWERING_AZALEA, 32)          // 开花杜鹃
                .requires(Items.PINK_PETALS, 64)               // 樱花瓣
                .build();

        addRecipe(ItemRegister.BeetleSoulItem.toStack())       // 甲虫魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.ELYTRA, 1)                     // 鞘翅
                .requires(Items.NETHERITE_SCRAP, 8)            // 下界合金碎片
                .requires(Items.FERMENTED_SPIDER_EYE, 8)      // 发酵蛛眼
                .requires(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 猪灵盔甲纹饰
                .build();

        addRecipe(ItemRegister.PumpkinSoulItem.toStack())      // 南瓜魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.JACK_O_LANTERN, 64)            // 南瓜灯
                .requires(Items.PUMPKIN_SEEDS, 64)             // 南瓜子
                .requires(Items.BONE_MEAL, 64)                 // 骨粉
                .requires(Items.PLENTY_POTTERY_SHERD, 1)       // 丰饶陶片
                .build();

        addRecipe(ItemRegister.SpiderSoulItem.toStack())       // 蜘蛛魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.COBWEB, 64)                    // 蜘蛛网
                .requires(Items.SPIDER_EYE, 64)                // 蜘蛛眼
                .requires(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 宁静盔甲纹饰
                .requires(Items.RECOVERY_COMPASS, 1)           // 回归罗盘
                .build();

        addRecipe(ItemRegister.TurtleSoulItem.toStack())       // 乌龟魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.TURTLE_HELMET, 1)              // 龟壳
                .requires(Items.TURTLE_SCUTE, 16)              // 鳞甲
                .requires(Items.HEART_OF_THE_SEA, 1)           // 海洋之心
                .requires(Items.CACTUS, 64)                     // 仙人掌
                .requires(Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 监守者盔甲纹饰
                .build();

        addRecipe(ItemRegister.LifePowerItem.toStack())        // 生命之力
                .requires(ItemRegister.BeeSoulItem.get(), 1)   // 蜜蜂魔石
                .requires(ItemRegister.BeetleSoulItem.get(), 1)// 甲虫魔石
                .requires(ItemRegister.PumpkinSoulItem.get(), 1)// 南瓜魔石
                .requires(ItemRegister.SpiderSoulItem.get(), 1)// 蜘蛛魔石
                .requires(ItemRegister.TurtleSoulItem.get(), 1)// 乌龟魔石
                .build();

        addRecipe(ItemRegister.CrimsonSoulItem.toStack())      // 猩红魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.NETHER_WART, 64)               // 下界疣
                .requires(Items.REDSTONE_BLOCK, 8)            // 红石块
                .requires(Items.GHAST_TEAR, 16)                // 恶魂之泪
                .requires(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 猪灵盔甲纹饰
                .build();

        addRecipe(ItemRegister.FrostSoulItem.toStack())        // 冰霜魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.BLUE_ICE, 64)                  // 蓝冰
                .requires(Items.POWDER_SNOW_BUCKET, 4)         // 细雪桶
                .requires(Items.PACKED_ICE, 64)                // 浮冰
                .requires(Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 寻路者盔甲纹饰
                .build();

        addRecipe(ItemRegister.GreenSoulItem.toStack())        // 叶绿魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.EMERALD_BLOCK, 4)             // 绿宝石块
                .requires(Items.SPORE_BLOSSOM, 16)             // 孢子花
                .requires(Items.FLOWERING_AZALEA_LEAVES, 64)   // 开花杜鹃树叶
                .requires(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 荒野盔甲纹饰
                .build();

        addRecipe(ItemRegister.LavaSoulItem.toStack())         // 熔岩魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.MAGMA_BLOCK, 64)               // 岩浆块
                .requires(Items.BLAZE_ROD, 32)                 // 烈焰棒
                .requires(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 1)// 净无合金升级锻造模板
                .requires(Items.BURN_POTTERY_SHERD, 1)         // 焚烧陶片
                .build();

        addRecipe(ItemRegister.MushroomSoulItem.toStack())     // 蘑菇魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.MYCELIUM, 32)                  // 菌丝
                .requires(Items.MUSHROOM_STEW, 16)             // 蘑菇煲
                .requires(Items.RED_MUSHROOM, 64)              // 红蘑菇
                .requires(Items.BROWN_MUSHROOM, 64)              // 棕蘑菇
                .requires(Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 塑造者盔甲纹饰
                .build();

        addRecipe(ItemRegister.RainCloudSoulItem.toStack())    // 雨云魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.CONDUIT, 1)                    // 潮汐核心
                .requires(Items.LIGHTNING_ROD, 8)              // 避雷针
                .requires(Items.PRISMARINE_SHARD, 64)          // 海晶石碎片
                .requires(Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 潮汐盔甲纹饰
                .build();

        addRecipe(ItemRegister.NaturePowerItem.toStack())      // 自然之力
                .requires(ItemRegister.CrimsonSoulItem.get(), 1)// 猩红魔石
                .requires(ItemRegister.FrostSoulItem.get(), 1) // 冰霜魔石
                .requires(ItemRegister.GreenSoulItem.get(), 1) // 叶绿魔石
                .requires(ItemRegister.LavaSoulItem.get(), 1)  // 熔岩魔石
                .requires(ItemRegister.MushroomSoulItem.get(), 1)// 蘑菇魔石
                .requires(ItemRegister.RainCloudSoulItem.get(), 1)// 雨云魔石
                .build();

        addRecipe(ItemRegister.AncientHolySoulItem.toStack())  // 远古神圣魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.ENCHANTED_GOLDEN_APPLE, 1)     // 附魔金苹果
                .requires(Items.BEACON, 1)                     // 信标
                .requires(Items.GOLD_BLOCK, 16)                // 金块
                .requires(Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 塑造者盔甲纹饰
                .build();

        addRecipe(ItemRegister.ForbiddenSoulItem.toStack())    // 禁戒魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.BREEZE_ROD, 16)                // 旋风棒
                .requires(Items.OCHRE_FROGLIGHT, 16)           // 赭色蛙光灯
                .requires(Items.LAPIS_LAZULI, 64)              // 青金石
                .requires(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 荒丘盔甲纹饰
                .build();

        addRecipe(ItemRegister.GhostSoulItem.toStack())        // 幽魂魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.ECHO_SHARD, 8)                 // 回响碎片
                .requires(Items.TOTEM_OF_UNDYING, 4)           // 不死图腾
                .requires(Items.SCULK_SHRIEKER, 4)             // 幽匿尖啸体
                .requires(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 宁静盔甲纹饰
                .build();

        addRecipe(ItemRegister.HolySoulItem.toStack())         // 神圣魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.CONDUIT, 1)                    // 潮汐核心
                .requires(Items.PEARLESCENT_FROGLIGHT, 16)     // 珠光蛙光灯
                .requires(Items.GOLDEN_APPLE, 16)              // 金苹果
                .requires(Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 监守者盔甲纹饰
                .build();

        addRecipe(ItemRegister.TekeSoulItem.toStack())         // 提基魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.CARVED_PUMPKIN, 16)            // 南瓜灯
                .requires(Items.BAMBOO, 64)                    // 竹子
                .requires(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 荒野盔甲纹饰
                .requires(Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 哨兵盔甲纹饰
                .build();

        addRecipe(ItemRegister.SpiritPowerItem.toStack())      // 心灵之力
                .requires(ItemRegister.AncientHolySoulItem.get(), 1)// 远古神圣魔石
                .requires(ItemRegister.ForbiddenSoulItem.get(), 1)// 禁戒魔石
                .requires(ItemRegister.GhostSoulItem.get(), 1) // 幽魂魔石
                .requires(ItemRegister.HolySoulItem.get(), 1)  // 神圣魔石
                .requires(ItemRegister.TekeSoulItem.get(), 1)  // 提基魔石
                .build();

        addRecipe(ItemRegister.CopperSoulItem.toStack())       // 铜魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.COPPER_BLOCK, 64)              // 铜块
                .requires(Items.LIGHTNING_ROD, 8)              // 避雷针
                .requires(Items.COPPER_BULB, 16)               // 铜灯
                .requires(Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 提升者盔甲纹饰
                .build();

        addRecipe(ItemRegister.IronSoulItem.toStack())         // 铁魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.IRON_BLOCK, 32)                // 铁块
                .requires(Items.LODESTONE, 2)                  // 磁石
                .requires(Items.ANVIL, 4)                      // 铁砧
                .requires(Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 哨兵盔甲纹饰
                .build();

        addRecipe(ItemRegister.LeadSoulItem.toStack())         // 铅魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.CAULDRON, 8)                   // 炼药锅
                .requires(Items.POISONOUS_POTATO, 32)          // 毒马铃薯
                .requires(Items.POINTED_DRIPSTONE, 64)         // 滴水石锥
                .requires(Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 宿主盔甲纹饰
                .build();

        addRecipe(ItemRegister.ObsidianSoulItem.toStack())     // 黑曜石魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.OBSIDIAN, 64)                  // 黑曜石
                .requires(Items.CRYING_OBSIDIAN, 16)           // 哭泣的黑曜石
                .requires(Items.MAGMA_BLOCK, 32)               // 岩浆块
                .requires(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 猪灵盔甲纹饰
                .build();

        addRecipe(ItemRegister.SilverSoulItem.toStack())       // 银魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.SHIELD, 8)                     // 盾牌
                .requires(Items.AMETHYST_CLUSTER, 16)          // 簇生紫水晶
                .requires(Items.EXPERIENCE_BOTTLE, 32)         // 附魔之瓶
                .requires(Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 潮汐盔甲纹饰
                .build();

        addRecipe(ItemRegister.TinSoulItem.toStack())          // 锡魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.GOLD_INGOT, 64)                // 金锭
                .requires(Items.TARGET, 16)                    // 目标靶
                .requires(Items.GLOWSTONE, 64)                 // 荧石
                .requires(Items.BOLT_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 螺栓盔甲纹饰
                .build();

        addRecipe(ItemRegister.TungstenSoulItem.toStack())     // 钨魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.MACE, 1)                       // 重锤
                .requires(Items.TRIDENT, 1)                    // 三叉戟
                .requires(Items.GILDED_BLACKSTONE, 64)         // 镶金黑石
                .requires(Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 尖塔盔甲纹饰
                .build();

        addRecipe(ItemRegister.TerraPowerItem.toStack())       // 泰拉之力
                .requires(ItemRegister.CopperSoulItem.get(), 1)// 铜魔石
                .requires(ItemRegister.IronSoulItem.get(), 1)  // 铁魔石
                .requires(ItemRegister.LeadSoulItem.get(), 1)  // 铅魔石
                .requires(ItemRegister.ObsidianSoulItem.get(), 1)// 黑曜石魔石
                .requires(ItemRegister.SilverSoulItem.get(), 1)// 银魔石
                .requires(ItemRegister.TinSoulItem.get(), 1)   // 锡魔石
                .requires(ItemRegister.TungstenSoulItem.get(), 1)// 钨魔石
                .build();

        addRecipe(ItemRegister.GladiatorSoulItem.toStack())    // 角斗士魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.TRIDENT, 1)                    // 三叉戟
                .requires(Items.IRON_SWORD, 8)                 // 铁剑
                .requires(Items.CHISELED_POLISHED_BLACKSTONE, 64)// 錾刻磨制黑石
                .requires(Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 尖塔盔甲纹饰
                .build();

        addRecipe(ItemRegister.GoldSoulItem.toStack())         // 金魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.GOLD_BLOCK, 32)                // 金块
                .requires(Items.GILDED_BLACKSTONE, 64)         // 镶金黑石
                .requires(Items.BELL, 4)                       // 钟
                .requires(Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 塑造者盔甲纹饰
                .build();

        addRecipe(ItemRegister.PlatinumSoulItem.toStack())     // 铂金魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.NETHERITE_INGOT, 4)            // 下界合金锭
                .requires(Items.DIAMOND, 32)                   // 钻石
                .requires(Items.EMERALD, 64)                   // 绿宝石
                .requires(Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 哨兵盔甲纹饰
                .build();

        addRecipe(ItemRegister.RedRidingSoulItem.toStack())    // 红色骑术魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.CROSSBOW, 4)                   // 弩
                .requires(Items.RED_WOOL, 64)                  // 红色羊毛
                .requires(Items.LEATHER, 64)                   // 皮革
                .requires(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 荒野盔甲纹饰
                .build();

        addRecipe(ItemRegister.ValhallaKnightSoulItem.toStack())// 英灵殿骑士魔石
                .requires(ItemRegister.Soul.get(), 1)          // 空白模板
                .requires(Items.DIAMOND_HORSE_ARMOR, 1)        // 钻石马铠
                .requires(Items.SADDLE, 4)                     // 鞍
                .requires(Items.EXPERIENCE_BOTTLE, 64)         // 附魔之瓶
                .requires(Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE, 1)// 监守者盔甲纹饰
                .build();

        addRecipe(ItemRegister.WillPowerItem.toStack())        // 意志之力
                .requires(ItemRegister.GladiatorSoulItem.get(), 1)// 角斗士魔石
                .requires(ItemRegister.GoldSoulItem.get(), 1)  // 金魔石
                .requires(ItemRegister.PlatinumSoulItem.get(), 1)// 铂金魔石
                .requires(ItemRegister.RedRidingSoulItem.get(), 1)// 红色骑术魔石
                .requires(ItemRegister.ValhallaKnightSoulItem.get(), 1)// 英灵殿骑士魔石
                .build();

        addRecipe(ItemRegister.TerraSoulItem.toStack())          // 泰拉之魂
                .requires(ItemRegister.TerraPowerItem.get(), 1)  // 泰拉之力
                .requires(ItemRegister.EarthPowerItem.get(), 1)  // 大地之力
                .requires(ItemRegister.NaturePowerItem.get(), 1) // 自然之力
                .requires(ItemRegister.LifePowerItem.get(), 1)   // 生命之力
                .requires(ItemRegister.SpiritPowerItem.get(), 1) // 心灵之力
                .requires(ItemRegister.WillPowerItem.get(), 1)   // 意志之力
                .requires(ItemRegister.ForestPowerItem.get(), 1) // 森林之力
                .requires(ItemRegister.DeathPowerItem.get(), 1)  // 死亡之力
                .requires(ItemRegister.CosmicPowerItem.get(), 1) // 宇宙之力
                .build();

        addRecipe(ItemRegister.Soul.toStack())
                .requires(Items.RABBIT_FOOT, 1)         // 兔子脚
                .requires(Items.TURTLE_SCUTE, 1)        // 龟壳鳞甲
                .requires(Items.DISC_FRAGMENT_5, 1)     // 唱片残片
                .requires(Items.ARMADILLO_SCUTE, 1)     // 犰狳鳞甲
                .requires(Items.AMETHYST_SHARD, 1)      // 紫水晶碎片
                .requires(Items.PITCHER_PLANT, 1)       // 猪笼草
                .requires(Items.MANGROVE_PROPAGULE, 1)  // 红树胎生苗
                .build();

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegister.CosmicCrucible.get())
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .define('A', Items.NETHERITE_INGOT)
                .define('B', Blocks.DRAGON_EGG)
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(Blocks.DRAGON_EGG.asItem()).getPath(), has(Blocks.DRAGON_EGG))
                .save(recipeOutput, BlockRegister.CosmicCrucible.getId());

    }

    public SoulRecipeBuilder addRecipe(ItemStack output) {
        SoulRecipeBuilder builder = SoulRecipeBuilder.soulRecipe(output);
        builder.setOutput(recipeOutput);
        return builder;
    }

    public void addSoulRecipe(ItemStack output, ItemStack... inputs) {
        DefaultedRegistry<Item> registry = BuiltInRegistries.ITEM;
        List<ItemStack> inputList = new ArrayList<>(List.of(inputs));
        inputList.addFirst(ItemRegister.Soul.toStack());
        SoulRecipeBuilder builder = SoulRecipeBuilder.soulRecipe(output);
        inputList.forEach(itemStack -> builder.addIngredient(itemStack.getItem(), itemStack.getCount()));
        builder.save(recipeOutput, registry.getKey(output.getItem()));
    }



}

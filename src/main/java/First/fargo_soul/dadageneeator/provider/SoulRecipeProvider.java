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

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        this.recipeOutput = recipeOutput;
        addSoulRecipe(// 配方 : 耀斑魔石 (Blaze Soul)
                item(ItemRegister.BlazeSoulItem.get(), 1), // 耀斑魔石
                item(Items.NETHER_STAR, 12),    // 下界之星
                item(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 12),// 下界合金升级锻造模板
                item(Items.BLAZE_ROD, 128),                // 烈焰棒
                item(Items.BLAZE_POWDER, 768),             // 烈焰粉
                item(Items.FIRE_CHARGE, 768),              // 火球
                item(Items.MAGMA_CREAM, 768),              // 岩浆膏
                item(Items.MAGMA_BLOCK, 128),              // 岩浆块
                item(Items.LAVA_BUCKET, 384),              // 岩浆桶
                item(Items.GLOWSTONE, 768),                // 荧石
                item(Items.SHROOMLIGHT, 768),              // 菌光体
                item(Items.TNT, 384),                      // TNT
                item(Items.WITHER_SKELETON_SKULL, 48),     // 凋灵骷髅头颅
                item(Items.NETHERRACK, 768),               // 下界岩
                item(Items.BASALT, 384),                   // 玄武岩
                item(Items.SOUL_SAND, 384),                // 灵魂沙
                item(Items.SOUL_SOIL, 384),                // 灵魂土
                item(Items.NETHER_QUARTZ_ORE, 384),        // 下界石英矿石
                item(Items.NETHER_GOLD_ORE, 384),          // 下界金矿石
                item(Items.NETHER_WART, 192),              // 下界疣
                item(Items.RED_NETHER_BRICKS, 192),        // 红色下界砖
                item(Items.TORCH, 768),                    // 火把
                item(Items.CRYING_OBSIDIAN, 64)            // 哭泣的黑曜石
        );

        addSoulRecipe(// 配方 : 星尘魔石 (Stardust Soul)
                item(ItemRegister.StardustSoulItem.get(), 1),// 星尘魔石
                item(Items.NETHER_STAR, 12),    // 下界之星
                item(Items.CLOCK, 128),                    // 时钟
                item(Items.END_CRYSTAL, 64),               // 末地水晶
                item(Items.CHORUS_FLOWER, 192),            // 紫颂花
                item(Items.DRAGON_BREATH, 64),             // 龙息
                item(Items.ECHO_SHARD, 96),                // 回响碎片
                item(Items.AMETHYST_CLUSTER, 128),         // 簇生紫水晶
                item(Items.END_STONE, 768),                // 末地石
                item(Items.END_STONE_BRICKS, 384),         // 末地石砖
                item(Items.OBSIDIAN, 768),                 // 黑曜石
                item(Items.CANDLE, 768),                   // 蜡烛
                item(Items.LAPIS_BLOCK, 128),              // 青金石块
                item(Items.PHANTOM_MEMBRANE, 192),         // 幻翼膜
                item(Items.BLUE_ICE, 384),                 // 蓝冰
                item(Items.SNOW_BLOCK, 768),               // 雪块
                item(Items.ENDER_EYE, 128),                // 末影之眼
                item(Items.RECOVERY_COMPASS, 12),          // 回归罗盘
                item(Items.SHULKER_SHELL, 64),             // 潜影壳
                item(Items.EXPERIENCE_BOTTLE, 768),        // 附魔之瓶
                item(Items.GILDED_BLACKSTONE, 128),        // 镶金黑石
                item(Items.PURPUR_BLOCK, 384)              // 紫珀块
        );

        addSoulRecipe(// 配方 : 星云魔石 (Nebula Soul)
                item(ItemRegister.NebulaSoulItem.get(), 1),// 星云魔石
                item(Items.NETHER_STAR, 12),    // 下界之星
                item(Items.GHAST_TEAR, 128),               // 恶魂之泪
                item(Items.AMETHYST_SHARD, 768),           // 紫水晶碎片
                item(Items.CHORUS_FRUIT, 384),             // 紫颂果
                item(Items.ENDER_EYE, 128),                // 末影之眼
                item(Items.DRAGON_BREATH, 64),             // 龙息
                item(Items.GLOW_BERRIES, 768),             // 发光浆果
                item(Items.GOLDEN_APPLE, 64),              // 金苹果
                item(Items.ENCHANTED_GOLDEN_APPLE, 8),     // 附魔金苹果
                item(Items.CRYING_OBSIDIAN, 128),          // 哭泣的黑曜石
                item(Items.LODESTONE, 16),                 // 磁石
                item(Items.PRISMARINE_SHARD, 768),         // 海晶石碎片
                item(Items.SEA_LANTERN, 384),              // 海晶灯
                item(Items.SCULK_SENSOR, 64),              // 幽匿感应器
                item(Items.CARVED_PUMPKIN, 384),           // 雕刻南瓜
                item(Items.TOTEM_OF_UNDYING, 12),          // 不死图腾
                item(Items.GLOW_INK_SAC, 768),             // 发光墨囊
                item(Items.BEACON, 4),                     // 信标
                item(Items.ENDER_PEARL, 768),              // 末影珍珠
                item(Items.POPPED_CHORUS_FRUIT, 768),      // 爆裂紫颂果
                item(Items.HEART_OF_THE_SEA, 12)           // 海洋之心
        );

        addSoulRecipe(// 配方 : 星旋魔石 (Vortex Soul)
                item(ItemRegister.VortexSoulItem.get(), 1),// 星旋魔石
                item(Items.NETHER_STAR, 12),    // 下界之星
                item(Items.ENDER_PEARL, 768),              // 末影珍珠
                item(Items.CHORUS_FRUIT, 768),             // 紫颂果
                item(Items.ENDER_EYE, 128),                // 末影之眼
                item(Items.LODESTONE, 64),                 // 磁石
                item(Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 守望者盔甲纹饰
                item(Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 尖塔盔甲纹饰
                item(Items.RECOVERY_COMPASS, 12),          // 回归罗盘
                item(Items.ECHO_SHARD, 128),               // 回响碎片
                item(Items.SCULK_SHRIEKER, 64),            // 幽匿尖啸体
                item(Items.MUSIC_DISC_5, 1),               // 唱片 5
                item(Items.GOAT_HORN, 8),                  // 山羊角
                item(Items.DISC_FRAGMENT_5, 64),           // 唱片残片 5
                item(Items.AMETHYST_CLUSTER, 256),         // 簇生紫水晶
                item(Items.PHANTOM_MEMBRANE, 384),         // 幻翼膜
                item(Items.OBSIDIAN, 768),                 // 黑曜石
                item(Items.CRYING_OBSIDIAN, 384),          // 哭泣的黑曜石
                item(Items.DRAGON_BREATH, 128),            // 龙息
                item(Items.END_ROD, 384),                  // 末地烛
                item(Items.SCRAPE_POTTERY_SHERD, 16),      // 焚烧陶片
                item(Items.GILDED_BLACKSTONE, 128),        // 镶金黑石
                item(Items.CONDUIT, 4)                     // 潮汐核心试炼
        );

        addSoulRecipe(// 配方 : 流星魔石 (Meteor Soul)
                item(ItemRegister.MeteorSoulItem.get(), 1),// 流星魔石
                item(Items.NETHER_STAR, 12),    // 下界之星
                item(Items.FIRE_CHARGE, 768),              // 火球
                item(Items.POINTED_DRIPSTONE, 384),        // 滴水石锥
                item(Items.RAW_GOLD, 384),                 // 金原矿
                item(Items.RAW_IRON, 384),                 // 铁原矿
                item(Items.GILDED_BLACKSTONE, 128),        // 镶金黑石
                item(Items.CRYING_OBSIDIAN, 128),          // 哭泣的黑曜石
                item(Items.GLOWSTONE, 768),                // 荧石
                item(Items.MAGMA_BLOCK, 384),              // 岩浆块
                item(Items.FEATHER, 768),                  // 羽毛
                item(Items.PHANTOM_MEMBRANE, 128),         // 幻翼膜
                item(Items.AMETHYST_CLUSTER, 128),         // 簇生紫水晶
                item(Items.BLAST_FURNACE, 32),             // 高炉
                item(Items.HEART_POTTERY_SHERD, 16),       // 心形陶片
                item(Items.DANGER_POTTERY_SHERD, 16),      // 危险陶片
                item(Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 寻路者盔甲纹饰
                item(Items.BURN_POTTERY_SHERD, 16),        // 焚烧陶片
                item(Items.BLAZE_ROD, 128),                // 烈焰棒
                item(Items.OBSIDIAN, 768),                 // 黑曜石
                item(Items.IRON_BLOCK, 64),                // 铁块
                item(Items.GOLD_BLOCK, 64),                // 金块
                item(Items.ANVIL, 16)                      // 铁砧
        );

        addSoulRecipe(// 配方 : 巫师魔石 (Wizard Soul)
                item(ItemRegister.WizardSoulItem.get(), 1),// 巫师魔石
                item(Items.NETHER_STAR, 12),    // 下界之星
                item(Items.ENCHANTING_TABLE, 32),          // 附魔台
                item(Items.BOOKSHELF, 384),                // 书架
                item(Items.AMETHYST_CLUSTER, 256),         // 簇生紫水晶
                item(Items.ECHO_SHARD, 128),               // 回响碎片
                item(Items.LODESTONE, 64),                 // 磁石
                item(Items.GLOWSTONE, 768),                // 荧石
                item(Items.PEARLESCENT_FROGLIGHT, 128),    // 珠光蛙光灯
                item(Items.OCHRE_FROGLIGHT, 128),          // 赭色蛙光灯
                item(Items.VERDANT_FROGLIGHT, 128),        // 青翠蛙光灯
                item(Items.BREWER_POTTERY_SHERD, 16),      // 制酒者陶片
                item(Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 监守者盔甲纹饰
                item(Items.RESPAWN_ANCHOR, 32),            // 重生锚
                item(Items.LAPIS_BLOCK, 128),              // 青金石块
                item(Items.DRAGON_BREATH, 64),             // 龙息
                item(Items.TOTEM_OF_UNDYING, 12),          // 不死图腾
                item(Items.EXPERIENCE_BOTTLE, 768),        // 附魔之瓶
                item(Items.CONDUIT, 8),                    // 潮汐核心
                item(Items.CANDLE, 768),                   // 蜡烛
                item(Items.GOAT_HORN, 8),                  // 山羊角
                item(Items.CHISELED_POLISHED_BLACKSTONE, 384), // 錾刻磨制黑石
                item(Items.BEACON, 4)                      // 信标
        );

        addSoulRecipe(// 配方 : 宇宙之力 (Cosmic Power)
                item(ItemRegister.CosmicPowerItem.get(), 1), // 宇宙之力
                item(ItemRegister.BlazeSoulItem.get(), 1),   // 耀斑魔石
                item(ItemRegister.StardustSoulItem.get(), 1),// 星尘魔石
                item(ItemRegister.NebulaSoulItem.get(), 1),  // 星云魔石
                item(ItemRegister.VortexSoulItem.get(), 1),  // 星旋魔石
                item(ItemRegister.MeteorSoulItem.get(), 1),  // 流星魔石
                item(ItemRegister.WizardSoulItem.get(), 1),  // 巫师魔石
                item(Items.NETHER_STAR, 64),               // 下界之星
                item(Items.BEACON, 16),                    // 信标
                item(Items.NETHERITE_BLOCK, 32),           // 净无合金块
                item(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, 4), // 宁静盔甲纹饰
                item(Items.RECOVERY_COMPASS, 64),          // 回归罗盘
                item(Items.ECHO_SHARD, 384),               // 回响碎片
                item(Items.TOTEM_OF_UNDYING, 32),          // 不死图腾
                item(Items.RESPAWN_ANCHOR, 64),            // 重生锚
                item(Items.END_CRYSTAL, 128),              // 末地水晶
                item(Items.MUSIC_DISC_5, 1),               // 唱片 5
                item(Items.OMINOUS_TRIAL_KEY, 12),         // 不详试炼钥匙
                item(Items.BREEZE_ROD, 384),               // 旋风棒
                item(Items.HEART_OF_THE_SEA, 32),          // 海洋之心
                item(Items.CONDUIT, 16),                   // 潮汐核心
                item(Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE, 12) // 监守者盔甲纹饰
        );

        addSoulRecipe(// 配方 : 远古暗影魔石 (Ancient Shadow Soul)
                item(ItemRegister.AncientShadowSoulItem.get(), 1), // 远古暗影魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.DRAGON_BREATH, 64),             // 龙息
                item(Items.OBSIDIAN, 768),                 // 黑曜石
                item(Items.CRYING_OBSIDIAN, 128),          // 哭泣的黑曜石
                item(Items.BLACK_CANDLE, 768),             // 黑色蜡烛
                item(Items.WITHER_SKELETON_SKULL, 12),     // 凋灵骷髅头颅
                item(Items.SCULK_SHRIEKER, 64),            // 幽匿尖啸体
                item(Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 哨兵盔甲纹饰
                item(Items.BLACKSTONE_SLAB, 384),          // 黑石台阶
                item(Items.ECHO_SHARD, 96),                // 回响碎片
                item(Items.INK_SAC, 768)                   // 墨囊
        );

        addSoulRecipe(// 配方 : 水晶刺客魔石 (Crystal Assassin Soul)
                item(ItemRegister.CrystalAssassinSoulItem.get(), 1), // 水晶刺客魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.AMETHYST_CLUSTER, 256),         // 簇生紫水晶
                item(Items.LARGE_AMETHYST_BUD, 128),       // 大紫水晶芽
                item(Items.SUGAR, 768),                    // 糖
                item(Items.RABBIT_FOOT, 64),               // 兔子脚
                item(Items.POINTED_DRIPSTONE, 384),        // 滴水石锥
                item(Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 海岸盔甲纹饰
                item(Items.BLUE_ICE, 128),                 // 蓝冰
                item(Items.QUARTZ_BLOCK, 384)              // 石英块
        );

        addSoulRecipe(// 配方 : 暗黑艺术家魔石 (Dark Artist Soul)
                item(ItemRegister.DarkArtistSoulItem.get(), 1), // 暗黑艺术家魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.FIRE_CHARGE, 768),              // 火球
                item(Items.ARROW, 768),                    // 箭
                item(Items.SPECTRAL_ARROW, 384),           // 光灵箭
                item(Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 潮汐盔甲纹饰
                item(Items.BREWER_POTTERY_SHERD, 16),      // 制酒者陶片
                item(Items.BLAZE_ROD, 128),                // 烈焰棒
                item(Items.MAGENTA_CANDLE, 384),           // 品红色蜡烛
                item(Items.ENCHANTING_TABLE, 16),          // 附魔台
                item(Items.GLOWSTONE, 768)                 // 荧石
        );

        addSoulRecipe(// 配方 : 阴森魔石 (Gloomy Soul)
                item(ItemRegister.GloomySoulItem.get(), 1), // 阴森魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.SOUL_SOIL, 768),                // 灵魂土
                item(Items.SOUL_SAND, 768),                // 灵魂沙
                item(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 荒野盔甲纹饰
                item(Items.MOURNER_POTTERY_SHERD, 16),     // 哀悼者陶片
                item(Items.GHAST_TEAR, 64),                // 恶魂之泪
                item(Items.WITHER_ROSE, 64),               // 凋灵玫瑰
                item(Items.JACK_O_LANTERN, 384),           // 南瓜灯
                item(Items.SKELETON_SKULL, 48),            // 骷髅头颅
                item(Items.NETHER_BRICKS, 768)             // 下界砖块
        );

        addSoulRecipe(// 配方 : 死灵魔石 (Necromancer Soul)
                item(ItemRegister.NecromancerSoulItem.get(), 1), // 死灵魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.BONE, 768),                     // 骨头
                item(Items.BONE_BLOCK, 128),               // 骨块
                item(Items.WITHER_SKELETON_SKULL, 64),     // 凋灵骷髅头颅
                item(Items.SKELETON_SKULL, 64),            // 骷髅头颅
                item(Items.ZOMBIE_HEAD, 64),               // 僵尸头颅
                item(Items.SKULL_POTTERY_SHERD, 32),       // 头颅陶片
                item(Items.RIB_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 肋骨盔甲纹饰
                item(Items.RESPAWN_ANCHOR, 32),            // 重生锚
                item(Items.TOTEM_OF_UNDYING, 12)           // 不死图腾
        );

        addSoulRecipe(// 配方 : 忍者魔石 (Ninja Soul)
                item(ItemRegister.NinjaSoulItem.get(), 1), // 忍者魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.GRAY_CANDLE, 768),              // 灰色蜡烛
                item(Items.FEATHER, 768),                  // 羽毛
                item(Items.LEATHER_BOOTS, 32),             // 皮靴子
                item(Items.SCULK_SENSOR, 64),              // 幽匿感应器
                item(Items.BLADE_POTTERY_SHERD, 16),       // 刀刃陶片
                item(Items.IRON_SWORD, 64),                // 铁剑
                item(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, 4), // 宁静盔甲纹饰
                item(Items.COBWEB, 128),                   // 蜘蛛网
                item(Items.OBSIDIAN, 384)                  // 黑曜石
        );

        addSoulRecipe(// 配方 : 渗透忍者魔石 (Penetrating Ninja Soul)
                item(ItemRegister.PenetratingNinjaSoulItem.get(), 1), // 渗透忍者魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.ECHO_SHARD, 128),               // 回响碎片
                item(Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 守望者盔甲纹饰
                item(Items.RECOVERY_COMPASS, 16),          // 回归罗盘
                item(Items.CHORUS_FRUIT, 768),             // 紫颂果
                item(Items.END_ROD, 384),                  // 末地烛
                item(Items.WITHER_ROSE, 32),               // 凋灵玫瑰
                item(Items.VEX_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 威克斯盔甲纹饰
                item(Items.NETHER_WART, 384),              // 下界疣
                item(Items.NETHERITE_INGOT, 12)            // 下界合金锭
        );

        addSoulRecipe(// 配方 : 死亡之力 (Death Power)
                item(ItemRegister.DeathPowerItem.get(), 1), // 死亡之力
                item(ItemRegister.AncientShadowSoulItem.get(), 1), // 远古暗影魔石
                item(ItemRegister.CrystalAssassinSoulItem.get(), 1), // 水晶刺客魔石
                item(ItemRegister.DarkArtistSoulItem.get(), 1), // 暗黑艺术家魔石
                item(ItemRegister.GloomySoulItem.get(), 1), // 阴森魔石
                item(ItemRegister.NecromancerSoulItem.get(), 1), // 死灵魔石
                item(ItemRegister.NinjaSoulItem.get(), 1), // 忍者魔石
                item(ItemRegister.PenetratingNinjaSoulItem.get(), 1), // 渗透忍者魔石
                item(Items.NETHER_STAR, 64),               // 下界之星
                item(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 宁静盔甲纹饰
                item(Items.ECHO_SHARD, 384),               // 回响碎片
                item(Items.NETHERITE_BLOCK, 16),           // 下界合金块
                item(Items.DRAGON_BREATH, 128),            // 龙息
                item(Items.SOUL_LANTERN, 384),             // 灵魂灯笼
                item(Items.WITHER_ROSE, 128),              // 凋灵玫瑰
                item(Items.HEARTBREAK_POTTERY_SHERD, 16),  // 心碎陶片
                item(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 猪灵盔甲纹饰
                item(Items.OMINOUS_BOTTLE, 12),            // 不详之瓶
                item(Items.TRIAL_KEY, 12),                 // 试炼钥匙
                item(Items.GILDED_BLACKSTONE, 384),        // 镶金黑石
                item(Items.CRACKED_POLISHED_BLACKSTONE_BRICKS, 384), // 裂纹磨制黑石砖
                item(Items.MUSIC_DISC_5, 1)                // 唱片 5
        );
        addSoulRecipe(// 配方 : 精金魔石 (Adamantite Soul)
                item(ItemRegister.AdamantiteSoulItem.get(), 1), // 精金魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.REDSTONE_BLOCK, 128),           // 红石块
                item(Items.RECOVERY_COMPASS, 12),          // 回归罗盘
                item(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 猪灵盔甲纹饰
                item(Items.RAW_IRON, 768),                 // 铁原矿
                item(Items.IRON_BLOCK, 64),                // 铁块
                item(Items.ANVIL, 16),                     // 铁砧
                item(Items.TARGET, 64),                    // 目标靶
                item(Items.LODESTONE, 16),                 // 磁石
                item(Items.CRIMSON_NYLIUM, 384)            // 绯红尼里
        );

        addSoulRecipe(// 配方 : 钴蓝魔石 (Cobalt Soul)
                item(ItemRegister.CobaltSoulItem.get(), 1), // 钴蓝魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.LAPIS_BLOCK, 128),              // 青金石块
                item(Items.BLUE_CANDLE, 768),              // 蓝色蜡烛
                item(Items.TNT, 384),                      // TNT
                item(Items.FIRE_CHARGE, 384),              // 火球
                item(Items.LAVA_BUCKET, 128),              // 岩浆桶
                item(Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 海岸盔甲纹饰
                item(Items.OXIDIZED_COPPER_BULB, 64),      // 氧化铜灯
                item(Items.WARPED_NYLIUM, 384),            // 诡异尼里
                item(Items.BLUE_ICE, 256)                  // 蓝冰
        );

        addSoulRecipe(// 配方 : 秘银魔石 (Mithril Soul)
                item(ItemRegister.MithrilSoulItem.get(), 1), // 秘银魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.PRISMARINE_BRICKS, 384),        // 海晶石砖
                item(Items.DIAMOND_BLOCK, 32),             // 钻石块
                item(Items.GRINDSTONE, 32),                // 磨石
                item(Items.EXPERIENCE_BOTTLE, 768),        // 附魔之瓶
                item(Items.BOOKSHELF, 128),                // 书架
                item(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, 4), // 宁静盔甲纹饰
                item(Items.RAW_GOLD, 768),                 // 金原矿
                item(Items.LIGHT_BLUE_CANDLE, 768),        // 淡蓝色蜡烛
                item(Items.FLOW_POTTERY_SHERD, 16)         // 流向陶片
        );

        addSoulRecipe(// 配方 : 山铜魔石 (Orichalcum Soul)
                item(ItemRegister.OrichalcumSoulItem.get(), 1), // 山铜魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.PINK_PETALS, 768),              // 樱花瓣
                item(Items.SPORE_BLOSSOM, 64),             // 孢子花
                item(Items.AMETHYST_CLUSTER, 256),         // 簇生紫水晶
                item(Items.FLOWERING_AZALEA_LEAVES, 384),  // 开花杜鹃树叶
                item(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 荒野盔甲纹饰
                item(Items.FERMENTED_SPIDER_EYE, 128),     // 发酵蛛眼
                item(Items.POISONOUS_POTATO, 64),          // 毒马铃薯
                item(Items.PINK_CANDLE, 768),              // 粉红色蜡烛
                item(Items.SHEAF_POTTERY_SHERD, 16)        // 捆束陶片
        );

        addSoulRecipe(// 配方 : 钯金魔石 (Palladium Soul)
                item(ItemRegister.PalladiumSoulItem.get(), 1), // 钯金魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.GOLDEN_APPLE, 128),             // 金苹果
                item(Items.GLISTERING_MELON_SLICE, 768),   // 闪烁的西瓜片
                item(Items.GHAST_TEAR, 64),                // 恶魂之泪
                item(Items.ORANGE_CANDLE, 768),            // 橙色蜡烛
                item(Items.COPPER_BLOCK, 128),             // 铜块
                item(Items.RAW_COPPER, 768),               // 铜原矿
                item(Items.HEART_POTTERY_SHERD, 32),       // 心形陶片
                item(Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 寻路者盔甲纹饰
                item(Items.PEARLESCENT_FROGLIGHT, 64)      // 珠光蛙光灯
        );

        addSoulRecipe(// 配方 : 钛金魔石 (Titanium Soul)
                item(ItemRegister.TitaniumSoulItem.get(), 1), // 钛金魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 12), // 净无合金升级锻造模板
                item(Items.SHIELD, 32),                    // 盾牌
                item(Items.GILDED_BLACKSTONE, 384),        // 镶金黑石
                item(Items.POLISHED_BLACKSTONE_BRICKS, 768), // 磨制黑石砖
                item(Items.RESPAWN_ANCHOR, 32),            // 重生锚
                item(Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 监守者盔甲纹饰
                item(Items.SHELTER_POTTERY_SHERD, 16),   // 钙化陶片 (注：原版为MOURNER或根据环境选择)
                item(Items.GRAY_CANDLE, 768),              // 灰色蜡烛
                item(Items.SCRAPE_POTTERY_SHERD, 16)       // 刮削陶片
        );

        addSoulRecipe(// 配方 : 大地之力 (Earth Power)
                item(ItemRegister.EarthPowerItem.get(), 1), // 大地之力
                item(ItemRegister.AdamantiteSoulItem.get(), 1), // 精金魔石
                item(ItemRegister.CobaltSoulItem.get(), 1), // 钴蓝魔石
                item(ItemRegister.MithrilSoulItem.get(), 1), // 秘银魔石
                item(ItemRegister.OrichalcumSoulItem.get(), 1), // 山铜魔石
                item(ItemRegister.PalladiumSoulItem.get(), 1), // 钯金魔石
                item(ItemRegister.TitaniumSoulItem.get(), 1), // 钛金魔石
                item(Items.NETHER_STAR, 64),               // 下界之星
                item(Items.NETHERITE_BLOCK, 16),           // 净无合金块
                item(Items.BEACON, 8),                     // 信标
                item(Items.HEART_OF_THE_SEA, 16),          // 海洋之心
                item(Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 塑造者盔甲纹饰
                item(Items.DRIPSTONE_BLOCK, 768),          // 滴水石块
                item(Items.POINTED_DRIPSTONE, 768),        // 滴水石锥
                item(Items.CALCITE, 768),                  // 方解石
                item(Items.TUFF, 768),                     // 凝灰岩
                item(Items.DEEPSLATE, 768),                // 深层石
                item(Items.RECOVERY_COMPASS, 32),          // 回归罗盘
                item(Items.TOTEM_OF_UNDYING, 16),          // 不死图腾
                item(Items.ENCHANTED_GOLDEN_APPLE, 8),     // 附魔金苹果
                item(Items.MUSIC_DISC_OTHERSIDE, 1)        // 唱片 Otherside
        );

        addSoulRecipe(// 配方 : 乌木魔石 (Ebony Wood Soul)
                item(ItemRegister.EbonyWoodSoulItem.get(), 1), // 乌木魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.DARK_OAK_LOG, 768),             // 深色橡木原木
                item(Items.SCULK, 384),                    // 幽匿块
                item(Items.ECHO_SHARD, 64),                // 回响碎片
                item(Items.WITHER_ROSE, 32),               // 凋灵玫瑰
                item(Items.FERMENTED_SPIDER_EYE, 128),     // 发酵蛛眼
                item(Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 宿主盔甲纹饰
                item(Items.BLACK_CANDLE, 384),             // 黑色蜡烛
                item(Items.MOURNER_POTTERY_SHERD, 16),     // 哀悼者陶片
                item(Items.CRYING_OBSIDIAN, 128)           // 哭泣的黑曜石
        );

        addSoulRecipe(// 配方 : 棕榈木魔石 (Palm Wood Soul)
                item(ItemRegister.PalmWoodSoulItem.get(), 1), // 棕榈木魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.JUNGLE_LOG, 768),               // 丛林原木
                item(Items.SAND, 768),                     // 沙子
                item(Items.MAGMA_BLOCK, 384),              // 岩浆块
                item(Items.FIRE_CHARGE, 384),              // 火球
                item(Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 海岸盔甲纹饰
                item(Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 潮汐盔甲纹饰
                item(Items.BURN_POTTERY_SHERD, 16),        // 焚烧陶片
                item(Items.SHELTER_POTTERY_SHERD, 16),     // 匆忙陶片
                item(Items.ORANGE_CANDLE, 384)             // 橙色蜡烛
        );

        addSoulRecipe(// 配方 : 珍珠木魔石 (Pearl Wood Soul)
                item(ItemRegister.PearlWoodSoulItem.get(), 1), // 珍珠木魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.BIRCH_LOG, 768),                // 白桦原木
                item(Items.PEARLESCENT_FROGLIGHT, 128),    // 珠光蛙光灯
                item(Items.END_ROD, 256),                  // 末地烛
                item(Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 塑造者盔甲纹饰
                item(Items.AMETHYST_CLUSTER, 128),         // 簇生紫水晶
                item(Items.WHITE_CANDLE, 768),             // 白色蜡烛
                item(Items.PRIZE_POTTERY_SHERD, 16),       // 奖励陶片
                item(Items.QUARTZ_BLOCK, 384),             // 石英块
                item(Items.EXPERIENCE_BOTTLE, 384)         // 附魔之瓶
        );

        addSoulRecipe(// 配方 : 针叶木魔石 (Pine Wood Soul)
                item(ItemRegister.PineWoodSoulItem.get(), 1), // 针叶木魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.SPRUCE_LOG, 768),               // 云杉原木
                item(Items.SNOW_BLOCK, 768),               // 雪块
                item(Items.BLUE_ICE, 256),                 // 蓝冰
                item(Items.SNOWBALL, 768),                 // 雪球
                item(Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 寻路者盔甲纹饰
                item(Items.GUSTER_POTTERY_SHERD, 16),      // 狂风陶片
                item(Items.LIGHT_BLUE_CANDLE, 384),        // 淡蓝色蜡烛
                item(Items.POWDER_SNOW_BUCKET, 64),        // 细雪桶
                item(Items.PACKED_ICE, 384)                // 浮冰
        );

        addSoulRecipe(// 配方 : 红木魔石 (Rose Wood Soul)
                item(ItemRegister.RoseWoodSoulItem.get(), 1), // 红木魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.ACACIA_LOG, 768),               // 金合欢原木
                item(Items.MANGROVE_LOG, 768),             // 红树原木
                item(Items.FISHING_ROD, 32),               // 钓鱼竿
                item(Items.LEAD, 64),                      // 栓绳
                item(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 荒野盔甲纹饰
                item(Items.ANGLER_POTTERY_SHERD, 16),      // 垂钓者陶片
                item(Items.RED_CANDLE, 384),               // 红色蜡烛
                item(Items.COPPER_GRATE, 128),             // 铜格栅
                item(Items.POINTED_DRIPSTONE, 384)         // 滴水石锥
        );

        addSoulRecipe(// 配方 : 阴影木魔石 (Shadow Wood Soul)
                item(ItemRegister.ShadowWoodSoulItem.get(), 1), // 阴影木魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.CHERRY_LOG, 768),               // 樱花原木
                item(Items.GHAST_TEAR, 64),                // 恶魂之泪
                item(Items.SOUL_LANTERN, 128),             // 灵魂灯笼
                item(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, 4), // 宁静盔甲纹饰
                item(Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 提升者盔甲纹饰
                item(Items.HEARTBREAK_POTTERY_SHERD, 16),  // 心碎陶片
                item(Items.PURPLE_CANDLE, 384),            // 紫色蜡烛
                item(Items.WITHER_SKELETON_SKULL, 12),     // 凋灵骷髅头颅
                item(Items.SKELETON_SKULL, 32)             // 骷髅头颅
        );

        addSoulRecipe(// 配方 : 木魔石 (Wood Soul)
                item(ItemRegister.WoodSoulItem.get(), 1),  // 木魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.OAK_LOG, 768),                  // 橡木原木
                item(Items.EMERALD_BLOCK, 64),             // 绿宝石块
                item(Items.VILLAGER_SPAWN_EGG, 1),         // 村民生成蛋
                item(Items.TOTEM_OF_UNDYING, 8),           // 不死图腾
                item(Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 哨兵盔甲纹饰
                item(Items.FRIEND_POTTERY_SHERD, 16),      // 友谊陶片
                item(Items.SHELTER_POTTERY_SHERD, 16),     // 庇护所陶片
                item(Items.BROWN_CANDLE, 384),             // 棕色蜡烛
                item(Items.BOOK, 768)                      // 书
        );

        addSoulRecipe(// 配方 : 森林之力 (Forest Power)
                item(ItemRegister.ForestPowerItem.get(), 1), // 森林之力
                item(ItemRegister.EbonyWoodSoulItem.get(), 1), // 乌木魔石
                item(ItemRegister.PalmWoodSoulItem.get(), 1), // 棕榈木魔石
                item(ItemRegister.PearlWoodSoulItem.get(), 1), // 珍珠木魔石
                item(ItemRegister.PineWoodSoulItem.get(), 1), // 针叶木魔石
                item(ItemRegister.RoseWoodSoulItem.get(), 1), // 红木魔石
                item(ItemRegister.ShadowWoodSoulItem.get(), 1), // 阴影木魔石
                item(ItemRegister.WoodSoulItem.get(), 1),  // 木魔石
                item(Items.NETHER_STAR, 64),               // 下界之星
                item(Items.NETHERITE_BLOCK, 8),            // 净无合金块
                item(Items.FLOWERING_AZALEA_LEAVES, 768),  // 开花杜鹃树叶
                item(Items.PINK_PETALS, 768),              // 樱花瓣
                item(Items.SPORE_BLOSSOM, 128),            // 孢子花
                item(Items.HEART_OF_THE_SEA, 16),          // 海洋之心
                item(Items.ENCHANTED_GOLDEN_APPLE, 8),     // 附魔金苹果
                item(Items.BEE_SPAWN_EGG, 1),              // 蜜蜂生成蛋
                item(Items.HONEY_BLOCK, 128),              // 蜂蜜块
                item(Items.FLOWER_POT, 64),                // 花盆
                item(Items.MOSS_BLOCK, 768),               // 苔藓块
                item(Items.MUSIC_DISC_5, 1),               // 唱片 5
                item(Items.TRIAL_KEY, 12)                  // 试炼钥匙
        );

        addSoulRecipe(// 配方 : 蜜蜂魔石 (Bee Soul)
                item(ItemRegister.BeeSoulItem.get(), 1), // 蜜蜂魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.BEE_SPAWN_EGG, 1),              // 蜜蜂生成蛋
                item(Items.HONEY_BLOCK, 128),              // 蜂蜜块
                item(Items.HONEY_BOTTLE, 128),             // 蜂蜜瓶
                item(Items.HONEYCOMB, 384),                // 蜜脾
                item(Items.FLOWERING_AZALEA, 64),          // 开花杜鹃
                item(Items.PINK_PETALS, 384),              // 樱花瓣
                item(Items.FEATHER, 768),                  // 羽毛
                item(Items.ANGLER_POTTERY_SHERD, 16),      // 垂钓者陶片
                item(Items.YELLOW_CANDLE, 768)             // 黄色蜡烛
        );

        addSoulRecipe(// 配方 : 甲虫魔石 (Beetle Soul)
                item(ItemRegister.BeetleSoulItem.get(), 1), // 甲虫魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.ELYTRA, 1),                     // 鞘翅
                item(Items.SPIDER_EYE, 768),               // 蜘蛛眼
                item(Items.FERMENTED_SPIDER_EYE, 128),     // 发酵蛛眼
                item(Items.IRON_INGOT, 768),               // 铁锭
                item(Items.NETHERITE_SCRAP, 16),           // 下界合金碎片
                item(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 猪灵盔甲纹饰
                item(Items.BLADE_POTTERY_SHERD, 16),       // 刀刃陶片
                item(Items.BROWN_CANDLE, 768),             // 棕色蜡烛
                item(Items.GILDED_BLACKSTONE, 128)         // 镶金黑石
        );

        addSoulRecipe(// 配方 : 南瓜魔石 (Pumpkin Soul)
                item(ItemRegister.PumpkinSoulItem.get(), 1), // 南瓜魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.PUMPKIN, 768),                  // 南瓜
                item(Items.CARVED_PUMPKIN, 128),           // 雕刻南瓜
                item(Items.PUMPKIN_SEEDS, 768),            // 南瓜子
                item(Items.JACK_O_LANTERN, 384),           // 南瓜灯
                item(Items.BONE_MEAL, 768),                // 骨粉
                item(Items.PLENTY_POTTERY_SHERD, 16),      // 丰饶陶片
                item(Items.SHEAF_POTTERY_SHERD, 16),       // 捆束陶片
                item(Items.ORANGE_CANDLE, 768),            // 橙色蜡烛
                item(Items.DIRT, 768)                      // 泥土
        );

        addSoulRecipe(// 配方 : 蜘蛛魔石 (Spider Soul)
                item(ItemRegister.SpiderSoulItem.get(), 1), // 蜘蛛魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.COBWEB, 384),                   // 蜘蛛网
                item(Items.STRING, 768),                   // 线
                item(Items.SPIDER_EYE, 768),               // 蜘蛛眼
                item(Items.FERMENTED_SPIDER_EYE, 128),     // 发酵蛛眼
                item(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, 4), // 宁静盔甲纹饰
                item(Items.ARCHER_POTTERY_SHERD, 16),      // 弓箭手陶片
                item(Items.SKULL_POTTERY_SHERD, 16),       // 头颅陶片
                item(Items.GRAY_CANDLE, 768),              // 灰色蜡烛
                item(Items.RECOVERY_COMPASS, 12)           // 回归罗盘
        );

        addSoulRecipe(// 配方 : 乌龟魔石 (Turtle Soul)
                item(ItemRegister.TurtleSoulItem.get(), 1), // 乌龟魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.TURTLE_HELMET, 8),              // 龟壳
                item(Items.TURTLE_SCUTE, 64),              // 鳞甲
                item(Items.CACTUS, 768),                   // 仙人掌
                item(Items.SHIELD, 16),                    // 盾牌
                item(Items.PRISMARINE_SHARD, 768),         // 海晶石碎片
                item(Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 监守者盔甲纹饰
                item(Items.DANGER_POTTERY_SHERD, 16),      // 危险陶片
                item(Items.LIME_CANDLE, 768),              // 黄绿色蜡烛
                item(Items.HEART_OF_THE_SEA, 4)            // 海洋之心
        );

        addSoulRecipe(// 配方 : 生命之力 (Life Power)
                item(ItemRegister.LifePowerItem.get(), 1), // 生命之力
                item(ItemRegister.BeeSoulItem.get(), 1),   // 蜜蜂魔石
                item(ItemRegister.BeetleSoulItem.get(), 1),// 甲虫魔石
                item(ItemRegister.PumpkinSoulItem.get(), 1),// 南瓜魔石
                item(ItemRegister.SpiderSoulItem.get(), 1),// 蜘蛛魔石
                item(ItemRegister.TurtleSoulItem.get(), 1),// 乌龟魔石
                item(Items.NETHER_STAR, 64),               // 下界之星
                item(Items.NETHERITE_BLOCK, 12),           // 净无合金块
                item(Items.TOTEM_OF_UNDYING, 16),          // 不死图腾
                item(Items.ENCHANTED_GOLDEN_APPLE, 8),     // 附魔金苹果
                item(Items.HEART_POTTERY_SHERD, 32),       // 心形陶片
                item(Items.FRIEND_POTTERY_SHERD, 32),      // 友谊陶片
                item(Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 潮汐盔甲纹饰
                item(Items.RECOVERY_COMPASS, 32),          // 回归罗盘
                item(Items.FROGSPAWN, 64),                 // 青蛙卵
                item(Items.ECHO_SHARD, 128),               // 回响碎片
                item(Items.CONDUIT, 8),                    // 潮汐核心
                item(Items.MUSIC_DISC_PIGSTEP, 1)          // 唱片 Pigstep
        );

        addSoulRecipe(// 配方 : 猩红魔石 (Crimson Soul)
                item(ItemRegister.CrimsonSoulItem.get(), 1), // 猩红魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.NETHER_WART, 768),              // 下界疣
                item(Items.FERMENTED_SPIDER_EYE, 128),     // 发酵蛛眼
                item(Items.RED_CANDLE, 768),               // 红色蜡烛
                item(Items.HEART_POTTERY_SHERD, 16),       // 心形陶片
                item(Items.HEARTBREAK_POTTERY_SHERD, 16),  // 心碎陶片
                item(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 猪灵盔甲纹饰
                item(Items.REDSTONE_BLOCK, 128),           // 红石块
                item(Items.GHAST_TEAR, 64),                // 恶魂之泪
                item(Items.RAW_IRON, 384)                  // 铁原矿
        );

        addSoulRecipe(// 配方 : 冰霜魔石 (Frost Soul)
                item(ItemRegister.FrostSoulItem.get(), 1), // 冰霜魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.BLUE_ICE, 768),                 // 蓝冰
                item(Items.SNOWBALL, 768),                 // 雪球
                item(Items.PACKED_ICE, 768),               // 浮冰
                item(Items.POWDER_SNOW_BUCKET, 64),        // 细雪桶
                item(Items.GUSTER_POTTERY_SHERD, 16),      // 狂风陶片
                item(Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 寻路者盔甲纹饰
                item(Items.LIGHT_BLUE_CANDLE, 768),        // 淡蓝色蜡烛
                item(Items.MINER_POTTERY_SHERD, 16),      // 冰冻陶片 (此处建议使用原版 MINER 或根据风格选)
                item(Items.WHITE_CANDLE, 384)              // 白色蜡烛
        );

        addSoulRecipe(// 配方 : 叶绿魔石 (Green Soul)
                item(ItemRegister.GreenSoulItem.get(), 1), // 叶绿魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.EMERALD_BLOCK, 64),             // 绿宝石块
                item(Items.JUNGLE_LOG, 768),               // 丛林原木
                item(Items.AMETHYST_CLUSTER, 256),         // 簇生紫水晶
                item(Items.GLOW_BERRIES, 768),             // 发光浆果
                item(Items.FLOWERING_AZALEA_LEAVES, 384),  // 开花杜鹃树叶
                item(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 荒野盔甲纹饰
                item(Items.SHEAF_POTTERY_SHERD, 16),       // 捆束陶片
                item(Items.LIME_CANDLE, 768),              // 黄绿色蜡烛
                item(Items.SPORE_BLOSSOM, 64)              // 孢子花
        );

        addSoulRecipe(// 配方 : 熔岩魔石 (Lava Soul)
                item(ItemRegister.LavaSoulItem.get(), 1),  // 熔岩魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.LAVA_BUCKET, 384),              // 岩浆桶
                item(Items.MAGMA_BLOCK, 768),              // 岩浆块
                item(Items.FIRE_CHARGE, 768),              // 火球
                item(Items.BLAZE_ROD, 128),                // 烈焰棒
                item(Items.BURN_POTTERY_SHERD, 32),        // 焚烧陶片
                item(Items.DANGER_POTTERY_SHERD, 16),      // 危险陶片
                item(Items.ORANGE_CANDLE, 768),            // 橙色蜡烛
                item(Items.GILDED_BLACKSTONE, 384),        // 镶金黑石
                item(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 4) // 净无合金升级锻造模板
        );

        addSoulRecipe(// 配方 : 蘑菇魔石 (Mushroom Soul)
                item(ItemRegister.MushroomSoulItem.get(), 1), // 蘑菇魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.MUSHROOM_STEW, 128),            // 蘑菇煲
                item(Items.RED_MUSHROOM, 768),             // 红蘑菇
                item(Items.BROWN_MUSHROOM, 768),           // 棕蘑菇
                item(Items.MYCELIUM, 128),                 // 菌丝
                item(Items.FRIEND_POTTERY_SHERD, 16),      // 友谊陶片
                item(Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 塑造者盔甲纹饰
                item(Items.BROWN_CANDLE, 384),             // 棕色蜡烛
                item(Items.FERMENTED_SPIDER_EYE, 64)       // 发酵蛛眼
        );

        addSoulRecipe(// 配方 : 雨云魔石 (Rain Cloud Soul)
                item(ItemRegister.RainCloudSoulItem.get(), 1), // 雨云魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.LIGHTNING_ROD, 64),             // 避雷针
                item(Items.COPPER_BLOCK, 128),             // 铜块
                item(Items.PRISMARINE_SHARD, 768),         // 海晶石碎片
                item(Items.BLUE_CANDLE, 768),              // 蓝色蜡烛
                item(Items.FLOW_POTTERY_SHERD, 16),        // 流向陶片
                item(Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 潮汐盔甲纹饰
                item(Items.ECHO_SHARD, 64),                // 回响碎片
                item(Items.WATER_BUCKET, 128),             // 水桶
                item(Items.CONDUIT, 4)                     // 潮汐核心
        );

        addSoulRecipe(// 配方 : 自然之力 (Nature Power)
                item(ItemRegister.NaturePowerItem.get(), 1), // 自然之力
                item(ItemRegister.CrimsonSoulItem.get(), 1), // 猩红魔石
                item(ItemRegister.FrostSoulItem.get(), 1),   // 冰霜魔石
                item(ItemRegister.GreenSoulItem.get(), 1),   // 叶绿魔石
                item(ItemRegister.LavaSoulItem.get(), 1),    // 熔岩魔石
                item(ItemRegister.MushroomSoulItem.get(), 1),// 蘑菇魔石
                item(ItemRegister.RainCloudSoulItem.get(), 1),// 雨云魔石
                item(Items.NETHER_STAR, 64),               // 下界之星
                item(Items.NETHERITE_BLOCK, 12),           // 净无合金块
                item(Items.HEART_OF_THE_SEA, 16),          // 海洋之心
                item(Items.ENCHANTED_GOLDEN_APPLE, 8),     // 附魔金苹果
                item(Items.RECOVERY_COMPASS, 32),          // 回归罗盘
                item(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, 8), // 宁静盔甲纹饰
                item(Items.EXPLORER_POTTERY_SHERD, 32),    // 探险家陶片
                item(Items.TOTEM_OF_UNDYING, 16),          // 不死图腾
                item(Items.DRAGON_BREATH, 128),            // 龙息
                item(Items.BEACON, 4),                     // 信标
                item(Items.MUSIC_DISC_5, 1)                // 唱片 5
        );

        addSoulRecipe(// 配方 : 远古神圣魔石 (Ancient Holy Soul)
                item(ItemRegister.AncientHolySoulItem.get(), 1), // 远古神圣魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.GOLD_BLOCK, 128),               // 金块
                item(Items.ENCHANTED_GOLDEN_APPLE, 4),     // 附魔金苹果
                item(Items.EXPERIENCE_BOTTLE, 768),        // 附魔之瓶
                item(Items.GLOWSTONE, 768),                // 荧石
                item(Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 塑造者盔甲纹饰
                item(Items.PRIZE_POTTERY_SHERD, 16),       // 奖励陶片
                item(Items.WHITE_CANDLE, 768),             // 白色蜡烛
                item(Items.END_ROD, 384),                  // 末地烛
                item(Items.BEACON, 1)                      // 信标
        );

        addSoulRecipe(// 配方 : 禁戒魔石 (Forbidden Soul)
                item(ItemRegister.ForbiddenSoulItem.get(), 1), // 禁戒魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.SAND, 768),                     // 沙子
                item(Items.GOLD_INGOT, 768),               // 金锭
                item(Items.GUSTER_POTTERY_SHERD, 32),      // 狂风陶片
                item(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 荒丘盔甲纹饰
                item(Items.ARMS_UP_POTTERY_SHERD, 16),     // 举手陶片
                item(Items.BREEZE_ROD, 128),               // 旋风棒
                item(Items.LAPIS_LAZULI, 768),             // 青金石
                item(Items.OCHRE_FROGLIGHT, 128),          // 赭色蛙光灯
                item(Items.MAGMA_BLOCK, 384)               // 岩浆块
        );

        addSoulRecipe(// 配方 : 幽魂魔石 (Ghost Soul)
                item(ItemRegister.GhostSoulItem.get(), 1), // 幽魂魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.ECHO_SHARD, 128),               // 回响碎片
                item(Items.SOUL_SAND, 768),                // 灵魂沙
                item(Items.SOUL_SOIL, 768),                // 灵魂土
                item(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, 4), // 宁静盔甲纹饰
                item(Items.MOURNER_POTTERY_SHERD, 32),     // 哀悼者陶片
                item(Items.CYAN_CANDLE, 768),              // 青色蜡烛
                item(Items.TOTEM_OF_UNDYING, 8),           // 不死图腾
                item(Items.SCULK_SHRIEKER, 64)             // 幽匿尖啸体
        );

        addSoulRecipe(// 配方 : 神圣魔石 (Holy Soul)
                item(ItemRegister.HolySoulItem.get(), 1),  // 神圣魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.GOLDEN_APPLE, 128),             // 金苹果
                item(Items.TOTEM_OF_UNDYING, 12),          // 不死图腾
                item(Items.CONDUIT, 8),                    // 潮汐核心
                item(Items.HEART_POTTERY_SHERD, 32),       // 心形陶片
                item(Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 监守者盔甲纹饰
                item(Items.PEARLESCENT_FROGLIGHT, 128),    // 珠光蛙光灯
                item(Items.AMETHYST_CLUSTER, 256),         // 簇生紫水晶
                item(Items.GLOW_INK_SAC, 768),             // 发光墨囊
                item(Items.SEA_LANTERN, 384)               // 海晶灯
        );

        addSoulRecipe(// 配方 : 提基魔石 (Teke Soul)
                item(ItemRegister.TekeSoulItem.get(), 1),  // 提基魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.JUNGLE_LOG, 768),               // 丛林原木
                item(Items.CARVED_PUMPKIN, 128),           // 南瓜灯
                item(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 荒野盔甲纹饰
                item(Items.FRIEND_POTTERY_SHERD, 16),      // 友谊陶片
                item(Items.MELON, 768),                    // 西瓜
                item(Items.FEATHER, 768),                  // 羽毛
                item(Items.BAMBOO, 768),                   // 竹子
                item(Items.TOTEM_OF_UNDYING, 4),           // 不死图腾
                item(Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE, 12) // 哨兵盔甲纹饰
        );

        addSoulRecipe(// 配方 : 心灵之力 (Spirit Power)
                item(ItemRegister.SpiritPowerItem.get(), 1), // 心灵之力
                item(ItemRegister.AncientHolySoulItem.get(), 1), // 远古神圣魔石
                item(ItemRegister.ForbiddenSoulItem.get(), 1), // 禁戒魔石
                item(ItemRegister.GhostSoulItem.get(), 1),   // 幽魂魔石
                item(ItemRegister.HolySoulItem.get(), 1),    // 神圣魔石
                item(ItemRegister.TekeSoulItem.get(), 1),    // 提基魔石
                item(Items.NETHER_STAR, 64),               // 下界之星
                item(Items.NETHERITE_BLOCK, 12),           // 净无合金块
                item(Items.RECOVERY_COMPASS, 32),          // 回归罗盘
                item(Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 守望者盔甲纹饰
                item(Items.HEART_OF_THE_SEA, 16),          // 海洋之心
                item(Items.ENCHANTED_GOLDEN_APPLE, 12),    // 附魔金苹果
                item(Items.OMINOUS_BOTTLE, 16),            // 不详之瓶
                item(Items.ECHO_SHARD, 384),               // 回响碎片
                item(Items.RESPAWN_ANCHOR, 64),            // 重生锚
                item(Items.BEACON, 8),                     // 信标
                item(Items.MUSIC_DISC_5, 1)                // 唱片 5
        );

        addSoulRecipe(// 配方 : 铜魔石 (Copper Soul)
                item(ItemRegister.CopperSoulItem.get(), 1), // 铜魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.COPPER_BLOCK, 384),             // 铜块
                item(Items.RAW_COPPER, 768),               // 铜原矿
                item(Items.LIGHTNING_ROD, 64),             // 避雷针
                item(Items.COPPER_BULB, 128),              // 铜灯
                item(Items.COPPER_GRATE, 128),             // 铜格栅
                item(Items.FLOW_POTTERY_SHERD, 16),        // 流向陶片
                item(Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 提升者盔甲纹饰
                item(Items.YELLOW_CANDLE, 768),            // 黄色蜡烛
                item(Items.BEACON, 1)                      // 信标
        );

        addSoulRecipe(// 配方 : 铁魔石 (Iron Soul)
                item(ItemRegister.IronSoulItem.get(), 1),  // 铁魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.IRON_BLOCK, 128),               // 铁块
                item(Items.RAW_IRON, 768),                 // 铁原矿
                item(Items.HOPPER, 64),                    // 漏斗
                item(Items.LODESTONE, 16),                 // 磁石
                item(Items.MINER_POTTERY_SHERD, 16),       // 矿工陶片
                item(Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 哨兵盔甲纹饰
                item(Items.ANVIL, 16),                     // 铁砧
                item(Items.GRAY_CANDLE, 768),              // 灰色蜡烛
                item(Items.BUCKET, 128)                    // 铁桶
        );

        addSoulRecipe(// 配方 : 铅魔石 (Lead Soul)
                item(ItemRegister.LeadSoulItem.get(), 1),  // 铅魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.CAULDRON, 64),                  // 炼药锅
                item(Items.FERMENTED_SPIDER_EYE, 128),     // 发酵蛛眼
                item(Items.POISONOUS_POTATO, 64),          // 毒马铃薯
                item(Items.DANGER_POTTERY_SHERD, 16),      // 危险陶片
                item(Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 宿主盔甲纹饰
                item(Items.BLACKSTONE_SLAB, 384),          // 黑石台阶
                item(Items.CYAN_CANDLE, 768),              // 青色蜡烛
                item(Items.RAW_IRON, 384),                 // 铁原矿 (作为铅的代用品)
                item(Items.POINTED_DRIPSTONE, 384)         // 滴水石锥
        );

        addSoulRecipe(// 配方 : 黑曜石魔石 (Obsidian Soul)
                item(ItemRegister.ObsidianSoulItem.get(), 1), // 黑曜石魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.OBSIDIAN, 768),                 // 黑曜石
                item(Items.CRYING_OBSIDIAN, 128),          // 哭泣的黑曜石
                item(Items.FIRE_CHARGE, 768),              // 火球
                item(Items.MAGMA_BLOCK, 768),              // 岩浆块
                item(Items.BURN_POTTERY_SHERD, 16),        // 焚烧陶片
                item(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 猪灵盔甲纹饰
                item(Items.FLINT_AND_STEEL, 64),           // 打火石
                item(Items.LAVA_BUCKET, 128),              // 岩浆桶
                item(Items.BLACK_CANDLE, 768)              // 黑色蜡烛
        );

        addSoulRecipe(// 配方 : 银魔石 (Silver Soul)
                item(ItemRegister.SilverSoulItem.get(), 1), // 银魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.SHIELD, 32),                    // 盾牌
                item(Items.IRON_INGOT, 768),               // 铁锭 (作为银的代用品)
                item(Items.AMETHYST_CLUSTER, 128),         // 簇生紫水晶
                item(Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 潮汐盔甲纹饰
                item(Items.SCRAPE_POTTERY_SHERD, 16),      // 刮削陶片
                item(Items.WHITE_CANDLE, 768),             // 白色蜡烛
                item(Items.GUSTER_POTTERY_SHERD, 16),      // 狂风陶片
                item(Items.IRON_DOOR, 64),                 // 铁门
                item(Items.EXPERIENCE_BOTTLE, 384)         // 附魔之瓶
        );

        addSoulRecipe(// 配方 : 锡魔石 (Tin Soul)
                item(ItemRegister.TinSoulItem.get(), 1),   // 锡魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.RAW_GOLD, 384),                 // 金原矿 (作为锡的代用品)
                item(Items.TARGET, 64),                    // 目标靶
                item(Items.ARCHER_POTTERY_SHERD, 16),      // 弓箭手陶片
                item(Items.PLENTY_POTTERY_SHERD, 16),      // 丰饶陶片
                item(Items.BOLT_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 螺栓盔甲纹饰
                item(Items.GLOWSTONE, 768),                // 荧石
                item(Items.LIME_CANDLE, 768),              // 黄绿色蜡烛
                item(Items.GOLD_INGOT, 384),               // 金锭
                item(Items.NETHER_BRICK_SLAB, 384)         // 下界砖台阶
        );

        addSoulRecipe(// 配方 : 钨魔石 (Tungsten Soul)
                item(ItemRegister.TungstenSoulItem.get(), 1), // 钨魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.GILDED_BLACKSTONE, 384),        // 镶金黑石
                item(Items.BLADE_POTTERY_SHERD, 32),       // 刀刃陶片
                item(Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 尖塔盔甲纹饰
                item(Items.END_ROD, 384),                  // 末地烛
                item(Items.TRIDENT, 4),                    // 三叉戟
                item(Items.WHITE_CANDLE, 384),             // 白色蜡烛
                item(Items.POLISHED_BLACKSTONE_BRICKS, 768), // 磨制黑石砖
                item(Items.DRIPSTONE_BLOCK, 384),          // 滴水石块
                item(Items.MACE, 1)                        // 重锤 (1.21新武器对应大尺寸)
        );

        addSoulRecipe(// 配方 : 泰拉之力 (Terra Power)
                item(ItemRegister.TerraPowerItem.get(), 1),// 泰拉之力
                item(ItemRegister.CopperSoulItem.get(), 1),// 铜魔石
                item(ItemRegister.IronSoulItem.get(), 1),  // 铁魔石
                item(ItemRegister.LeadSoulItem.get(), 1),  // 铅魔石
                item(ItemRegister.ObsidianSoulItem.get(), 1), // 黑曜石魔石
                item(ItemRegister.SilverSoulItem.get(), 1),// 银魔石
                item(ItemRegister.TinSoulItem.get(), 1),   // 锡魔石
                item(ItemRegister.TungstenSoulItem.get(), 1), // 钨魔石
                item(Items.NETHER_STAR, 64),               // 下界之星
                item(Items.NETHERITE_BLOCK, 12),           // 净无合金块
                item(Items.BEACON, 8),                     // 信标
                item(Items.HEART_OF_THE_SEA, 16),          // 海洋之心
                item(Items.TRIAL_KEY, 32),                 // 试炼钥匙
                item(Items.OMINOUS_TRIAL_KEY, 12),         // 不详试炼钥匙
                item(Items.RECOVERY_COMPASS, 32),          // 回归罗盘
                item(Items.ENCHANTED_GOLDEN_APPLE, 8),     // 附魔金苹果
                item(Items.TOTEM_OF_UNDYING, 16),          // 不死图腾
                item(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, 8), // 宁静盔甲纹饰
                item(Items.SKULL_POTTERY_SHERD, 16),       // 头颅陶片
                item(Items.MUSIC_DISC_OTHERSIDE, 1)        // 唱片 Otherside
        );

        addSoulRecipe(// 配方 : 角斗士魔石 (Gladiator Soul)
                item(ItemRegister.GladiatorSoulItem.get(), 1), // 角斗士魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.ARROW, 768),                    // 箭
                item(Items.SPECTRAL_ARROW, 384),           // 光灵箭
                item(Items.TRIDENT, 4),                    // 三叉戟
                item(Items.ARCHER_POTTERY_SHERD, 16),      // 弓箭手陶片
                item(Items.BLADE_POTTERY_SHERD, 16),       // 刀刃陶片
                item(Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 尖塔盔甲纹饰
                item(Items.CHISELED_POLISHED_BLACKSTONE, 384), // 錾刻磨制黑石
                item(Items.IRON_SWORD, 64),                // 铁剑
                item(Items.YELLOW_CANDLE, 384)             // 黄色蜡烛
        );

        addSoulRecipe(// 配方 : 金魔石 (Gold Soul)
                item(ItemRegister.GoldSoulItem.get(), 1),  // 金魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.GOLD_BLOCK, 128),               // 金块
                item(Items.GOLD_INGOT, 768),               // 金锭
                item(Items.RAW_GOLD, 768),                 // 金原矿
                item(Items.GILDED_BLACKSTONE, 384),        // 镶金黑石
                item(Items.PRIZE_POTTERY_SHERD, 32),       // 奖励陶片
                item(Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 塑造者盔甲纹饰
                item(Items.GOLDEN_APPLE, 64),              // 金苹果
                item(Items.YELLOW_CANDLE, 768),            // 黄色蜡烛
                item(Items.BELL, 16)                       // 钟
        );

        addSoulRecipe(// 配方 : 铂金魔石 (Platinum Soul)
                item(ItemRegister.PlatinumSoulItem.get(), 1), // 铂金魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.IRON_BLOCK, 128),               // 铁块 (代指铂金)
                item(Items.NETHERITE_INGOT, 12),           // 下界合金锭
                item(Items.DIAMOND, 128),                  // 钻石
                item(Items.PLENTY_POTTERY_SHERD, 32),      // 丰饶陶片
                item(Items.MAP, 8),                        // 藏宝图
                item(Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 哨兵盔甲纹饰
                item(Items.WHITE_CANDLE, 768),             // 白色蜡烛
                item(Items.EMERALD, 768)                   // 绿宝石
        );

        addSoulRecipe(// 配方 : 红色骑术魔石 (Red Riding Soul)
                item(ItemRegister.RedRidingSoulItem.get(), 1), // 红色骑术魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.RED_WOOL, 768),                 // 红色羊毛
                item(Items.CROSSBOW, 32),                  // 弩
                item(Items.RED_CANDLE, 768),               // 红色蜡烛
                item(Items.HOWL_POTTERY_SHERD, 16),        // 嗥叫陶片
                item(Items.DANGER_POTTERY_SHERD, 16),      // 危险陶片
                item(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 荒野盔甲纹饰
                item(Items.LEATHER, 768),                  // 皮革
                item(Items.FEATHER, 384)                   // 羽毛
        );

        addSoulRecipe(// 配方 : 英灵殿骑士魔石 (Valhalla Knight Soul)
                item(ItemRegister.ValhallaKnightSoulItem.get(), 1), // 英灵殿骑士魔石
                item(Items.NETHER_STAR, 12),               // 下界之星
                item(Items.SADDLE, 16),                    // 鞍
                item(Items.GOLDEN_HORSE_ARMOR, 8),         // 金马铠
                item(Items.DIAMOND_HORSE_ARMOR, 4),        // 钻石马铠
                item(Items.HEART_POTTERY_SHERD, 16),       // 心形陶片
                item(Items.SHELTER_POTTERY_SHERD, 16),     // 庇护所陶片
                item(Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 监守者盔甲纹饰
                item(Items.EXPERIENCE_BOTTLE, 768),        // 附魔之瓶
                item(Items.OCHRE_FROGLIGHT, 128),          // 赭色蛙光灯
                item(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 4) // 净无合金升级锻造模板
        );

        addSoulRecipe(// 配方 : 意志之力 (Will Power)
                item(ItemRegister.WillPowerItem.get(), 1), // 意志之力
                item(ItemRegister.GladiatorSoulItem.get(), 1), // 角斗士魔石
                item(ItemRegister.GoldSoulItem.get(), 1),  // 金魔石
                item(ItemRegister.PlatinumSoulItem.get(), 1), // 铂金魔石
                item(ItemRegister.RedRidingSoulItem.get(), 1), // 红色骑术魔石
                item(ItemRegister.ValhallaKnightSoulItem.get(), 1), // 英灵殿骑士魔石
                item(Items.NETHER_STAR, 64),               // 下界之星
                item(Items.NETHERITE_BLOCK, 12),           // 净无合金块
                item(Items.BEACON, 8),                     // 信标
                item(Items.RECOVERY_COMPASS, 32),          // 回归罗盘
                item(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, 8), // 宁静盔甲纹饰
                item(Items.TOTEM_OF_UNDYING, 16),          // 不死图腾
                item(Items.EXPLORER_POTTERY_SHERD, 32),    // 探险家陶片
                item(Items.BOLT_ARMOR_TRIM_SMITHING_TEMPLATE, 12), // 螺栓盔甲纹饰
                item(Items.MUSIC_DISC_5, 1),               // 唱片 5
                item(Items.OMINOUS_TRIAL_KEY, 16),         // 不详试炼钥匙
                item(Items.TRIAL_SPAWNER, 1)               // 试炼生成器
        );

        addSoulRecipe(// 配方 : 泰拉之魂 (Terra Soul) - 终极整合版
                item(ItemRegister.TerraSoulItem.get(), 1),   // 泰拉之魂
                item(ItemRegister.TerraPowerItem.get(), 1),  // 泰拉之力
                item(ItemRegister.EarthPowerItem.get(), 1),  // 大地之力
                item(ItemRegister.NaturePowerItem.get(), 1), // 自然之力
                item(ItemRegister.LifePowerItem.get(), 1),   // 生命之力
                item(ItemRegister.SpiritPowerItem.get(), 1), // 心灵之力
                item(ItemRegister.WillPowerItem.get(), 1),   // 意志之力
                item(ItemRegister.ForestPowerItem.get(), 1), // 森林之力
                item(ItemRegister.DeathPowerItem.get(), 1),  // 死亡之力
                item(ItemRegister.CosmicPowerItem.get(), 1), // 宇宙之力

                // --- 权力与号角 (Power & Horns) ---
                item(Items.GOAT_HORN, 8),                  // 山羊角 (多种音调象征号令)
                item(Items.TRIAL_KEY, 64),                 // 试炼钥匙
                item(Items.OMINOUS_TRIAL_KEY, 32),         // 不详试炼钥匙

                // --- 远古陶片 (Ancient Sherds) - 象征人类文明与历史 ---
                item(Items.ARCHER_POTTERY_SHERD, 16),      // 弓箭手 (战争)
                item(Items.PRIZE_POTTERY_SHERD, 16),       // 奖励 (财富)
                item(Items.EXPLORER_POTTERY_SHERD, 16),    // 探险家 (勇气)
                item(Items.SKULL_POTTERY_SHERD, 16),       // 头颅 (死亡)
                item(Items.HEART_POTTERY_SHERD, 16),       // 心形 (生命)
                item(Items.FLOW_POTTERY_SHERD, 16),        // 流向 (元素)

                // --- 锻造模板 (Smithing Templates) - 象征极致的工艺 ---
                item(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, 16), // 宁静 (最稀有)
                item(Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE, 16),    // 监守者
                item(Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE, 16),     // 守望者
                item(Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE, 16),   // 尖塔
                item(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 32),  // 下界合金升级

                // --- 宇宙与大地核心 (Core Materials) ---
                item(Items.NETHER_STAR, 768),              // 下界之星
                item(Items.NETHERITE_BLOCK, 64),           // 下界合金块
                item(Items.BEACON, 32),                    // 信标
                item(Items.CONDUIT, 32),                   // 潮汐核心
                item(Items.ECHO_SHARD, 768),               // 回响碎片
                item(Items.ENCHANTED_GOLDEN_APPLE, 64),    // 附魔金苹果
                item(Items.TOTEM_OF_UNDYING, 64),          // 不死图腾
                item(Items.RECOVERY_COMPASS, 64),          // 回归罗盘
                item(Items.MUSIC_DISC_5, 8),               // 唱片 5
                item(Items.MUSIC_DISC_PIGSTEP, 8)          // 唱片 Pigstep
        );

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegister.CosmicCrucible.get())
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .define('A', ItemRegister.Soul)
                .define('B', Blocks.DRAGON_EGG)
                .unlockedBy("has_" + ItemRegister.Soul.getId().getPath(), has(ItemRegister.Soul))
                .save(recipeOutput, BlockRegister.CosmicCrucible.getId());

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegister.Soul)
                .requires(Items.RABBIT_FOOT)
                .requires(Items.TURTLE_SCUTE)
                .requires(Items.DISC_FRAGMENT_5)
                .requires(Items.HANGING_ROOTS)
                .requires(Items.ARMADILLO_SCUTE)
                .requires(Items.FROGSPAWN)
                .requires(Items.AMETHYST_SHARD)
                .requires(Items.PITCHER_PLANT)
                .requires(Items.MANGROVE_PROPAGULE)
                .unlockedBy("has_echo_shard", has(Items.ECHO_SHARD))
                .save(recipeOutput);

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

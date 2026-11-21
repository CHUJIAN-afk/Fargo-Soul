package first.fargo_soul.register;

import first.lyra.provider.LyraAbstractLootProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;

public class FargoSoulLootProvider extends LyraAbstractLootProvider {

    public FargoSoulLootProvider(String modid) {
        super(modid);
    }

    @Override
    public void loot() {
        addEntityLoot(EntityType.BLAZE, pool(FargoSoulItemRegister.BlazeSoulItem, 0.01F));
        addEntityLoot(EntityType.PHANTOM, pool(FargoSoulItemRegister.METEOR_SOUL, 0.01F));
        addChestLoot(BuiltInLootTables.NETHER_BRIDGE, chestPool(FargoSoulItemRegister.METEOR_SOUL, 0.15F));
        addEntityLoot(EntityType.SHULKER, pool(FargoSoulItemRegister.NebulaSoulItem, 0.01F));
        addChestLoot(BuiltInLootTables.END_CITY_TREASURE, chestPool(FargoSoulItemRegister.NebulaSoulItem, 0.2F));
        addEntityLoot(EntityType.ENDERMAN, pool(FargoSoulItemRegister.StardustSoulItem, 0.01F));
        addChestLoot(BuiltInLootTables.END_CITY_TREASURE, chestPool(FargoSoulItemRegister.StardustSoulItem, 0.15F));
        addEntityLoot(EntityType.ENDERMAN, pool(FargoSoulItemRegister.VortexSoulItem, 0.01F));
        addChestLoot(BuiltInLootTables.END_CITY_TREASURE, chestPool(FargoSoulItemRegister.VortexSoulItem, 0.1F));
        addEntityLoot(EntityType.WITCH, pool(FargoSoulItemRegister.WizardSoulItem, 0.01F));
        addEntityLoot(EntityType.WARDEN, pool(FargoSoulItemRegister.AncientShadowSoulItem, 0.01F));
        addChestLoot(BuiltInLootTables.ANCIENT_CITY, chestPool(FargoSoulItemRegister.AncientShadowSoulItem, 0.2F));
        addEntityLoot(EntityType.PILLAGER, pool(FargoSoulItemRegister.NinjaSoulItem, 0.01F));
        addChestLoot(BuiltInLootTables.PILLAGER_OUTPOST, chestPool(FargoSoulItemRegister.NinjaSoulItem, 0.15F));
        addEntityLoot(EntityType.BREEZE, pool(FargoSoulItemRegister.CrystalAssassinSoulItem, 0.01F));
        addEntityLoot(EntityType.EVOKER, pool(FargoSoulItemRegister.DarkArtistSoulItem, 0.01F));
        addChestLoot(BuiltInLootTables.WOODLAND_MANSION, chestPool(FargoSoulItemRegister.DarkArtistSoulItem, 0.1F));
        addEntityLoot(EntityType.ENDERMAN, pool(FargoSoulItemRegister.GloomySoulItem, 0.01F));
        addChestLoot(BuiltInLootTables.ANCIENT_CITY, chestPool(FargoSoulItemRegister.GloomySoulItem, 0.15F));
        addEntityLoot(EntityType.SKELETON, pool(FargoSoulItemRegister.NecromancerSoulItem, 0.01F));
        addEntityLoot(EntityType.WITHER_SKELETON, pool(FargoSoulItemRegister.NecromancerSoulItem, 0.01F));
        addEntityLoot(EntityType.VINDICATOR, pool(FargoSoulItemRegister.PenetratingNinjaSoulItem, 0.01F));
        addEntityLoot(EntityType.DROWNED, pool(FargoSoulItemRegister.CopperSoulItem, 0.01F));
        addChestLoot(BuiltInLootTables.ABANDONED_MINESHAFT, chestPool(FargoSoulItemRegister.CopperSoulItem, 0.08F));
        addEntityLoot(EntityType.SKELETON, pool(FargoSoulItemRegister.TinSoulItem, 0.01F));
        addChestLoot(BuiltInLootTables.DESERT_PYRAMID, chestPool(FargoSoulItemRegister.TinSoulItem, 0.1F));
        addEntityLoot(EntityType.IRON_GOLEM, pool(FargoSoulItemRegister.IronSoulItem, 0.01F));
        addChestLoot(BuiltInLootTables.ABANDONED_MINESHAFT, chestPool(FargoSoulItemRegister.IronSoulItem, 0.1F));
        addEntityLoot(EntityType.CAVE_SPIDER, pool(FargoSoulItemRegister.LeadSoulItem, 0.01F));
        addEntityLoot(EntityType.SILVERFISH, pool(FargoSoulItemRegister.SilverSoulItem, 0.01F));
        addChestLoot(BuiltInLootTables.SIMPLE_DUNGEON, chestPool(FargoSoulItemRegister.SilverSoulItem, 0.1F));
        addEntityLoot(EntityType.RAVAGER, pool(FargoSoulItemRegister.TungstenSoulItem, 0.01F));
        addChestLoot(BuiltInLootTables.BASTION_TREASURE, chestPool(FargoSoulItemRegister.TungstenSoulItem, 0.05F));
        addEntityLoot(EntityType.MAGMA_CUBE, pool(FargoSoulItemRegister.ObsidianSoulItem, 0.01F));
        addChestLoot(BuiltInLootTables.NETHER_BRIDGE, chestPool(FargoSoulItemRegister.ObsidianSoulItem, 0.1F));
        addChestLoot(BuiltInLootTables.VILLAGE_PLAINS_HOUSE, chestPool(FargoSoulItemRegister.WoodSoulItem, 0.2F));
        addChestLoot(BuiltInLootTables.VILLAGE_SAVANNA_HOUSE, chestPool(FargoSoulItemRegister.WoodSoulItem, 0.2F));
        addChestLoot(BuiltInLootTables.VILLAGE_WEAPONSMITH, chestPool(FargoSoulItemRegister.WoodSoulItem, 0.15F));
        addEntityLoot(EntityType.STRAY, pool(FargoSoulItemRegister.PineWoodSoulItem, 0.01F));
        addChestLoot(BuiltInLootTables.JUNGLE_TEMPLE, chestPool(FargoSoulItemRegister.RoseWoodSoulItem, 0.1F));
        addChestLoot(BuiltInLootTables.WOODLAND_MANSION, chestPool(FargoSoulItemRegister.EbonyWoodSoulItem, 0.1F));
        addChestLoot(BuiltInLootTables.WOODLAND_MANSION, chestPool(FargoSoulItemRegister.ShadowWoodSoulItem, 0.08F));
        addChestLoot(BuiltInLootTables.SHIPWRECK_TREASURE, chestPool(FargoSoulItemRegister.PalmWoodSoulItem, 0.1F));
        addChestLoot(BuiltInLootTables.SHIPWRECK_TREASURE, chestPool(FargoSoulItemRegister.PearlWoodSoulItem, 0.15F));
        addEntityLoot(EntityType.HOGLIN, pool(FargoSoulItemRegister.CobaltSoulItem, 0.01F));
        addChestLoot(BuiltInLootTables.NETHER_BRIDGE, chestPool(FargoSoulItemRegister.CobaltSoulItem, 0.08F));
        addEntityLoot(EntityType.WITCH, pool(FargoSoulItemRegister.PalladiumSoulItem, 0.01F));
        addEntityLoot(EntityType.VEX, pool(FargoSoulItemRegister.MithrilSoulItem, 0.01F));
        addChestLoot(BuiltInLootTables.STRONGHOLD_LIBRARY, chestPool(FargoSoulItemRegister.MithrilSoulItem, 0.12F));
        addEntityLoot(EntityType.ENDERMAN, pool(FargoSoulItemRegister.OrichalcumSoulItem, 0.01F));
        addChestLoot(BuiltInLootTables.JUNGLE_TEMPLE, chestPool(FargoSoulItemRegister.OrichalcumSoulItem, 0.08F));
        addEntityLoot(EntityType.WITHER_SKELETON, pool(FargoSoulItemRegister.AdamantiteSoulItem, 0.01F));
        addEntityLoot(EntityType.RAVAGER, pool(FargoSoulItemRegister.TitaniumSoulItem, 0.01F));
        addEntityLoot(EntityType.ZOMBIE, pool(FargoSoulItemRegister.CrimsonSoulItem, 0.01F));
        addEntityLoot(EntityType.MAGMA_CUBE, pool(FargoSoulItemRegister.LavaSoulItem, 0.01F));
        addChestLoot(BuiltInLootTables.NETHER_BRIDGE, chestPool(FargoSoulItemRegister.LavaSoulItem, 0.1F));
        addEntityLoot(EntityType.PHANTOM, pool(FargoSoulItemRegister.RainCloudSoulItem, 0.01F));
        addChestLoot(BuiltInLootTables.SHIPWRECK_TREASURE, chestPool(FargoSoulItemRegister.RainCloudSoulItem, 0.08F));
        addEntityLoot(EntityType.STRAY, pool(FargoSoulItemRegister.FrostSoulItem, 0.01F));
        addEntityLoot(EntityType.SPIDER, pool(FargoSoulItemRegister.GreenSoulItem, 0.01F));
        addChestLoot(BuiltInLootTables.JUNGLE_TEMPLE, chestPool(FargoSoulItemRegister.GreenSoulItem, 0.05F));
        addChestLoot(BuiltInLootTables.ANCIENT_CITY, chestPool(FargoSoulItemRegister.GreenSoulItem, 0.08F));
        addEntityLoot(EntityType.ZOMBIE, pool(FargoSoulItemRegister.MushroomSoulItem, 0.01F));
        addEntityLoot(EntityType.BEE, pool(FargoSoulItemRegister.BeeSoulItem, 0.01F));
        addEntityLoot(EntityType.CREEPER, pool(FargoSoulItemRegister.BeetleSoulItem, 0.01F));
        addChestLoot(BuiltInLootTables.JUNGLE_TEMPLE, chestPool(FargoSoulItemRegister.BeetleSoulItem, 0.15F));
        addEntityLoot(EntityType.ZOMBIE, pool(FargoSoulItemRegister.PumpkinSoulItem, 0.01F));
        addEntityLoot(EntityType.SPIDER, pool(FargoSoulItemRegister.SpiderSoulItem, 0.01F));
        addEntityLoot(EntityType.TURTLE, pool(FargoSoulItemRegister.TurtleSoulItem, 0.01F));
        addEntityLoot(EntityType.EVOKER, pool(FargoSoulItemRegister.ForbiddenSoulItem, 0.01F));
        addChestLoot(BuiltInLootTables.DESERT_PYRAMID, chestPool(FargoSoulItemRegister.HolySoulItem, 0.2F));
        addEntityLoot(EntityType.VEX, pool(FargoSoulItemRegister.AncientHolySoulItem, 0.01F));
        addChestLoot(BuiltInLootTables.WOODLAND_MANSION, chestPool(FargoSoulItemRegister.AncientHolySoulItem, 0.12F));
        addChestLoot(BuiltInLootTables.JUNGLE_TEMPLE, chestPool(FargoSoulItemRegister.TekeSoulItem, 0.2F));
        addEntityLoot(EntityType.GHAST, pool(FargoSoulItemRegister.GhostSoulItem, 0.01F));
        addChestLoot(BuiltInLootTables.SIMPLE_DUNGEON, chestPool(FargoSoulItemRegister.GhostSoulItem, 0.08F));
        addChestLoot(BuiltInLootTables.ANCIENT_CITY, chestPool(FargoSoulItemRegister.GhostSoulItem, 0.1F));
        addEntityLoot(EntityType.ZOMBIFIED_PIGLIN, pool(FargoSoulItemRegister.GoldSoulItem, 0.01F));
        addChestLoot(BuiltInLootTables.DESERT_PYRAMID, chestPool(FargoSoulItemRegister.GoldSoulItem, 0.05F));
        addChestLoot(BuiltInLootTables.BASTION_TREASURE, chestPool(FargoSoulItemRegister.GoldSoulItem, 0.15F));
        addEntityLoot(EntityType.PIGLIN_BRUTE, pool(FargoSoulItemRegister.PlatinumSoulItem, 0.01F));
        addChestLoot(BuiltInLootTables.BASTION_TREASURE, chestPool(FargoSoulItemRegister.PlatinumSoulItem, 0.1F));
        addChestLoot(BuiltInLootTables.BURIED_TREASURE, chestPool(FargoSoulItemRegister.PlatinumSoulItem, 0.1F));
        addEntityLoot(EntityType.RAVAGER, pool(FargoSoulItemRegister.GladiatorSoulItem, 0.01F));
        addChestLoot(BuiltInLootTables.WOODLAND_MANSION, chestPool(FargoSoulItemRegister.GladiatorSoulItem, 0.15F));
        addEntityLoot(EntityType.WOLF, pool(FargoSoulItemRegister.RedRidingSoulItem, 0.01F));
        addEntityLoot(EntityType.VINDICATOR, pool(FargoSoulItemRegister.ValhallaKnightSoulItem, 0.01F));

    }

    private static LootPool.Builder pool(ItemLike item, float chance) {
        return LootPool.lootPool()
                .add(LootItem.lootTableItem(item)
                        .when(LootItemRandomChanceCondition.randomChance(chance))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer()));
    }

    private static LootPool.Builder chestPool(ItemLike item, float chance) {
        return LootPool.lootPool()
                .add(LootItem.lootTableItem(item)
                        .when(LootItemRandomChanceCondition.randomChance(chance)));
    }
}

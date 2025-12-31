package First.fargo_soul.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerSoulConfig {

    private static final ModConfigSpec.Builder Builder = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue CosmicCrucibleSize = Builder.defineInRange("cosmic_crucible_size", 64, 1, 1024);

    public static final ModConfigSpec.DoubleValue SoulChance = Builder.defineInRange("soul_chance", 0.01, 0.0, 1.0);

    public static final ModConfigSpec.DoubleValue SoulDropChance = Builder.defineInRange("soul_drop_chance", 0.1, 0.0, 1.0);

    public static final ModConfigSpec.DoubleValue SoulHpIncreasePerSoul = Builder.defineInRange("soul_hp_increase_per_soul", 5, 0.0, 1000.0);

    public static final ModConfigSpec.DoubleValue SoulHpMultiplierPerSoul = Builder.defineInRange("soul_hp_multiplier_per_soul", 0.5, 0.0, 1000.0);

    public static final ModConfigSpec.DoubleValue SoulSizeMultiplierPerSoul = Builder.defineInRange("soul_size_multiplier_per_soul", 0.05, 0.0, 1.0);

    public static final ModConfigSpec.BooleanValue AllowCreaturesThatSpawnThroughUnnaturalPathsToCarrySouls = Builder.define("allow_creatures_that_spawn_through_unnatural_paths_to_carry_souls", false);

    public static final ModConfigSpec.BooleanValue AllowCreaturesThatSpawnThroughUnnaturalPathsToDropSouls = Builder.define("allow_creatures_that_spawn_through_unnatural_paths_to_drop_souls", false);

    public static final ModConfigSpec.BooleanValue AllowFriendlyMobSoul = Builder.define("allow_friendly_mob_soul", true);

    public static final ModConfigSpec.BooleanValue AllowHostileMobSoul = Builder.define("allow_hostile_mob_soul", true);

    public static final ModConfigSpec Spec = Builder.build();

}

package First.fargo_soul.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ClientConfig {

    private static final ModConfigSpec.Builder Builder = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue CosmicCrucibleItemRendering = Builder.define("cosmic_crucible_item_rendering", true);

    public static final ModConfigSpec.BooleanValue CosmicCrucibleBlackHoleEventHorizonRendering = Builder.define("cosmic_crucible_black_hole_event_horizon_rendering", true);

    public static final ModConfigSpec Spec = Builder.build();

}

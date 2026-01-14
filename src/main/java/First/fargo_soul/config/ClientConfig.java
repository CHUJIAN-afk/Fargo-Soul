package First.fargo_soul.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ClientConfig {

    private static final ModConfigSpec.Builder Builder = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue CosmicCrucibleItemRendering =
            Builder.define("cosmic_crucible_item_rendering", true);

    public static final ModConfigSpec.BooleanValue CosmicCrucibleBlackHoleEventHorizonRendering =
            Builder.define("cosmic_crucible_black_hole_event_horizon_rendering", true);

    public static final ModConfigSpec.BooleanValue EmbedAChildSoulInTheItemTooltip =
            Builder.define("embed_a_child_soul_in_the_item_tooltip", true);

    public static final ModConfigSpec.BooleanValue CreatureSoulRendering =
            Builder.define("creature_soul_rendering", true);

    public static final ModConfigSpec.BooleanValue ItemRenderScaling =
            Builder.define("item_render_scaling", true);

    public static final ModConfigSpec.BooleanValue ShowSoulTooltip =
            Builder.define("show_soul_tooltip", true);

    public static final ModConfigSpec.DoubleValue ShowSoulTooltipScale =
            Builder.defineInRange("show_soul_tooltip_scale", 0.75, 0.01, 2);

    public static final ModConfigSpec.IntValue ShowSoulTooltipXOffset =
            Builder.defineInRange("show_soul_tooltip_x_offset", 0, -1000, 1000);

    public static final ModConfigSpec.IntValue ShowSoulTooltipYOffset =
            Builder.defineInRange("show_soul_tooltip_y_offset", 0, -1000, 1000);

    public static final ModConfigSpec.IntValue InformationInterval =
            Builder.defineInRange("information_interval", 10, 1, 100);

    public static final ModConfigSpec Spec = Builder.build();

}

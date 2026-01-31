package First.fargo_soul.common.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SoulRarity implements DataComponentType<SoulRarity> {

    public static final SoulRarity COMMON = new SoulRarity("common", 16777215);
    public static final SoulRarity UNCOMMON = new SoulRarity("uncommon", 16777045);
    public static final SoulRarity RARE = new SoulRarity("rare", 5636095);
    public static final SoulRarity EPIC = new SoulRarity("epic", 16733695);

    public static final SoulRarity GRAY = new SoulRarity("gray", 0x828282);
    public static final SoulRarity WHITE = new SoulRarity("white", 0xFFFFFF);
    public static final SoulRarity BLUE = new SoulRarity("blue", 0x9696FF);
    public static final SoulRarity GREEN = new SoulRarity("green", 0x96FF96);
    public static final SoulRarity ORANGE = new SoulRarity("orange", 0xFFC896);
    public static final SoulRarity LIGHT_RED = new SoulRarity("light_red", 0xFF9696);
    public static final SoulRarity PINK = new SoulRarity("pink", 0xFF96FF);
    public static final SoulRarity LIGHT_PURPLE = new SoulRarity("light_purple", 0xD2A0FF);
    public static final SoulRarity LIME = new SoulRarity("lime", 0x96FF0A);
    public static final SoulRarity YELLOW = new SoulRarity("yellow", 0xFFFF0A);
    public static final SoulRarity CYAN = new SoulRarity("cyan", 0x05C8FF);
    public static final SoulRarity RED = new SoulRarity("red", 0xFF2864);
    public static final SoulRarity PURPLE = new SoulRarity("purple", 0xB428FF);

    public static final SoulRarity EXPERT = new SoulRarity("expert", -1);
    public static final SoulRarity MASTER = new SoulRarity("master", -2);
    public static final SoulRarity QUEST = new SoulRarity("quest", 0xFFAF00);

    public static final Codec<SoulRarity> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(SoulRarity::getName),
            Codec.INT.fieldOf("color").forGetter(SoulRarity::getColor)
    ).apply(instance, SoulRarity::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, SoulRarity> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, SoulRarity::getName,
            ByteBufCodecs.INT, SoulRarity::getColor,
            SoulRarity::new
    );

    private final String name;
    private int color;

    private SoulRarity(String name, int color) {
        this.name = name;
        this.color = color;
    }

    @Override
    public @Nullable Codec<SoulRarity> codec() {
        return CODEC;
    }

    @Override
    public @NotNull StreamCodec<? super RegistryFriendlyByteBuf, SoulRarity> streamCodec() {
        return STREAM_CODEC;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SoulRarity soulRarity) {
            return this.name.equals(soulRarity.name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode() + this.color;
    }

    public int getARGB() {
        return 0xFF000000 | color;
    }

    public int getInverseARGB() {
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;
        return 0xFF000000 | ((255 - r) << 16) | ((255 - g) << 8) | (255 - b);
    }

}

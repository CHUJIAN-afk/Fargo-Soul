package first.fargo_soul.common.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public record SoulRarity(int color) {
    public static final SoulRarity Gray = new SoulRarity(0x828282);
    public static final SoulRarity White = new SoulRarity(0xFFFFFF);
    public static final SoulRarity Blue = new SoulRarity(0x9696FF);
    public static final SoulRarity Green = new SoulRarity(0x96FF96);
    public static final SoulRarity Orange = new SoulRarity(0xFFC896);
    public static final SoulRarity LightRed = new SoulRarity(0xFF9696);
    public static final SoulRarity Pink = new SoulRarity(0xFF96FF);
    public static final SoulRarity LightPurple = new SoulRarity(0xD2A0FF);
    public static final SoulRarity Lime = new SoulRarity(0x96FF0A);
    public static final SoulRarity Yellow = new SoulRarity(0xFFFF0A);
    public static final SoulRarity Cyan = new SoulRarity(0x05C8FF);
    public static final SoulRarity Red = new SoulRarity(0xFF2864);
    public static final SoulRarity Purple = new SoulRarity(0xB428FF);
    public static final SoulRarity Rainbow = new SoulRarity(-1);

    public static final Map<Integer, Integer> SoulData = new HashMap<>();
    public static final SoulRarity Empty = new SoulRarity(0);
    public static final Codec<SoulRarity> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.INT.fieldOf("color").forGetter(SoulRarity::color)).apply(instance, SoulRarity::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, SoulRarity> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC);

    public boolean isEmpty() {
        return this == Empty;
    }

    public int getColor() {
        return SoulData.getOrDefault(color, color);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof SoulRarity(int color1)) {
            return color == color1;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(color);
    }

    public static void tick() {
        SoulData.put(Rainbow.color(), stepDisco(SoulData.getOrDefault(Rainbow.color(), 0xFF0000)));
    }

    private static int stepDisco(int state) {
        int stage = (state >> 24) & 0xFF;
        int r = (state >> 16) & 0xFF;
        int g = (state >> 8) & 0xFF;
        int b = state & 0xFF;
        switch (stage) {
            case 0 -> { // 红 -> 黄：绿上升
                if (g < 255) g = Math.min(g + 7, 255);
                if (g == 255) { r = 255 - 7; stage = 1; }
            }
            case 1 -> { // 黄 -> 绿：红下降
                if (r > 0) r = Math.max(r - 7, 0);
                if (r == 0) { b = 7; stage = 2; }
            }
            case 2 -> { // 绿 -> 青：蓝上升
                if (b < 255) b = Math.min(b + 7, 255);
                if (b == 255) { g = 255 - 7; stage = 3; }
            }
            case 3 -> { // 青 -> 蓝：绿下降
                if (g > 0) g = Math.max(g - 7, 0);
                if (g == 0) { r = 7; stage = 4; }
            }
            case 4 -> { // 蓝 -> 紫：红上升
                if (r < 255) r = Math.min(r + 7, 255);
                if (r == 255) { b = 255 - 7; stage = 5; }
            }
            case 5 -> { // 紫 -> 红：蓝下降
                if (b > 0) b = Math.max(b - 7, 0);
                if (b == 0) stage = 0;
            }
        }
        return (stage << 24) | (r << 16) | (g << 8) | b;
    }
}

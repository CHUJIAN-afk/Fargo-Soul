package First.fargo_soul.utils;

import net.minecraft.network.chat.TextColor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.util.Optional;

public abstract class RenderUtils {

    public static float getAgeInTicks(Entity attacker, float partialTick, float speed) {
        float render = attacker.tickCount * speed;
        return Mth.lerp(partialTick, render - speed, render);
    }

    public static float getAgeInTicks(Level level, float partialTick, float speed) {
        float render = level.getGameTime() * speed;
        return Mth.lerp(partialTick, render - speed, render);
    }

    public static int getColor(String color) {
        Optional<TextColor> result = TextColor.parseColor(color).result();
        return result.map(TextColor::getValue).orElse(0xFFFFFF);
    }

}

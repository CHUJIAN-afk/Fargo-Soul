package First.fargo_soul.utils;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public abstract class RenderUtils {

    public static float getAgeInTicks(Entity attacker, float partialTick, float speed) {
        float render = attacker.tickCount * speed;
        return Mth.lerp(partialTick, render - speed, render);
    }

    public static float getAgeInTicks(Level level, float partialTick, float speed) {
        float render = level.getGameTime() * speed;
        return Mth.lerp(partialTick, render - speed, render);
    }

}

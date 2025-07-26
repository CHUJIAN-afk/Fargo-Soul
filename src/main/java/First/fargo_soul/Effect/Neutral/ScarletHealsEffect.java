package First.fargo_soul.Effect.Neutral;

import First.fargo_soul.Item.Soul.Souls;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class ScarletHealsEffect extends MobEffect {

    public ScarletHealsEffect() {
        super(MobEffectCategory.NEUTRAL, 0xFFDC143C);
    }

}

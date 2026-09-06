package first.fargo_soul.common.item.terraSoul.naturePower;

import first.fargo_soul.common.attachment.SoulTargetCache;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.base.ValueModifier;
import first.fargo_soul.common.item.base.ValueOperation;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LavaSoul extends SoulItem {

    public LavaSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(Player player) {
        if (player.tickCount % 20 == 0) {
            List<LivingEntity> enemies = SoulTargetCache.get(player).getEntitiesInRadius(player.getBoundingBox().getCenter(), 6, null);
            for (LivingEntity enemy : enemies) {
                enemy.igniteForTicks(100);
            }
        }
    }

    @Override
    public void attack(@NotNull Player attacker, @NotNull LivingEntity target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        if (target.isOnFire()) {
            modifiers.add(new ValueModifier(0.2f, ValueOperation.ADD_MULTIPLIED_TOTAL));
        }
    }

    @Override
    public void hurt(@Nullable Entity attacker, @NotNull Player target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        if (container.getSource().is(DamageTypes.LAVA)) {
            modifiers.add(new ValueModifier(-1, ValueOperation.ADD_MULTIPLIED_TOTAL));
        }
    }
}
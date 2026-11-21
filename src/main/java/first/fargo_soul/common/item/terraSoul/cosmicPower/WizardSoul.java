package first.fargo_soul.common.item.terraSoul.cosmicPower;

import first.fargo_soul.common.item.base.ValueOperation;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.base.ValueModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WizardSoul extends SoulItem {

    public WizardSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void attack(@NotNull Player attacker, @NotNull LivingEntity target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        if (container.getSource().is(Tags.DamageTypes.IS_MAGIC)) {
            modifiers.add(new ValueModifier(0.6f, ValueOperation.ADD_MULTIPLIED_BASE));
        }
    }
}

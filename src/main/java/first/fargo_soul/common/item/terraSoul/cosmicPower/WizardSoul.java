package first.fargo_soul.common.item.terraSoul.cosmicPower;

import first.fargo_soul.common.item.base.SoulItem;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WizardSoul extends SoulItem {

    public WizardSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void attack(@NotNull LivingEntity target, @NotNull LivingEntity attacker, @NotNull DamageContainer container, boolean isClient) {
        if (!isClient) {
            DamageSource source = container.getSource();
            if (source.is(Tags.DamageTypes.IS_MAGIC)) {
                container.setNewDamage(container.getNewDamage() * 1.25f);
            }
        }
    }

    @Override
    public void target(@NotNull LivingEntity target, @Nullable Entity attacker, @NotNull DamageContainer container, boolean isClient) {
        if (!isClient) {
            DamageSource source = container.getSource();
            if (source.is(Tags.DamageTypes.IS_MAGIC)) {
                RandomSource random = target.getRandom();
                container.setNewDamage(container.getNewDamage() * 0.85f - random.nextFloat() * 0.15f);
            }
        }
    }
}

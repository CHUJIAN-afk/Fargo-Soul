package first.fargo_soul.common.item.terraSoul.deathPower;

import first.fargo_soul.common.attachment.SoulTargetCache;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.base.ValueModifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class AncientShadowSoul extends SoulItem {

    public AncientShadowSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void attack(@NotNull Player attacker, @NotNull LivingEntity target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        if (attacker.getRandom().nextFloat() < 0.2f) {
            target.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 100));
        }
    }

    @Override
    public void tick(Player player) {
        List<LivingEntity> list = SoulTargetCache.get(player).getEntitiesInRadius(player.getBoundingBox().getCenter(), 8, living -> living.hasEffect(MobEffects.DARKNESS));
        for (LivingEntity living : list) {

        }
    }

    @Override
    public boolean effectApplicable(Player player, MobEffectInstance effectInstance) {
        if (effectInstance.is(MobEffects.BLINDNESS)) {
            return false;
        }
        return super.effectApplicable(player, effectInstance);
    }
}

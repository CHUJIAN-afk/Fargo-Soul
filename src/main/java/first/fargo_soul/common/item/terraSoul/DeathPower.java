package first.fargo_soul.common.item.terraSoul;

import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.base.ValueModifier;
import first.fargo_soul.common.item.base.ValueOperation;
import first.fargo_soul.register.FargoSoulMobEffectRegister;
import first.lyra.common.entity.IEntityCollision;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

import static first.fargo_soul.register.FargoSoulItemRegister.*;

public class DeathPower extends SoulItem {

    public DeathPower(Properties properties) {
        super(properties);
    }

    @Override
    public Set<SoulItem> getSoulItemList() {
        return Set.of(AncientShadowSoulItem.get(), CrystalAssassinSoulItem.get(), DarkArtistSoulItem.get(), GloomySoulItem.get(), NecromancerSoulItem.get(), NinjaSoulItem.get(), PenetratingNinjaSoulItem.get());
    }

    @Override
    public void sprintClient(Player player, List<ValueModifier> modifiers) {
        modifiers.add(new ValueModifier(0.5f, ValueOperation.ADD_MULTIPLIED_BASE));
    }

    @Override
    public void sprintCollision(Player player, List<IEntityCollision.HitContext> list, Set<LivingEntity> hits) {
        for (IEntityCollision.HitContext context : list) {
            LivingEntity living = context.entity();
            living.addEffect(new MobEffectInstance(FargoSoulMobEffectRegister.DeathMark, 200));
        }
    }

    @Override
    public void attack(@NotNull Player attacker, @NotNull LivingEntity target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        if (target.hasEffect(FargoSoulMobEffectRegister.DeathMark)) {
            modifiers.add(new ValueModifier(2, ValueOperation.ADD_MULTIPLIED_BASE));
        }
        if (target.getHealth() < target.getMaxHealth() * 0.12f) {
            modifiers.add(new ValueModifier(target.getMaxHealth() * 0.25f, ValueOperation.ADD_VALUE));
        }
    }

    @Override
    public boolean effectApplicable(Player player, MobEffectInstance effectInstance) {
        if (effectInstance.is(FargoSoulMobEffectRegister.DeathMark)) {
            return false;
        }
        return super.effectApplicable(player, effectInstance);
    }
}

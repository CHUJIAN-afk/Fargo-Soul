package first.fargo_soul.common.item.terraSoul.forestPower;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.attachment.SoulTargetCache;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.base.ValueModifier;
import first.fargo_soul.common.item.base.ValueOperation;
import first.fargo_soul.register.FargoSoulKeyRegister;
import first.fargo_soul.register.FargoSoulMobEffectRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PalmWoodSoul extends SoulItem {

    public PalmWoodSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void attack(@NotNull Player attacker, @NotNull LivingEntity target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        if (target.hasEffect(FargoSoulMobEffectRegister.Oiled)) {
            modifiers.add(new ValueModifier(0.2f, ValueOperation.ADD_MULTIPLIED_BASE));
            target.igniteForTicks(100);
        }
    }

    @Override
    public @Nullable ResourceLocation keyPressed(Player player, int key) {
        if (FargoSoulKeyRegister.PalmWoodKey.getKey().getValue() == key) {
            return FargoSoul.rl("palm_oil");
        }
        return super.keyPressed(player, key);
    }

    @Override
    public void keyHandle(Player player, ResourceLocation location) {
        if (location.equals(FargoSoul.rl("palm_oil"))) {
            List<LivingEntity> enemies = SoulTargetCache.get(player).getEntitiesInRadius(player.getBoundingBox().getCenter(), 6, null);
            for (LivingEntity enemy : enemies) {
                enemy.addEffect(new MobEffectInstance(FargoSoulMobEffectRegister.Oiled, 400));
            }
        }
    }
}
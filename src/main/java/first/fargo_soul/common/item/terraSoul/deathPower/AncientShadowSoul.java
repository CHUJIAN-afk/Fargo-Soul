package first.fargo_soul.common.item.terraSoul.deathPower;

import first.fargo_soul.common.entity.ShadowOrb;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.base.ValueModifier;
import first.fargo_soul.register.SummonerAttachmentEntityRegister;
import first.lyra.api.LyraHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/** 概率施加黑暗，免疫黑暗并维持三颗环绕暗影球。 */
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
        List<ShadowOrb> orbs = LyraHelper.get(player).getEntityData().get(SummonerAttachmentEntityRegister.SHADOW_ORB.get());
        if (orbs.isEmpty()) {
            for (int i = 0; i < 3; i++) {
                ShadowOrb entity = new ShadowOrb();
                entity.setOwner(player);
                entity.init(entity.getOrbitNode(1));
                LyraHelper.get(player).add(entity);
            }
        }
    }

    @Override
    public boolean effectApplicable(Player player, MobEffectInstance effectInstance) {
        if (effectInstance.is(MobEffects.DARKNESS)) {
            return false;
        }
        return super.effectApplicable(player, effectInstance);
    }
}

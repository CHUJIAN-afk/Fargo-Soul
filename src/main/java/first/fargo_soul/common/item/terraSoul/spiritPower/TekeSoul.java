package first.fargo_soul.common.item.terraSoul.spiritPower;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.base.ValueModifier;
import first.fargo_soul.register.FargoSoulMobEffectRegister;
import first.lyra.common.entity.AttachmentEntityDamageSource;
import first.lyra.register.LyraAttributeRegister;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TekeSoul extends SoulItem {

    public TekeSoul(Properties properties) {
        super(properties);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> map = HashMultimap.create();
        map.put(LyraAttributeRegister.SummonDamage, new AttributeModifier(FargoSoul.rl("teke_damage"), 0.35, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        return map;
    }

    @Override
    public void attack(@NotNull Player attacker, @NotNull LivingEntity target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        if (container.getSource() instanceof AttachmentEntityDamageSource) {
            target.addEffect(new MobEffectInstance(FargoSoulMobEffectRegister.TekeMist, 240));
        }
    }
}

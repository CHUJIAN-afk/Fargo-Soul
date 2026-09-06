package first.fargo_soul.common.item.terraSoul.willPower;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.base.ValueModifier;
import first.fargo_soul.common.item.base.ValueOperation;
import first.lyra.register.LyraAttributeRegister;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ValhallaKnightSoul extends SoulItem {

    public ValhallaKnightSoul(Properties properties) {
        super(properties);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> map = HashMultimap.create();
        if (player.getVehicle() != null) {
            map.put(Attributes.ARMOR, new AttributeModifier(FargoSoul.rl("valhalla_armor"), 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
            map.put(LyraAttributeRegister.HealthRegen, new AttributeModifier(FargoSoul.rl("valhalla_regen"), 2, AttributeModifier.Operation.ADD_VALUE));
        }
        return map;
    }

    @Override
    public void attack(@NotNull Player attacker, @NotNull LivingEntity target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        if (attacker.getVehicle() != null) {
            modifiers.add(new ValueModifier(0.4f, ValueOperation.ADD_MULTIPLIED_BASE));
        }
    }

    @Override
    public void healAmount(Player player, float amount, List<ValueModifier> modifiers) {
        modifiers.add(new ValueModifier(0.15f, ValueOperation.ADD_MULTIPLIED_BASE));
    }
}
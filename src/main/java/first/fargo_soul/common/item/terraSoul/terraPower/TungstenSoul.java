package first.fargo_soul.common.item.terraSoul.terraPower;

import com.google.common.collect.Multimap;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.register.FargoSoulItemRegister;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class TungstenSoul extends SoulItem {

    public TungstenSoul(Properties properties) {
        super(properties);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = super.getAttributeModifiers(player);
        modifiers.put(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(FargoSoulItemRegister.TungstenSoulItem.getId(), 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        return modifiers;
    }
}

package first.fargo_soul.common.item.terraSoul.terraPower;

import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.register.FargoSoulItemRegister;
import first.fargo_soul.utils.AttributeUtils;
import first.fargo_soul.utils.CurioUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class TungstenSoul extends SoulItem {

    public TungstenSoul(Properties properties) {
        super();
    }

    @Override
    public void tick(LivingEntity ticker, boolean isClient) {
        if (!ticker.level().isClientSide()) {
            AttributeUtils.condition(
                    ticker,
                    Attributes.ENTITY_INTERACTION_RANGE,
                    FargoSoulItemRegister.TungstenSoulItem.getId(),
                    0.5,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
                    CurioUtils.isEquipped(ticker, TungstenSoul.class)
            );
        }
    }

}

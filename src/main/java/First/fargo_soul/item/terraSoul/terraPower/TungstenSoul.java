package First.fargo_soul.item.terraSoul.terraPower;

import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.utils.AttributeUtils;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class TungstenSoul extends SoulItem {

    public TungstenSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(LivingEntity ticker) {
        if (!ticker.level().isClientSide()) {
            AttributeUtils.condition(
                    ticker,
                    Attributes.ENTITY_INTERACTION_RANGE,
                    ItemRegister.TungstenSoulItem.getId(),
                    0.5,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
                    CurioUtils.isEquipped(ticker, TungstenSoul.class)
            );
        }
    }

}

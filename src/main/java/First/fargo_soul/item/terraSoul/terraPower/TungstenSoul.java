package First.fargo_soul.item.terraSoul.terraPower;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.utils.AttributeUtils;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class TungstenSoul extends SoulItem {

    public TungstenSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.BLUE));
    }

    @EventBusSubscriber(modid = FargoSoul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Tick(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                AttributeUtils.ConditionAttributeModifier(
                        attacker,
                        Attributes.ENTITY_INTERACTION_RANGE,
                        ItemRegister.TungstenSoulItem.getId(),
                        0.5,
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
                        CurioUtils.isEquipped(attacker, TungstenSoul.class)
                );
            }
        }

    }

}

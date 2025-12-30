package First.fargo_soul.item.terraSoul.earthPower;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.EarthPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.utils.AttributeUtils;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class MithrilSoul extends SoulItem {

    public MithrilSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PINK));
    }

    @EventBusSubscriber(modid = FargoSoul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Post1(LivingDamageEvent.Post event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                if (CurioUtils.isEquipped(attacker, MithrilSoul.class)) {
                    SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(MithrilSoul.class);
                    soulInfo.setMaxCooldown(CurioUtils.isEquipped(attacker, EarthPower.class) ? 160 : 220);
                    if (soulInfo.isReady()) {
                        soulInfo.setCooldown(soulInfo.getMaxCooldown());
                    }
                    ResourceLocation resourceLocation = ItemRegister.MithrilSoulItem.getId();
                    AttributeUtils.ConditionAttributeModifier(
                            attacker,
                            Attributes.ATTACK_SPEED,
                            resourceLocation,
                            0.5,
                            AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
                            soulInfo.getCooldown() >= 100 && soulInfo.getCooldown() <= soulInfo.getMaxCooldown()
                    );
                }
            }
        }

    }

}

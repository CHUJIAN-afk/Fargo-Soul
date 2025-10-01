package First.fargo_soul.Item.Soul.TerraSoul.EarthPower.SoulStone;

import First.fargo_soul.Attachment.Attachment.SoulAbilityData;
import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Item.Soul.TerraSoul.EarthPower.EarthPower;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.SoulUtils;
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

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Post1(LivingDamageEvent.Post event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                if (SoulUtils.isEquipped(attacker, MithrilSoul.class)) {
                    SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(MithrilSoul.class);
                    soulInfo.maxCooldown = SoulUtils.isEquipped(attacker, EarthPower.class) ? 160 : 220;
                    if (soulInfo.cooldown == 0) {
                        soulInfo.cooldown = soulInfo.maxCooldown;
                    }
                    ResourceLocation resourceLocation = SoulsRegister.MithrilSoul.getId();
                    AttributeUtils.ConditionAttributeModifier(
                            attacker,
                            Attributes.ATTACK_SPEED,
                            resourceLocation,
                            0.5,
                            AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
                            soulInfo.cooldown >= 100 && soulInfo.cooldown <= soulInfo.maxCooldown
                    );
                }
            }
        }

    }

}

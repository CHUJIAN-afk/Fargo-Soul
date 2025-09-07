package First.create.CreatePower.SoulStone;

import First.create.SoulsRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.CurioUtils;
import com.simibubi.create.content.equipment.extendoGrip.ExtendoGripItem;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class GogglesSoul extends SoulItem {

    public GogglesSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.YELLOW));
    }

    public static void GogglesSoulTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof Player player) {
            ResourceLocation resourceLocation = SoulsRegister.Goggles_Soul.getId();
            Holder<Attribute> blockInteractionRange = Attributes.BLOCK_INTERACTION_RANGE;
            if (player.getAttribute(blockInteractionRange) instanceof AttributeInstance attributeInstance) {
                AttributeModifier singleRangeAttributeModifier = attributeInstance.getModifier(ExtendoGripItem.singleRangeAttributeModifier.id());
                AttributeModifier doubleRangeAttributeModifier = attributeInstance.getModifier(ExtendoGripItem.doubleRangeAttributeModifier.id());
                AttributeModifier attributeModifier = singleRangeAttributeModifier != null ? singleRangeAttributeModifier : doubleRangeAttributeModifier;
                if (attributeModifier != null && attributeInstance.getModifier(attributeModifier.id()) instanceof AttributeModifier modifier && CurioUtils.isEquipped(player, SoulsRegister.Goggles_Soul.get())) {
                    AttributeUtils.addAttributeModifier(player, blockInteractionRange, resourceLocation, modifier.amount() * 2, modifier.operation());
                } else {
                    AttributeUtils.removeAttributeModifier(player, blockInteractionRange, resourceLocation);
                }
            }
        }
    }

}

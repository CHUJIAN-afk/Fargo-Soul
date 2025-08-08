package First.fargo_soul.Compact.Create.CreatePower.SoulStone;

import First.fargo_soul.Compact.Create.CreateSoulsRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.CurioUtils;
import com.simibubi.create.content.equipment.extendoGrip.ExtendoGripItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

public class GogglesSoul extends SoulItem {

    public GogglesSoul(Properties properties) {
        super(properties);
    }

    public final List<Component> AttributeList = List.of(
            Component.literal("获得工程师护目镜的能力").withStyle(ChatFormatting.BLUE),
            Component.literal("开启工具箱的距离大幅提高").withStyle(ChatFormatting.BLUE),
            Component.literal("伸缩机械手的触及距离大幅提高").withStyle(ChatFormatting.BLUE),
            Component.literal("站在蓝图加农炮旁使它的效率大幅提高").withStyle(ChatFormatting.BLUE)
    );

    public final List<Component> TooltipList = List.of(
            Component.literal("“工程师必备”").withStyle(ChatFormatting.DARK_GRAY)
    );

    @Override
    public List<Component> getAttributeList() {
        return AttributeList;
    }

    @Override
    public List<Component> getTooltipList() {
        return TooltipList;
    }

    public static void GogglesSoulTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof Player player) {
            ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(Fargo_soul.MODID, "GogglesSoul");
            Holder<Attribute> blockInteractionRange = Attributes.BLOCK_INTERACTION_RANGE;
            if (player.getAttribute(blockInteractionRange) instanceof AttributeInstance attributeInstance) {
                AttributeModifier singleRangeAttributeModifier = attributeInstance.getModifier(ExtendoGripItem.singleRangeAttributeModifier.id());
                AttributeModifier doubleRangeAttributeModifier = attributeInstance.getModifier(ExtendoGripItem.doubleRangeAttributeModifier.id());
                AttributeModifier attributeModifier = singleRangeAttributeModifier != null ? singleRangeAttributeModifier : doubleRangeAttributeModifier;
                if (attributeModifier != null && attributeInstance.getModifier(attributeModifier.id()) instanceof AttributeModifier modifier && CurioUtils.isEquipped(player, CreateSoulsRegister.Goggles_Soul.get())) {
                    AttributeUtils.addAttributeModifier(player, blockInteractionRange, resourceLocation, modifier.amount() * 2, modifier.operation());
                } else {
                    AttributeUtils.removeAttributeModifier(player, blockInteractionRange, resourceLocation);
                }
            }
        }
    }

}

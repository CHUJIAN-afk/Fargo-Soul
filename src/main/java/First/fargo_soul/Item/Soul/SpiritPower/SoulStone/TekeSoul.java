package First.fargo_soul.Item.Soul.SpiritPower.SoulStone;

import First.fargo_soul.Attribute.AttributeRegister;
import First.fargo_soul.Item.Soul.SoulItem;
import First.fargo_soul.Item.Soul.Souls;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

public class TekeSoul extends SoulItem {

    public TekeSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("增加20%近战攻击范围与实体交互范围").withStyle(ChatFormatting.BLUE),
            Component.literal("交互你的召唤物可以使它们的攻击和击退提高至120%").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“Aku Aku!”").withStyle(ChatFormatting.DARK_GRAY)
    );

    @Override
    public List<Component> getAttributeList() {
        return this.AttributeList;
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, Item.TooltipContext context, ItemStack stack) {
        tooltips.addAll(AttributeList);
        tooltips.addAll(TooltipList);
        return tooltips;
    }

    public static void TekeSoulTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ResourceLocation resourceLocation = Souls.TekeSoul.getId();
            if (CurioUtils.isEquipped(player, Souls.TekeSoul.get())) {
                AttributeUtils.addAttributeModifier(player, Attributes.ENTITY_INTERACTION_RANGE, resourceLocation, 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
            } else {
                AttributeUtils.removeAttributeModifier(player, AttributeRegister.CriticalChance, resourceLocation);
            }
        }
    }


    public static void TekeSoulEntityInteractHandler(PlayerInteractEvent.EntityInteract event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.TekeSoul.get()) && event.getTarget() instanceof TamableAnimal animal && player.equals(animal.getOwner())) {
            ResourceLocation resourceLocation = Souls.TekeSoul.getId();
            AttributeUtils.addAttributeModifier(player, Attributes.ATTACK_DAMAGE, resourceLocation, 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
            AttributeUtils.addAttributeModifier(player, Attributes.ATTACK_KNOCKBACK, resourceLocation, 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        }
    }


}

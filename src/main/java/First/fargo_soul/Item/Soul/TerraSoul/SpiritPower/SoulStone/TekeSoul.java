package First.fargo_soul.Item.Soul.TerraSoul.SpiritPower.SoulStone;

import First.fargo_soul.Attribute.AttributeRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

public class TekeSoul extends SoulItem {

    public TekeSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.LIME));
    }

    public final List<Component> AttributeList = List.of(
            Component.translatable("item.fargo_soul.teke_soul.attribute.1").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.teke_soul.attribute.2").withStyle(ChatFormatting.BLUE)
    );

    public final List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.teke_soul.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
    );


    @Override
    public List<Component> getAttributeList() {
        return AttributeList;
    }

    @Override
    public List<Component> getTooltipList() {
        return TooltipList;
    }

    public static void TekeSoulTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ResourceLocation resourceLocation = SoulsRegister.TekeSoul.getId();
            if (CurioUtils.isEquipped(player, SoulsRegister.TekeSoul.get())) {
                AttributeUtils.addAttributeModifier(player, Attributes.ENTITY_INTERACTION_RANGE, resourceLocation, 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
            } else {
                AttributeUtils.removeAttributeModifier(player, AttributeRegister.CriticalChance, resourceLocation);
            }
        }
    }


    public static void TekeSoulEntityInteractHandler(PlayerInteractEvent.EntityInteract event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.TekeSoul.get()) && event.getTarget() instanceof TamableAnimal animal && player.equals(animal.getOwner())) {
            ResourceLocation resourceLocation = SoulsRegister.TekeSoul.getId();
            AttributeUtils.addAttributeModifier(player, Attributes.ATTACK_DAMAGE, resourceLocation, 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
            AttributeUtils.addAttributeModifier(player, Attributes.ATTACK_KNOCKBACK, resourceLocation, 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        }
    }


}

package First.fargo_soul.Item.Soul.TerraSoul.DeathPower.SoulStone;

import First.fargo_soul.Attribute.AttributeRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;


public class NinjaSoul extends SoulItem {

    public NinjaSoul(Properties properties) {
        super(properties);
    }

    public final List<Component> AttributeList = List.of(
            Component.translatable("item.fargo_soul.ninja_soul.attribute.1").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.ninja_soul.attribute.2").withStyle(ChatFormatting.BLUE)
    );

    public final List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.ninja_soul.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
    );

    @Override
    public List<Component> getAttributeList() {
        return this.AttributeList;
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
        tooltips.addAll(AttributeList);
        tooltips.addAll(TooltipList);
        return tooltips;
    }

    public static void NinjaSoulChangeTargetHandler(LivingChangeTargetEvent event) {
        if (event.getNewAboutToBeSetTarget() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.NinjaSoul.get())) {
            if (player.isShiftKeyDown() && event.getEntity() instanceof Monster monster) {
                if (monster.distanceTo(player) > 4){
                    event.setCanceled(true);
                }
            }
        }
    }

    public static void NinjaSoulTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ResourceLocation resourceLocation = SoulsRegister.NinjaSoul.getId();
            if (CurioUtils.isEquipped(player, SoulsRegister.NinjaSoul.get()) && player.isShiftKeyDown() && player.getAttribute(AttributeRegister.CriticalChance) instanceof AttributeInstance attributeInstance) {
                double amount = 0;
                if (attributeInstance.getModifier(resourceLocation) instanceof AttributeModifier modifier) {
                    amount = modifier.amount();
                }
                AttributeUtils.addAttributeModifier(player, AttributeRegister.CriticalChance, resourceLocation, amount + 0.005, AttributeModifier.Operation.ADD_VALUE);
            } else {
                AttributeUtils.removeAttributeModifier(player, AttributeRegister.CriticalChance, resourceLocation);
            }
        }
    }



}

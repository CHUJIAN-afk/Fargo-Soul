package First.fargo_soul.Item.Soul.TerraSoul.WillPower.Soulstone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

import java.util.Collection;
import java.util.List;

public class PlatinumSoul extends SoulItem {

    public PlatinumSoul(Properties properties) {
        super(properties);
    }

    public final List<Component> AttributeList = List.of(
            Component.translatable("item.fargo_soul.platinum_soul.attribute.1").withStyle(ChatFormatting.BLUE)
    );

    public final List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.platinum_soul.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
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

    public static void PlatinumSoulDropsEvent(LivingDropsEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.PlatinumSoul.get())) {
            if (event.getEntity() instanceof Monster && Utils.random.nextDouble() < 0.2 && event.getEntity() instanceof LivingEntity) {
                Collection<ItemEntity> itemEntities = event.getDrops();
                for (ItemEntity itemEntity : itemEntities) {
                    ItemStack itemStack = itemEntity.getItem();
                    itemStack.setCount(itemStack.getCount() * 5);
                }
            }
        }
    }





}

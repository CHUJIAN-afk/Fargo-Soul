package First.fargo_soul.Item.Soul.TerraSoul.TerraPower.SoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

import static First.fargo_soul.Item.Soul.SoulsRegister.IronSoul;

public class IronSoul extends SoulItem {

    public IronSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.GREEN));
    }

    public final List<Component> AttributeList = List.of(
            Component.translatable("item.fargo_soul.iron_soul.attribute.1").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.iron_soul.attribute.2").withStyle(ChatFormatting.BLUE)
    );

    public final List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.iron_soul.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
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

    public static void IronSoulPickupHandler(ItemEntityPickupEvent.Post event) {
        if (event.getPlayer() instanceof ServerPlayer player && CurioUtils.isEquipped(player, IronSoul.get())) {
            player.getPersistentData().putLong("IronSoul", player.server.getTickCount() + 100);
        }
    }

    public static void IronSoulDamageHandler2(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, IronSoul.get()) && player.getPersistentData().getLong("IronSoul") > player.server.getTickCount()) {
            event.setAmount(event.getAmount() * 0.8f);
        }
    }

    public static void IronSoulTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, IronSoul.get())) {
            List<ItemEntity> itemEntityList = player.serverLevel().getEntitiesOfClass(ItemEntity.class, player.getBoundingBox().inflate(8));
            if (itemEntityList.isEmpty()) return;
            for (ItemEntity itemEntity : itemEntityList) {
                if (!itemEntity.hasPickUpDelay()) {
                    Vec3 delta = player.position().subtract(itemEntity.position()).normalize();
                    itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add(delta));
                }
            }
        }
    }
}
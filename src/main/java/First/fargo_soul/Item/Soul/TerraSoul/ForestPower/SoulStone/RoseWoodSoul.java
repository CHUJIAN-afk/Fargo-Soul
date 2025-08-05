package First.fargo_soul.Item.Soul.TerraSoul.ForestPower.SoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

import static First.fargo_soul.Item.Soul.SoulsRegister.RosewoodSoul;

public class RoseWoodSoul extends SoulItem {

    public RoseWoodSoul(Properties properties) {
        super(properties);
    }

    public final List<Component> AttributeList = List.of(
            Component.translatable("item.fargo_soul.rose_wood_soul.attribute.1").withStyle(ChatFormatting.BLUE)
    );

    public final List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.rose_wood_soul.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
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

    public static void RosewoodSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && !player.onGround() && CurioUtils.isEquipped(player, RosewoodSoul.get())) {
            event.setAmount(event.getAmount() * 0.9f);
            if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
                livingEntity.hurt(player.damageSources().magic(), event.getAmount() * 0.5f);
                livingEntity.invulnerableTime = 0;
                Vec3 delta = player.position().subtract(livingEntity.position()).normalize();
                livingEntity.addDeltaMovement(delta);
            }
        }
    }
}

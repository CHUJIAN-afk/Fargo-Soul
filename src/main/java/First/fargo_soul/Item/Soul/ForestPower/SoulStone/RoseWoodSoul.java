package First.fargo_soul.Item.Soul.ForestPower.SoulStone;

import First.fargo_soul.Item.Soul.SoulItem;
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

import static First.fargo_soul.Item.Soul.Souls.RosewoodSoul;

public class RoseWoodSoul extends SoulItem {

    public RoseWoodSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("不在地面时受到伤害，你将获得10%伤害减免和50%的荆棘效果，并将攻击者向自身位置牵引一小段距离").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“保证钩到你”").withStyle(ChatFormatting.DARK_GRAY)
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

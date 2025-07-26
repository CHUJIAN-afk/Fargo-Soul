package First.fargo_soul.Item.Soul.WillPower.Soulstone;

import First.fargo_soul.Item.Soul.SoulItem;
import First.fargo_soul.Item.Soul.Souls;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

public class ValhallaKnightSoul extends SoulItem {

    public ValhallaKnightSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("提升骑乘坐骑的速度和跳跃高度").withStyle(ChatFormatting.BLUE),
            Component.literal("骑乘坐骑时，你与坐骑获得15点防御").withStyle(ChatFormatting.BLUE),
            Component.literal("使坐骑免疫坠落伤害并在移动时撞飞敌人").withStyle(ChatFormatting.BLUE),
            Component.literal("回复生命值时，治疗量增加15%").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“瓦尔哈拉的呼唤”").withStyle(ChatFormatting.DARK_GRAY)
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

    public static void ValhallaKnightSoulTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.ValhallaKnightSoul.get())) {
            if (player.getVehicle() instanceof LivingEntity livingEntity) {
                List<LivingEntity> entities = player.serverLevel().getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(1));
                entities.removeIf(livingEntity1 -> livingEntity1.equals(livingEntity) || livingEntity1.equals(player));
                for (LivingEntity entity : entities) {
                    double dx = entity.getX() - livingEntity.getX();
                    double dz = entity.getZ() - livingEntity.getZ();
                    float yaw = livingEntity.getYRot();
                    double lookX = -Math.sin(Math.toRadians(yaw));
                    double lookZ = Math.cos(Math.toRadians(yaw));
                    double dot = dx * lookX + dz * lookZ;
                    if (dot > 0) {
                        entity.addDeltaMovement(player.getLookAngle().scale(3));
                    }
                }
                AttributeUtils.addAttributeModifier(livingEntity, Attributes.MOVEMENT_SPEED, Souls.ValhallaKnightSoul.getId(), 0.25, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
                AttributeUtils.addAttributeModifier(livingEntity, Attributes.JUMP_STRENGTH, Souls.ValhallaKnightSoul.getId(), 0.25, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
                AttributeUtils.addAttributeModifier(livingEntity, Attributes.ARMOR, Souls.ValhallaKnightSoul.getId(), 15, AttributeModifier.Operation.ADD_VALUE);
                AttributeUtils.addAttributeModifier(player, Attributes.ARMOR, Souls.ValhallaKnightSoul.getId(), 15, AttributeModifier.Operation.ADD_VALUE);
            } else {
                AttributeUtils.removeAttributeModifier(player, Attributes.ARMOR, Souls.ValhallaKnightSoul.getId());
            }
        }
    }

    public static void ValhallaKnightSoulDamageHandler2(LivingIncomingDamageEvent event) {
        if (event.getSource().is(DamageTypes.FALL)) {
            if (event.getEntity() instanceof LivingEntity livingEntity) {
                List<Entity> entities = livingEntity.getPassengers();
                for (Entity entity : entities) {
                    if (entity instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.ValhallaKnightSoul.get())) {
                        event.setCanceled(true);
                        break;
                    }
                }
            }
            if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.ValhallaKnightSoul.get())) {
                if (player.getVehicle() != null) {
                    event.setCanceled(true);
                }
            }
        }
    }

    public static void ValhallaKnightSoulHealHandler(LivingHealEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.ValhallaKnightSoul.get())) {
            event.setAmount(event.getAmount() * 1.15f);
        }
    }

}

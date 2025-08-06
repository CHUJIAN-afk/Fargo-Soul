package First.fargo_soul.Item.Soul.TerraSoul.WillPower.Soulstone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
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
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

public class ValhallaKnightSoul extends SoulItem {

    public ValhallaKnightSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.YELLOW));
    }

    public final List<Component> AttributeList = List.of(
            Component.translatable("item.fargo_soul.valhalla_knight_soul.attribute.1").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.valhalla_knight_soul.attribute.2").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.valhalla_knight_soul.attribute.3").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.valhalla_knight_soul.attribute.4").withStyle(ChatFormatting.BLUE)
    );

    public final List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.valhalla_knight_soul.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
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
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.ValhallaKnightSoul.get())) {
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
                AttributeUtils.addAttributeModifier(livingEntity, Attributes.MOVEMENT_SPEED, SoulsRegister.ValhallaKnightSoul.getId(), 0.25, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
                AttributeUtils.addAttributeModifier(livingEntity, Attributes.JUMP_STRENGTH, SoulsRegister.ValhallaKnightSoul.getId(), 0.25, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
                AttributeUtils.addAttributeModifier(livingEntity, Attributes.ARMOR, SoulsRegister.ValhallaKnightSoul.getId(), 15, AttributeModifier.Operation.ADD_VALUE);
                AttributeUtils.addAttributeModifier(player, Attributes.ARMOR, SoulsRegister.ValhallaKnightSoul.getId(), 15, AttributeModifier.Operation.ADD_VALUE);
            } else {
                AttributeUtils.removeAttributeModifier(player, Attributes.ARMOR, SoulsRegister.ValhallaKnightSoul.getId());
            }
        }
    }

    public static void ValhallaKnightSoulDamageHandler2(LivingIncomingDamageEvent event) {
        if (event.getSource().is(DamageTypes.FALL)) {
            if (event.getEntity() instanceof LivingEntity livingEntity) {
                List<Entity> entities = livingEntity.getPassengers();
                for (Entity entity : entities) {
                    if (entity instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.ValhallaKnightSoul.get())) {
                        event.setCanceled(true);
                        break;
                    }
                }
            }
            if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.ValhallaKnightSoul.get())) {
                if (player.getVehicle() != null) {
                    event.setCanceled(true);
                }
            }
        }
    }

    public static void ValhallaKnightSoulHealHandler(LivingHealEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.ValhallaKnightSoul.get())) {
            event.setAmount(event.getAmount() * 1.15f);
        }
    }

}

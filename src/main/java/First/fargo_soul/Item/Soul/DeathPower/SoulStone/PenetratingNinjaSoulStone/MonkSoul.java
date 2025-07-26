package First.fargo_soul.Item.Soul.DeathPower.SoulStone.PenetratingNinjaSoulStone;

import First.fargo_soul.Item.Soul.DeathPower.SoulStone.PenetratingNinjaSoul;
import First.fargo_soul.Item.Soul.SoulItem;
import First.fargo_soul.Item.Soul.Souls;
import First.fargo_soul.Network.Packet.MonkSoulPacket;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.KeyUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;


public class MonkSoul extends SoulItem {

    public MonkSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("使你获得冲刺能力").withStyle(ChatFormatting.BLUE),
            Component.literal("双击前进键进行冲刺").withStyle(ChatFormatting.BLUE),
            Component.literal("冲刺后的1秒内免疫一切伤害").withStyle(ChatFormatting.BLUE),
            Component.literal("冲刺撞击敌人可对敌人造成玩家50%最大生命值的近战伤害与大量击退").withStyle(ChatFormatting.BLUE),
            Component.literal("冲刺冷却为20秒").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“返本还僧”").withStyle(ChatFormatting.DARK_GRAY)
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

    public static void MonkSoulMovementTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.MonkSoul.get())) {
            if (player.getPersistentData().getBoolean("MonkSoulDamage")) {
                List<LivingEntity> livingEntityList = player.serverLevel().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(1));
                for (LivingEntity livingEntity : livingEntityList) {
                    livingEntity.hurt(player.damageSources().playerAttack(player), player.getMaxHealth() * 0.5f);
                    livingEntity.knockback(5, player.getX(), player.getZ());
                }
            }
        }
    }

    public static void MonkSoulMovementInputHandler(MovementInputUpdateEvent event) {
        if (event.getEntity() instanceof LocalPlayer player && CurioUtils.isEquipped(player, Souls.MonkSoul.get())) {
            long MonkSoul = player.getPersistentData().getLong("MonkSoul");
            long GamaTime = player.level().getGameTime();
            if (MonkSoul < GamaTime) {
                if (KeyUtils.isDoubleTappingForward(event.getInput())) {
                    player.getPersistentData().putLong("MonkSoul", GamaTime + 400);
                    PacketDistributor.sendToServer(new MonkSoulPacket());
                    PenetratingNinjaSoul.PenetratingNinjaHandler(player);
                    Vec3 viewVector = player.getLookAngle().scale(4.0);
                    player.addDeltaMovement(viewVector);
                }
            }
        }
    }

}

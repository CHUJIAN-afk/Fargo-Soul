package First.fargo_soul.Curios.Soul.ForestPower.SoulStone;

import First.fargo_soul.Curios.SoulItem;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.ParticleUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

import static First.fargo_soul.Curios.Souls.ShadowWoodSoul;

public class ShadowWoodSoul extends SoulItem {

    public ShadowWoodSoul(Item.Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("召唤一圈鲜血光环").withStyle(ChatFormatting.BLUE),
            Component.literal("在鲜血光环内的敌人对你造成伤害的10%转化为生命值恢复").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“出奇的干净”").withStyle(ChatFormatting.DARK_GRAY)
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


    public static void ShadowWoodSoulTickHandler(PlayerTickEvent.Post event){
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, ShadowWoodSoul.get()) && player.tickCount % 60 == 0){
            ParticleUtils.spawnExpandingParticleCircle(
                    player.serverLevel(),
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    ParticleTypes.DRAGON_BREATH,
                    4,
                    40,
                    0.0f,
                    0,
                    10,
                    20
            );
        }
    }

    public static void ShadowWoodSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, ShadowWoodSoul.get()) && event.getSource().getEntity() instanceof LivingEntity livingEntity) {
            float distance = player.distanceTo(livingEntity);
            if (distance <= 4) {
                player.heal(event.getAmount() * 0.1f);
            }
        }
    }
}

package First.fargo_soul.Curios.Soul.ForestPower.SoulStone;

import First.fargo_soul.Curios.SoulItem;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.ParticleUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

import static First.fargo_soul.Curios.Souls.EbonyWoodSoul;

public class EbonyWoodSoul extends SoulItem {

    public EbonyWoodSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("你被一圈腐化光环环绕").withStyle(ChatFormatting.BLUE),
            Component.literal("光环内的敌人越多，腐化值越高，在10名敌人时达到最大值").withStyle(ChatFormatting.BLUE),
            Component.literal("根据腐化值增加至多5点固定伤害和5%伤害减免").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“潜力未完全开发”").withStyle(ChatFormatting.DARK_GRAY)
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

    public static void EbonyWoodDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, EbonyWoodSoul.get())) {
            int size = player.level().getEntitiesOfClass(Monster.class, player.getBoundingBox().inflate(10)).size();
            event.setAmount(event.getAmount() + Math.min(size / 2, 5));
        }
    }

    public static void EbonyWoodDamageHandler2(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, EbonyWoodSoul.get())) {
            int size = player.level().getEntitiesOfClass(Monster.class, player.getBoundingBox().inflate(10)).size();
            event.setAmount(event.getAmount() * ((100 - (float) Math.min(size / 2, 5)) / 100));
        }
    }

    public static void EbonyWoodTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, EbonyWoodSoul.get()) && player.tickCount % 100 == 0) {
            ParticleUtils.spawnParticleSphere(
                    player.serverLevel(),
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    ParticleTypes.SOUL,
                    10,
                    500,
                    0.0f
            );
        }
    }
}

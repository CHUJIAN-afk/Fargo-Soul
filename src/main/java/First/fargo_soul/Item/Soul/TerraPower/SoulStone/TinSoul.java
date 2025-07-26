package First.fargo_soul.Item.Soul.TerraPower.SoulStone;

import First.fargo_soul.Item.Soul.SoulItem;
import First.fargo_soul.Item.Soul.Souls;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.MathUtils;
import First.fargo_soul.Utils.ParticleUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

public class TinSoul extends SoulItem {

    public TinSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("移除跳跃暴击能力，获得锡暴击能力").withStyle(ChatFormatting.BLUE),
            Component.literal("将你的锡暴击率设为10%，锡暴击伤害设为200%").withStyle(ChatFormatting.BLUE),
            Component.literal("每次锡暴击时都会增加10%锡暴击率，锡暴击率的最大值为60%").withStyle(ChatFormatting.BLUE),
            Component.literal("受伤会使锡暴击率减半，最低为10%").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“暴击回归”").withStyle(ChatFormatting.DARK_GRAY)
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

    public static void TinSoulLivingDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.TinSoul.get())) {
            player.setOnGround(true);
            double TinSoul = player.getPersistentData().getDouble("TinSoul");
            TinSoul = Math.max(TinSoul, 0.1);
            TinSoul = Math.min(TinSoul, 0.6);
            if (MathUtils.random.nextDouble() < TinSoul && event.getEntity() instanceof LivingEntity livingEntity) {
                player.getPersistentData().putDouble("TinSoul", TinSoul + 0.1);
                event.setAmount(event.getAmount() * 2.0f);
                ParticleUtils.spawnParticleSphere(
                        player.serverLevel(),
                        livingEntity.getBoundingBox().getCenter(),
                        ParticleTypes.CRIT,
                        0.5f,
                        8,
                        0.5f,
                        0.01f
                );
            }
        }
    }

    public static void TinSoulLivingDamageHandler2(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.TinSoul.get())) {
            double TinSoul = player.getPersistentData().getDouble("TinSoul");
            TinSoul = Math.max(TinSoul, 0.1);
            TinSoul = Math.min(TinSoul, 0.6);
            player.getPersistentData().putDouble("TinSoul", TinSoul);
        }
    }

}


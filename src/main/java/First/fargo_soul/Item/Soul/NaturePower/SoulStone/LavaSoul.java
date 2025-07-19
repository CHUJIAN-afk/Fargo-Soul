package First.fargo_soul.Item.Soul.NaturePower.SoulStone;

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
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class LavaSoul extends SoulItem {
    public static final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    public LavaSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("引燃你附近的敌人").withStyle(ChatFormatting.BLUE),
            Component.literal("所有敌怪在狱火圈内时多受到20%伤害").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“他们将感受到地狱的愤怒”").withStyle(ChatFormatting.DARK_GRAY)
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

    public static void LavaSoulTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.LavaSoul.get()) && player.tickCount % 20 == 0) {
            List<LivingEntity> livingEntityList = player.serverLevel().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(5));
            livingEntityList.removeIf(livingEntity -> CurioUtils.isEquipped(livingEntity, Souls.LavaSoul.get()));
            for (LivingEntity livingEntity : livingEntityList) {
                livingEntity.setRemainingFireTicks(Math.min(livingEntity.getRemainingFireTicks() + 40, 80));
            }
            Random random = MathUtils.random;
            for (int i = 0; i < 4; i++) {
                ParticleUtils.spawnParticleLine(
                        player.serverLevel(),
                        player.position().add(4 - random.nextDouble(8), 2 - random.nextDouble(4), 4 - random.nextDouble(8)),
                        player.position().add(4 - random.nextDouble(8), 2 - random.nextDouble(4), 4 - random.nextDouble(8)),
                        ParticleTypes.FLAME,
                        10,
                        0.0f
                );
            }
        }
    }
}

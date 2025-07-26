package First.fargo_soul.Item.Soul.SpiritPower.SoulStone;

import First.fargo_soul.Item.Soul.SoulItem;
import First.fargo_soul.Item.Soul.Souls;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HolySoul extends SoulItem {

    public HolySoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("治疗效果增加40%").withStyle(ChatFormatting.BLUE),
            Component.literal("治疗时会产生冲击波，可击落附近的敌对射弹").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("《尽管放马过来》").withStyle(ChatFormatting.DARK_GRAY)
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

    public static void HolySoulHealHandler(LivingHealEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.GhostSoul.get())) {
            event.setAmount(event.getAmount() * 1.4f);
            List<AbstractArrow> arrowList = player.serverLevel().getEntitiesOfClass(AbstractArrow.class, player.getHitbox().inflate(12), abstractArrow -> !player.equals(abstractArrow.getOwner()));
            for (AbstractArrow abstractArrow : arrowList) {
                abstractArrow.setNoPhysics(true);
                abstractArrow.setDeltaMovement(0, 0, 0);
                ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                executorService.schedule(() -> {
                    if (abstractArrow.isNoPhysics()) {
                        abstractArrow.setNoPhysics(false);
                    }
                }, 500, TimeUnit.MILLISECONDS);
            }
        }
    }


}

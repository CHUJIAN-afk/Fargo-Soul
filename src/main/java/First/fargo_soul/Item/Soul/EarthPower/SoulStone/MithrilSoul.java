package First.fargo_soul.Item.Soul.EarthPower.SoulStone;

import First.fargo_soul.Item.Soul.SoulItem;
import First.fargo_soul.Item.Soul.Souls;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MithrilSoul extends SoulItem {

    public MithrilSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("在一段时间不攻击后开始攻击会短暂地提高50%攻击速度").withStyle(ChatFormatting.BLUE),
            Component.literal("使用速度加成会在攻击后3秒消失，并在5秒不攻击后恢复").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“你感觉武器的知识渗透进你的脑海中”").withStyle(ChatFormatting.DARK_GRAY)
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

    public static void MithrilSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.MithrilSoul.get())) {
            long MithrilSoulLastDamage = player.getPersistentData().getLong("MithrilSoulLastDamage");
            player.getPersistentData().putLong("MithrilSoulLastDamage", player.serverLevel().getGameTime() + 100);
            if (MithrilSoulLastDamage < player.serverLevel().getGameTime()) {
                ResourceLocation resourceLocation = Souls.MithrilSoul.getId();
                AttributeUtils.addAttributeModifier(player, Attributes.ATTACK_SPEED, resourceLocation, 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
                AttributeUtils.executorService.schedule(() -> AttributeUtils.removeAttributeModifier(player, Attributes.ATTACK_SPEED, resourceLocation), 3, TimeUnit.SECONDS);
            }
        }
    }

}

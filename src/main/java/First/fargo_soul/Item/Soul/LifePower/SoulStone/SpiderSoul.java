package First.fargo_soul.Item.Soul.LifePower.SoulStone;

import First.fargo_soul.Attribute.AttributeRegister;
import First.fargo_soul.Item.Soul.SoulItem;
import First.fargo_soul.Item.Soul.Souls;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.MathUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

public class SpiderSoul extends SoulItem {
    public SpiderSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("你的仆从可以造成暴击，仆从暴击率为25%，暴击伤害为150%").withStyle(ChatFormatting.BLUE),
            Component.literal("通过召唤暴击增加的伤害上限为100点").withStyle(ChatFormatting.BLUE),
            Component.literal("增加10%暴击率").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“蜘蛛恐惧者？作为惩罚，让他被蜘蛛干掉吧！”").withStyle(ChatFormatting.DARK_GRAY)
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

    public static void SpiderSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof TamableAnimal animal) {
            if (animal.getOwner() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.SpiderSoul.get())) {
                if (MathUtils.random.nextDouble() < 0.25) {
                    float damage = event.getAmount();
                    event.setAmount(damage + Math.min(damage * 0.5f, 100));
                }
            }
        }
    }

    public static void SpiderSoulTickHnadler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ResourceLocation resourceLocation = Souls.AdamantiteSoul.getId();
            if (CurioUtils.isEquipped(player, Souls.SpiderSoul.get())) {
                AttributeUtils.addAttributeModifier(player, AttributeRegister.CriticalChance, resourceLocation, 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
            } else {
                AttributeUtils.removeAttributeModifier(player, AttributeRegister.CriticalChance, resourceLocation);
            }
        }
    }




}

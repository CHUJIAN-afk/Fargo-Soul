package First.fargo_soul.Item.Soul.NaturePower.SoulStone;

import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Item.Soul.SoulItem;
import First.fargo_soul.Item.Soul.Souls;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

public class CrimsonSoul extends SoulItem {

    public CrimsonSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("受到伤害后，给予你猩红治愈增益，若7秒内不再受击，则恢复此次受击受到伤害的一半").withStyle(ChatFormatting.BLUE),
            Component.literal("如果你在完成恢复前再次受伤，移除猩红治愈，并且生命恢复效果不会叠加").withStyle(ChatFormatting.BLUE),
            Component.literal("不会对低于10点的伤害生效，包括受击与完成恢复前的再次受伤").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“你从敌人的鲜血中重生”").withStyle(ChatFormatting.DARK_GRAY)
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

    public static void CrimsonSoulDamageHandler2(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.CrimsonSoul.get()) && event.getAmount() > 10) {
            if (player.getEffect(EffectRegister.ScarletHeals) == null) {
                player.getPersistentData().putFloat("CrimsonSoul", event.getAmount() * 0.5f);
                player.addEffect(new MobEffectInstance(EffectRegister.ScarletHeals, 140));
            } else {
                player.getPersistentData().remove("CrimsonSoul");
                player.removeEffect(EffectRegister.ScarletHeals);
            }
        }
    }




}

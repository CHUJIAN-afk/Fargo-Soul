package First.fargo_soul.Item.Soul.TerraSoul.ForestPower.SoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

import static First.fargo_soul.Item.Soul.SoulsRegister.PalmWoodSoul;

public class PalmWoodSoul extends SoulItem {

    public PalmWoodSoul(Properties properties) {
        super(properties);
    }

    public final List<Component> AttributeList = List.of(
            Component.literal("攻击时点燃敌人，如果敌人已经燃烧则延长燃烧时间，最大延长至30秒").withStyle(ChatFormatting.BLUE),
            Component.literal("被你攻击的敌人在5秒内受到的火焰伤害+100%").withStyle(ChatFormatting.BLUE)
    );

    public final List<Component> TooltipList = List.of(
            Component.literal("“出奇的宁静”").withStyle(ChatFormatting.DARK_GRAY)
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

    public static void PalmWoodDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, PalmWoodSoul.get())) {
                int remainingFireTicks = livingEntity.getRemainingFireTicks() + 40;
                remainingFireTicks = Math.min(remainingFireTicks, 600);
                livingEntity.setRemainingFireTicks(remainingFireTicks);
                livingEntity.getPersistentData().putLong("PalmWoodSoul", player.server.getTickCount() + 100);
            }
            if (event.getSource().is(DamageTypes.ON_FIRE)) {
                if (livingEntity.getServer() != null && livingEntity.getPersistentData().getLong("PalmWoodSoul") > livingEntity.getServer().getTickCount()) {
                    event.setAmount(event.getAmount() * 2.0f);
                }
            }
        }
    }
}

package First.fargo_soul.Item.Soul.LifePower.SoulStone;

import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Item.Soul.SoulItem;
import First.fargo_soul.Item.Soul.Souls;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

public class BeetleSoul extends SoulItem {

    public BeetleSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("对敌人造成伤害时会获得甲虫力量，增加你的伤害和速度").withStyle(ChatFormatting.BLUE),
            Component.literal("受到伤害时会获得甲虫耐力，在十秒内减少你受到的伤害").withStyle(ChatFormatting.BLUE),
            Component.literal("甲虫力量每层提供10%伤害和攻击速度加成").withStyle(ChatFormatting.BLUE),
            Component.literal("甲虫耐力每层提供15%伤害减免").withStyle(ChatFormatting.BLUE),
            Component.literal("甲虫增益至多叠加两层，刷新时重置持续时间并提高层数").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“你的血管里流淌着看不见的粪便生命”").withStyle(ChatFormatting.DARK_GRAY)
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

    public static void BeetleSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.BeetleSoul.get())) {
            if (player.getEffect(EffectRegister.BeetleMight) instanceof MobEffectInstance mobEffectInstance) {
                if (mobEffectInstance.getAmplifier() < 2) {
                    player.addEffect(new MobEffectInstance(EffectRegister.BeetleMight, 200, 1));
                }
            } else {
                player.addEffect(new MobEffectInstance(EffectRegister.BeetleMight, 200));
            }
        }
    }

    public static void BeetleSoulDamageHandler2(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.BeetleSoul.get())) {
            if (player.getEffect(EffectRegister.BeetleEndurance) instanceof MobEffectInstance mobEffectInstance) {
                if (mobEffectInstance.getAmplifier() < 2) {
                    player.addEffect(new MobEffectInstance(EffectRegister.BeetleEndurance, 200, 1));
                }
            } else {
                player.addEffect(new MobEffectInstance(EffectRegister.BeetleEndurance, 200));
            }
        }
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            if (livingEntity.getEffect(EffectRegister.BeetleEndurance) instanceof MobEffectInstance mobEffectInstance) {
                event.setAmount(event.getAmount() * (1 - ((mobEffectInstance.getAmplifier() + 1) * 0.15f)));
            }
        }
    }


}

package First.fargo_soul.Curios.Soul.EarthPower.SoulStone;

import First.fargo_soul.Curios.SoulItem;
import First.fargo_soul.Curios.Souls;
import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.MathUtils;
import First.fargo_soul.Utils.ParticleUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;
import java.util.Random;

public class OrichalcumSoul extends SoulItem {

    public OrichalcumSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("攻击时召唤花瓣落到你攻击的敌人的身上，造成额外伤害和山铜中毒减益").withStyle(ChatFormatting.BLUE),
            Component.literal("山铜中毒会持续造成魔法伤害").withStyle(ChatFormatting.BLUE),
            Component.literal("敌人在被山铜中毒影响时，受到的所有毒性伤害+250%").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“自然祝福着你”").withStyle(ChatFormatting.DARK_GRAY)
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

    public static void OrichalcumSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.OrichalcumSoul.get()) && event.getEntity() instanceof LivingEntity livingEntity) {
            if (event.getSource().getMsgId().contains("poison") && livingEntity.getEffect(EffectRegister.OrichalcumPoisoning) != null) {
                event.setAmount(event.getAmount() * 3.5f);
            }
            livingEntity.hurt(player.damageSources().magic(), event.getAmount() * 0.05f);
            livingEntity.addEffect(new MobEffectInstance(EffectRegister.OrichalcumPoisoning));
            Random random = MathUtils.random;
            ParticleUtils.spawnParticleLine(
                    player.serverLevel(),
                    livingEntity.getBoundingBox().getCenter().add((1 - random.nextDouble(4)), (1 - random.nextDouble(2)), (1 - random.nextDouble(4))),
                    livingEntity.getBoundingBox().getCenter(),
                    ParticleTypes.CHERRY_LEAVES,
                    20,
                    0.1f
            );
        }
    }

}

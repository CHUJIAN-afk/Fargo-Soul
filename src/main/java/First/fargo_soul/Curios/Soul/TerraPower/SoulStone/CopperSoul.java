package First.fargo_soul.Curios.Soul.TerraPower.SoulStone;

import First.fargo_soul.Curios.SoulItem;
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

import static First.fargo_soul.Curios.Souls.CopperSoul;

public class CopperSoul extends SoulItem {

    public CopperSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("攻击有概率释放闪电攻击敌人，闪电基础伤害为攻击的75%，此效果有5秒冷却").withStyle(ChatFormatting.BLUE),
            Component.literal("闪电可以连锁攻击附近至多5名其他敌人，但伤害衰减50%").withStyle(ChatFormatting.BLUE),
            Component.literal("命中有雨中或水中的敌人时释放闪电的概率增加").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“它的音乐还是电音”").withStyle(ChatFormatting.DARK_GRAY)
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

    public static void CopperSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, CopperSoul.get()) && event.getEntity() instanceof LivingEntity livingEntity) {
            long serverTickCount = player.server.getTickCount();
            long LastCopperSoul = player.getPersistentData().getLong("LastCopperSoul");
            double random = 0.1;
            if ((player.level().canSeeSky(livingEntity.blockPosition()) && player.level().isRaining() || livingEntity.isInWater())) {
                random = 0.2;
            }
            if (serverTickCount > LastCopperSoul && MathUtils.random.nextDouble() < random) {
                player.getPersistentData().putLong("LastCopperSoul", serverTickCount + 100);
                ParticleUtils.spawnParticleLine(
                        player.serverLevel(),
                        player.getEyePosition().add(0, 0.5, 0),
                        livingEntity.getEyePosition(),
                        ParticleTypes.ELECTRIC_SPARK,
                        100,
                        0.0f
                );
                livingEntity.hurt(player.damageSources().lightningBolt(), event.getAmount() * 0.5f);
                List<LivingEntity> livingEntityList = livingEntity.level().getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(5));
                livingEntityList.removeIf(livingEntity1 -> livingEntity1.equals(player));
                if (livingEntityList.isEmpty()) return;
                for (int i = 0; i < Math.min(livingEntityList.size(), 5); i++) {
                    LivingEntity livingEntity1 = livingEntityList.get(i);
                    livingEntity1.hurt(player.damageSources().lightningBolt(), event.getAmount() * 0.25f);
                    ParticleUtils.spawnParticleLine(
                            player.serverLevel(),
                            livingEntity.getEyePosition().add(0, 0.5, 0),
                            livingEntity1.getEyePosition(),
                            ParticleTypes.ELECTRIC_SPARK,
                            50,
                            0.1f
                    );
                }
            }
        }
    }


}

package First.fargo_soul.Item.Soul.ForestPower.SoulStone;

import First.fargo_soul.Item.Soul.SoulItem;
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

import static First.fargo_soul.Item.Soul.Souls.PearlWoodSoul;

public class PearlWoodSoul extends SoulItem {

    public PearlWoodSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.translatable("item.fargo_soul.pearl_wood_soul.attribute.1").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.pearl_wood_soul.attribute.2").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.pearl_wood_soul.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
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

    public static void PearlWoodDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, PearlWoodSoul.get())) {
            if (event.getEntity() instanceof LivingEntity livingEntity && MathUtils.random.nextDouble() < 0.1) {
                event.setAmount(event.getAmount() * 1.5f);
                List<LivingEntity> monsterList = livingEntity.level().getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(10), monster -> !monster.equals(livingEntity));
                monsterList.removeIf(livingEntity1 -> livingEntity1.equals(player));
                if (!monsterList.isEmpty()) {
                    LivingEntity monster = monsterList.get(MathUtils.random.nextInt(monsterList.size()));
                    monster.hurt(player.damageSources().magic(), event.getAmount());
                    monster.invulnerableTime = 0;
                    ParticleUtils.spawnParticleLine(
                            player.serverLevel(),
                            livingEntity.getEyePosition(),
                            monster.getEyePosition(),
                            ParticleTypes.ENCHANT,
                            5,
                            0.1f
                    );
                    player.heal(player.getMaxHealth() * 0.05f);
                }
            }
        }
    }
}

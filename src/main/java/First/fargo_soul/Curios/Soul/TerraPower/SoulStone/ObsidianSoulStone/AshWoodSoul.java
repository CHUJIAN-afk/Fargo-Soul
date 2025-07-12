package First.fargo_soul.Curios.Soul.TerraPower.SoulStone.ObsidianSoulStone;

import First.fargo_soul.Curios.SoulItem;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.MathUtils;
import First.fargo_soul.Utils.ParticleUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

import static First.fargo_soul.Curios.Souls.AshWoodSoul;
import static First.fargo_soul.Utils.MathUtils.random;

public class AshWoodSoul extends SoulItem {

    public AshWoodSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("定期产生火球攻击附近敌人").withStyle(ChatFormatting.BLUE),
            Component.literal("极大降低岩浆的接触伤害").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“你告诉我，这不是木头？”").withStyle(ChatFormatting.DARK_GRAY)
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

    public static void AshWoodSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, AshWoodSoul.get())) {
            if (event.getSource().is(DamageTypes.LAVA)) {
                event.setAmount(event.getAmount() * 0.25f);
            }
        }
    }

    public static void AshWoodSoulTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, AshWoodSoul.get()) && player.tickCount % 40 == 0) {
            TargetingConditions conditions = TargetingConditions.forCombat().range(30.0);
            LivingEntity monster = player.level().getNearestEntity(LivingEntity.class, conditions, player, player.getX(), player.getY(), player.getZ(), player.getBoundingBox().inflate(10));
            if (monster != null) {
                double rand = 0.5 - MathUtils.random.nextDouble(1);
                Vec3 toMonster = monster.position().add(rand * MathUtils.random.nextDouble(), rand * MathUtils.random.nextDouble() - 1, rand * MathUtils.random.nextDouble()).subtract(player.getX(), player.getY(), player.getZ()).normalize();
                double x = player.getX() + (1 - random.nextDouble());
                double y = player.getY() + 2;
                double z = player.getZ() + (1 - random.nextDouble());
                SmallFireball fireball = new SmallFireball(
                        player.level(),
                        x,
                        y,
                        z,
                        toMonster
                );
                fireball.setOwner(player);
                player.level().addFreshEntity(fireball);
                ParticleUtils.spawnParticleSphere(
                        player.serverLevel(),
                        x,
                        y,
                        z,
                        ParticleTypes.LAVA,
                        0.2f,
                        5,
                        0.2f
                );
            }
        }
    }


}

package First.fargo_soul.Item.Soul.TerraSoul.TerraPower.SoulStone.ObsidianSoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.ParticleUtils;
import First.fargo_soul.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

import static First.fargo_soul.Item.Soul.SoulsRegister.AshWoodSoul;
import static First.fargo_soul.Utils.Utils.random;

public class AshWoodSoul extends SoulItem {

    public AshWoodSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.GREEN));
    }

    public final List<Component> AttributeList = List.of(
            Component.translatable("item.fargo_soul.ash_wood_soul.attribute.1").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.ash_wood_soul.attribute.2").withStyle(ChatFormatting.BLUE)
    );

    public final List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.ash_wood_soul.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
    );


    @Override
    public List<Component> getAttributeList() {
        return AttributeList;
    }

    @Override
    public List<Component> getTooltipList() {
        return TooltipList;
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
                double rand = 0.5 - Utils.random.nextDouble(1);
                Vec3 toMonster = monster.position().add(rand * Utils.random.nextDouble(), rand * Utils.random.nextDouble() - 1, rand * Utils.random.nextDouble()).subtract(player.getX(), player.getY(), player.getZ()).normalize();
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

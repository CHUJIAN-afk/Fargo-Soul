package First.fargo_soul.Item.Soul.TerraSoul.ForestPower.SoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.ParticleUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

import static First.fargo_soul.Item.Soul.SoulsRegister.PineWoodSoul;
import static First.fargo_soul.Utils.Utils.random;

public class PineWoodSoul extends SoulItem {

    public PineWoodSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.GREEN));
    }

    public final List<Component> AttributeList = List.of(
            Component.translatable("item.fargo_soul.pine_wood_soul.attribute.1").withStyle(ChatFormatting.BLUE)
    );

    public final List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.pine_wood_soul.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
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

    public static void PineWoodSoulTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, PineWoodSoul.get()) && player.tickCount % 20 == 0) {
            TargetingConditions conditions = TargetingConditions.forCombat().range(30.0);
            LivingEntity target = player.level().getNearestEntity(LivingEntity.class, conditions, player, player.getX(), player.getY(), player.getZ(), player.getBoundingBox().inflate(10));
            if (target != null) {
                double x = player.getX() + (1 - random.nextDouble());
                double y = player.getY() + 2;
                double z = player.getZ() + (1 - random.nextDouble());
                Snowball snowball = new Snowball(player.level(), x, y, z);
                double rand = 0.5 - random.nextDouble(1);
                Vec3 toMonster = target.position().add(rand * random.nextDouble(), rand * random.nextDouble() - 1, rand * random.nextDouble()).subtract(player.getX(), player.getY(), player.getZ()).normalize();
                snowball.shoot(toMonster.x, toMonster.y, toMonster.z, 2.0f, 1.0f);
                snowball.setOwner(player);
                player.level().addFreshEntity(snowball);
                ParticleUtils.spawnParticleSphere(
                        player.serverLevel(),
                        x,
                        y,
                        z,
                        ParticleTypes.ITEM_SNOWBALL,
                        0.2f,
                        10,
                        0.5f
                );
            }
        }
    }
}
